package talkie.client.speakers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import talkie.common.constants.Talkie;

/**
 * Соединение, основанное на UDP пакетах
 * 
 * @author ssv
 */
public class UDPSpeaker
    extends ClientSpeaker
{
    /**
     * клиентский сокет
     */
    private DatagramSocket socket        = null;
    /**
     * адрес сервера после установки "соединения"
     */
    protected InetAddress  serverAddress = null;
    /**
     * порт сервера после установки "соединения"
     */
    protected int          serverPort    = -1;

    public UDPSpeaker(
        String host,
        int port )
        throws IOException
    {
        this.socket = new DatagramSocket();
    }

    @Override
    public void close()
    {
        if ( socket != null )
        {
            socket.close();
        }
    }

    @Override
    public void send(
        String message )
    {
        byte[] bytes = message.getBytes();
        DatagramPacket packet = new DatagramPacket( bytes, bytes.length, serverAddress, serverPort );
        try
        {
            socket.send( packet );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void setAddress(
        InetAddress address )
    {
        this.serverAddress = address;
    }

    public void setPort(
        int port )
    {
        this.serverPort = port;
    }

    @Override
    protected String receive()
    {
        byte[] data = new byte[Talkie.MSG_SIZE];
        DatagramPacket packet = new DatagramPacket( data, data.length );
        try
        {
            socket.receive( packet );
            return new String( packet.getData(), 0, packet.getLength() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String receive(
        int millis )
    {
        byte[] data = new byte[Talkie.MSG_SIZE];
        DatagramPacket packet = new DatagramPacket( data, data.length );
        try
        {
            int soTimeout = socket.getSoTimeout();
            socket.setSoTimeout( millis );
            socket.receive( packet );
            socket.setSoTimeout( soTimeout );
            return new String( packet.getData(), 0, packet.getLength() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }
}
