package talkie.server.process.dispatchers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.log4j.Logger;

import talkie.common.constants.Talkie;
import talkie.server.process.listeners.ServerConnector;
import talkie.server.process.listeners.UDPServerConnector;

public class UDPDispatcher
    extends DispatchProtocol
{
    private Logger log = Logger.getLogger( UDPDispatcher.class );

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

                ServerConnector target = new UDPServerConnector( server, inPacket );
                new Thread( target ).start();
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
}
