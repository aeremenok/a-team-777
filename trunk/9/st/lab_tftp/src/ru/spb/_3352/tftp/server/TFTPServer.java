package ru.spb._3352.tftp.server;

import ru.spb._3352.tftp.common.VirtualFileSystem;

/**
 * ртро-яепбеп
 */
public class TFTPServer
{
    /**
     * йнмярюмрю онпрю он слнквюмхч
     */
    final static int          DEFAULT_PORT = 69;

    /**
     * яксьюрекэ янашрхи
     */
    private EventListener     listener     = null;

    /**
     * пюглеп оскю
     */
    private int               poolSize     = 5;

    /**
     * мнлеп онпрю
     */
    private int               port         = DEFAULT_PORT;

    /**
     * онрнй яепбепю
     */
    private Thread            server;

    /**
     * янйермши яепбеп
     */
    private TFTPServerSocket  ss;

    /**
     * пеюкхгюжхъ бхпрсюкэмни тюикнбни яхярелш
     */
    private VirtualFileSystem vfs;

    /**
     * йнмярпсйрнп
     * 
     * @param vfs пеюкхгюжхъ бхпрсюкэмни тюикнбни яхярелш
     * @param listener яксьюрекэ дкъ янашрхи он оепедюве
     */
    public TFTPServer(
        VirtualFileSystem vfs,
        EventListener listener )
    {
        this.vfs = vfs;
        this.listener = listener;
    }

    /**
     * бнгбпюыюер пюглеп оскю
     * 
     * @return int пюглеп оскю
     */
    public int getPoolSize()
    {
        return poolSize;
    }

    /**
     * бнгбпюыюер мнлеп онпрю
     * 
     * @return int мнлеп онпрю
     */
    public int getPort()
    {
        return port;
    }

    /**
     * сярюмюбкхбюер пюглеп оскю
     * 
     * @param size пюглеп оскю
     */
    public void setPoolSize(
        int poolSize )
    {
        this.poolSize = poolSize;
        if ( ss == null )
        {
            return;
        }
        ss.setPoolSize( poolSize );
    }

    /**
     * сярюмюбкхбюер мнлеп онпрю
     * 
     * @param port мнлеп онпрю
     */
    public void setPort(
        int port )
    {
        this.port = port;
    }

    /**
     * янгдю╗р х гюосяйюер янйермши яепбеп б мнбнл онрнйе
     */
    public void start()
    {
        if ( port == 0 )
        {
            port = DEFAULT_PORT;
        }

        System.out.println( "Starting new TFTP server socket on port: " + port );

        ss = new TFTPServerSocket( port, poolSize, vfs, listener );
        server = new Thread( ss );
        server.start();
    }

    /**
     * нярюмюбкхбюер х смхврнфюер янйермши яепбеп
     */
    public void stop()
    {
        if ( ss == null )
        {
            System.out.println( "ServerSocket is already null so, is tftpServer closed???" );
            return;
        }

        System.out.println( "Shutting down TFTP server socket." );

        ss.stop();

        if ( server == null )
        {
            System.out.println( "FIXME: ServerSocket was not null but tftpServer is!" );
            return;
        }

        try
        {
            server.join( 6000 );
        }
        catch ( InterruptedException e )
        {
            System.out.println( "Could not close all TFTPServer thread!" );
        }

        server = null;
        ss = null;
    }
}
