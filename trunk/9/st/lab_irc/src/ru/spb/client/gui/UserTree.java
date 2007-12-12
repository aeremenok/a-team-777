package ru.spb.client.gui;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.User;
import ru.spb.client.gui.trees.IRCTree;

public class UserTree
    extends IRCTree
{
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
        DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Users of " + channel.getName() );
        UserTree result = new UserTree( root );

        result.addUsers( channel.getUsers() );

        return result;
    }

    /**
     * добавить пользователя в дерево
     * 
     * @param user
     */
    public void addUser(
        User user )
    {
        UserNode userNode = new UserNode( user );
        _root.add( userNode );
        expandRow( getRowCount() - 1 );
    }

    /**
     * добавить пользователей в дерево ]:->
     * 
     * @param users пользователи
     */
    public void addUsers(
        User[] users )
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
     * @param user
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
}
