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
 * ртро-йкхемр
 */
public class TFTPClient
{

    /**
     * уняр он слнквюмхч
     */
    public static final String DEFAULT_HOSTNAME = "localhost";

    /**
     * онпр он слнквюмхч
     */
    public static final int    DEFAULT_PORT     = 69;

    /**
     * люйяхлюкэмши пюглеп дюммшу б оюйере
     */
    private static final int   MAX_PACKAGE_SIZE = 8192;

    /**
     * днонкмхрекэмше ножхх - пюглеп акнйю
     */
    private int                blksize;

    /**
     * хлъ унярю
     */
    private String             hostName         = null;

    /**
     * днонкмхрекэмше ножхх - бпелъ нфхдюмхъ
     */
    private int                timeout;

    /**
     * днонкмхрекэмше ножхх - пюглеп оюйерю
     */
    private int                tsize;

    /**
     * йнмярпсйрнп
     * 
     * @param hostName хлъ унярю
     */
    public TFTPClient(
        String hostName )
    {
        super();
        this.setHostName( hostName );
    }

    /**
     * лернд дкъ гюцпсгйх тюикнб я яепбепю
     * 
     * @param rrq оюйер я гюопнянл времхъ
     * @param os бшундмни онрнй
     * @return boolean сяоеьмн?
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
        // янгдю╗л ртро-янйер
        TFTPSocket tftpSock = new TFTPSocket( timeout );

        int sequenceNumber = 1;

        // гюцксьйю
        byte[] dummyByteArray = new byte[1];

        DATA receive = new DATA( sequenceNumber, dummyByteArray );
        ACK surprisePacket = this.sendRequest( tftpSock, rrq, receive );

        if ( surprisePacket == null )
        {
            // мер нрберю нр яепбепю
            System.out.println( "Nothing returned from the server after the initial read request." );
            return false;
        }

        if ( surprisePacket instanceof OACK )
        {
            // еярэ днонкмхрекэмше ножхх
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
                // мхвецн ме опхькн я яепбепю оняке ондрбепфдемхъ онксвемхъ дно.
                // ножхи
                System.out.println( "Nothing returned from the server after ack on oack." );
                return false;
            }
        }
        else if ( surprisePacket instanceof DATA )
        {
            // хмтнплюжхъ
            receive = (DATA) surprisePacket;
        }

        // гюохяшбюел б тюик
        os.write( receive.getData() );

        // нопедекъел онпр х юдпея, бшапюммше яепбепнл, х ондйкчвюеляъ
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
                // яепбеп лнквхр
                System.out.println( "Nothing returned from the server after the transfer." );
                return false;
            }

            os.write( receive.getData() );
        }

        // реоепэ онякедмхи оюйер тюикю нрнякюм, йкхемр днкфем онякюрэ
        // ондрбепфдемхе врнаш сднярнбепхрэяъ, врн нм бя╗ онксвхк. еякх мер -
        // яепбеп ошрюеряъ днякюрэ онрепъммши оюйер
        System.out.println( "send ack to say that we have received last message." );
        ack = new ACK( sequenceNumber );
        receive = (DATA) TFTPUtils.dataTransfer( tftpSock, ack, null );

        // гюйпшбюел бшундмни онрнй
        os.close();

        return true;
    }

    /**
     * бнгбпюыюер хлъ унярю мюгмювемхъ
     * 
     * @return хлъ унярю мюгмювемхъ
     */
    private String getHostName()
    {
        return this.hostName;
    }

    /**
     * бнгбпюыюер юдпея мюгмювемхъ
     * 
     * @return юдпея мюгмювемхъ
     * @throws UnknownHostException
     */
    private InetAddress getInetAddress()
        throws UnknownHostException
    {
        return InetAddress.getByName( this.getHostName() );
    }

    /**
     * бнгбпюыюер ножхч "пюглеп акнйю"
     * 
     * @return пюглеп акнйю
     */
    public int getOptionBlockSize()
    {
        return blksize;
    }

    /**
     * бнгбпюыюер ножхч "рюилюср"
     * 
     * @return рюилюср
     */
    public int getOptionTimeout()
    {
        return timeout;
    }

    /**
     * бнгбпюыюер ножхч "пюглеп тюикю"
     * 
     * @return пюглеп тюикю
     */
    public int getOptionTransferSize()
    {
        return tsize;
    }

