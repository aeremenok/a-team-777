package ru.spb._3352.tftp.common;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * сопюбкъер гюопняюлх мю времхе х гюохяэ б янйер
 */
public class TFTPSocket
{
    /**
     * пюглеп акнйю
     */
    public static final int BLOCK_SIZE = 2048;

    /**
     * онпр
     */
    static int              handlePort;

    /**
     * бнгбпюыюер яксвюимн бшапюммши ябнандмши янйер
     * 
     * @return янйер
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
                // опнднкфюел онхяй ябнандмнцн янйерю
                continue;
            }
        }

        // ябнандмшу онпрнб мер
        System.out.println( "Could not find a free port!" );
        throw new SocketException();
    }

    /**
     * астеп аюирнб
     */
    byte[]         buffer;

    /**
     * юдпея мюгмювемхъ
     */
    InetAddress    destAddr;

    /**
     * онпр мюгмювемхъ
     */
    int            destPort;

    /**
     * рюилюср б лхккхяейсмдюу
     */
    private int    timeout;

    /**
     * янйер
     */
    DatagramSocket udpSocket;

    /**
     * йнмярпсйрнп
     * 
     * @param timeout рюилюср
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
     * нвхярйю янйерю
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
     * янедхмхрэ я янйернл
     * 
     * @param addr юдпея
     * @param port онпр
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
     * нрйкчвюеляъ нр янйерю
     */
    public void disconnect()
    {
        udpSocket.disconnect();
        udpSocket.close();
    }

    /**
     * бнгбпюыюер юдпея мюгмювемхъ
     * 
     * @return юдпея мюгмювемхъ
     */
    public InetAddress getAddress()
    {
        return destAddr;
    }

    /**
     * бнгбпюыюер онпр мюгмювемхъ
     * 
     * @return онпр мюгмювемхъ
     */
    public int getPort()
    {
        return destPort;
    }

    /**
     * бнгбпюыюер рюилюср янйерю б яейсмдюу
     * 
     * @return рюилюср янйерю
     */
    public int getSocketTimeOut()
    {
        return timeout / 1000;
    }

    /**
     * времхе дюммшу хг янйерю
     * 
     * @return оюйер ртро
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
            // бпелъ бшькн, оюйерю мер
            return null;
        }

        byte[] udpData = udpPacket.getData();
        int udpLength = udpPacket.getLength();
        System.out.println( "received udpPacket length: " + udpLength );

        // йнохпсел дюммше
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
     * сярюмюбкхбюер рюилюср янйерю б яейсмдюу
     * 
     * @param secs рюилюср янйерю
     */
    public void setSocketTimeOut(
        int secs )
        throws SocketException
    {
        this.timeout = secs * 1000;
        udpSocket.setSoTimeout( secs * 1000 );
    }

    /**
     * сярюмюбкхбюер рюилюср янйерю б лхккхяейсмдюу
     * 
     * @param msecs рюилюср янйерю
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
     * гюохяэ б янйер
     * 
     * @param tftpP ртро-оюйер б бхде люяяхбю аюир
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
