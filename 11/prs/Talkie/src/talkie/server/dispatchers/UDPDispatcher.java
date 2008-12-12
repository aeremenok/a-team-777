package talkie.server.dispatchers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.log4j.Logger;

import talkie.common.constants.Talkie;
import talkie.server.connectors.ServerConnector;
import talkie.server.connectors.UDPServerConnector;

public class UDPDispatcher
    extends DispatchProtocol
{
    private Logger         log = Logger.getLogger( UDPDispatcher.class );
    private DatagramSocket socket;

    public UDPDispatcher()
    {
    }

    public void run()
    {
        try
        {
            socket = new DatagramSocket( 7777 );
            while ( !Thread.currentThread().isInterrupted() && valid )
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
    }

    @Override
    protected void close()
    {
        socket.close();
    }
}
