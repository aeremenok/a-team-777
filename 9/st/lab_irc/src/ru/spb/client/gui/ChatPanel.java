package ru.spb.client.gui;

import javax.swing.JSplitPane;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.IChattable;
import ru.spb.client.gui.logpanels.ChatLogPanel;
import ru.spb.client.gui.trees.UserTree;

public class ChatPanel
    extends JSplitPane
{
    // todo можно это не хранить
    /**
     * дерево пользователей
     */
    private UserTree     userTree;

    /**
     * панель, куда выводится лог чата
     */
    private ChatLogPanel chatLogPanel;

    /**
     * поле ввода сообщений
     */
    private MessageArea  messageArea;

    /**
     * с кем разговор
     */
    private IChattable   _chattable;

    public ChatPanel(
        IChattable chattable )
    {
        super( JSplitPane.VERTICAL_SPLIT );
        _chattable = chattable;
        {
            JSplitPane pane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );

            if ( _chattable instanceof Channel )
            { // в канале есть список юзеров
                Channel channel = (Channel) _chattable;
                userTree = UserTree.getTreeForChannel( channel );
                pane.setRightComponent( userTree );
            }

            chatLogPanel = new ChatLogPanel( _chattable );
            pane.setLeftComponent( chatLogPanel );

            setTopComponent( pane );
        }

        messageArea = new MessageArea( _chattable );
        setBottomComponent( messageArea );
    }

    public String getTitle()
    {
        return _chattable.getName();
    }
}
