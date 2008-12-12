package talkie.client.connectors;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import talkie.client.Client;
import talkie.common.corba.IDLTalkieServer;
import talkie.common.corba.IDLTalkieServerHelper;

public class CORBAConnector
    extends ClientConnector
{
    private IDLTalkieServer     talkieServer;
    private IDLTalkieClientImpl talkieClient;

    public CORBAConnector(
        Client client )
    {
        super( client );
        try
        {
            connect();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void close()
    {
        talkieServer.logout( login );
    }

    @Override
    public boolean establishConnection()
    {
        return talkieServer.login( talkieClient );
    }

    @Override
    public void send(
        String message )
    {
        talkieServer.postMessage( login, message );
    }

    private void connect()
        throws InvalidName,
            NotFound,
            CannotProceed,
            org.omg.CosNaming.NamingContextPackage.InvalidName
    {
        String params[] = new String[] { "-ORBInitialPort", "1050" };
        ORB orb = ORB.init( params, null );
        org.omg.CORBA.Object corbaObject = orb.resolve_initial_references( "NameService" );
        NamingContext naming = NamingContextHelper.narrow( corbaObject );
        NameComponent nameComponent = new NameComponent( "TalkieServer", "" );
        NameComponent path[] = { nameComponent };
        corbaObject = naming.resolve( path );
        talkieServer = IDLTalkieServerHelper.narrow( corbaObject );
    }

    @Override
    protected void mainLoopStep()
    {
        talkieClient = new IDLTalkieClientImpl( this );
        while ( !Thread.currentThread().isInterrupted() && valid )
        {
            Thread.yield();
        }
    }
}
