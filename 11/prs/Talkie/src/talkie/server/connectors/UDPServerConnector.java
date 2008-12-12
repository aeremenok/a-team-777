package talkie.server.connectors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import talkie.common.constants.Status;
import talkie.common.constants.Talkie;
import talkie.server.Server;

public class UDPServerConnector
    extends ServerConnector
{
    /**
     * через 5 минут молчания клиент считается отвалившимся
     */
    public static final int UDP_TIMEOUT   = 300000;
    private DatagramSocket  socket        = null;
    private InetAddress     clientAddress = null;
    private int             clientPort    = -1;

    public UDPServerConnector(
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
        process( first );
    }

    @Override
    public void close()
    {
        super.close();
        socket.close();
    }

    @Override
    protected void mainLoopStep()
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
            close();
            synchronized ( user )
            {
                user.setStatus( Status.AWAY );
            }
            Thread.currentThread().interrupt();
        }

        String message = new String( inPacket.getData(), 0, inPacket.getLength() );
        process( message );
    }

    @Override
    protected void send(
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
