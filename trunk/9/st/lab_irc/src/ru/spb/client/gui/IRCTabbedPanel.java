package ru.spb.client.gui;

import javax.swing.JTabbedPane;

import ru.spb.client.entities.Channel;
import ru.spb.client.gui.trees.ChannelTree;

public class IRCTabbedPanel
    extends JTabbedPane
{
    /**
     * 
     */
    private static final long     serialVersionUID = 5072760225390450177L;

    private static IRCTabbedPanel instance;

    public IRCTabbedPanel()
    {
        addTab( ChannelTree.NAME, ChannelTree.getInstance() );
    }

    public void addChat(
        Channel channel )
    {
        ChatPanel chatPanel = new ChatPanel( channel );
        addTab( channel.getName(), chatPanel );
        setSelectedComponent( chatPanel );
    }

    public static IRCTabbedPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new IRCTabbedPanel();
        }
        return instance;
    }

}
