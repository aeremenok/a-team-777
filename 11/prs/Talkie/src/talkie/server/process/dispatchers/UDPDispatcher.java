package talkie.server.process.dispatchers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import talkie.common.constants.Talkie;
import talkie.server.Server;
import talkie.server.process.listeners.UDPServerListener;

public class UDPDispatcher
    implements
        DispatchProtocol
{
    private Logger log = Logger.getLogger( UDPDispatcher.class );

    private Server server;

    public UDPDispatcher()
    {
    }

    public void run()
    {
        DatagramSocket socket = null;
        try
        {
            socket = new DatagramSocket( 7777 );
            while ( !Thread.currentThread().isInterrupted() )
            {
                byte[] inBuf = new byte[Talkie.MSG_SIZE];
                DatagramPacket inPacket = new DatagramPacket( inBuf, inBuf.length );
                socket.receive( inPacket );

                try
                {
                    UDPServerListener target = new UDPServerListener( server, inPacket );
                    new Thread( target ).start();
                }
                catch ( SocketException e )
                {
                    log.error( "Не удалось создать UDP-сокет для работы с новым клиентом", e );
                }
            }
        }
        catch ( Exception e )
        {
            log.error( "", e );
        }

        if ( socket != null )
        {
            socket.close();
        }
    }

    public void setServer(
        Server server )
    {
        this.server = server;
    }
}
