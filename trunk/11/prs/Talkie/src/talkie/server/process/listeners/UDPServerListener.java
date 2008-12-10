package talkie.server.process.listeners;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import talkie.common.constants.Message;
import talkie.common.constants.Status;
import talkie.common.constants.Talkie;
import talkie.server.Server;
import talkie.server.data.User;

public class UDPServerListener
    extends ServerListener
{
    /**
     * через 5 минут молчания клиент считается отвалившимся
     */
    public static final int UDP_TIMEOUT   = 300000;
    private DatagramSocket  socket        = null;
    private InetAddress     clientAddress = null;
    private int             clientPort    = -1;
    private Server          server        = null;
    private boolean         valid         = true;
    private User            user          = null;

    public UDPServerListener(
        Server server,
        DatagramPacket inPacket )
        throws SocketException
    {
        super( server );

        this.socket = new DatagramSocket();
        this.clientAddress = inPacket.getAddress();
        this.clientPort = inPacket.getPort();
        this.socket.setSoTimeout( UDP_TIMEOUT );

        String first = new String( inPacket.getData(), 0, inPacket.getLength() );
        processMessage( first );
    }

    @Override
    public void logout()
    {
        byte[] logout = Message.LOGOUT.getBytes();
        DatagramPacket outP = new DatagramPacket( logout, logout.length, clientAddress, clientPort );
        try
        {
            socket.send( outP );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }

    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() && valid )
        {
            byte[] data = new byte[Talkie.MSG_SIZE];
            DatagramPacket inPacket = new DatagramPacket( data, data.length );

            try
            {
                socket.receive( inPacket );
            }
            catch ( IOException e )
            {
                // клиент отвалился
                socket.close();
                synchronized ( user )
                {
                    user.setStatus( Status.AWAY );
                }
                Thread.currentThread().interrupt();
            }

            String message = new String( inPacket.getData(), 0, inPacket.getLength() );
            processMessage( message );
        }
    }

    private boolean login(
        String login,
        String pass )
    {
        valid = false;

        user = server.getUsers().get( login );

        if ( user != null && pass.equals( user.getPass() ) && user.getStatus() != Status.ONLINE )
        {
            synchronized ( user )
            {
                valid = true;
                user.setStatus( Status.ONLINE );
                user.setHandler( this );
            }
        }

        String outMsg = String.valueOf( valid );
        byte[] outBuf = outMsg.getBytes();
        DatagramPacket outPacket = new DatagramPacket( outBuf, outBuf.length, clientAddress, clientPort );

        synchronized ( socket )
        {
            try
            {
                socket.send( outPacket );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        return valid;
    }

    private void processMessage(
        String message )
    {
        StringTokenizer tokenizer = new StringTokenizer( message, " " );
        if ( tokenizer.countTokens() > 0 )
        {
            String operation = tokenizer.nextToken();

            if ( Message.LOGIN.equalsIgnoreCase( operation ) )
            {
                String login = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
                String pass = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
                boolean yes = login( login, pass );
                if ( yes )
                {
                    String date = DateFormat.getDateTimeInstance().format( new Date() );
                    HashMap<String, User> users = server.getUsers();
                    for ( String key : users.keySet() )
                    {
                        User u = users.get( key );
                        if ( u.getStatus() == Status.ONLINE )
                        {
                            u.getHandler()
                                .sendMessage( "[" + date + "] В чат приходит пользователь " + user.getLogin() );
                        }
                    }
                }
            }
            else if ( Message.LIST.equalsIgnoreCase( operation ) )
            {
                String toSend = "Сейчас в чате: " + user.getLogin();
                HashMap<String, User> users = server.getUsers();
                for ( String key : users.keySet() )
                {
                    User u = users.get( key );
                    if ( u.getStatus() == Status.ONLINE && u != user )
                    {
                        toSend += ", " + u.getLogin();
                    }
                }
                sendMessage( toSend );
            }
            else if ( Message.MESSAGE.equalsIgnoreCase( operation ) )
            {
                HashMap<String, User> users = server.getUsers();
                String date = DateFormat.getDateTimeInstance().format( new Date() );
                for ( String key : users.keySet() )
                {
                    User u = users.get( key );
                    if ( u.getStatus() == Status.ONLINE )
                    {
                        String toSend = "[" + date + "] " + user.getLogin() + " говорит: " + message.substring( 3 );
                        u.getHandler().sendMessage( toSend );
                    }
                }
            }
            else if ( Message.LOGOUT.equalsIgnoreCase( operation ) )
            {
                synchronized ( user )
                {
                    user.setHandler( null );
                    user.setStatus( Status.AWAY );
                }
                String date = DateFormat.getDateTimeInstance().format( new Date() );
                HashMap<String, User> users = server.getUsers();
                for ( String key : users.keySet() )
                {

                    User u = users.get( key );
                    if ( u.getStatus() == Status.ONLINE )
                    {
                        u.getHandler().sendMessage( "[" + date + "] Пользователь " + user.getLogin() + " покинул чат!" );
                    }
                }
            }
        }
    }

    private void sendMessage(
        String toSend )
    {
        byte[] buf = toSend.getBytes();
        DatagramPacket outP = new DatagramPacket( buf, buf.length, clientAddress, clientPort );
        try
        {
            socket.send( outP );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
