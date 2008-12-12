package talkie.server.dispatchers;

import java.rmi.Naming;
import java.rmi.RemoteException;

import talkie.server.dispatchers.rmi.TalkieServerImpl;

public class RMIDispatcher
    extends DispatchProtocol
{
    TalkieServerImpl talkie;
    private String   name;

    public RMIDispatcher()
    {
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

    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() && valid )
        {
            Thread.yield();
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
