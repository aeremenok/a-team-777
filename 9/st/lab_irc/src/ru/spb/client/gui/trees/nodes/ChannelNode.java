package ru.spb.client.gui.trees.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.IChattable;
import ru.spb.client.entities.IConnectable;
import ru.spb.client.gui.logpanels.ChatLogPanel;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.messages.PrivateMessage;

public class ChannelNode
    extends DefaultMutableTreeNode
    implements
        IConnectable,
        IChattable
{
    private static final long serialVersionUID = -6096950684657632514L;

    private Channel           channel;

    public ChannelNode(
        Channel channel )
    {
        super( channel.getName() );
        this.channel = channel;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public void connect()
    {
        channel.connect();
    }

    public void disconnect()
    {
        channel.disconnect();
    }

    public boolean isConnected()
    {
        return channel.isConnected();
    }

    public void startChat(
        IChattable chattable )
    {
        channel.startChat( chattable );
    }

    public String getName()
    {
        return channel.getName();
    }

    public void quitChat(
        IChattable chattable )
    {
        channel.quitChat( chattable );
    }

    public void toggleChat(
        IChattable chattable )
    {
        channel.toggleChat( chattable );
    }

    public void toggleConnection()
    {
        channel.toggleConnection();
    }

    public void say(
        PrivateMessage message )
    {
        channel.say( message );
    }

    public void addMessageListener(
        MessageListener messageListener )
    {
        channel.addMessageListener( messageListener );
    }

    public void setChatLogPanel(
        ChatLogPanel chatLogPanel )
    {
        channel.setChatLogPanel( chatLogPanel );
    }
}
