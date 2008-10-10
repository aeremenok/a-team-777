package talkie.server.ui;

import java.awt.BorderLayout;

import javax.swing.JMenuBar;

import talkie.common.ui.MyFrame;
import talkie.server.Server;

public class ServerUI
    implements
        Runnable
{
    private MyFrame      frame = new MyFrame();
    private final Server server;

    public ServerUI(
        Server server )
    {
        this.server = server;
    }

    public void run()
    {
        frame.setLayout( new BorderLayout() );

        JMenuBar mb = new JMenuBar();
        frame.setJMenuBar( mb );

    }
}
