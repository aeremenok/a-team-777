package talkie.client.process;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import talkie.client.Client;
import talkie.client.connect.Connection;
import talkie.common.constants.Talkie;

public class ClientListener
    implements
        Runnable
{
    private final Client   client;
    private DatagramSocket socket;
    private String         login;
    private String         pass;

    public ClientListener(
        Client client )
    {
        this.client = client;

        try
        {
            socket = new DatagramSocket();
        }
        catch ( SocketException e )
        {
            System.out.println( "Unable to start listener for client" );
            e.printStackTrace();
            System.exit( 0 );
        }
    }

    public boolean attemptToLogin()
    {
        Connection connection = client.getConnection();
        client.getLoginDialog().lock();

        boolean success = connection.login( login, pass );

        if ( success )
        {
            client.getLoginDialog().unlock();
            client.getLoginDialog().setVisible( false );
            client.setTitle( login );
            client.display();
            return true;
        }
        else
        {
            client.getLoginDialog().unlock();
            client.getLoginDialog().getFailedLabel().setVisible( true );
            return false;
        }
    }

    public DatagramSocket getSocket()
    {
        return socket;
    }

    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() )
        {
            try
            {
                byte[] data = new byte[Talkie.MSG_SIZE];
                DatagramPacket inPacket = new DatagramPacket( data, data.length );
                socket.receive( inPacket );

                String msg = new String( inPacket.getData(), 0, inPacket.getLength() );
                synchronized ( client )
                {
                    processMsg( msg );
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public void setLoginAndPass(
        String login,
        String pass )
    {
        this.login = login;
        this.pass = pass;
    }

    private void processMsg(
        String msg )
    {
        // todo Auto-generated method stub
    }
}
