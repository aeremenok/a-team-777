package ru.spb.client.gui.trees.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.IChattable;
import ru.spb.client.entities.IConnectable;

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

    public IConnectable getChannel()
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
}
