package ru.spb.client.gui.trees;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.Server;
import ru.spb.client.gui.listeners.ConnectingListener;
import ru.spb.client.gui.trees.nodes.ServerNode;

/**
 * дерево, отображающее список серверов
 * 
 * @author eav
 */
public class ServerTree
    extends IRCTree
{
    static ServerTree         _instance;

    private static final long serialVersionUID = -5455623554324837581L;

    private ServerTree(
        DefaultMutableTreeNode root )
    {
        super( root );
        addMouseListener( new ConnectingListener( this ) );
    }

    /**
     * задать список серверов
     * 
     * @param servers список серверов
     */
    public void addServers(
        Server[] servers )
    {
        for ( Server server : servers )
        {
            ServerNode node = new ServerNode( server );
            _root.add( node );
        }
        expandRow( getRowCount() - 1 );
    }

    /**
     * добавить сервер
     * 
     * @param server новый сервер
     */
    public void addServer(
        Server server )
    {
        ServerNode serverNode = new ServerNode( server );
        _root.add( serverNode );
    }

    public static ServerTree getInstance()
    {
        if ( _instance == null )
        {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Servers" );
            _instance = new ServerTree( root );
        }
        return _instance;
    }
}
