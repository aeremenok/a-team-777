package ru.spb._3352.tftp.server;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Stack;

import ru.spb._3352.tftp.common.FRQ;
import ru.spb._3352.tftp.common.VirtualFileSystem;

/**
 * оск напюанрвхйнб гюопнянб
 */
public class TFTPPool
{
    /**
     * пюанрюер я онрнйюлх х напюанрвхйюлх гюопнянб
     */
    class TFTPRHThread
        extends Thread
    {
        /**
         * юдпея йкхемрю
         */
        private InetAddress        address;

        /**
         * ткюц гюбепьемхъ онрнйю
         */
        private boolean            die     = false;

        /**
         * онпр йкхемрю
         */
        int                        port;

        /**
         * гюопня
         */
        private FRQ                requestPacket;

        /**
         * ткюц гюмърнярх
         */
        private boolean            running = false;

        /**
         * напюанрвхй гюопнянб
         */
        private TFTPRequestHandler tftpRequestHandler;

        /**
         * йнмярпсйрнп
         * 
         * @param id ярпнйю-хдемрхтхйюрнп
         * @param rh напюанрвхй гюопнянб
         */
        public TFTPRHThread(
            String id,
            TFTPRequestHandler rh )
        {
            super( id );
            tftpRequestHandler = rh;
            requestPacket = null;
            address = null;
            port = 0;
        }

        /**
         * онрнйс йнмеж
         */
        synchronized public void die()
        {
            die = true;

            interrupt();
        }

        /**
         * бнгбпюыюер IP х онпр йкхемрю
         * 
         * @return IP х онпр йкхемрю
         */
        public String getClient()
        {
            if ( address == null )
            {
                return "No client helped yet";
            }
            return address.getHostAddress() + ":" + port;
        }

        /**
         * бнгбпюыюер гмювемхе ткюцю гюмърнярх
         * 
         * @return ткюц гюмърнярх
         */
        public boolean getRunning()
        {
            return running;
        }

        /**
         * бнгбпюыюер напюанрвхй гюопнянб
         * 
         * @return напюанрвхй гюопнянб
         */
        public TFTPRequestHandler getWorker()
        {
            return tftpRequestHandler;
        }

        /* (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        synchronized public void run()
        {
            try
            {
                boolean stayAround = true;
                while ( stayAround )
                {
                    if ( die )
                    {
                        return;
                    }

                    if ( requestPacket == null )
                    {
                        try
                        {
                            wait();
                        }
                        catch ( InterruptedException e )
                        {
                            continue;
                        }
                    }

                    running = true;
                    tftpRequestHandler.run( requestPacket, address, port );
                    running = false;

                    synchronized ( connections )
                    {
                        connections.remove( address + ":" + port );
                    }

                    requestPacket = null;
                    address = null;
                    port = 0;
                    stayAround = push( this );
                }
            }
            finally
            {
                tftpRequestHandler.stop();
            }
        }

        /**
         * пюгасдхрэ онрнй
         * 
         * @param frq гюопня
         * @param clientAddress юдпея
         * @param clientPort онпр
         */
        synchronized void wake(
            FRQ frq,
            InetAddress clientAddress,
            int clientPort )
        {
            requestPacket = frq;
            address = clientAddress;
            port = clientPort;
            notify();
        }
    }

    /**
     * янедхмемхъ я йкхемрюлх
     */
    private Hashtable         connections;

    /**
     * ябнандмше напюанрвхйх
     */
    private Stack             idleWorkers;

    /**
     * яксьюрекэ янашрхи
     */
    private EventListener     listener = null;

    /**
     * пюглеп оскю
     */
    private int               size;

    /**
     * бхпрсюкэмюъ тюикнбюъ яхярелю
     */
    private VirtualFileSystem vfs      = null;

