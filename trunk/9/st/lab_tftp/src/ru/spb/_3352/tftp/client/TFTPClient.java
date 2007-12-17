package ru.spb._3352.tftp.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import ru.spb._3352.tftp.common.ACK;
import ru.spb._3352.tftp.common.DATA;
import ru.spb._3352.tftp.common.ERROR;
import ru.spb._3352.tftp.common.FRQ;
import ru.spb._3352.tftp.common.OACK;
import ru.spb._3352.tftp.common.RRQ;
import ru.spb._3352.tftp.common.TFTPPacket;
import ru.spb._3352.tftp.common.TFTPSocket;
import ru.spb._3352.tftp.common.TFTPUtils;
import ru.spb._3352.tftp.common.WRQ;

/**
 * ����-������
 */
public class TFTPClient
{

    /**
     * ���� �� ���������
     */
    public static final String DEFAULT_HOSTNAME = "localhost";

    /**
     * ���� �� ���������
     */
    public static final int    DEFAULT_PORT     = 69;

    /**
     * ������������ ������ ������ � ������
     */
    private static final int   MAX_PACKAGE_SIZE = 8192;

    /**
     * �������������� ����� - ������ �����
     */
    private int                blksize;

    /**
     * ��� �����
     */
    private String             hostName         = null;

    /**
     * �������������� ����� - ����� ��������
     */
    private int                timeout;

    /**
     * �������������� ����� - ������ ������
     */
    private int                tsize;

    /**
     * �����������
     * 
     * @param hostName ��� �����
     */
    public TFTPClient(
        String hostName )
    {
        super();
        this.setHostName( hostName );
    }

    /**
     * ����� ��� �������� ������ � �������
     * 
     * @param rrq ����� � �������� ������
     * @param os �������� �����
     * @return boolean �������?
     * @throws SocketException
     * @throws InstantiationException
     * @throws IOException
     */
    public boolean download(
        RRQ rrq,
        OutputStream os )
        throws SocketException,
            InstantiationException,
            IOException
    {
        // ������� ����-�����
        TFTPSocket tftpSock = new TFTPSocket( timeout );

        int sequenceNumber = 1;

        // ��������
        byte[] dummyByteArray = new byte[1];

        DATA receive = new DATA( sequenceNumber, dummyByteArray );
        ACK surprisePacket = this.sendRequest( tftpSock, rrq, receive );

        if ( surprisePacket == null )
        {
            // ��� ������ �� �������
            System.out.println( "Nothing returned from the server after the initial read request." );
            return false;
        }

        if ( surprisePacket instanceof OACK )
        {
            // ���� �������������� �����
            OACK oack = (OACK) surprisePacket;
            tsize = oack.getTransferSize();
            timeout = oack.getTimeout();
            blksize = oack.getBlockSize();

            ACK ack = new ACK( 0 );
            ack.setPort( surprisePacket.getPort() );
            ack.setAddress( surprisePacket.getAddress() );

            receive = (DATA) TFTPUtils.dataTransfer( tftpSock, ack, receive );
            if ( receive == null )
            {
                // ������ �� ������ � ������� ����� ������������� ��������� ���.
                // �����
                System.out.println( "Nothing returned from the server after ack on oack." );
                return false;
            }
        }
        else if ( surprisePacket instanceof DATA )
        {
            // ����������
            receive = (DATA) surprisePacket;
        }

        // ���������� � ����
        os.write( receive.getData() );

        // ���������� ���� � �����, ��������� ��������, � ������������
        int serverPort = receive.getPort();
        System.out.println( "The server has chosen the following port as the communication port: " + serverPort );
        InetAddress serverAddress = rrq.getAddress();
        tftpSock.connect( serverAddress, serverPort );

        ACK ack = null;
        while ( receive.getData().length >= MAX_PACKAGE_SIZE )
        {
            ack = new ACK( sequenceNumber++ );
            receive = new DATA( sequenceNumber, dummyByteArray );
            System.out.println( "receiving block" + sequenceNumber );

            receive = (DATA) TFTPUtils.dataTransfer( tftpSock, ack, receive );

            if ( receive == null )
            {
                // ������ ������
                System.out.println( "Nothing returned from the server after the transfer." );
                return false;
            }

            os.write( receive.getData() );
        }

        // ������ ��������� ����� ����� �������, ������ ������ �������
        // ������������� ����� ��������������, ��� �� �Ѩ �������. ���� ��� -
        // ������ �������� ������� ���������� �����
        System.out.println( "send ack to say that we have received last message." );
        ack = new ACK( sequenceNumber );
        receive = (DATA) TFTPUtils.dataTransfer( tftpSock, ack, null );

        // ��������� �������� �����
        os.close();

        return true;
    }

