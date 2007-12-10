package ru.spb.client.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
        this.addTab( "ChannelList", ChannelTree.getInstance() );
        this.addTab( "Chat", new JPanel() );
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
