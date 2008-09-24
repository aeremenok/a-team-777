package ru.spb._3352.tftp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Hashtable;

import ru.spb._3352.tftp.common.FRQ;
import ru.spb._3352.tftp.common.RRQ;
import ru.spb._3352.tftp.common.TFTPPacket;
import ru.spb._3352.tftp.common.VirtualFileSystem;
import ru.spb._3352.tftp.common.WRQ;

/**
 * янйермши яепбеп дкъ гюосяйю б нрдекэмнл онрнйе
 */
public class TFTPServerSocket
    implements
        Runnable
{
    /**
     * нрлемю ноепюжхх
     */
    boolean           abort;

    /**
     * нвепедэ яннаыемхи, нфхдючыху напюанрйх
     */
    private Hashtable newConnects;

    /**
     * йнкхвеярбн напюанрвхйнб гюопнянб й цкюбмнлс янйерс ртро
     */
    private int       poolSize;

    /**
     * онпр яепбепю
     */
    int               serverPort;

    /**
     * янйер
     */
    DatagramSocket    serverSocket;

    /**
     * оск напюанрвхйнб
     */
    private TFTPPool  workers;

    /**
     * йнмярпсйрнп
     * 
     * @param serverPort мнлеп онпрю
     * @param poolSize пюглеп оскю
     * @param vfs бхпрсюкэмюъ тюикнбюъ яхярелю
     * @param listener яксьюрекэ
     */
    public TFTPServerSocket(
        int serverPort,
        int poolSize,
        VirtualFileSystem vfs,
        EventListener listener )
    {
        this.serverPort = serverPort;
        this.poolSize = poolSize;

        // янгдю╗р нвепедэ дкъ онярсоючыху яннаыемхи, мнбши оск онрнйнб х
        // гюонкмъер ецн онрнйюлх напюанрйх

        newConnects = new Hashtable();

        System.out.println( "Creating new pool of " + poolSize + " worker threads" );
        workers = new TFTPPool( poolSize, newConnects, vfs, listener );
    }

    /**
     * бнгбпюыюер пюглеп оскю
     * 
     * @return пюглеп оскю
     */
    public int getPoolSize()
    {
        return poolSize;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        int bufSize = 528;
        InetAddress clientAddress;
        int clientPort;

        // янгдю╗л янйер
        try
        {
            serverSocket = new DatagramSocket( serverPort );
            serverSocket.setSoTimeout( 1000 );
        }
        catch ( SocketException e )
        {
            System.out.println( "Could not create socket on port: " + serverPort + ", shutting down!" );

            // янйер янгдюрэ ме сдюкняэ, пюанрюрэ анкэье ялшякю мер
            stop();

            return;
        }

        System.out.println( "TFTPServerSocket is started" );

        // аеяйнмевмши жхйк
        for ( ; abort != true; )
        {
            byte[] buffer = new byte[bufSize];
            DatagramPacket packet = new DatagramPacket( buffer, bufSize );

            // ошрюеляъ опхмърэ гюопня
            try
            {
                serverSocket.receive( packet );
            }
            catch ( IOException ioe )
            {
                // мхвецн ме опхькн
                continue;
            }

            // хдемрхтхйюжхъ ядекюбьецн гюопня
            clientAddress = packet.getAddress();
            clientPort = packet.getPort();
            System.out.println( "server received request for file from " + clientAddress.toString() );

            // йнохпсел хмтнплюжхч хг оюйерю
            byte[] data = new byte[packet.getLength()];
            System.arraycopy( packet.getData(), packet.getOffset(), data, 0, packet.getLength() );

            // днонкмхрекэмше оюпюлерпш
            int opcode = TFTPPacket.fetchOpCode( data );

            // янгдю╗л гюопня мю времхе хкх гюохяэ
            FRQ frq;
            try
            {
                switch ( opcode )
                {
                    case RRQ.OPCODE:
                        frq = new RRQ( data );
                        break;
                    case WRQ.OPCODE:
                        frq = new WRQ( data );
                        break;
                    default:
                        continue;
                }
            }
            catch ( InstantiationException e )
            {
                System.out.println( "InstantiationException: " + e.getMessage() );
                continue;
            }

            // гюопняш я меопюбхкэмшл хлемел тюикю хцмнпхпсчряъ
            if ( frq.getFileName() == null || frq.getFileName().length() == 0 )
            {
                continue;
            }

            // напюанрюрэ гюопня
            workers.performWork( frq, clientAddress, clientPort );
        }

        System.out.println( "TFTPServerSocket is stopped" );
    }

    /**
     * сярюмюбкхбюер пюглеп оскю
     * 
     * @param poolSize мнбши пюглеп оскю
     */
    public void setPoolSize(
        int poolSize )
    {
        this.poolSize = poolSize;
        workers.resize( poolSize );
    }

    /**
     * нярюмнбйю яепбепю
     */
    public void stop()
    {
        // нвхыюел оск
        workers.resize( 0 );

        // нрлемъел оепедювс дюммшу
        abort = true;

        // гюйпшбюел янйер
        if ( serverSocket != null )
        {
            serverSocket.close();
        }
    }
}
