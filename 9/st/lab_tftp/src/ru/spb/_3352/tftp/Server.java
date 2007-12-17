package ru.spb._3352.tftp;

import java.net.InetAddress;

import ru.spb._3352.tftp.common.VirtualFileSystem;
import ru.spb._3352.tftp.server.EventListener;
import ru.spb._3352.tftp.server.TFTPServer;

/**
 * �����-�������� ��� ������� �������
 */
public class Server
    implements
        EventListener
{
    /**
     * ����� �����
     * 
     * @param args ��������� ��������� ������
     */
    public static void main(
        String[] args )
    {
        // ��������� ��������� ��������� ������
        if ( args.length != 3 )
        {
            printUsage();
            return;
        }

        int port = 69;
        int poolSize = 5;

        try
        {
            port = Integer.parseInt( args[0] );
        }
        catch ( NumberFormatException e )
        {
            System.out.println( "Wrong port specified, using default" );
        }

        try
        {
            poolSize = Integer.parseInt( args[1] );
        }
        catch ( NumberFormatException e )
        {
            System.out.println( "Wrong poolSize specified, using default" );
        }

        VirtualFileSystem vfs = new FileSystem( args[2] );

        // ������� ����� ��������� �������
        Server server = new Server( vfs, poolSize, port );

        try
        {
            server.start();
            System.out.println( "Press ENTER to shutdown the server!" );
            System.in.read();
            server.stop();
        }
        catch ( Exception e )
        {
            System.out.println( "Exception occured: " + e );
            e.printStackTrace();
        }
    }

    /**
     * ������� �� ����� ���������� �� ���������� ��������� ������
     */
    public static void printUsage()
    {
        System.out.println( "Program usage:" );
        System.out.println( "\tserver PORT POOL_SIZE HOME_DIR\n" );
        System.out.println( "Example:" );
        System.out.println( "\tserver 69 5 d:\\temp\\\n" );
    }

    /**
     * ��������� ����-�������
     */
    private TFTPServer tftpServer;

    /**
     * �����������
     * 
     * @param vfs ���������� ����������� �������� �������
     * @param poolSize ������ ���� ������������
     * @param port ���� ��� ����������
     */
    public Server(
        VirtualFileSystem vfs,
        int poolSize,
        int port )
    {
        tftpServer = new TFTPServer( vfs, this );
        tftpServer.setPoolSize( poolSize );
        tftpServer.setPort( port );
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.server.EventListener#onAfterDownload(java.net.InetAddress, int, java.lang.String, boolean)
     */
    public void onAfterDownload(
        InetAddress a,
        int p,
        String fileName,
        boolean ok )
    {
        if ( ok )
        {
            System.out.println( "Send " + fileName + " sucessfully to client: " + a.getHostAddress() + " port: " + p );
        }
        else
        {
            System.out.println( "Send " + fileName + " file not sucessfully to client: " + a.getHostAddress()
                                + " port: " + p );
        }
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.server.EventListener#onAfterUpload(java.net.InetAddress, int, java.lang.String, boolean)
     */
    public void onAfterUpload(
        InetAddress a,
        int p,
        String fileName,
        boolean ok )
    {
        if ( ok )
        {
            System.out.println( "received " + fileName + " sucessfully from client: " + a.getHostAddress() + " port: "
                                + p );
        }
        else
        {
            System.out.println( "received " + fileName + " file not sucessfully from client: " + a.getHostAddress()
                                + " port: " + p );
        }
    }

    /**
     * ������
     * 
     * @throws Exception
     */
    public void start()
        throws Exception
    {
        if ( tftpServer == null )
        {
            return;
        }
        tftpServer.start();
    }

    /**
     * ���������
     */
    public void stop()
    {
        if ( tftpServer == null )
        {
            return;
        }
        tftpServer.stop();
    }

}
