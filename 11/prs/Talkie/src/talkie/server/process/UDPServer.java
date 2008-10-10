package talkie.server.process;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import talkie.common.constants.Talkie;
import talkie.server.Server;

public class UDPServer
    implements
        Runnable
{
    private final int    port;
    private final Server server;

    public UDPServer(
        Server server,
        int port )
    {
        this.server = server;
        this.port = port;
    }

    public void run()
    {
        DatagramSocket socket = null;
        try
        {
            socket = new DatagramSocket( port );
            while ( true )
            {
                byte[] inBuf = new byte[Talkie.MSG_SIZE];
                DatagramPacket inPacket = new DatagramPacket( inBuf, inBuf.length );
                socket.receive( inPacket );

                try
                {
                    ClientHandler target = new ClientHandler( server, inPacket );
                    new Thread( target ).start();
                }
                catch ( SocketException e )
                {
                    System.err.println( "Не удалось создать сокет для работы с новым клиентом" );
                    e.printStackTrace();
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            if ( socket != null )
            {
                socket.close();
            }
        }
    }
}
