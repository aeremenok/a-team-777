package ru.spb._3352.tftp.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Vector;

import ru.spb._3352.tftp.common.ACK;
import ru.spb._3352.tftp.common.DATA;
import ru.spb._3352.tftp.common.ERROR;
import ru.spb._3352.tftp.common.OACK;
import ru.spb._3352.tftp.common.TFTPOptions;
import ru.spb._3352.tftp.common.TFTPPacket;
import ru.spb._3352.tftp.common.TFTPSocket;
import ru.spb._3352.tftp.common.TFTPUtils;

/**
 * ��������� ��������� ������ ��������
 */
public class ClientHandler
{
    /**
     * ������ ����� �� ���������
     */
    public final static int DEFAULT_TSIZE = 2048 * 1024;

    /**
     * �������� �������������
     */
    private boolean         ackReceived   = true;

    /**
     * ����, ����������� �� ������������ ������ �������� ����������� ��������
     */
    private boolean         stuffedLink   = false;

    /**
     * ����-�����
     */
    TFTPSocket              tftpSock;

    /**
     * ������� ����� � ��������
     */
    private int             timeout       = 5;

    /**
     * ������� ������ �����
     */
    private int             tsize         = DEFAULT_TSIZE;

    /**
     * �����������
     */
    public ClientHandler()
        throws SocketException
    {
        tftpSock = new TFTPSocket( timeout );
    }

    /**
     * ��������� � ��������, ������ � ������� ���������������� �������� ������
     * 
     * @param inetAddress ���˨���� �����
     * @param clientPort ���� �������
     */
    public void connect(
        InetAddress inetAddress,
        int clientPort )
    {
        tftpSock.connect( inetAddress, clientPort );
    }

    /**
     * ��������� ������������ ������ �������������
     * 
     * @param expecting ��������� �����
     * @param received ���������� �����
     * @return TRUE ���� ������ �������������
     */
    private boolean correctAnswer(
        ACK expecting,
        ACK received )
    {
        if ( expecting == null )
        {
            return false;
        }

        if ( received == null )
        {
            return false;
        }

        if ( expecting.getOpCode() != received.getOpCode() )
        {
            return false;
        }

        if ( expecting.getBlockNr() != received.getBlockNr() )
        {
            return false;
        }

        return true;
    }

    /**
     * ����������� �� �������, ��� ������ ������ ������ ��������� ����� � ����
     */
    public void disconnect()
    {
        tftpSock.disconnect();
    }

    /**
     * ������ ���� ��������� �������������
     * 
     * @return ������������� ��������?
     */
    public synchronized boolean getAckReceived()
    {
        return ackReceived;
    }

    /**
     * ���������� IP-����� � ����� ����� �������
     */
    public String getClient()
    {
        String client = "";
        InetAddress addr = tftpSock.getAddress();
        int port = tftpSock.getPort();

        if ( addr != null )
        {
            client += addr.getHostAddress();
        }

        if ( port != 0 )
        {
            client += ":";
            client += port;
        }

        return client;
    }

    /**
     * ���������� �������
     * 
     * @return ������� � ��������
     */
    public int getTimeout()
    {
        return timeout;
    }

    /**
     * ���������� ������ �����
     * 
     * @return int ������ �����
     */
    public int getTransferSize()
    {
        return tsize;
    }

    /**
     * ����� ������
     */
    public void newRequest()
    {
        setAckReceived( true );

        try
        {
            Thread.sleep( 150 );
        }
        catch ( InterruptedException e )
        {
        }
    }

