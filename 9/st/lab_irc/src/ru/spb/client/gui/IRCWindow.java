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

    private static IRCWindow _instance;
    private JFrame           _mainWindow;

    @Override
    public void run()
    {
        _mainWindow = new JFrame( "IRC Client (c) eav 3351" );

        JSplitPane panel = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        {
            JSplitPane pane = new JSplitPane();

            ServerTree tree = ServerTree.getInstance();
            tree.addServer( new Server( "epa" ) );
            tree.addServer( new Server( "alien1", "irc.run.net" ) );
            tree.addServer( new Server( "alien2", "irc.generalnet.net" ) );
            pane.setLeftComponent( tree );

            pane.setRightComponent( IRCTabbedPanel.getInstance() );

            panel.setTopComponent( pane );
            pane.setVisible( true );
            pane.setDividerLocation( 0.3 );
        }
        panel.setBottomComponent( ServiceLogPanel.getInstance() );
        panel.setVisible( true );
        panel.setDividerLocation( 0.7 );

        _mainWindow.getContentPane().add( panel );

        _mainWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        _mainWindow.pack();
        _mainWindow.setVisible( true );
        _mainWindow.setExtendedState( JFrame.MAXIMIZED_BOTH );
    }

    public static IRCWindow getInstance()
    {
        if ( _instance == null )
        {
            _instance = new IRCWindow();
        }
        return _instance;
    }

    public JFrame getMainWindiow()
    {
        return _mainWindow;
    }

}
