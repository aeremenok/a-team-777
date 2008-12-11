package talkie.server.process.dispatchers;

import talkie.server.Server;

public class RMIDispatcher
    extends DispatchProtocol
{
    private Server server;

    public void run()
    {
    }

    @Override
    public void setServer(
        Server server )
    {
        this.server = server;
    }
}
