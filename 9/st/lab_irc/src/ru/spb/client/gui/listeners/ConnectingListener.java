package ru.spb.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.tree.TreePath;

import ru.spb.client.entities.IConnectable;
import ru.spb.client.gui.ServiceLogPanel;
import ru.spb.client.gui.trees.IRCTree;

public class ConnectingListener
    implements
        MouseListener
{

    IRCTree tree;

    public ConnectingListener(
        IRCTree tree )
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
                ServiceLogPanel.getInstance().info( "already connected, disconnecting" );
                connectable.disconnect();
            }
            else
            {
                ServiceLogPanel.getInstance().info( "already disconnected, connecting" );
                connectable.connect();
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
