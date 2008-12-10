package talkie.server.process.dispatchers;

import talkie.server.Server;

public interface DispatchProtocol
    extends
        Runnable
{
    void setServer(
        Server server );
}
