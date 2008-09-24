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
 * напюанрвхй гюопнянб
 */
public class TFTPRequestHandler
{

    /**
     * йнмрейяр
     */
    Context                   ctx;

    /**
     * пюглеп акнйю он слнквюмхч - 2048 йа
     */
    int                       DEF_BLKSIZE = 2048;

    /**
     * рюилюср он слнквюмхч - 5 яейсмд
     */
    int                       DEF_TIMEOUT = 5;

    /**
     * пюглеп тюикю он слнквюмхч - 200 ла
     */
    int                       DEF_TSIZE   = 100 * 2048 * 1024;

    /**
     * яксьюрекэ янашрхи
     */
    private EventListener     listener    = null;

    /**
     * ртро-йкхемр
     */
    ClientHandler             tftpClient;

    /**
     * рейсыхи онрнй
     */
    Thread                    thisThread;

    /**
     * яяшкйю мю тюикнбсч яхярелс дкъ дюкэмеиьецн хяонкэгнбюмхъ
     */
    private VirtualFileSystem vfs         = null;

    /**
     * йнмярпсйрнп
     * 
     * @param vfs бхпрсюкэмюъ тюикнбюъ яхярелш
     * @param listener яксьюрекэ янашрхи
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
     * рнвйю бундю б онрнй бшонкмемхъ
     * 
     * @param frq гюопня
     * @param clientAddress юдпея йкхемрю
     * @param clientPort онпр йкхемрю
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

        // янедхмъел ртро-йкхемр я йкхемрнл
        tftpClient.connect( clientAddress, clientPort );

        // онксвюел рюилюср
        int timeout = frq.getTimeout();
        if ( timeout <= 0 )
        {
            timeout = DEF_TIMEOUT;
        }
        tftpClient.setTimeout( timeout );

        // онксвюел пюглеп тюикю
        int tsize = frq.getTransferSize();
        if ( tsize < 0 )
        {
            tsize = DEF_TSIZE;
        }
        tftpClient.setTransferSize( tsize );

        // онксвюел рюилюср
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

            // цемепхпсел янашрхе он нйнмвюмхх гюйювйх
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

            // онксвюел тюик нр йкхемрю
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
     * нярюмнбйю онрнйю
     */
    public void stop()
    {
        // нрйкчвюеляъ нр йкхемрю
        tftpClient.disconnect();
    }

    /**
     * бшгшбюеряъ йнцдю йкхемр оняшкюер дпсцни гюопня мю времхе хкх гюохяэ, б рн
     * бпелъ йюй щрнр напюанрвхй еы╗ гюмър опедшдсыхл гюопнянл
     * 
     * @param frq гюопня
     * @return напюанрвхй ябнандем?
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
