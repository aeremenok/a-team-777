package talkie.server.process.dispatchers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import talkie.server.process.listeners.TCPServerConnector;

public class TCPDispatcher
    extends DispatchProtocol
{
    private Logger       log = Logger.getLogger( TCPDispatcher.class );
    private ServerSocket serverSocket;

    public TCPDispatcher()
    {
    }

    public void run()
    {
        try
        {
            serverSocket = new ServerSocket( 7778 );
        }
        catch ( IOException e1 )
        {
            log.error( "accepting connection failed", e1 );
            Thread.currentThread().interrupt();
        }

        while ( !Thread.currentThread().isInterrupted() )
        {
            Socket socket;
            try
            {
                socket = serverSocket.accept();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                socket = null;
            }
            if ( socket != null )
            {
                TCPServerConnector target = new TCPServerConnector( server, socket );
                new Thread( target ).start();
            }
        }
    }

    @Override
    protected void close()
    {
        try
        {
            serverSocket.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
