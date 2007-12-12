package ru.spb.client.gui;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.IChattable;
import ru.spb.client.entities.User;

public class UserNode
    extends DefaultMutableTreeNode
    implements
        IChattable
{
    User user;

    public UserNode(
        User user )
    {
        super( user.getName() );
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    @Override
    public void startChat(
        IChattable chattable )
    {
        user.startChat( chattable );
    }

    @Override
    public boolean isChattingWithMe()
    {
        return user.isChattingWithMe();
    }
}
