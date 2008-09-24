package ru.spb.client.gui.trees.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.IChattable;
import ru.spb.client.entities.User;
import ru.spb.client.gui.logpanels.ChatLogPanel;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.messages.PrivateMessage;

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

    public void startChat(
        IChattable chattable )
    {
        user.startChat( chattable );
    }

    public String getName()
    {
        return user.getName();
    }

    public void quitChat(
        IChattable chattable )
    {
        user.quitChat( chattable );
    }

    public void toggleChat(
        IChattable chattable )
    {
        user.toggleChat( chattable );
    }

    public void say(
        PrivateMessage message )
    {
        user.say( message );
    }

    public void addMessageListener(
        MessageListener messageListener )
    {
        user.addMessageListener( messageListener );
    }

    public void setChatLogPanel(
        ChatLogPanel chatLogPanel )
    {
        user.setChatLogPanel( chatLogPanel );
    }
}
