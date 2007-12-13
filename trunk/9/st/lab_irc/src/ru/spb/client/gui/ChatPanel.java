package ru.spb.client.gui;

import javax.swing.JSplitPane;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.IChattable;
import ru.spb.client.gui.trees.UserTree;

public class ChatPanel
    extends JSplitPane
{
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
     * заголовок панели
     */
    private String       _title;

    /**
     * с кем разговор
     */
    private IChattable   _chattable;

    public ChatPanel(
        IChattable chattable )
    {
        super( JSplitPane.VERTICAL_SPLIT );
        _chattable = chattable;
        _title = chattable.getName();
        {
            JSplitPane pane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );

            if ( chattable instanceof Channel )
            { // в канале есть список юзеров
                Channel channel = (Channel) chattable;
                userTree = UserTree.getTreeForChannel( channel );
                pane.setRightComponent( userTree );
            }

            chatLogPanel = new ChatLogPanel();
            pane.setLeftComponent( chatLogPanel );

            setTopComponent( pane );
        }

        messageArea = new MessageArea( chatLogPanel );
        setBottomComponent( messageArea );
    }

    public String getTitle()
    {
        return _title;
    }

    public IChattable getСhattable()
    {
        return _chattable;
    }
}
