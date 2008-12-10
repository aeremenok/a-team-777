package talkie.server.process.dispatchers;

import org.apache.log4j.Logger;

import talkie.server.Server;

public class TCPDispatcher
    implements
        DispatchProtocol
{
    private Logger log = Logger.getLogger( TCPDispatcher.class );
    private Server server;

    public TCPDispatcher()
    {
    }

    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() )
        {
            System.out.println( "TCP server is running!" );

            try
            {
                Thread.sleep( 1000 );
            }
            catch ( InterruptedException e )
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setServer(
        Server server )
    {
        this.server = server;
    }
}
