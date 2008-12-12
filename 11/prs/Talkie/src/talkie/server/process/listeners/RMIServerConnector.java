package talkie.server.process.listeners;

import talkie.server.Server;
import talkie.server.process.dispatchers.rmi.TalkieServerImpl;

public class RMIServerConnector
    extends ServerConnector
{
    private final TalkieServerImpl talkieServerImpl;

    public RMIServerConnector(
        Server server,
        TalkieServerImpl talkieServerImpl )
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
