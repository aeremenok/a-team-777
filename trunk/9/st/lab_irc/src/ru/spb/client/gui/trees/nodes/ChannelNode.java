package ru.spb.client.gui.trees.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.IConnectable;

public class ChannelNode extends DefaultMutableTreeNode implements IConnectable {

    /**
     * 
     */
    private static final long serialVersionUID = -6096950684657632514L;

    private IConnectable channel;

    public ChannelNode(String name, IConnectable channel) {
	super(name);
	if (channel == null) {
	    throw new NullPointerException("channel must exist in node");
	}
	this.channel = channel;
    }

    public IConnectable getChannel() {
	return channel;
    }

    public void connect() {
	channel.connect();
    }

    public void disconnect() {
	channel.disconnect();
    }

    public boolean isConnected() {
	return channel.isConnected();
    }

}
