package ru.spb.client.gui.trees;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.User;
import ru.spb.client.gui.listeners.ChattingListener;
import ru.spb.client.gui.listeners.WallopsListener;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.client.gui.trees.nodes.UserNode;
import ru.spb.messages.JoinMessage;
import ru.spb.messages.WallopsMessage;

/**
 * отображаемое дерево и контейнер пользователей
 * 
 * @author eav
 */
public class UserTree
    extends IRCTree
{
    private Channel         _channel;
    // todo убрать
    private ArrayList<User> _users = new ArrayList<User>();

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

        result.setChannel( channel );
        channel.retrieveUsers( result );

        return result;
    }

    private void setChannel(
        Channel channel )
    {
        _channel = channel;
        // обработчик изменений состава участников канала
        _channel.addWallopsListener( new WallopsListener()
        {
            @Override
            public void onWallops(
                WallopsMessage wallopsMessage )
            {
                if ( wallopsMessage.getServiceMessage() instanceof JoinMessage )
                {
                    JoinMessage joinMessage = (JoinMessage) wallopsMessage.getServiceMessage();
                    ServiceLogPanel.getInstance().info(
                                                        _channel,
                                                        wallopsMessage.getAuthor() + " has joined " +
                                                                        joinMessage.getChannelName() );
                    _channel.addUser( new User( wallopsMessage.getAuthor() ) );
                }
            }
        } );
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
            _users.add( user );
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
            _users.add( user );
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
                _users.remove( user );
            }
        }
    }

    public Channel getChannel()
    {
        return _channel;
    }

    public ArrayList<User> getUsers()
    {
        return _users;
    }
}
