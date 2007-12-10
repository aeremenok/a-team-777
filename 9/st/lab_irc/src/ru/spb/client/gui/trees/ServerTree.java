package ru.spb.client.gui.trees;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.Server;
import ru.spb.client.gui.ConnectingAdapter;
import ru.spb.client.gui.trees.nodes.ServerNode;

/**
 * дерево, отображающее список серверов
 * 
 * @author eav
 */
public class ServerTree
    extends ConnectableTree
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
        DefaultMutableTreeNode server1 = new ServerNode( "Main Server", new Server() );
        root.add( server1 );

        addMouseListener( new ConnectingAdapter( this ) );
    }

    /**
     * задать список серверов
     * 
     * @param servers список серверов
     */
    void setServerList(
        Server[] servers )
    {
        // todo
    }

    /**
     * добавить сервер
     * 
     * @param server новый сервер
     */
    void addServer(
        Server server )
    {

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
