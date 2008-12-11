package talkie.server.process.dispatchers;

import talkie.server.Server;

public abstract class DispatchProtocol
    implements
        Runnable
{
    protected Server server;

    public Server getServer()
    {
        return server;
    }

    public void setServer(
        Server server )
    {
        this.server = server;
    }
}
