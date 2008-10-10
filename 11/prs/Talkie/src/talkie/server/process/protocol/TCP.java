package talkie.server.process.protocol;

import org.apache.log4j.Logger;

import talkie.server.Server;

public class TCP
    implements
        TalkieProtocol
{
    private Logger log = Logger.getLogger( TCP.class );
    private Server server;

    public TCP()
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
