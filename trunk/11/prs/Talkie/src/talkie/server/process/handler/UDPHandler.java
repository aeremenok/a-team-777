package talkie.server.process.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.StringTokenizer;

import talkie.common.constants.Message;
import talkie.common.constants.Status;
import talkie.common.constants.Talkie;
import talkie.server.Server;
import talkie.server.data.User;

public class UDPHandler
    implements
        Runnable
{
    /**
     * время в миллисекундах, по истечении которого UDP-клиент будет считаться "отвалившимся" и сервер на него забьёт
     */
    public static final int UDP_TIMEOUT     = 300000;

    private DatagramSocket  socket          = null;
    private InetAddress     clientAddress   = null;
    private int             clientPort      = -1;
    private byte[]          firstPacketData = null;
    private Server          server          = null;
    private boolean         work            = true;
    private User            user            = null;

    public UDPHandler(
        Server server,
        DatagramPacket inPacket )
        throws SocketException
    {
        this.server = server;
        this.socket = new DatagramSocket();
        this.clientAddress = inPacket.getAddress();
        this.clientPort = inPacket.getPort();
        this.firstPacketData = inPacket.getData();

        this.socket.setSoTimeout( UDP_TIMEOUT );

        processPacket( inPacket );
    }

    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() )
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

            processPacket( inPacket );
        }
    }

    private boolean doLogin(
        StringTokenizer tokenizer )
    {
        boolean success = false;
        String login = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String pass = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        System.out.println( "login attempt detected, login='" + login + "', pass='" + pass + "'" );

        synchronized ( server )
        {
            user = server.getUsers().get( login );
        }

        synchronized ( user )
        {
            if ( user != null && pass.equals( user.getPass() ) )
            {
                success = true;
                user.setStatus( Status.ONLINE );
                user.setHandler( this );
            }
        }

        String outMsg = String.valueOf( success );
        System.out.println( "\tvalid: " + outMsg.toUpperCase() );

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

        if ( !success )
        {
            socket.close();
            Thread.currentThread().interrupt();
        }

        return success;
    }

    private void processPacket(
        DatagramPacket packet )
    {
        String inMsg = new String( packet.getData(), 0, packet.getLength() );

        StringTokenizer tokenizer = new StringTokenizer( inMsg, " " );
        if ( tokenizer.countTokens() > 0 )
        {
            String operation = tokenizer.nextToken();

            if ( Message.LOGIN.equalsIgnoreCase( operation ) )
            {
                boolean yes = doLogin( tokenizer );
                if ( yes )
                {
                    HashMap<String, User> users = server.getUsers();
                    for ( String key : users.keySet() )
                    {
                        User u = users.get( key );
                        if ( u.getStatus() == Status.ONLINE )
                        {
                            u.getHandler().sendMessage( "В чат приходит пользователь " + user.getLogin() );
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
                for ( String key : users.keySet() )
                {
                    User u = users.get( key );
                    if ( u.getStatus() == Status.ONLINE )
                    {
                        String toSend = u.getLogin() + " говорит:\n" + inMsg.substring( 2 );
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
                    HashMap<String, User> users = server.getUsers();
                    for ( String key : users.keySet() )
                    {
                        User u = users.get( key );
                        if ( u.getStatus() == Status.ONLINE )
                        {
                            u.getHandler().sendMessage( "Пользователь " + user.getLogin() + " покинул чат!" );
                        }
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
        synchronized ( socket )
        {
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
}
