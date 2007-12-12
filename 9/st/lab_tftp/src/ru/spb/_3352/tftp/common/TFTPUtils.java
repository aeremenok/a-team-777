package ru.spb._3352.tftp.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * ��������� ����� ������� ��� ����
 */
public class TFTPUtils
{

    /**
     * ��������� ���������� ����� �������������, ��� �������� � ����� �����
     * 
     * @param expecting ��������� �����
     * @param received ���������� �����
     * @return TRUE ���� ������ ���������, FALSE �����
     */
    public static boolean correctAnswer(
        ACK expecting,
        ACK received )
    {
        if ( expecting == null || received == null )
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
     * �������� ������
     * 
     * @param tftpSock �����
     * @param send ��������� �����
     * @param recv ���������� �����
     * @return
     */
    public static ACK dataTransfer(
        TFTPSocket tftpSock,
        ACK send,
        ACK recv )
        throws IOException
    {
        int retransmits = 0;
        int spamcount = 0;

        // ���� ������˨����� ������, ��������������� � ������ ��������� ������
        // ����� �������� ���������� �������. ��������� ��� �������� ��������,
        // ����� ���������� ����� �� ����������� �������
        boolean stuffedLink = false;
        int timeout = tftpSock.getSocketTimeOut();

        // �������� �����
        tftpSock.write( send );

        TFTPPacket tftpP;
        boolean receiving = true;

        // �Ĩ� �������������
        while ( receiving )
        {
            tftpP = tftpSock.read();

            // ���� ������ �� ��������
            if ( tftpP == null )
            {
                // � �� �Ĩ� ������, ������ ��� �������� ���������
                // �������������. ������ ���������� �Ĩ� ���� ��� ���Ĩ�, ��
                // ������ �������������� ���������, ����� ��� ����������.
                if ( recv == null )
                {
                    return null;
                }

                // ���� ��������� ������ ������� - �������
                if ( retransmits++ > 5 )
                {
                    throw new IOException( getClient( tftpSock ) + " Maximum retransmit count exceeded" );
                }

                // ���������� ����� � ����� �Ĩ�
                System.out.println( getClient( tftpSock ) + " expected packet before time out, sending ACK/DATA again" );
                tftpSock.write( send );

                // ������������� ����, ��� ����� �� ������������ �����, �����
                // ����� ������� ������, ����� ���������� �����
                stuffedLink = true;

                try
                {
                    tftpSock.setSocketTimeOut( tftpSock.getSocketTimeOut() * 2 );
                }
                catch ( SocketException e )
                {
                    System.out
                              .println( getClient( tftpSock ) + "Could not change timeout on socket. " + e.getMessage() );
                    // ����������
                }

                continue;
            }

            // ���� �������� ������
            if ( tftpP instanceof ERROR )
            {
                throw new IOException( getClient( tftpSock ) + " " + ((ERROR) tftpP).getErrorMessage() );
            }

            // �������� ��� ������
            if ( (tftpP instanceof ACK) && correctAnswer( recv, (ACK) tftpP ) )
            {
                // ������������� ������� �������
                try
                {
                    if ( stuffedLink )
                    {
                        stuffedLink = false;
                        tftpSock.setSocketTimeOut( timeout );
                    }
                }
                catch ( SocketException e )
                {
                    System.out
                              .println( getClient( tftpSock ) + "Could not change timeout on socket. " + e.getMessage() );
                    // ����������
                }
                return (ACK) tftpP;
            }

            // ���� ������� ����� ����������� ������� - �������, ��� ������ ��
            // ������
            if ( spamcount++ > 5 )
            {
                return null;
            }
        }
        return null;
    }

    /**
     * ���������� IP � ���� �������, � ������� �Ĩ� ������
     * 
     * @param tftpSock �����
     * @return ������, � IP � ������ �������
     */
    public static String getClient(
        TFTPSocket tftpSock )
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
     * ������� ��������� �� ������
     * 
     * @param tftpSock �����
     * @param errorCode ��� ������
     * @param errorMsg �������� ������
     */
    public static void sendErrPacket(
        TFTPSocket tftpSock,
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
            System.out.println( getClient( tftpSock ) + " UDP send ERROR packet failure." );
            System.out.println( e.toString() );
            return;
        }

        System.out.println( "SEND ERROR" + " [" + getClient( tftpSock ) + "] EC = [" + errorCode + "] " + errorMsg );
    }

    private TFTPUtils()
    {
        // ���������� ����� ������ ������ ���������
    }
}
