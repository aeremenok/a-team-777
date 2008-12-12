package talkie.server.connectors;

import talkie.server.Server;
import talkie.server.dispatchers.IDLTalkieServerImpl;

public class CORBAServerConnector
    extends ServerConnector
{
    private final IDLTalkieServerImpl talkieServerImpl;

    public CORBAServerConnector(
        Server server,
        IDLTalkieServerImpl talkieServerImpl )
    {
        super( server );
        this.talkieServerImpl = talkieServerImpl;
    }

    @Override
    public boolean needsStopping()
    {
        return false;
    }

    public void run()
    {
    }

    @Override
    protected void send(
        String string )
    {
        try
        {
            talkieServerImpl.send( user, string );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
