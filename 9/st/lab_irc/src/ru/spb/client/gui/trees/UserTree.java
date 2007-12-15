package ru.spb.client.gui.trees;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.User;
import ru.spb.client.gui.listeners.ChattingListener;
import ru.spb.client.gui.trees.nodes.UserNode;

public class UserTree
    extends IRCTree
{
    private Channel _channel;

    private UserTree(
        DefaultMutableTreeNode root )
    {
        super( root );
        addMouseListener( new ChattingListener( this ) );
    }

    /**
     * получить новое дерево для заданного канала
     * 
     * @param channel канал
     * @return дерево с его пользователями
     */
    public static UserTree getTreeForChannel(
        Channel channel )
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Chatting about " + channel.getTopic() );
        UserTree result = new UserTree( root );

        channel.retrieveUsers( result );

        return result;
    }

    public void setChannel(
        Channel channel )
    {
        _channel = channel;
    }

    /**
     * добавить пользователя в дерево
     * 
     * @param user пользователь
     */
    public void addUser(
        User user )
    {
        if ( user != null )
        {
            UserNode userNode = new UserNode( user );
            DefaultTreeModel model = (DefaultTreeModel) getModel();
            model.insertNodeInto( userNode, _root, _root.getChildCount() );
            expandRow( getRowCount() - 1 );
        }
    }

    /**
     * добавить пользователей в дерево ]:->
     * 
     * @param users пользователи
     */
    public void addUsers(
        ArrayList<User> users )
    {
        for ( User user : users )
        {
            UserNode userNode = new UserNode( user );
            _root.add( userNode );
        }
        expandRow( getRowCount() - 1 );
    }

    /**
     * удалить пользователя из дерева
     * 
     * @param user пользователь
     */
    public void removeUser(
        User user )
    {
        Enumeration iter = _root.children();
        while ( iter.hasMoreElements() )
        {
            UserNode node = (UserNode) iter.nextElement();
            if ( node.getUser().equals( user ) )
            {
                _root.remove( node );
            }
        }
    }

    public Channel getChannel()
    {
        return _channel;
    }
}
