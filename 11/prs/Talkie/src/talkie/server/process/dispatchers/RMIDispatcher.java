package talkie.server.process.dispatchers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import talkie.server.process.dispatchers.rmi.TalkieServerImpl;

public class RMIDispatcher
    extends DispatchProtocol
{
    TalkieServerImpl talkie;
    private String   name;

    public RMIDispatcher()
    {
    }

    public void run()
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
        catch ( RemoteException e )
        {
            e.printStackTrace();
            stop();
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
            stop();
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
