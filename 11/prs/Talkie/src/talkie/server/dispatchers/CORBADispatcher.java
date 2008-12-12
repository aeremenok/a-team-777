package talkie.server.dispatchers;

public class CORBADispatcher
    extends DispatchProtocol
{
    private IDLTalkieServerImpl talkieServerImpl;

    public CORBADispatcher()
    {
    }

    @Override
    public boolean needsStopping()
    {
        return false;
    }

    public void run()
    {
        talkieServerImpl = IDLTalkieServerImpl.getInstance( server );
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
