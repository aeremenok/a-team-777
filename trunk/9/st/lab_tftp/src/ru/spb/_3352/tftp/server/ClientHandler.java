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
 * сопюбкъер оепедювеи дюммшу йкхемрюл
 */
public class ClientHandler
{
    /**
     * пюглеп тюикю он слнквюмхч
     */
    public final static int DEFAULT_TSIZE = 2048 * 1024;

    /**
     * онксвемн ондрбепфдемхе
     */
    private boolean         ackReceived   = true;

    /**
     * ткюц, сйюгшбючыхи мю оепеонкмемхе йюмюкю онбрнпмн дняшкюелшлх оюйерюлх
     */
    private boolean         stuffedLink   = false;

    /**
     * ртро-янйер
     */
    TFTPSocket              tftpSock;

    /**
     * рюилюср ябъгх я йкхемрнл
     */
    private int             timeout       = 5;

    /**
     * рейсыхи пюглеп тюикю
     */
    private int             tsize         = DEFAULT_TSIZE;

    /**
     * йнмярпсйрнп
     */
    public ClientHandler()
        throws SocketException
    {
        tftpSock = new TFTPSocket( timeout );
    }

    /**
     * янедхмъер я йкхемрнл, юдпеяю б оюйерюу оепегюохяшбючряъ юдпеяюлх янйерю
     * 
     * @param inetAddress сдюк╗ммши юдпея
     * @param clientPort онпр йкхемрю
     */
    public void connect(
        InetAddress inetAddress,
        int clientPort )
    {
        tftpSock.connect( inetAddress, clientPort );
    }

    /**
     * опнбепъер йнппейрмнярэ оюйерю ондрбепфдемхъ
     * 
     * @param expecting нфхдюелши оюйер
     * @param received онксвеммши оюйер
     * @return TRUE еякх оюйерш яннрберярбсчр
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
     * нрйкчвюеряъ нр йкхемрю, бяе оюйерш нрмшме днкфмш яндепфюрэ юдпея х онпр
     */
    public void disconnect()
    {
        tftpSock.disconnect();
    }

    /**
     * вхрюер ткюц онксвемхъ ондрбепфдемхъ
     * 
     * @return ондрбпефдемхе онксвемн?
     */
    public synchronized boolean getAckReceived()
    {
        return ackReceived;
    }

    /**
     * бнгбпюыюер IP-юдпея х мнлеп онпрю йкхемрю
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
     * бнгбпюыюер рюилюср
     * 
     * @return рюилюср б яейсмдюу
     */
    public int getTimeout()
    {
        return timeout;
    }

    /**
     * бнгбпюыюер пюглеп тюикю
     * 
     * @return int пюглеп тюикю
     */
    public int getTransferSize()
    {
        return tsize;
    }

    /**
     * мнбши гюопня
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
     * онксвхрэ тюик нр йкхемрю
     * 
     * @param os бшундмни онрнй
     * @param clientAddress юдпея йкхемрю
     * @param clientPort онпр йкхемрю
     * @param useOptions ножхх
     * @return TRUE, еякх сдювмн
     */
    public boolean receiveFileFromClient(
        OutputStream os,
        InetAddress clientAddress,
        int clientPort,
        boolean useOptions )
    {
        tftpSock.connect( clientAddress, clientPort );
        Vector dataBlocks = new Vector( 10 );

        // оняшкюел оепбне ондрбепфдемхе б нрбер мю гюопня гюохях
        int blockNumber = 0;
        byte[] data = new byte[1];
        int totalSize = 0;

        ACK send;
        DATA receive;
        DATA expect;

        // еякх еярэ дно. ножхх - ондрбепфдюел ху
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

            // опнбепъел пюглеп тюикю
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

        // рюй йюй оняшкйю онякедмецн ондрбепфдемхъ лнфер гюмърэ днкцне бпелъ,
        // гюйпшбюел онрнй оепед щрхл
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

        // оняшкюел онякедмхи оюйер х фд╗л, еякх опхундхр - оепеяшкюел онякедмее
        // ондрбепфдемхе
        // FIXME врн еякх гдеяэ опхд╗р оюйер ERROR?
        sendLastAckForDataPacket( receive );

        return true;
    }

    /**
     * оняшкюер оюйер ондрбепфдемхъ
     * 
     * @param blocknumber мнлеп акнйю
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
     * оняшкюер оюйер я дюммшлх
     * 
     * @param blocknumber мнлеп акнйю
     * @param data дюммше
     * @param offset ялеыемхе
     * @param dataSize пюглеп дюммшу
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
     * оняшкюер оюйер ньхайх
     * 
     * @param errorCode йнд ньхайх
     * @param errorMsg яннаыемхе на ньхайе
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
     * оняшкюер тюик йкхемрс
     * 
     * @param is онрнй времхъ
     * @param clientAddress юдпея йкхемрю
     * @param clientPort онпр йкхемрю
     * @param useOptions хяонкэгсел днонкмхрекэмше ножхх
     * @return TRUE еякх сдювмн
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

        // нвхыюел янйер нр ярюпшу х дсакхпнбюммшу оюйернб
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
            // еякх гюопня хлек ножхх - ямювюкю ондрбепфдюел ху
            if ( useOptions )
            {
                TFTPOptions options = new TFTPOptions();
                options.setTimeout( getTimeout() );
                options.setTransferSize( getTransferSize() );
                send = new OACK( 0, options );
            }
            else
            {
                // вхрюел TFTPSocket.BLOCK_SIZE дюммшу хг онрнйю
                read = bis.read( data );

                // еякх онксвхкх -1 - онрнй гюйпшр
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
                // еякх хмтнплюжхъ б акнйюу пюбмю б рнвмнярх пюглепс тюикю -
                // мюдн онякюрэ осярни акнй онякедмхл
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
     * дняшкюел ондрбепфдемхе дкъ онякедмецн оюйерю дюммшу
     * 
     * @param data дюммше
     */
    public void sendLastAckForDataPacket(
        DATA data )
    {
        try
        {
            setAckReceived( false );
            sendAckPacket( data.getBlockNr() );

            // фд╗л дкъ оепеяшкйх дюммшу хкх мнбнцн гюопняю мю времхе
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
                    // ренперхвеяйх бнглнфмн, врн декюрэ - меъямн
                }
            }

            // йкхемр рнвмн онксвхк оюйер
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
     * сярюмюбкхбюер ткюц онксвемхъ ондрбепфдемхъ
     * 
     * @param ackReceived ондрбпефдемхе онксвемн?
     */
    public synchronized void setAckReceived(
        boolean ackReceived )
    {
        this.ackReceived = ackReceived;
    }

    /**
     * сярюмюбкхбюер рюилюср
     * 
     * @param timeout рюилюср б яейсмдюу
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
     * сярюмюбкхбюер пюглеп тюикю
     * 
     * @param tsize пюглеп тюикю
     */
    public void setTransferSize(
        int tsize )
    {
        this.tsize = tsize;
    }

    /**
     * фд╗л мнбнцн гюопняю
     * 
     * @return гюопня онксвем?
     */
    public boolean waitingForNewRequest()
    {
        return !getAckReceived();
    }

}
