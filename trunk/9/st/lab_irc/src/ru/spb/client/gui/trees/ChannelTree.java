package ru.spb.client.gui.trees;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.Server;
import ru.spb.client.gui.listeners.ChattingListener;
import ru.spb.client.gui.listeners.ConnectingListener;
import ru.spb.client.gui.trees.nodes.ChannelNode;

public class ChannelTree
    extends IRCTree
{
    /**
     * имя сервера с каналами
     */
    private String _name;

    private ChannelTree(
        DefaultMutableTreeNode root )
    {
        super( root );
        addMouseListener( new ConnectingListener( this ) );
        addMouseListener( new ChattingListener( this ) );
    }

    /**
     * выдает новое дерево каналов для заданного сервера
     * 
     * @param server сервер
     * @return дерево каналов
     */
    public static ChannelTree getChannelTreeForServer(
        Server server )
    {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Channels" );
        ChannelTree result = new ChannelTree( root );
        result._name = server.getName();
        result.addChannels( server.getChannels() );
        return result;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 5408874563647082570L;

    /**
     * добавляет каналы в дерево
     * 
     * @param channels массив каналов
     */
    public void addChannels(
        Channel[] channels )
    {
        for ( Channel channel : channels )
        {
            ChannelNode channelNode = new ChannelNode( channel );
            _root.add( channelNode );
        }
        this.expandRow( getRowCount() - 1 );
    }

    public String getName()
    {
        return _name;
    }
}
