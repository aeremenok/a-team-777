package talkie.process;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import talkie.Client;
import talkie.constants.Talkie;

public class ClientListener
    implements
        Runnable
{
    private final Client   client;

    private DatagramSocket socket;

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

                String msg = new String( inPacket.getData() );
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

    private void processMsg(
        String msg )
    {
        // todo Auto-generated method stub
    }
}