    /**
     * ���������� ��� ����� ����������
     * 
     * @return ��� ����� ����������
     */
    private String getHostName()
    {
        return this.hostName;
    }

    /**
     * ���������� ����� ����������
     * 
     * @return ����� ����������
     * @throws UnknownHostException
     */
    private InetAddress getInetAddress()
        throws UnknownHostException
    {
        return InetAddress.getByName( this.getHostName() );
    }

    /**
     * ���������� ����� "������ �����"
     * 
     * @return ������ �����
     */
    public int getOptionBlockSize()
    {
        return blksize;
    }

    /**
     * ���������� ����� "�������"
     * 
     * @return �������
     */
    public int getOptionTimeout()
    {
        return timeout;
    }

    /**
     * ���������� ����� "������ �����"
     * 
     * @return ������ �����
     */
    public int getOptionTransferSize()
    {
        return tsize;
    }

    /**
     * ������� ������ �� ����������
     * 
     * @param fileName ��� �����
     * @param optionTimeout �������
     * @param optionTransferSize ������ ��������
     * @param optionBlockSize ������ �����
     * @return �����
     * @throws InstantiationException
     * @throws UnknownHostException
     */
    public RRQ initializeDownload(
        String fileName,
        int optionTimeout,
        int optionTransferSize,
        int optionBlockSize )
        throws InstantiationException,
            UnknownHostException
    {
        // ������ �� �������� � ����� � ������� �� ��� �����
        RRQ rrq = new RRQ();
        rrq.setFileName( fileName );

        if ( optionTimeout != 0 )
        {
            rrq.setTimeout( optionTimeout );
        }

        if ( optionTransferSize != 0 )
        {
            rrq.setTransferSize( optionTransferSize );
        }

        if ( optionBlockSize != 0 )
        {
            rrq.setBlockSize( optionBlockSize );
        }

        rrq.setMode( FRQ.OCTET_MODE );
        rrq.setAddress( InetAddress.getByName( this.getHostName() ) );
        rrq.setPort( DEFAULT_PORT );

        return rrq;
    }

    /**
     * ������ �� ������� ����� �� ������
     * 
     * @param fileName ��� �����
     * @param optionTimeout �������
     * @param optionTransferSize ������ ��������
     * @param optionBlockSize ������ �����
     * @return �����
     * @throws InstantiationException
     * @throws UnknownHostException
     */
    public WRQ initializeUpload(
        String fileName,
        int optionTimeout,
        int optionTransferSize,
        int optionBlockSize )
        throws InstantiationException,
            UnknownHostException
    {
        // ��������� ������ ��� �������� ����� �� ������
        WRQ wrq = new WRQ();
        wrq.setFileName( fileName );

        if ( optionTimeout != 0 )
        {
            wrq.setTimeout( optionTimeout );
        }

        if ( optionTransferSize != 0 )
        {
            wrq.setTransferSize( optionTransferSize );
        }

        if ( optionBlockSize != 0 )
        {
            wrq.setBlockSize( optionBlockSize );
        }

        wrq.setMode( FRQ.OCTET_MODE );
        wrq.setAddress( InetAddress.getByName( getHostName() ) );
        wrq.setPort( DEFAULT_PORT );

        return wrq;
    }

