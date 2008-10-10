package talkie.server.process;

import java.net.DatagramSocket;

public class UDPServer
    implements
        Runnable
{
    private final int port = 0;

    // private final Server server = null;

    public UDPServer()
    {
        // this.server = server;
        // this.port = port;
    }

    public void run()
    {
        DatagramSocket socket = null;
        try
        {
            socket = new DatagramSocket( port );
            while ( !Thread.currentThread().isInterrupted() )
            {
                System.out.println( "udp server is running..." );
                Thread.sleep( 1000 );
                // byte[] inBuf = new byte[Talkie.MSG_SIZE];
                // DatagramPacket inPacket = new DatagramPacket( inBuf, inBuf.length );
                // socket.receive( inPacket );
                //
                // try
                // {
                // ClientHandler target = new ClientHandler( server, inPacket );
                // new Thread( target ).start();
                // }
                // catch ( SocketException e )
                // {
                // System.err.println( "Не удалось создать сокет для работы с новым клиентом" );
                // e.printStackTrace();
                // }
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
