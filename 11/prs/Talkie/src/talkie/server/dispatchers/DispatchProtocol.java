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

    public abstract boolean needsStopping();

    public void setServer(
        Server server )
    {
        this.server = server;
    }

    public void stop()
    {
        valid = false;
        close();
        if ( needsStopping() )
        {
            Thread.currentThread().interrupt();
        }
    }

    protected abstract void close();
}
