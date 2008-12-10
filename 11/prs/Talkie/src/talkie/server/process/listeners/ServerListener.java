package talkie.server.process.listeners;

import talkie.server.Server;

public abstract class ServerListener
    implements
        Runnable
{
    private final Server server;

    public ServerListener(
        Server server )
    {
        this.server = server;
    }

    public abstract void logout();
}
