package ru.spb._3352.tftp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Hashtable;

import ru.spb._3352.tftp.common.FRQ;
import ru.spb._3352.tftp.common.RRQ;
import ru.spb._3352.tftp.common.TFTPPacket;
import ru.spb._3352.tftp.common.VirtualFileSystem;
import ru.spb._3352.tftp.common.WRQ;

/**
 * �������� ������ ��� ������� � ��������� ������
 */
public class TFTPServerSocket
    implements
        Runnable
{
    /**
     * ������ ��������
     */
    boolean           abort;

    /**
     * ������� ���������, ��������� ���������
     */
    private Hashtable newConnects;

    /**
     * ���������� ������������ �������� � �������� ������ ����
     */
    private int       poolSize;

    /**
     * ���� �������
     */
    int               serverPort;

    /**
     * �����
     */
    DatagramSocket    serverSocket;

    /**
     * ��� ������������
     */
    private TFTPPool  workers;

    /**
     * �����������
     * 
     * @param serverPort ����� �����
     * @param poolSize ������ ����
     * @param vfs ����������� �������� �������
     * @param listener ���������
     */
    public TFTPServerSocket(
        int serverPort,
        int poolSize,
        VirtualFileSystem vfs,
        EventListener listener )
    {
        this.serverPort = serverPort;
        this.poolSize = poolSize;

        // ������� ������� ��� ����������� ���������, ����� ��� ������� �
        // ��������� ��� �������� ���������

        newConnects = new Hashtable();

        System.out.println( "Creating new pool of " + poolSize + " worker threads" );
        workers = new TFTPPool( poolSize, newConnects, vfs, listener );
    }

    /**
     * ���������� ������ ����
     * 
     * @return ������ ����
     */
    public int getPoolSize()
    {
        return poolSize;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        int bufSize = 528;
        InetAddress clientAddress;
        int clientPort;

        // ������� �����
        try
        {
            serverSocket = new DatagramSocket( serverPort );
            serverSocket.setSoTimeout( 1000 );
        }
        catch ( SocketException e )
        {
            System.out.println( "Could not create socket on port: " + serverPort + ", shutting down!" );

            // ����� ������� �� �������, �������� ������ ������ ���
            stop();

            return;
        }

        System.out.println( "TFTPServerSocket is started" );

        // ����������� ����
        for ( ; abort != true; )
        {
            byte[] buffer = new byte[bufSize];
            DatagramPacket packet = new DatagramPacket( buffer, bufSize );

            // �������� ������� ������
            try
            {
                serverSocket.receive( packet );
            }
            catch ( IOException ioe )
            {
                // ������ �� ������
                continue;
            }

            // ������������� ���������� ������
            clientAddress = packet.getAddress();
            clientPort = packet.getPort();
            System.out.println( "server received request for file from " + clientAddress.toString() );

            // �������� ���������� �� ������
            byte[] data = new byte[packet.getLength()];
            System.arraycopy( packet.getData(), packet.getOffset(), data, 0, packet.getLength() );

            // �������������� ���������
            int opcode = TFTPPacket.fetchOpCode( data );

            // ������� ������ �� ������ ��� ������
            FRQ frq;
            try
            {
                switch ( opcode )
                {
                    case RRQ.OPCODE:
                        frq = new RRQ( data );
                        break;
                    case WRQ.OPCODE:
                        frq = new WRQ( data );
                        break;
                    default:
                        continue;
                }
            }
            catch ( InstantiationException e )
            {
                System.out.println( "InstantiationException: " + e.getMessage() );
                continue;
            }

            // ������� � ������������ ������ ����� ������������
            if ( frq.getFileName() == null || frq.getFileName().length() == 0 )
            {
                continue;
            }

            // ���������� ������
            workers.performWork( frq, clientAddress, clientPort );
        }

        System.out.println( "TFTPServerSocket is stopped" );
    }

    /**
     * ������������� ������ ����
     * 
     * @param poolSize ����� ������ ����
     */
    public void setPoolSize(
        int poolSize )
    {
        this.poolSize = poolSize;
        workers.resize( poolSize );
    }

    /**
     * ��������� �������
     */
    public void stop()
    {
        // ������� ���
        workers.resize( 0 );

        // �������� �������� ������
        abort = true;

        // ��������� �����
        if ( serverSocket != null )
        {
            serverSocket.close();
        }
    }
}
