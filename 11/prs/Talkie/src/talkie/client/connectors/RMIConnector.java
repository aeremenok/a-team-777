package talkie.client.connectors;

import java.rmi.Naming;
import java.rmi.RemoteException;

import talkie.client.Client;
import talkie.client.connectors.rmi.TalkieClientImpl;
import talkie.server.process.dispatchers.rmi.TalkieServer;

public class RMIConnector
    extends ClientConnector
{
    private TalkieServer     talkieServer;
    private TalkieClientImpl talkieClientImpl;

    public RMIConnector(
        Client client )
    {
        super( client );
        try
        {
            talkieClientImpl = new TalkieClientImpl( this );
        }
        catch ( RemoteException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void close()
    {
    }

    @Override
    public boolean establishConnection()
    {
        String toConnect = "rmi://" + serverName + "/TalkieServer";
        try
        {
            talkieServer = (TalkieServer) Naming.lookup( toConnect );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        try
        {
            valid = talkieServer.login( talkieClientImpl );
        }
        catch ( RemoteException e )
        {
            e.printStackTrace();
        }

        return valid;
    }

    @Override
    public void send(
        String message )
    {
        try
        {
            talkieServer.postMessage( talkieClientImpl, message );
        }
        catch ( RemoteException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void mainLoopStep()
    {
    }
}
