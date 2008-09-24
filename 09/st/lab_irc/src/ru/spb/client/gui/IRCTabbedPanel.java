package ru.spb.client.gui;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import ru.spb.client.entities.IChattable;
import ru.spb.client.entities.Server;
import ru.spb.client.gui.trees.ChannelTree;

/**
 * панель, где отображаются списки каналов серверов и панели чатов в каналах.
 * todo сделать панели чатов с юзерами
 * 
 * @author eav
 */
public class IRCTabbedPanel
    extends JTabbedPane
{
    private static final long     serialVersionUID = 5072760225390450177L;

    private static IRCTabbedPanel instance;

    /**
     * добавить вкладку с новым чатом
     * 
     * @param chattable с кем чат
     */
    public void addChat(
        IChattable chattable )
    {
        ChatPanel chatPanel = new ChatPanel( chattable );
        addTab( chatPanel.getTitle(), chatPanel );
        setSelectedComponent( chatPanel );
    }

    /**
     * добавить вкладку со списком каналов сервера
     * 
     * @param server сервер
     */
    public ChannelTree addChannelTree(
        Server server )
    {
        ChannelTree channelTree = ChannelTree.getChannelTreeForServer( server );
        JScrollPane scrollPane = new JScrollPane( channelTree );
        addTab( channelTree.getName(), scrollPane );
        return channelTree;
    }

    /**
     * удалить список каналов заданного сервера
     * 
     * @param server сервер
     */
    public void removeChannelTree(
        Server server )
    {
        removeTabAt( indexOfTab( server.getName() ) );
    }

    public static IRCTabbedPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new IRCTabbedPanel();
        }
        return instance;
    }

    /**
     * удалить вкладку с чатом
     * 
     * @param chattable с кем чат
     */
    public void removeChat(
        IChattable chattable )
    {
        removeTabAt( indexOfTab( chattable.getName() ) );
    }

}
