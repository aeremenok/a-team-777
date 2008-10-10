package talkie.server.process.protocol;

import talkie.server.Server;

public interface TalkieProtocol
    extends
        Runnable
{
    void setServer(
        Server server );
}
