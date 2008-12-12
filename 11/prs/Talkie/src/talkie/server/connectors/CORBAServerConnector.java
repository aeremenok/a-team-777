package talkie.server.connectors;

import talkie.server.Server;
import talkie.server.dispatchers.corba.IDLTalkieServerImpl;

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
    protected void mainLoopStep()
    {
    }

    @Override
    protected void send(
        String string )
    {
        talkieServerImpl.send( user, string );
    }
}