    /**
     * ��������� ���������� ����� �������� � ��������
     * 
     * @param tftpSock ����-�����
     * @param frq ������
     * @param recv �����
     * @return �����
     */
    public ACK sendRequest(
        TFTPSocket tftpSock,
        FRQ frq,
        ACK recv )
    {

        int retransmits = 0;
        int spamcount = 0;

        // ����, ����������� �� ������� ���������� �������� ��������� ���������
        // � ������, ����������� �������� ����� ��������, ����� �� ��������
        // �����
        boolean stuffedLink = false;

        // �������� �����
        try
        {
            tftpSock.write( frq );

            // ���� ����� ����� �������� - �Ĩ� ����� ������
            if ( stuffedLink )
            {
                tftpSock.setSocketTimeOut( tftpSock.getSocketTimeOut() * 2 );
            }
        }
        catch ( Exception e )
        {
            System.out.println( TFTPUtils.getClient( tftpSock ) + " UDP send packet failure1." );
            System.out.println( e.toString() );
        }

        TFTPPacket tftpP = null;
        boolean receiving = true;

        // �Ĩ� �������������
        while ( receiving )
        {
            try
            {
                tftpP = tftpSock.read();

                // ������� ���� ����� � ������, �.�. ��������� ��� ����������
                if ( stuffedLink )
                {
                    tftpSock.setSocketTimeOut( tftpSock.getSocketTimeOut() );
                    stuffedLink = false;
                }
            }
            catch ( IOException ios )
            {
                System.out.println( "IOException: " + ios.getMessage() );
                return null;
            }

            // ������ �� �������� � �����
            if ( tftpP == null )
            {
                if ( retransmits++ > 5 )
                {
                    // ������� ����� ��������� ���������
                    TFTPUtils.sendErrPacket( tftpSock, ERROR.ERR_NOT_DEFINED, "Retransmit limit exceeded" );
                    System.out.println( TFTPUtils.getClient( tftpSock ) + " Maximum retransmit count exceeded" );
                    return null;
                }
                else
                {
                    // ���������� ����� �٨ ���
                    System.out.println( TFTPUtils.getClient( tftpSock )
                                        + " expected packet before time out, sending ACK/DATA again" );
                    try
                    {
                        tftpSock.write( frq );
                        stuffedLink = true;
                    }
                    catch ( Exception e )
                    {
                        System.out.println( TFTPUtils.getClient( tftpSock ) + " UDP send packet failure2." );
                        System.out.println( e.toString() );
                    }
                    continue;
                }
            }

            // �������� ��������� �����
            if ( tftpP instanceof ERROR )
            {
                System.out.println( TFTPUtils.getClient( tftpSock ) + " " + ((ERROR) tftpP).getErrorMessage() );
                return null;
            }

            // ���� �������� ������������� �������� ����� ������ ������
            if ( (tftpP instanceof OACK) && (recv instanceof DATA) && (recv.getBlockNr() == 1) )
            {
                return (ACK) tftpP;
            }

            // ���� �������� ������������� ����� ������ ������������� ������
            if ( (tftpP instanceof OACK) && (recv instanceof ACK) && (recv.getBlockNr() == 0) )
            {
                return (ACK) tftpP;
            }

            // �������� ��� ������
            if ( (tftpP instanceof ACK) && TFTPUtils.correctAnswer( recv, (ACK) tftpP ) )
            {
                return (ACK) tftpP;
            }

            // ���-�� ����� ������, ���� ������� ����� - ������
            if ( spamcount++ > 5 )
            {
                return null;
            }
        }
        return null;
    }

    /**
     * ������������� ��� ����� ����������
     * 
     * @param hostName ����� ��� ����� ����������
     */
    private void setHostName(
        String hostName )
    {
        this.hostName = hostName;
    }

    /**
     * ����� ��� ������� ������ �� ������ � �������
     * 
     * @param wrq ������ �� ������
     * @param is ������� �����
     * @return boolean ������?
     * @throws SocketException
     * @throws InstantiationException
     * @throws IOException
     */
    public boolean upload(
        WRQ wrq,
        InputStream is )
        throws SocketException,
            InstantiationException,
            IOException
    {
        System.out.println( "[M] : upload: " + wrq.getFileName() );

        TFTPSocket tftpSock = new TFTPSocket( timeout );

        int sequenceNumber = 0;

        ACK receive = new ACK( 0 );
        receive = this.sendRequest( tftpSock, wrq, receive );

        // ������ ������ ������� ����� ������������� � ������� ����� 0
        if ( receive == null )
        {
            // ������ ������
            System.out.println( "Nothing returned from the server after the initial send." );
            return false;
        }

        if ( receive.getBlockNr() != 0 )
        {
            // �� ��� ����� �����
            System.out.println( "The server has sent an ACK with wrong block number." );
            return false;
        }

        if ( receive instanceof OACK )
        {
            // � ������������� � ������� ���� �������������� �����!!!
            OACK oack = (OACK) receive;
            tsize = oack.getTransferSize();
            timeout = oack.getTimeout();
            blksize = oack.getBlockSize();
        }

        // ���������� ����, ������� ������ ������ ��� ����������
        int serverPort = receive.getPort();
        System.out.println( "The server has chosen the following port as the communication port: " + serverPort );
        InetAddress serverAddress = wrq.getAddress();
        tftpSock.connect( serverAddress, serverPort );

        // ������ ���� � ����������
        byte[] sendBytes = new byte[MAX_PACKAGE_SIZE];
        DATA send = new DATA();
        int returnValue = 0;

        while ( (returnValue = is.read( sendBytes )) != -1 )
        {
            System.out.println( "sending packet number: " + sequenceNumber );

            // ������������ ������ ���� � ���������� ������ ������ ����
            send = new DATA( ++sequenceNumber, sendBytes, 0, returnValue );
            receive = new ACK( sequenceNumber );

            // �������� �� ������, � ����� �������� �������������
            receive = TFTPUtils.dataTransfer( tftpSock, send, receive );
        }

        // �� �������� ������� ����� ��� ������
        is.close();

        return true;
    }
}
