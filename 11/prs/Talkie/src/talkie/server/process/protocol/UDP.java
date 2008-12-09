package talkie.server.process.protocol;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import talkie.common.constants.Talkie;
import talkie.server.Server;
import talkie.server.process.handler.UDPHandler;

public class UDP
    implements
        TalkieProtocol
{
    private Logger log = Logger.getLogger( UDP.class );

    private Server server;

    public UDP()
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

                System.out.println( "new client!!!" );
                try
                {
                    UDPHandler target = new UDPHandler( server, inPacket );
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