    /**
     * йнмярпсйрнп
     * 
     * @param size пюглеп нвепедх напюанрвхйнб
     * @param connections янедхмемхъ я йкхемрюлх
     * @param vfs тюикнбюъ яхярелю
     * @param listener яксьюрекэ
     */
    public TFTPPool(
        int size,
        Hashtable connections,
        VirtualFileSystem vfs,
        EventListener listener )
    {
        this.vfs = vfs;
        this.listener = listener;
        this.size = size;
        this.connections = connections;

        idleWorkers = new Stack();
        TFTPRHThread wt;

        // янгдю╗л пюанвху, гюосяйюел х йкюд╗л б ярей
        for ( int i = 0; i < size; i++ )
        {
            String birthDate = "name=TFTPRequestHandler,workerid=" + i + ",birthdate=" + (new Date()).getTime();
            try
            {
                wt = new TFTPRHThread( birthDate, new TFTPRequestHandler( vfs, listener ) );
                wt.start();
                idleWorkers.push( wt );
            }
            catch ( SocketException e )
            {
                System.out.println( "Could not create TFTPRequestHandler nr: " + i + " for TFTPPool" );
            }
        }
    }

    /**
     * лернд бшгшбюеряъ хг яепбепмнцн янйерю опх онксвемхх гюопняю мю
     * времхе/гюохяэ. гюопня оепедю╗ряъ ябнандмнлс напюанрвхйс.
     * 
     * @param frq гюопня
     * @param clientAddress юдпея йкхемрю
     * @param clientPort онпр йкхемрю
     */
    synchronized public void performWork(
        FRQ frq,
        InetAddress clientAddress,
        int clientPort )
    {

        TFTPRHThread wt = null;

        // онксвем оюйер. опнбепъел мю дсакхпнбюмхе х днаюбкъел.
        TFTPRequestHandler existingConnection;
        synchronized ( connections )
        {
            existingConnection = (TFTPRequestHandler) connections.get( clientAddress + ":" + clientPort );
        }

        if ( existingConnection != null )
        {
            if ( !existingConnection.waitingForNewRequest( frq ) )
            {
                System.out.println( "Hey, I am already working for you. Be patient!" );
                return;
            }
        }

        synchronized ( idleWorkers )
        {
            if ( idleWorkers.empty() )
            {
                // гюяшоюел у.а. мю 2 лхкхяейсмдш, врнаш цюпюмрхпнбюрэ
                // смхйюкэмнярэ birthdate х нцпюмхвхрэ пняр йнкхвеярбю онрнйнб
                try
                {
                    Thread.sleep( 2 );
                }
                catch ( InterruptedException e1 )
                {
                }

                // бяе напюанрвхйх гюмърш, янгдюрэ мнбнцн х бшбеярх
                // опедсопефдемхе
                String birthDate = "name=TFTPRequestHandler,workerid=x,birthdate=" + (new Date()).getTime();
                try
                {
                    System.out.println( "WARNING: Overload on tftpPool! ReHash!" );
                    wt = new TFTPRHThread( birthDate, new TFTPRequestHandler( vfs, listener ) );
                    wt.start();
                }
                catch ( SocketException e )
                {
                    System.out.println( "ERROR: Can not handle overload!" );
                }
            }
            else
            {
                // онксвхрэ ябнандмнцн напюанрвхйю
                wt = (TFTPRHThread) idleWorkers.pop();
            }
        }

        synchronized ( connections )
        {
            wt.wake( frq, clientAddress, clientPort );
            connections.put( clientAddress + ":" + clientPort, wt.getWorker() );
        }
    }

    /**
     * днаюбкъер б ярей напюанрвхйю
     * 
     * @param wt напюанрвхй
     * @return сдюкняэ?
     */
    private boolean push(
        TFTPRHThread wt )
    {
        boolean stayAround = false;

        synchronized ( idleWorkers )
        {
            if ( idleWorkers.size() < size )
            {
                stayAround = true;
                idleWorkers.push( wt );
            }
        }

        return stayAround;
    }

    /**
     * хглемхрэ пюглеп оскю напюанрвхйнб. пюглеп хглемхряъ япюгс, ю напюанрвхйх
     * асдср янгдюбюрэяъ он лепе менаундхлнярх.
     * 
     * @param newPoolSize мнбши пюглеп оскю
     */
    public void resize(
        int newPoolSize )
    {
        if ( newPoolSize < size )
        {
            for ( int x = 0; x < (size - newPoolSize); x++ )
            {
                if ( idleWorkers.isEmpty() )
                {
                    break;
                }

                TFTPRHThread wt = (TFTPRHThread) idleWorkers.pop();

                if ( wt == null )
                {
                    break;
                }

                wt.die();
            }
        }

        size = newPoolSize;
    };
}