    /**
     * �������� ���� �� �������
     * 
     * @param os �������� �����
     * @param clientAddress ����� �������
     * @param clientPort ���� �������
     * @param useOptions �����
     * @return TRUE, ���� ������
     */
    public boolean receiveFileFromClient(
        OutputStream os,
        InetAddress clientAddress,
        int clientPort,
        boolean useOptions )
    {
        tftpSock.connect( clientAddress, clientPort );
        Vector dataBlocks = new Vector( 10 );

        // �������� ������ ������������� � ����� �� ������ ������
        int blockNumber = 0;
        byte[] data = new byte[1];
        int totalSize = 0;

        ACK send;
        DATA receive;
        DATA expect;

        // ���� ���� ���. ����� - ������������ ��
        if ( useOptions )
        {
            TFTPOptions options = new TFTPOptions();
            options.setTimeout( getTimeout() );
            options.setTransferSize( getTransferSize() );
            send = new OACK( 0, options );
        }
        else
        {
            send = new ACK( blockNumber );
        }

        do
        {
            try
            {
                expect = new DATA( blockNumber + 1, data );
                receive = (DATA) TFTPUtils.dataTransfer( tftpSock, send, expect );
            }
            catch ( ClassCastException e )
            {
                System.out.println( "CODING ERROR? this case should be fetched in DataTransfer!" );
                return false;
            }
            catch ( InstantiationException e )
            {
                System.out.println( "CODING ERROR? cannot construct DATA and ACK packets in receiveFileFromClient" );
                return false;
            }
            catch ( IOException e )
            {
                sendErrPacket( ERROR.ERR_NOT_DEFINED, e.getMessage() );
                System.out.println( getClient() + e.getMessage() );
                return false;
            }
            catch ( Throwable t )
            {
                t.printStackTrace();
                System.out.println( "Some unrecoverable error occured. cannot continue with client: " + t );
                return false;
            }

            if ( receive == null )
            {
                return false;
            }

            data = receive.getData();
            totalSize += data.length;
            System.out.println( "totalSize of receiveid packets in receiveFileFromClient: " + totalSize );

            // ��������� ������ �����
            if ( totalSize > getTransferSize() )
            {
                sendErrPacket( ERROR.ERR_NOT_DEFINED, "Exceed MFY buffer size" );
                System.out.println( getClient() + " Client tries to write file and exceed buffer size!" );
                return false;
            }

            try
            {
                os.write( data );
            }
            catch ( IOException e )
            {
                sendErrPacket( ERROR.ERR_ILLEGAL_OP, e.getMessage() );
                System.out.println( "OutputStream got closed in receiveFileFromClient: " + e.getMessage()
                                    + " caused by: " + e.getCause() );
                return false;
            }

            blockNumber = receive.getBlockNr();
            send = new ACK( blockNumber );
        }
        while ( data.length == TFTPSocket.BLOCK_SIZE );

        // ��� ��� ������� ���������� ������������� ����� ������ ������ �����,
        // ��������� ����� ����� ����
        try
        {
            os.close();
        }
        catch ( IOException e )
        {
            System.out.println( "problem while closing OutputStream in receiveFileFromClient: " + e.getMessage()
                                + " caused by: " + e.getCause() );
            return false;
        }

        // �������� ��������� ����� � �Ĩ�, ���� �������� - ���������� ���������
        // �������������
        // FIXME ��� ���� ����� ���Ĩ� ����� ERROR?
        sendLastAckForDataPacket( receive );

        return true;
    }

    /**
     * �������� ����� �������������
     * 
     * @param blocknumber ����� �����
     */
    void sendAckPacket(
        int blocknumber )
    {
        try
        {
            TFTPPacket tftpP = new ACK( blocknumber );
            tftpSock.write( tftpP );
        }
        catch ( Exception e )
        {
            System.out.println( getClient() + " UDP send ACK packet failure." );
            System.out.println( e.toString() );
        }
        System.out.println( "SEND ACK" + " [" + getClient() + "] BN=[" + blocknumber + "]" );
    }

    /**
     * �������� ����� � �������
     * 
     * @param blocknumber ����� �����
     * @param data ������
     * @param offset ��������
     * @param dataSize ������ ������
     */
    void sendDataPacket(
        int blocknumber,
        byte[] data,
        int offset,
        int dataSize )
    {
        try
        {
            TFTPPacket tftpP = new DATA( blocknumber, data, offset, dataSize );
            tftpSock.write( tftpP );
        }
        catch ( Exception e )
        {
            System.out.println( getClient() + " UDP send DATA packet failure." );
            System.out.println( e.toString() );
        }
        System.out.println( "SEND DATA" + " [" + getClient() + "] BN=[" + blocknumber + "]" );
    }

    /**
     * �������� ����� ������
     * 
     * @param errorCode ��� ������
     * @param errorMsg ��������� �� ������
     */
    void sendErrPacket(
        int errorCode,
        String errorMsg )
    {
        try
        {
            TFTPPacket tftpP = new ERROR( errorCode, errorMsg );
            tftpSock.write( tftpP );
        }
        catch ( Exception e )
        {
            System.out.println( getClient() + " UDP send ERROR packet failure." );
            System.out.println( e.toString() );
            return;
        }
        System.out.println( "SEND ERROR" + " [" + getClient() + "] EC = [" + errorCode + "] " + errorMsg );
    }

