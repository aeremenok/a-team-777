package talkie.server.dispatchers;


public class CORBADispatcher
    extends DispatchProtocol
{
    private IDLTalkieServerImpl talkieServerImpl;

    public CORBADispatcher()
    {
    }

    public void run()
    {
        talkieServerImpl = IDLTalkieServerImpl.getInstance( server );
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
