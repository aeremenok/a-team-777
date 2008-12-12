package talkie.server.dispatchers;

import talkie.server.Server;

public abstract class DispatchProtocol
    implements
        Runnable
{
    protected Server  server;
    protected boolean valid = true;

    public Server getServer()
    {
        return server;
    }

    public void setServer(
        Server server )
    {
        this.server = server;
    }

    public void stop()
    {
        valid = false;
        close();
        Thread.currentThread().interrupt();
    }

    protected abstract void close();
}
