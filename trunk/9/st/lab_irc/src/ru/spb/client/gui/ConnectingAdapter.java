package ru.spb.client.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.tree.TreePath;

import ru.spb.client.entities.IConnectable;
import ru.spb.client.entities.Server;
import ru.spb.client.gui.trees.ChannelTree;
import ru.spb.client.gui.trees.ConnectableTree;
import ru.spb.client.gui.trees.nodes.ServerNode;

public class ConnectingAdapter
    implements
        MouseListener
{

    ConnectableTree tree;

    public ConnectingAdapter(
        ConnectableTree tree )
    {
        this.tree = tree;
    }

    @Override
    public void mouseClicked(
        MouseEvent e )
    {
        if ( e.getClickCount() == 2 )
        {
            TreePath selPath = tree.getPathForLocation( e.getX(), e.getY() );
            Object selected = selPath.getLastPathComponent();
            connectTo( selected );
        }
    }

    /**
     * если объект - сервер или канал, налаживает подключение
     * 
     * @param selected выбранный объект
     */
    private void connectTo(
        Object selected )
    {
        if ( selected instanceof IConnectable )
        {
            IConnectable connectable = (IConnectable) selected;
            if ( connectable.isConnected() )
            {
                LogPanel.getInstance().info( "already connected, disconnecting" );
                connectable.disconnect();
            }
            else
            {
                LogPanel.getInstance().info( "already disconnected, connecting" );
                connectable.connect();
                // получаем список каналов
                // todo сделать нормальную связь!
                if ( connectable instanceof ServerNode )
                {
                    Server server = ((ServerNode) connectable).getServer();
                    ChannelTree.getInstance().addChannels( server.getChannels() );
                }
            }
        }
    }

    @Override
    public void mouseEntered(
        MouseEvent e )
    {
    }

    @Override
    public void mouseExited(
        MouseEvent e )
    {
    }

    @Override
    public void mousePressed(
        MouseEvent e )
    {
    }

    @Override
    public void mouseReleased(
        MouseEvent e )
    {
    }

}
