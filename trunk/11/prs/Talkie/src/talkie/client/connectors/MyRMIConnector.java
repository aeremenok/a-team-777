package talkie.client.connectors;

import java.rmi.Naming;
import java.rmi.RemoteException;

import talkie.client.Client;
import talkie.client.connectors.rmi.TalkieClientImpl;
import talkie.server.dispatchers.rmi.TalkieServer;

public class MyRMIConnector
    extends ClientConnector
{
    private TalkieServer     talkieServer;
    private TalkieClientImpl talkieClientImpl;

    public MyRMIConnector(
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
    public void logout()
    {
        try
        {
            talkieServer.logout( talkieClientImpl );
        }
        catch ( RemoteException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean needsRunning()
    {
        return false;
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
        Thread.yield();
    }
}
