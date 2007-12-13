package ru.spb.client.gui;

import javax.swing.JSplitPane;

import ru.spb.client.entities.Channel;
import ru.spb.client.gui.trees.UserTree;

public class ChatPanel
    extends JSplitPane
{
    private UserTree     userTree;

    private ChatLogPanel chatLogPanel;

    private MessageArea  messageArea;

    public ChatPanel(
        Channel channel )
    {
        super( JSplitPane.VERTICAL_SPLIT );
        {
            JSplitPane pane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );

            userTree = UserTree.getTreeForChannel( channel );
            pane.setRightComponent( userTree );
            chatLogPanel = new ChatLogPanel();
            pane.setLeftComponent( chatLogPanel );

            setTopComponent( pane );
        }

        messageArea = new MessageArea( chatLogPanel );
        setBottomComponent( messageArea );
    }
}
