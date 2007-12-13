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

    static ServerTree         instance;

    /**
     * 
     */
    private static final long serialVersionUID = -5455623554324837581L;

    public ServerTree(
        DefaultMutableTreeNode root,
        Server connectable )
    {
        super( root );
        Server s = new Server( "Main Server" );
        DefaultMutableTreeNode serverNode = new ServerNode( s );
        _root.add( serverNode );

        addMouseListener( new ConnectingListener( this ) );
    }

    /**
     * задать список серверов
     * 
     * @param servers список серверов
     */
    void addServers(
        Server[] servers )
    {
        clear();
        for ( Server server : servers )
        {
            ServerNode node = new ServerNode( server );
            _root.add( node );
        }
        this.expandRow( getRowCount() - 1 );
    }

    /**
     * добавить сервер
     * 
     * @param server новый сервер
     */
    void addServer(
        Server server )
    {
        ServerNode serverNode = new ServerNode( server );
        _root.add( serverNode );
    }

    public static ServerTree getInstance(
        Server connectable )
    {
        if ( instance == null )
        {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Servers" );
            instance = new ServerTree( root, connectable );
        }
        return instance;
    }
}
