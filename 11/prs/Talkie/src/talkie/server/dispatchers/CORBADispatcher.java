package talkie.server.dispatchers;

import talkie.server.dispatchers.corba.IDLTalkieServerImpl;

public class CORBADispatcher
    extends DispatchProtocol
{
    private IDLTalkieServerImpl talkieServerImpl;

    public CORBADispatcher()
    {
        talkieServerImpl = IDLTalkieServerImpl.getInstance( server );
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
            talkieServerImpl.unregister();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
