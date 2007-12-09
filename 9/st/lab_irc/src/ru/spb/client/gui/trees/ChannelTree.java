package ru.spb.client.gui.trees;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.Channel;
import ru.spb.client.gui.ConnectingAdapter;
import ru.spb.client.gui.trees.nodes.ChannelNode;

public class ChannelTree extends ConnectableTree {

    private static ChannelTree instance;

    public ChannelTree(DefaultMutableTreeNode root) {
	super(root);
	addMouseListener(new ConnectingAdapter(this));
    }

    /**
     * 
     */
    private static final long serialVersionUID = 5408874563647082570L;

    public void addChannels(Channel[] channels) {
	for (Channel channel : channels) {
	    ChannelNode channelNode = new ChannelNode(channel.getName(), channel);
	    _root.add(channelNode);
	}
	this.expandRow(getRowCount() - 1);
    }

    public static ChannelTree getInstance() {
	if (instance == null) {
	    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Channels");
	    instance = new ChannelTree(root);
	}
	return instance;
    }

}
