package ru.spb.client.gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import ru.spb.client.entities.Server;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.client.gui.trees.ServerTree;

public class IRCWindow
    implements
        Runnable
{

    private static IRCWindow instance;
    private JFrame           mainWindiow;

    @Override
    public void run()
    {
        JFrame frame = new JFrame( "IRC Client (c) eav 3351" );
        mainWindiow = frame;

        JSplitPane panel = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        {
            JSplitPane pane = new JSplitPane();

            ServerTree tree = ServerTree.getInstance();
            // todo заглушка
            Server[] servers = new Server[] { new Server( "epa" ) };
            tree.addServers( servers );
            pane.setLeftComponent( tree );

            pane.setRightComponent( IRCTabbedPanel.getInstance() );

            panel.setTopComponent( pane );
        }
        panel.setBottomComponent( ServiceLogPanel.getInstance() );

        frame.getContentPane().add( panel );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible( true );
    }

    public static IRCWindow getInstance()
    {
        if ( instance == null )
        {
            instance = new IRCWindow();
        }
        return instance;
    }

    public JFrame getMainWindiow()
    {
        return mainWindiow;
    }

}