    /**
     * �������� ���� �������
     * 
     * @param is ����� ������
     * @param clientAddress ����� �������
     * @param clientPort ���� �������
     * @param useOptions ���������� �������������� �����
     * @return TRUE ���� ������
     */
    public boolean sendFileToClient(
        InputStream is,
        InetAddress clientAddress,
        int clientPort,
        boolean useOptions )
    {
        if ( clientAddress == null || clientPort == 0 )
        {
            return false;
        }

        tftpSock.connect( clientAddress, clientPort );

        System.out.println( "clearing socket buffer" );

        // ������� ����� �� ������ � ������������� �������
        tftpSock.clear();

        int offset = 0;
        byte[] data = new byte[TFTPSocket.BLOCK_SIZE];
        InputStream bis = new BufferedInputStream( is );
        int read = 0;

        int blockNumber = 0;

        ACK send;
        ACK expect;
        ACK receive;

        try
        {
            // ���� ������ ���� ����� - ������� ������������ ��
            if ( useOptions )
            {
                TFTPOptions options = new TFTPOptions();
                options.setTimeout( getTimeout() );
                options.setTransferSize( getTransferSize() );
                send = new OACK( 0, options );
            }
            else
            {
                // ������ TFTPSocket.BLOCK_SIZE ������ �� ������
                read = bis.read( data );

                // ���� �������� -1 - ����� ������
                if ( read <= 0 )
                {
                    return false;
                }

                send = new DATA( ++blockNumber, data, 0, read );
            }

            expect = new ACK( blockNumber );
            receive = TFTPUtils.dataTransfer( tftpSock, send, expect );

            if ( receive == null )
            {
                return false;
            }

            if ( !useOptions && (read < TFTPSocket.BLOCK_SIZE) )
            {
                return true;
            }
        }
        catch ( ClassCastException e )
        {
            System.out.println( "CODING ERROR this case should be fetched in DataTransfer!" );
            return false;
        }
        catch ( InstantiationException e )
        {
            System.out.println( "CODING ERROR: Could not create DATA and ACK packet in sendFileToClient" );
            return false;
        }
        catch ( IOException e )
        {
            sendErrPacket( ERROR.ERR_NOT_DEFINED, e.getMessage() );
            System.out.println( getClient() + e.getMessage() );
            return false;
        }
        catch ( Throwable t )
        {
            t.printStackTrace();
            System.out.println( "Some unrecoverable error occured. cannot continue with client: " + t );
            return false;
        }

        do
        {
            try
            {
                read = bis.read( data );
                // ���� ���������� � ������ ����� � �������� ������� ����� -
                // ���� ������� ������ ���� ���������
                if ( read < 0 )
                {
                    read = 0;
                }

                send = new DATA( ++blockNumber, data, 0, read );
                expect = new ACK( blockNumber );
                receive = TFTPUtils.dataTransfer( tftpSock, send, expect );
                if ( receive == null )
                {
                    return false;
                }
            }
            catch ( ClassCastException e )
            {
                System.out.println( "CODING ERROR this case should be fetched in DataTransfer!" );
                return false;
            }
            catch ( InstantiationException e )
            {
                System.out.println( "CODING ERROR: Could not create DATA and ACK packet in sendFileToClient" );
                return false;
            }
            catch ( IOException e )
            {
                sendErrPacket( ERROR.ERR_NOT_DEFINED, e.getMessage() );
                System.out.println( getClient() + e.getMessage() );
                return false;
            }
            catch ( Throwable t )
            {
                t.printStackTrace();
                System.out.println( "Some unrecoverable error occured. Cannot continue with client: " + t );
                return false;
            }
        }
        while ( read == TFTPSocket.BLOCK_SIZE );
        return true;
    }

    /**
     * �������� ������������� ��� ���������� ������ ������
     * 
     * @param data ������
     */
    public void sendLastAckForDataPacket(
        DATA data )
    {
        try
        {
            setAckReceived( false );
            sendAckPacket( data.getBlockNr() );

            // �Ĩ� ��� ��������� ������ ��� ������ ������� �� ������
            tftpSock.setSockTimeoutMSec( 100 );
            int count = timeout * 1000 / 100;
            while ( (!getAckReceived()) && (count > 0) )
            {
                count--;
                TFTPPacket tftpP = tftpSock.read();

                if ( tftpP == null )
                {
                    continue;
                }

                if ( tftpP instanceof DATA )
                {
                    sendAckPacket( data.getBlockNr() );
                }

                if ( tftpP instanceof ERROR )
                {
                    // ������������ ��������, ��� ������ - ������
                }
            }

            // ������ ����� ������� �����
        }
        catch ( Throwable t )
        {
            System.out.println( "Could not wait to check if last ack was received by client!" );
        }
        finally
        {
            setAckReceived( true );
            setTimeout( timeout );
        }
    }

    /**
     * ������������� ���� ��������� �������������
     * 
     * @param ackReceived ������������� ��������?
     */
    public synchronized void setAckReceived(
        boolean ackReceived )
    {
        this.ackReceived = ackReceived;
    }

    /**
     * ������������� �������
     * 
     * @param timeout ������� � ��������
     */
    public void setTimeout(
        int timeout )
    {
        this.timeout = timeout;
        try
        {
            if ( tftpSock != null )
            {
                tftpSock.setSocketTimeOut( timeout );
            }
        }
        catch ( SocketException e )
        {
            System.out.println( "Could not set socket timeout on worker socket!" );
        }
    }

    /**
     * ������������� ������ �����
     * 
     * @param tsize ������ �����
     */
    public void setTransferSize(
        int tsize )
    {
        this.tsize = tsize;
    }

    /**
     * �Ĩ� ������ �������
     * 
     * @return ������ �������?
     */
    public boolean waitingForNewRequest()
    {
        return !getAckReceived();
    }

}
