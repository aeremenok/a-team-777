package talkie.server.process;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.StringTokenizer;

import talkie.common.constants.Message;
import talkie.common.constants.Status;
import talkie.common.constants.Talkie;
import talkie.server.Server;
import talkie.server.data.User;

public class ClientHandler
    implements
        Runnable
{
    private DatagramSocket socket          = null;
    private InetAddress    clientAddress   = null;
    private int            clientPort      = -1;
    private byte[]         firstPacketData = null;
    private Server         server          = null;
    private boolean        work            = true;
    private User           user            = null;

    public ClientHandler(
        Server server,
        DatagramPacket inPacket )
        throws SocketException
    {
        this.server = server;
        this.socket = new DatagramSocket();
        this.clientAddress = inPacket.getAddress();
        this.clientPort = inPacket.getPort();
        this.firstPacketData = inPacket.getData();

        this.socket.setSoTimeout( Talkie.UDP_TIMEOUT );

        processPacket( inPacket );
    }

    public void run()
    {
        while ( work )
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
                work = false;
                socket.close();
                synchronized ( user )
                {
                    user.setStatus( Status.AWAY );
                }
            }

            processPacket( inPacket );
        }
    }

    private void doLogin(
        StringTokenizer tokenizer )
    {
        boolean success = false;
        String login = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String pass = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        System.out.println( "login attempt detected, login='" + login + "', pass='" + pass + "'" );

        synchronized ( server )
        {
            // user = server.getUsers().get( login );
        }

        synchronized ( user )
        {
            if ( user != null && pass.equals( user.getPass() ) )
            {
                success = true;
                user.setStatus( Status.ONLINE );
            }
        }

        String outMsg = "" + success + " ";
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
    }

    private void processPacket(
        DatagramPacket packet )
    {
        String inMsg = new String( packet.getData() );

        StringTokenizer tokenizer = new StringTokenizer( inMsg, " " );
        if ( tokenizer.countTokens() > 0 )
        {
            String operation = tokenizer.nextToken();

            if ( Message.LOGIN.equalsIgnoreCase( operation ) )
            {
                doLogin( tokenizer );
            }
            else if ( Message.LIST.equalsIgnoreCase( operation ) )
            {
                // todo
            }
            else if ( Message.MESSAGE.equalsIgnoreCase( operation ) )
            {
                // todo
            }
        }
    }
}