    /**
     * ядекюрэ гюопня мю яйювхбюмхе
     * 
     * @param fileName хлъ тюикю
     * @param optionTimeout рюилюср
     * @param optionTransferSize пюглеп оепедювх
     * @param optionBlockSize пюглеп акнйю
     * @return оюйер
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
        // гюопня мю оепедювс я тюикю я яепбепю он ецн хлемх
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
     * гюопня мю гюкхбйс тюикю мю яепбеп
     * 
     * @param fileName хлъ тюикю
     * @param optionTimeout рюилюср
     * @param optionTransferSize пюглеп оепедювх
     * @param optionBlockSize пюглеп акнйю
     * @return оюйер
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
        // тнплхпсел гюопня дкъ гюцпсгйх тюикю мю яепбеп
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
     * сярюмнбйю янедхмемхъ лефдс йкхемрнл х яепбепнл
     * 
     * @param tftpSock ртро-янйер
     * @param frq гюопня
     * @param recv нрбер
     * @return нрбер
     */
    public ACK sendRequest(
        TFTPSocket tftpSock,
        FRQ frq,
        ACK recv )
    {

        int retransmits = 0;
        int spamcount = 0;

        // ткюц, сйюгшбючыхи мю анкэьне йнкхвеярбн онбрнпмн днякюммшу яннаыемхи
        // б йюмюке, сбекхвхбюер гюдепфйс оепед дняшкйни, врнаш ме гюахбюрэ
        // йюмюк
        boolean stuffedLink = false;

        // оняшкюел оюйер
        try
        {
            tftpSock.write( frq );

            // еякх йюмюк гюахр оюйерюлх - фд╗л бдбне днкэье
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

        // фд╗л ондрбепфдемхъ
        while ( receiving )
        {
            try
            {
                tftpP = tftpSock.read();

                // нвхыюел ткюц укюлю б йюмюке, р.й. онднфдюкх сфе днярюрнвмн
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

            // мхвецн ме онксвхкх б нрбер
            if ( tftpP == null )
            {
                if ( retransmits++ > 5 )
                {
                    // якхьйнл лмнцн онбрнпмшу оепеяшкнй
                    TFTPUtils.sendErrPacket( tftpSock, ERROR.ERR_NOT_DEFINED, "Retransmit limit exceeded" );
                    System.out.println( TFTPUtils.getClient( tftpSock ) + " Maximum retransmit count exceeded" );
                    return null;
                }
                else
                {
                    // оепеяшкюел оюйер еы╗ пюг
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

            // онксвхкх ньханвмши оюйер
            if ( tftpP instanceof ERROR )
            {
                System.out.println( TFTPUtils.getClient( tftpSock ) + " " + ((ERROR) tftpP).getErrorMessage() );
                return null;
            }

            // еякх онксвхкх ондрбепфдемхе опхмърхъ ножхи блеярн дюммшу
            if ( (tftpP instanceof OACK) && (recv instanceof DATA) && (recv.getBlockNr() == 1) )
            {
                return (ACK) tftpP;
            }

            // еякх онксвхкх ондрбепфдемхъ ножхи блеярн ондрбепфдемхъ дюммшу
            if ( (tftpP instanceof OACK) && (recv instanceof ACK) && (recv.getBlockNr() == 0) )
            {
                return (ACK) tftpP;
            }

            // онксвхкх врн унрекх
            if ( (tftpP instanceof ACK) && TFTPUtils.correctAnswer( recv, (ACK) tftpP ) )
            {
                return (ACK) tftpP;
            }

            // вэх-рн кебше оюйерш, еякх якхьйнл лмнцн - ньхайю
            if ( spamcount++ > 5 )
            {
                return null;
            }
        }
        return null;
    }

    /**
     * сярюмюбкхбюер хлъ унярю мюгмювемхъ
     * 
     * @param hostName мнбне хлъ унярю мюгмювемхъ
     */
    private void setHostName(
        String hostName )
    {
        this.hostName = hostName;
    }

    /**
     * лернд дкъ гюкхбйх тюикнб мю яепбеп я йкхемрю
     * 
     * @param wrq гюопня мю гюохяэ
     * @param is бундмни онрнй
     * @return boolean сдювмн?
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

        // яепбеп днкфем онякюрэ оюйер ондрбепфдемхъ я мнлепнл акнйю 0
        if ( receive == null )
        {
            // яепбеп лнквхр
            System.out.println( "Nothing returned from the server after the initial send." );
            return false;
        }

        if ( receive.getBlockNr() != 0 )
        {
            // ме рнр мнлеп акнйю
            System.out.println( "The server has sent an ACK with wrong block number." );
            return false;
        }

        if ( receive instanceof OACK )
        {
            // б ондрбепфдемхх я яепбепю еярэ днонкмхрекэмше ножхх!!!
            OACK oack = (OACK) receive;
            tsize = oack.getTransferSize();
            timeout = oack.getTimeout();
            blksize = oack.getBlockSize();
        }

        // нопедекъел онпр, йнрнпши яепбеп бшапюк дкъ янедхмемхъ
        int serverPort = receive.getPort();
        System.out.println( "The server has chosen the following port as the communication port: " + serverPort );
        InetAddress serverAddress = wrq.getAddress();
        tftpSock.connect( serverAddress, serverPort );

        // вхрюел тюик х оепеяшкюел
        byte[] sendBytes = new byte[MAX_PACKAGE_SIZE];
        DATA send = new DATA();
        int returnValue = 0;

        while ( (returnValue = is.read( sendBytes )) != -1 )
        {
            System.out.println( "sending packet number: " + sequenceNumber );

            // йнмбепрхпсел люяяхб аюир б йнппейрмши тнплюр оюйерю ртро
            send = new DATA( ++sequenceNumber, sendBytes, 0, returnValue );
            receive = new ACK( sequenceNumber );

            // оняшкюел мю яепбеп, б нрбер онксвюел ондрбепфдемхе
            receive = TFTPUtils.dataTransfer( tftpSock, send, receive );
        }

        // ме гюашбюел гюйпшрэ онрнй дкъ времхъ
        is.close();

        return true;
    }
}
