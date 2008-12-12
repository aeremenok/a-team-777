package talkie.server.dispatchers.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import talkie.client.connectors.rmi.TalkieClient;
import talkie.common.constants.Message;
import talkie.server.Server;
import talkie.server.connectors.RMIServerConnector;
import talkie.server.data.User;

public class TalkieServerImpl
    extends UnicastRemoteObject
    implements
        TalkieServer
{
    private final Server                        server;
    private HashMap<String, RMIServerConnector> connectors = new HashMap<String, RMIServerConnector>();
    private HashMap<String, TalkieClient>       clients    = new HashMap<String, TalkieClient>();

    public TalkieServerImpl(
        Server server )
        throws RemoteException
    {
        super();
        this.server = server;
    }

    public boolean login(
        TalkieClient client )
        throws RemoteException
    {
        String login = client.getLogin();
        String pass = client.getPass();
        RMIServerConnector connector = new RMIServerConnector( server, this );
        boolean result = connector.login( login, pass );
        if ( result )
        {
            connectors.put( login, connector );
            clients.put( login, client );
        }
        return result;
    }

    public void logout(
        TalkieClient client )
        throws RemoteException
    {
        RMIServerConnector serverConnector = connectors.get( client.getLogin() );
        if ( serverConnector != null )
        {
            serverConnector.process( Message.LOGOUT );
            serverConnector.stop( false );
            connectors.remove( client.getLogin() );
            clients.remove( client.getLogin() );
        }
    }

    public void postMessage(
        TalkieClient client,
        String message )
        throws RemoteException
    {
        RMIServerConnector connector = connectors.get( client.getLogin() );
        connector.process( message );
    }

    public void send(
        User user,
        String string )
    {
        TalkieClient c = clients.get( user.getLogin() );
        if ( c != null )
        {
            try
            {
                c.deliverMessage( string );
            }
            catch ( RemoteException e )
            {
                e.printStackTrace();
            }
        }
    }

    public void whoIsHere(
        TalkieClient client )
        throws RemoteException
    {
        RMIServerConnector ctor = connectors.get( client.getLogin() );
        if ( ctor != null )
        {
            ctor.process( Message.LIST );
        }
    }
}
