package talkie.connect;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

import talkie.constants.Talkie;

/**
 * ����������, ���������� �� UDP �������
 * 
 * @author ssv
 */
public class UDPConnection
    extends Connection
{
    /**
     * ����� ������� ���������������
     */
    private InetAddress    initServerAddress = null;
    /**
     * ���� ������� ���������������
     */
    private int            initServerPort    = -1;
    /**
     * ���������� �����
     */
    private DatagramSocket socket            = null;
    /**
     * ����� ������� ����� ��������� "����������"
     */
    protected InetAddress  serverAddress     = null;
    /**
     * ���� ������� ����� ��������� "����������"
     */
    protected int          serverPort        = -1;

    public UDPConnection(
        String host,
        int port )
        throws IOException
    {
        DatagramSocket socket;
        this.socket = new DatagramSocket();
        this.initServerAddress = InetAddress.getByName( host );
        this.initServerPort = port;
    }

    @Override
    public void send(
        byte[] bytes )
    {
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

    @Override
    protected boolean establish(
        byte[] bytes )
    {
        boolean result = false;

        // ��������� ��������� ����������
        DatagramPacket outPacket = new DatagramPacket( bytes, bytes.length, initServerAddress, initServerPort );
        try
        {
            socket.send( outPacket );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        // ��������� �����
        byte[] data = new byte[Talkie.MSG_SIZE];
        DatagramPacket inPacket = new DatagramPacket( data, data.length );

        try
        {
            int soTimeout = socket.getSoTimeout();
            socket.setSoTimeout( 5000 );
            socket.receive( inPacket );
            socket.setSoTimeout( soTimeout );

            // ����� ������, ��������� ��������� ������, � ������� ����� �������� ������
            serverAddress = inPacket.getAddress();
            serverPort = inPacket.getPort();
            data = inPacket.getData();

            String sData = new String( data );
            StringTokenizer st = new StringTokenizer( sData, " " );
            if ( st.countTokens() == 0 )
            {
                return false;
            }

            return Boolean.parseBoolean( st.nextToken() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        if ( result )
        {
            // ���������� �������, ��������� ������� �������, ���������� ������ ��������� - ���� ������ ��� ��� ������
            // todo ��������, ����� ��?
        }

        return result;
    }

    @Override
    protected String receive()
    {
        byte[] data = new byte[Talkie.MSG_SIZE];
        DatagramPacket packet = new DatagramPacket( data, data.length );
        try
        {
            int soTimeout = socket.getSoTimeout();
            socket.receive( packet );

            data = packet.getData();
            return new String( data );
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
