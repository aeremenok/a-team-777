package talkie.connect;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import talkie.Talkie;

/**
 * Соединение, основанное на UDP пакетах
 * 
 * @author ssv
 */
public class UDPConnection
    extends Connection
{
    private InetAddress    inetAddress;
    private DatagramSocket socket;
    private int            port;

    public UDPConnection(
        String host,
        int port )
        throws IOException
    {
        DatagramSocket socket;
        this.socket = new DatagramSocket();
        inetAddress = InetAddress.getByName( host );
        this.port = port;
    }

    @Override
    public void send(
        byte[] bytes )
    {
        DatagramPacket packet = new DatagramPacket( bytes, bytes.length, inetAddress, port );
        try
        {
            socket.send( packet );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    protected String receive()
    {
        byte[] data = new byte[Talkie.MSG_SIZE];
        DatagramPacket packet = new DatagramPacket( data, data.length );
        try
        {
            int soTimeout = socket.getSoTimeout();
            socket.setSoTimeout( 5000 );
            socket.receive( packet );
            socket.setSoTimeout( soTimeout );
            data = packet.getData();
            return new String( data );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }
}
