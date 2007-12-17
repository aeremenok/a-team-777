package ru.spb._3352.tftp.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;

import javax.naming.Context;
import javax.naming.InitialContext;

import ru.spb._3352.tftp.common.ERROR;
import ru.spb._3352.tftp.common.FRQ;
import ru.spb._3352.tftp.common.RRQ;
import ru.spb._3352.tftp.common.VirtualFile;
import ru.spb._3352.tftp.common.VirtualFileImpl;
import ru.spb._3352.tftp.common.VirtualFileSystem;
import ru.spb._3352.tftp.common.WRQ;

/**
 * ���������� ��������
 */
public class TFTPRequestHandler
{

    /**
     * ��������
     */
    Context                   ctx;

    /**
     * ������ ����� �� ��������� - 2048 ��
     */
    int                       DEF_BLKSIZE = 2048;

    /**
     * ������� �� ��������� - 5 ������
     */
    int                       DEF_TIMEOUT = 5;

    /**
     * ������ ����� �� ��������� - 200 ��
     */
    int                       DEF_TSIZE   = 100 * 2048 * 1024;

    /**
     * ��������� �������
     */
    private EventListener     listener    = null;

    /**
     * ����-������
     */
    ClientHandler             tftpClient;

    /**
     * ������� �����
     */
    Thread                    thisThread;

    /**
     * ������ �� �������� ������� ��� ����������� �������������
     */
    private VirtualFileSystem vfs         = null;

    /**
     * �����������
     * 
     * @param vfs ����������� �������� �������
     * @param listener ��������� �������
     * @throws SocketException
     */
    public TFTPRequestHandler(
        VirtualFileSystem vfs,
        EventListener listener )
        throws SocketException
    {
        this.vfs = vfs;
        this.listener = listener;
        tftpClient = new ClientHandler();

        try
        {
            ctx = new InitialContext();
        }
        catch ( javax.naming.NamingException e )
        {
            System.out.println( "Could not create InitialContext! " + e.toString() );
            throw new SocketException( "Cannot get new InitialContext for TFTPRequesHandler, run would fail!" );
        }
    }

    /**
     * ����� ����� � ����� ����������
     * 
     * @param frq ������
     * @param clientAddress ����� �������
     * @param clientPort ���� �������
     */
    public void run(
        FRQ frq,
        InetAddress clientAddress,
        int clientPort )
    {
        if ( frq == null )
        {
            System.out.println( "TFTPRequestHandler run is called with null packet!" );
            return;
        }

        if ( clientAddress == null )
        {
            System.out.println( "TFTPRequestHandler run is called with invalid client address!" );
            return;
        }

        if ( clientPort == 0 )
        {
            System.out.println( "TFTPRequestHandler run is called with invalid client port!" );
            return;
        }

        // ��������� ����-������ � ��������
        tftpClient.connect( clientAddress, clientPort );

        // �������� �������
        int timeout = frq.getTimeout();
        if ( timeout <= 0 )
        {
            timeout = DEF_TIMEOUT;
        }
        tftpClient.setTimeout( timeout );

        // �������� ������ �����
        int tsize = frq.getTransferSize();
        if ( tsize < 0 )
        {
            tsize = DEF_TSIZE;
        }
        tftpClient.setTransferSize( tsize );

        // �������� �������
        int blkSize = frq.getBlockSize();
        if ( blkSize <= 0 )
        {
            blkSize = DEF_BLKSIZE;
        }
        tftpClient.setBlockSize( timeout );

        thisThread = Thread.currentThread();

        if ( frq instanceof RRQ )
        {
            RRQ rrq = (RRQ) frq;
            System.out.println( tftpClient.getClient() + " RRQ " + rrq.getFileName() );
            boolean sendOK = false;
            VirtualFile file = new VirtualFileImpl( rrq.getFileName() );

            InputStream is = null;
            try
            {
                is = vfs.getInputStream( file );
            }
            catch ( FileNotFoundException e )
            {
                System.out.println( "FileNotFoundException: " + e.getMessage() );
                tftpClient.sendErrPacket( ERROR.ERR_FILE_NOT_FOUND, e.getMessage() );
                return;
            }

            sendOK = tftpClient.sendFileToClient( is, clientAddress, clientPort, frq.hasOptions() );

            // ���������� ������� �� ��������� �������
            if ( listener != null )
            {
                listener.onAfterDownload( clientAddress, clientPort, rrq.getFileName(), sendOK );
            }

            try
            {
                is.close();
            }
            catch ( IOException e )
            {
                System.out.println( "This is sad but true, we cannot close the inputStream for " + rrq.getFileName() );
                return;
            }
        }
        else if ( frq instanceof WRQ )
        {
            WRQ wrq = (WRQ) frq;
            System.out.println( tftpClient.getClient() + " WRQ " + wrq.getFileName() );
            VirtualFile file = new VirtualFileImpl( wrq.getFileName() );

            // �������� ���� �� �������
            try
            {
                OutputStream os = vfs.getOutputStream( file );
                boolean receiveOK = tftpClient.receiveFileFromClient( os, clientAddress, clientPort, frq.hasOptions() );

                if ( listener != null )
                {
                    listener.onAfterUpload( clientAddress, clientPort, wrq.getFileName(), receiveOK );
                }
            }
            catch ( Exception e )
            {
                System.out.println( "Exception occurred in tftp.run " + e );
                e.printStackTrace();
            }
        }
    }

    /**
     * ��������� ������
     */
    public void stop()
    {
        // ����������� �� �������
        tftpClient.disconnect();
    }

    /**
     * ���������� ����� ������ �������� ������ ������ �� ������ ��� ������, � ��
     * ����� ��� ���� ���������� �٨ ����� ���������� ��������
     * 
     * @param frq ������
     * @return ���������� ��������?
     */
    public boolean waitingForNewRequest(
        FRQ frq )
    {
        if ( tftpClient.waitingForNewRequest() )
        {
            tftpClient.newRequest();
            return true;
        }
        else
        {
            return false;
        }
    }
}
