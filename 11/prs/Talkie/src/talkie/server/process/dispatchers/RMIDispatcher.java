package talkie.server.process.dispatchers;

import talkie.server.Server;

public class RMIDispatcher
    implements
        DispatchProtocol
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
