package talkie.server.dispatchers.corba;

import java.util.HashMap;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import talkie.common.constants.Message;
import talkie.server.Server;
import talkie.server.connectors.CORBAServerConnector;
import talkie.server.data.User;

public class IDLTalkieServerImpl
    extends _IDLTalkieServerImplBase
{
    HashMap<String, CORBAServerConnector> connectors = new HashMap<String, CORBAServerConnector>();
    static private IDLTalkieServerImpl    instance   = null;
    private final Server                  server;

    static public IDLTalkieServerImpl getInstance(
        Server server )
    {
        if ( instance == null )
        {
            instance = new IDLTalkieServerImpl( server );
        }
        return instance;
    }

    private IDLTalkieServerImpl(
        Server server )
    {
        this.server = server;
        try
        {
            register();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public boolean login(
        String login,
        String pass )
    {
        CORBAServerConnector conn = new CORBAServerConnector( server, this );
        boolean result = conn.login( login, pass );
        if ( result )
        {
            connectors.put( login, conn );
        }
        return result;
    }

    public void logout(
        String login )
    {
        CORBAServerConnector conn = connectors.get( login );
        if ( conn != null )
        {
            conn.process( Message.LOGOUT );
            conn.stop();
            connectors.remove( login );
        }
    }

    public void postMessage(
        String login,
        String message )
    {
        CORBAServerConnector conn = connectors.get( login );
        conn.process( message );
    }

    public void send(
        User user,
        String string )
    {
        // todo
    }

    public void unregister()
        throws NotFound,
            CannotProceed,
            org.omg.CosNaming.NamingContextPackage.InvalidName,
            InvalidName
    {
        String corbaName = "TalkieServer";
        ORB orb = ORB.init();
        orb.connect( this );
        org.omg.CORBA.Object corbaObject = orb.resolve_initial_references( "NameService" );
        NamingContext naming = NamingContextHelper.narrow( corbaObject );
        NameComponent nameComponent = new NameComponent( corbaName, "" );
        NameComponent path[] = { nameComponent };
        naming.unbind( path );
    }

    public void whoIsHere(
        String login )
    {
        CORBAServerConnector conn = connectors.get( login );
        if ( conn != null )
        {
            conn.process( Message.LIST );
        }
    }

    private void register()
        throws InvalidName,
            NotFound,
            CannotProceed,
            org.omg.CosNaming.NamingContextPackage.InvalidName
    {
        String corbaName = "TalkieServer";
        ORB orb = ORB.init();
        orb.connect( this );
        org.omg.CORBA.Object corbaObject = orb.resolve_initial_references( "NameService" );
        NamingContext naming = NamingContextHelper.narrow( corbaObject );
        NameComponent nameComponent = new NameComponent( corbaName, "" );
        NameComponent path[] = { nameComponent };
        naming.rebind( path, this );
    }
}
