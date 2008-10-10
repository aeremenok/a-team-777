package talkie.server.process.protocol;

import talkie.server.Server;

public class CORBA
    implements
        TalkieProtocol
{
    private Server server;

    public void run()
    {
    }

    public void setServer(
        Server server )
    {
        this.server = server;
    }
}
