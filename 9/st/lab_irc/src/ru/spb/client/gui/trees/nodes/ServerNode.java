package ru.spb.client.gui.trees.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import ru.spb.client.entities.IConnectable;
import ru.spb.client.entities.Server;

public class ServerNode
    extends DefaultMutableTreeNode
    implements
        IConnectable
{
    /**
     * 
     */
    private static final long serialVersionUID = 8241709727851465205L;

    private Server            server;

    public ServerNode(
        Server server )
    {
        super( server.getName() );
        this.server = server;
    }

    public Server getServer()
    {
        return server;
    }

    public void connect()
    {
        server.connect();
    }

    public void disconnect()
    {
        server.disconnect();
    }

    public boolean isConnected()
    {
        return server.isConnected();
    }

}
