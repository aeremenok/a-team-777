package ru.spb.client.gui.trees;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.Server;
import ru.spb.client.gui.listeners.ChattingListener;
import ru.spb.client.gui.listeners.ConnectingListener;
import ru.spb.client.gui.trees.nodes.ChannelNode;

public class ChannelTree
    extends IRCTree
{
    /**
     * сервер, содержащий каналы
     */
    private Server _server;

    private ChannelTree(
        DefaultMutableTreeNode root,
        Server server )
    {
        super( root );
        _server = server;
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
        ChannelTree result = new ChannelTree( root, server );
        server.retrieveChannels( result );

        return result;
    }

    private static final long serialVersionUID = 5408874563647082570L;

    /**
     * добавить канал
     * 
     * @param channel канал
     */
    public void addChannel(
        Channel channel )
    {
        if ( channel != null )
        {
            ChannelNode channelNode = new ChannelNode( channel );
            DefaultTreeModel model = (DefaultTreeModel) getModel();
            model.insertNodeInto( channelNode, _root, _root.getChildCount() );
            expandRow( getRowCount() - 1 );

            _channels.add( channel );

            if ( channel.getHost() == null )
            { // канал заказан пользователем - регистрируем на сервере
                _server.createChannel( channel );
            }
        }
    }

    ArrayList<Channel> _channels = new ArrayList<Channel>();

    /**
     * добавляет каналы в дерево
     * 
     * @param channels массив каналов
     */
    public void addChannels(
        ArrayList<Channel> channels )
    {
        for ( Channel channel : channels )
        {
            ChannelNode channelNode = new ChannelNode( channel );
            _root.add( channelNode );
            _channels.add( channel );
        }
        expandRow( getRowCount() - 1 );
    }

    public String getName()
    {
        return _server.getName();
    }

    public Server getServer()
    {
        return _server;
    }

    /**
     * переводит узлы дерева в удобный контейнер
     * 
     * @return список каналов
     */
    public ArrayList<Channel> getChannels()
    {
        return _channels;
    }
}
