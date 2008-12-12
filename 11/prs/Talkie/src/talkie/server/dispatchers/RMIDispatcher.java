package talkie.server.dispatchers;

import java.rmi.Naming;
import java.rmi.RemoteException;

import talkie.server.dispatchers.rmi.TalkieServer;
import talkie.server.dispatchers.rmi.TalkieServerImpl;

public class RMIDispatcher
    extends DispatchProtocol
{
    TalkieServer   talkie;
    private String name;

    public RMIDispatcher()
    {
    }

    @Override
    public boolean needsStopping()
    {
        return false;
    }

    public void run()
    {
        // System.setSecurityManager( new RMISecurityManager() );
        try
        {
            talkie = new TalkieServerImpl( server );
        }
        catch ( RemoteException e )
        {
            e.printStackTrace();
            stop();
        }
        name = "rmi://localhost/TalkieServer";
        try
        {
            Naming.rebind( name, talkie );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void close()
    {
        try
        {
            Naming.unbind( name );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
