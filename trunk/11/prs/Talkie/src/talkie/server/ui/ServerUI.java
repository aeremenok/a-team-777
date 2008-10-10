package talkie.server.ui;

import talkie.common.ui.MyFrame;
import talkie.server.Server;

public class ServerUI
    implements
        Runnable
{
    private MyFrame frame;

    public ServerUI(
        Server server )
    {
        // todo Auto-generated constructor stub
    }

    public void run()
    {
        this.frame = new MyFrame();

    }
}
