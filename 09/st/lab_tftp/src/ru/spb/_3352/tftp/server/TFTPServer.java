package ru.spb._3352.tftp.server;

import ru.spb._3352.tftp.common.VirtualFileSystem;

/**
 * ����-������
 */
public class TFTPServer
{
    /**
     * ��������� ����� �� ���������
     */
    final static int          DEFAULT_PORT = 69;

    /**
     * ��������� �������
     */
    private EventListener     listener     = null;

    /**
     * ������ ����
     */
    private int               poolSize     = 5;

    /**
     * ����� �����
     */
    private int               port         = DEFAULT_PORT;

    /**
     * ����� �������
     */
    private Thread            server;

    /**
     * �������� ������
     */
    private TFTPServerSocket  ss;

    /**
     * ���������� ����������� �������� �������
     */
    private VirtualFileSystem vfs;

    /**
     * �����������
     * 
     * @param vfs ���������� ����������� �������� �������
     * @param listener ��������� ��� ������� �� ��������
     */
    public TFTPServer(
        VirtualFileSystem vfs,
        EventListener listener )
    {
        this.vfs = vfs;
        this.listener = listener;
    }

    /**
     * ���������� ������ ����
     * 
     * @return int ������ ����
     */
    public int getPoolSize()
    {
        return poolSize;
    }

    /**
     * ���������� ����� �����
     * 
     * @return int ����� �����
     */
    public int getPort()
    {
        return port;
    }

    /**
     * ������������� ������ ����
     * 
     * @param size ������ ����
     */
    public void setPoolSize(
        int poolSize )
    {
        this.poolSize = poolSize;
        if ( ss == null )
        {
            return;
        }
        ss.setPoolSize( poolSize );
    }

    /**
     * ������������� ����� �����
     * 
     * @param port ����� �����
     */
    public void setPort(
        int port )
    {
        this.port = port;
    }

    /**
     * ������� � ��������� �������� ������ � ����� ������
     */
    public void start()
    {
        if ( port == 0 )
        {
            port = DEFAULT_PORT;
        }

        System.out.println( "Starting new TFTP server socket on port: " + port );

        ss = new TFTPServerSocket( port, poolSize, vfs, listener );
        server = new Thread( ss );
        server.start();
    }

    /**
     * ������������� � ���������� �������� ������
     */
    public void stop()
    {
        if ( ss == null )
        {
            System.out.println( "ServerSocket is already null so, is tftpServer closed???" );
            return;
        }

        System.out.println( "Shutting down TFTP server socket." );

        ss.stop();

        if ( server == null )
        {
            System.out.println( "FIXME: ServerSocket was not null but tftpServer is!" );
            return;
        }

        try
        {
            server.join( 6000 );
        }
        catch ( InterruptedException e )
        {
            System.out.println( "Could not close all TFTPServer thread!" );
        }

        server = null;
        ss = null;
    }
}
