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
 * ÒÔÒÏ-ÊËÈÅÍÒ
 */
public class TFTPClient
{

    /**
     * ÏÎÐÒ ÏÎ ÓÌÎË×ÀÍÈÞ
     */
    public static final int    DEFAULT_PORT     = 69;

    /**
     * ÕÎÑÒ ÏÎ ÓÌÎË×ÀÍÈÞ
     */
    public static final String DEFAULT_HOSTNAME = "localhost";

    /**
     * ÌÀÊÑÈÌÀËÜÍÛÉ ÐÀÇÌÅÐ ÏÀÊÅÒÀ
     */
    private static final int   MAX_PACKAGE_SIZE = 2048;

    /**
     * ÈÌß ÕÎÑÒÀ
     */
    private String             hostName         = null;

    /**
     * ÄÎÏÎËÍÈÒÅËÜÍÛÅ ÎÏÖÈÈ - ÂÐÅÌß ÎÆÈÄÀÍÈß
     */
    private int                timeout;

    /**
     * ÄÎÏÎËÍÈÒÅËÜÍÛÅ ÎÏÖÈÈ - ÐÀÇÌÅÐ ÏÀÊÅÒÀ
     */
    private int                tsize;

    /**
     * ÊÎÍÑÒÐÓÊÒÎÐ
     * 
     * @param hostName ÈÌß ÕÎÑÒÀ
     */
    public TFTPClient(
        String hostName )
    {
        super();
        this.setHostName( hostName );
    }

    /**
     * ÑÄÅËÀÒÜ ÇÀÏÐÎÑ ÍÀ ÑÊÀ×ÈÂÀÍÈÅ
     * 
     * @param fileName ÈÌß ÔÀÉËÀ
     * @param optionTimeout ÒÀÉÌÀÓÒ
     * @param optionTransferSize ÐÀÇÌÅÐ ÏÅÐÅÄÀ×È
     * @return ÏÀÊÅÒ
     * @throws InstantiationException
     * @throws UnknownHostException
     */
    public RRQ initializeDownload(
        String fileName,
        int optionTimeout,
        int optionTransferSize )
        throws InstantiationException,
            UnknownHostException
    {
        // ÇÀÏÐÎÑ ÍÀ ÏÅÐÅÄÀ×Ó Ñ ÔÀÉËÀ Ñ ÑÅÐÂÅÐÀ ÏÎ ÅÃÎ ÈÌÅÍÈ
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

        rrq.setMode( FRQ.OCTET_MODE );
        rrq.setAddress( InetAddress.getByName( this.getHostName() ) );
        rrq.setPort( DEFAULT_PORT );

        return rrq;
    }

    /**
     * ÇÀÏÐÎÑ ÍÀ ÇÀËÈÂÊÓ ÔÀÉËÀ ÍÀ ÑÅÐÂÅÐ
     * 
     * @param fileName ÈÌß ÔÀÉËÀ
     * @param optionTimeout ÒÀÉÌÀÓÒ
     * @param optionTransferSize ÐÀÇÌÅÐ ÏÅÐÅÄÀ×È
     * @return ÏÀÊÅÒ
     * @throws InstantiationException
     * @throws UnknownHostException
     */
    public WRQ initializeUpload(
        String fileName,
        int optionTimeout,
        int optionTransferSize )
        throws InstantiationException,
            UnknownHostException
    {
        // ÔÎÐÌÈÐÓÅÌ ÇÀÏÐÎÑ ÄËß ÇÀÃÐÓÇÊÈ ÔÀÉËÀ ÍÀ ÑÅÐÂÅÐ
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

        wrq.setMode( FRQ.OCTET_MODE );
        wrq.setAddress( InetAddress.getByName( getHostName() ) );
        wrq.setPort( DEFAULT_PORT );

        return wrq;
    }

