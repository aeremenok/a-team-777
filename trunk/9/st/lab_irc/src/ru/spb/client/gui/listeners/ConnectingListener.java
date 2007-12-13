package ru.spb.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.tree.TreePath;

import ru.spb.client.entities.IConnectable;
import ru.spb.client.gui.dialogs.ChannelParamsRequest;
import ru.spb.client.gui.dialogs.ServerParamsRequest;
import ru.spb.client.gui.trees.ChannelTree;
import ru.spb.client.gui.trees.IRCTree;
import ru.spb.client.gui.trees.ServerTree;

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
        if ( e.getButton() == MouseEvent.BUTTON1 )
        { // левая кнопка
            if ( e.getClickCount() == 2 )
            {
                TreePath selPath = tree.getPathForLocation( e.getX(), e.getY() );
                Object selected = selPath.getLastPathComponent();

                if ( selected instanceof IConnectable )
                {
                    IConnectable connectable = (IConnectable) selected;
                    connectable.toggleConnection();
                }
            }
        }
        else
        { // другая кнопка
            if ( e.getClickCount() == 2 )
            {
                if ( tree instanceof ServerTree )
                {
                    ServerTree serverTree = (ServerTree) tree;
                    serverTree.addServer( ServerParamsRequest.getNewServer() );
                }
                else if ( tree instanceof ChannelTree )
                {
                    ChannelTree channelTree = (ChannelTree) tree;
                    channelTree.addChannel( ChannelParamsRequest.getNewChannel() );
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
