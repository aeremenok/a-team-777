package talkie.server.process.listeners;

import talkie.server.Server;

public abstract class ServerListener
    implements
        Runnable
{
    protected final Server server;

    public ServerListener(
        Server server )
    {
        this.server = server;
    }

    public abstract void logout();
}