    /**
     * ÌÅÒÎÄ ÄËß ÇÀÃÐÓÇÊÈ ÔÀÉËÎÂ Ñ ÑÅÐÂÅÐÀ
     * 
     * @param rrq ÏÀÊÅÒ Ñ ÇÀÏÐÎÑÎÌ ×ÒÅÍÈß
     * @param os ÂÛÕÎÄÍÎÉ ÏÎÒÎÊ
     * @return boolean ÓÑÏÅØÍÎ?
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
        // ÑÎÇÄÀ¨Ì ÒÔÒÏ-ÑÎÊÅÒ
        TFTPSocket tftpSock = new TFTPSocket( 5 );

        int sequenceNumber = 1;

        byte[] dummyByteArray = new byte[1];
        // ÇÀÃËÓØÊÀ

        DATA receive = new DATA( sequenceNumber, dummyByteArray );
        ACK surprisePacket = this.sendRequest( tftpSock, rrq, receive );

        if ( surprisePacket == null )
        {
            // ÍÅÒ ÎÒÂÅÒÀ ÎÒ ÑÅÐÂÅÐÀ
            System.out.println( "Nothing returned from the server after the initial read request." );
            return false;
        }

        if ( surprisePacket instanceof OACK )
        {
            // ÅÑÒÜ ÄÎÏÎËÍÈÒÅËÜÍÛÅ ÎÏÖÈÈ
            OACK oack = (OACK) surprisePacket;
            tsize = oack.getTransferSize();
            timeout = oack.getTimeout();

            ACK ack = new ACK( 0 );
            ack.setPort( surprisePacket.getPort() );
            ack.setAddress( surprisePacket.getAddress() );

            receive = (DATA) TFTPUtils.dataTransfer( tftpSock, ack, receive );
            if ( receive == null )
            {
                // ÍÈ×ÅÃÎ ÍÅ ÏÐÈØËÎ Ñ ÑÅÐÂÅÐÀ ÏÎÑËÅ ÏÎÄÒÂÅÐÆÄÅÍÈß ÏÎËÓ×ÅÍÈß ÄÎÏ.
                // ÎÏÖÈÉ
                System.out.println( "Nothing returned from the server after ack on oack." );
                return false;
            }
        }
        else if ( surprisePacket instanceof DATA )
        {
            // ÈÍÔÎÐÌÀÖÈß
            receive = (DATA) surprisePacket;
        }

        // ÇÀÏÈÑÛÂÀÅÌ Â ÔÀÉË
        os.write( receive.getData() );

        // ÎÏÐÅÄÅËßÅÌ ÏÎÐÒ È ÀÄÐÅÑ, ÂÛÁÐÀÍÍÛÅ ÑÅÐÂÅÐÎÌ, È ÏÎÄÊËÞ×ÀÅÌÑß
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
                // ÑÅÐÂÅÐ ÌÎË×ÈÒ
                System.out.println( "Nothing returned from the server after the transfer." );
                return false;
            }

            os.write( receive.getData() );
        }

        // ÒÅÏÅÐÜ ÏÎÑËÅÄÍÈÉ ÏÀÊÅÒ ÔÀÉËÀ ÎÒÎÑËÀÍ, ÊËÈÅÍÒ ÄÎËÆÅÍ ÏÎÑËÀÒÜ
        // ÏÎÄÒÂÅÐÆÄÅÍÈÅ ×ÒÎÁÛ ÓÄÎÑÒÎÂÅÐÈÒÜÑß, ×ÒÎ ÎÍ ÂÑ¨ ÏÎËÓ×ÈË. ÅÑËÈ ÍÅÒ -
        // ÑÅÐÂÅÐ ÏÛÒÀÅÒÑß ÄÎÑËÀÒÜ ÏÎÒÅÐßÍÍÛÉ ÏÀÊÅÒ
        System.out.println( "send ack to say that we have received last message." );
        ack = new ACK( sequenceNumber );
        receive = (DATA) TFTPUtils.dataTransfer( tftpSock, ack, null );

        // ÇÀÊÐÛÂÀÅÌ ÂÛÕÎÄÍÎÉ ÏÎÒÎÊ
        os.close();

        return true;
    }

    /**
     * Method that is responsible for the mechanism by which files are sent to
     * the client from the server. Upload - Sending from the client to the
     * server..... 1. send a WRQ to the server. 2. receive an ACK with 0 for
     * block number from the server --- 3. need to check that it isnt an ERROR
     * code and is definitely an ACK! *** this is done in the sendRequest()
     * method 4. read the first package from the file and transfer to server 5.
     * receive an ACK from the server with the block number that has been sent.
     * 6. check the block number, if this does not correspond with the block
     * sent, then resend.
     * 
     * @param wrq The Write Request object
     * @param is
     * @return boolean
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
        // create a TFTP Socket
        TFTPSocket tftpSock = new TFTPSocket( 5 );

        int sequenceNumber = 0;

        ACK receive = new ACK( 0 );
        receive = this.sendRequest( tftpSock, wrq, receive );

        // the server should send an ACK (where block number=0, need to
        // check...)
        if ( receive == null )
        {
            // nothing returned from server...should throw an exception at this
            // point.
            System.out.println( "Nothing returned from the server after the initial send." );
            return false;
        }
        if ( receive.getBlockNr() != 0 )
        {
            // nothing returned from server...should throw an exception at this
            // point.
            System.out.println( "The server has sent an ACK with wrong block number." );
            return false;
        }

        if ( receive instanceof OACK )
        {
            // we received some extra's
            OACK oack = (OACK) receive;
            tsize = oack.getTransferSize();
            timeout = oack.getTimeout();
        }

        // need to find the port and address the server has chosen to
        // communicate on and connect to it.
        int serverPort = receive.getPort();
        System.out.println( "The server has chosen the following port as the communication port: " + serverPort );
        InetAddress serverAddress = wrq.getAddress();
        tftpSock.connect( serverAddress, serverPort );

        // need to read the file and send to server....
        byte[] sendBytes = new byte[MAX_PACKAGE_SIZE];
        DATA send = new DATA();
        int returnValue = 0;

        while ( (returnValue = is.read( sendBytes )) != -1 )
        {
            System.out.println( "sending packet number: " + sequenceNumber );

            // need to convert the byte array into correct TFTP format for the
            // DATA obejct
            send = new DATA( ++sequenceNumber, sendBytes, 0, returnValue );
            receive = new ACK( sequenceNumber );

            // now send to server, which in turn sends an acknowledgement
            receive = TFTPUtils.dataTransfer( tftpSock, send, receive );

        }
        // must remember to close the inputstream!
        is.close();

        return true;
    }

    /**
     * This method is responsible for the initial communication between the
     * client and the server .
     * 
     * @param tftpSock
     * @param frq
     * @param recv
     * @return
     */
    public ACK sendRequest(
        TFTPSocket tftpSock,
        FRQ frq,
        ACK recv )
    {

        int retransmits = 0;
        int spamcount = 0;

        /**
         * Boolean flag that is used internally to indicate we might have
         * stuffed the connection to the client with resent packages. This flag
         * is cleared when we duplicate the timeout with the next data package
         * sent which should help to clean up the channel that did get stuffed
         */
        boolean stuffedLink = false;

        // send the packet....
        try
        {
            tftpSock.write( frq );
            // if stuffed use duplicate timeout
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

        // wait for successful acknowledgement!
        while ( receiving )
        {
            try
            {
                tftpP = tftpSock.read();

                // set timeout back because we gave enough time to clean up
                // stuffed channel
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

            // case we did not receive any packet
            if ( tftpP == null )
            {
                if ( retransmits++ > 5 )
                {
                    // Too many retries, give up.
                    TFTPUtils.sendErrPacket( tftpSock, ERROR.ERR_NOT_DEFINED, "Retransmit limit exceeded" );
                    System.out.println( TFTPUtils.getClient( tftpSock ) + " Maximum retransmit count exceeded" );
                    return null;
                }
                else
                {
                    // resend the packet and wait again!
                    System.out.println( TFTPUtils.getClient( tftpSock )
                                        + " expected packet before time out, sending ACK/DATA again" );
                    try
                    {
                        tftpSock.write( frq );
                        // set the flag to indicate that we might be stuffing
                        // the pipe for the
                        // client, next packet needs to have longer timeout to
                        // give client some
                        // time to clean up the pipe
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

            // case we received error
            if ( tftpP instanceof ERROR )
            {
                System.out.println( TFTPUtils.getClient( tftpSock ) + " " + ((ERROR) tftpP).getErrorMessage() );
                return null;
            }

            // case we did recieve option acknowledgement while we expect
            // data(1)
            if ( (tftpP instanceof OACK) && (recv instanceof DATA) && (recv.getBlockNr() == 1) )
            {
                return (ACK) tftpP;
            }

            // case we did recieve option acknowledgement while we expect ack(0)
            if ( (tftpP instanceof OACK) && (recv instanceof ACK) && (recv.getBlockNr() == 0) )
            {
                return (ACK) tftpP;
            }

            // case we did receive expected
            if ( (tftpP instanceof ACK) && TFTPUtils.correctAnswer( recv, (ACK) tftpP ) )
            {
                return (ACK) tftpP;
            }

            // all other is spam and when too many of this crap is give up, and
            // do not signal
            if ( spamcount++ > 5 )
            {
                return null;
            }
        }
        return null;
    }

    public int getOptionTimeout()
    {
        return timeout;
    }

    public int getOptionTransferSize()
    {
        return tsize;
    }

    private void setHostName(
        String hostName )
    {
        this.hostName = hostName;
    }

    private String getHostName()
    {
        return this.hostName;
    }

    private InetAddress getInetAddress()
        throws UnknownHostException
    {
        return InetAddress.getByName( this.getHostName() );
    }
}
