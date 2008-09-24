package ru.spb._3352.tftp.common;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * ��������� ��������� �� ������ � ������ � �����
 */
public class TFTPSocket
{
    /**
     * ������ �����
     */
    public static final int BLOCK_SIZE = 2048;

    /**
     * ����
     */
    static int              handlePort;

    /**
     * ���������� �������� ��������� ��������� �����
     * 
     * @return �����
     * @throws SocketException
     */
    private static DatagramSocket getFreeSocket()
        throws SocketException
    {
        int loopPort = handlePort - 1;
        while ( loopPort != handlePort )
        {
            if ( (handlePort < 29001) || (++handlePort > 65000) )
            {
                handlePort = 29001;
            }
            try
            {
                DatagramSocket freeSocket = new DatagramSocket( handlePort );
                return freeSocket;
            }
            catch ( SocketException e )
            {
                // ���������� ����� ���������� ������
                continue;
            }
        }

        // ��������� ������ ���
        System.out.println( "Could not find a free port!" );
        throw new SocketException();
    }

    /**
     * ����� ������
     */
    byte[]         buffer;

    /**
     * ����� ����������
     */
    InetAddress    destAddr;

    /**
     * ���� ����������
     */
    int            destPort;

    /**
     * ������� � �������������
     */
    private int    timeout;

    /**
     * �����
     */
    DatagramSocket udpSocket;

    /**
     * �����������
     * 
     * @param timeout �������
     * @throws SocketException
     */
    public TFTPSocket(
        int timeout )
        throws SocketException
    {
        udpSocket = getFreeSocket();
        this.setSocketTimeOut( timeout );

        buffer = new byte[BLOCK_SIZE + 16];
    }

    /**
     * ������� ������
     */
    public void clear()
    {
        try
        {
            udpSocket.setSoTimeout( 10 );
        }
        catch ( SocketException e1 )
        {
            System.out.println( e1 );
            e1.printStackTrace();
        }

        byte[] data = new byte[516];
        DatagramPacket udpPacket = new DatagramPacket( data, data.length );

        while ( true )
        {
            try
            {
                udpSocket.receive( udpPacket );
            }
            catch ( SocketTimeoutException ste )
            {
                try
                {
                    udpSocket.setSoTimeout( timeout );
                }
                catch ( SocketException e1 )
                {
                    System.out.println( e1 );
                    e1.printStackTrace();
                }

                return;
            }
            catch ( Exception e )
            {
                System.out.println( "[clear] :" + e.getMessage() );

                try
                {
                    udpSocket.setSoTimeout( timeout );
                }
                catch ( SocketException e1 )
                {
                    System.out.println( e1 );
                    e1.printStackTrace();
                }

                return;
            }
        }
    }

    /**
     * ��������� � �������
     * 
     * @param addr �����
     * @param port ����
     */
    public void connect(
        InetAddress addr,
        int port )
    {
        udpSocket.disconnect();
        destAddr = addr;
        destPort = port;
        udpSocket.connect( destAddr, destPort );
    }

    /**
     * ����������� �� ������
     */
    public void disconnect()
    {
        udpSocket.disconnect();
        udpSocket.close();
    }

    /**
     * ���������� ����� ����������
     * 
     * @return ����� ����������
     */
    public InetAddress getAddress()
    {
        return destAddr;
    }

    /**
     * ���������� ���� ����������
     * 
     * @return ���� ����������
     */
    public int getPort()
    {
        return destPort;
    }

    /**
     * ���������� ������� ������ � ��������
     * 
     * @return ������� ������
     */
    public int getSocketTimeOut()
    {
        return timeout / 1000;
    }

    /**
     * ������ ������ �� ������
     * 
     * @return ����� ����
     * @throws IOException
     */
    public TFTPPacket read()
        throws IOException
    {
        DatagramPacket udpPacket = new DatagramPacket( buffer, BLOCK_SIZE + 16 );

        try
        {
            udpSocket.receive( udpPacket );
        }
        catch ( InterruptedIOException e )
        {
            // ����� �����, ������ ���
            return null;
        }

        byte[] udpData = udpPacket.getData();
        int udpLength = udpPacket.getLength();
        System.out.println( "received udpPacket length: " + udpLength );

        // �������� ������
        byte[] tftpPB = new byte[udpPacket.getLength()];
        System.arraycopy( udpData, udpPacket.getOffset(), tftpPB, 0, udpLength );

        TFTPPacket tftpP = null;

        try
        {
            int opcode = TFTPPacket.fetchOpCode( tftpPB );

            switch ( opcode )
            {
                case ACK.OPCODE:
                    tftpP = new ACK( tftpPB );
                    break;
                case DATA.OPCODE:
                    tftpP = new DATA( tftpPB, udpLength );
                    break;
                case RRQ.OPCODE:
                    tftpP = new RRQ( tftpPB );
                    break;
                case WRQ.OPCODE:
                    tftpP = new WRQ( tftpPB );
                    break;
                case OACK.OPCODE:
                    tftpP = new OACK( tftpPB );
                    break;
                case ERROR.OPCODE:
                    tftpP = new ERROR( tftpPB );
                    break;
                default:
                    System.out.println( "Unknown opcode: " + opcode );
                    break;
            }
        }
        catch ( InstantiationException e )
        {
            throw new IOException( "Could not discover tftp packet in recieved data!" + e.getMessage() );
        }

        tftpP.setPort( udpPacket.getPort() );
        tftpP.setAddress( udpPacket.getAddress() );

        return tftpP;
    }

    /**
     * ������������� ������� ������ � ��������
     * 
     * @param secs ������� ������
     */
    public void setSocketTimeOut(
        int secs )
        throws SocketException
    {
        this.timeout = secs * 1000;
        udpSocket.setSoTimeout( secs * 1000 );
    }

    /**
     * ������������� ������� ������ � �������������
     * 
     * @param msecs ������� ������
     * @throws SocketException
     */
    public void setSockTimeoutMSec(
        int msecs )
        throws SocketException
    {
        this.timeout = msecs;
        udpSocket.setSoTimeout( msecs );
    }

    /**
     * ������ � �����
     * 
     * @param tftpP ����-����� � ���� ������� ����
     * @throws IOException
     */
    public void write(
        TFTPPacket tftpP )
        throws IOException
    {
        byte[] data = tftpP.getBytes();
        InetAddress address = tftpP.getAddress();

        int port = tftpP.getPort();

        if ( udpSocket.isConnected() )
        {
            address = destAddr;
            port = destPort;
        }

        DatagramPacket udpPacket = new DatagramPacket( data, data.length, address, port );
        udpSocket.send( udpPacket );
    }
}
