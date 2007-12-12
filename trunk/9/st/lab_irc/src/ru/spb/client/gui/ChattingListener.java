package ru.spb.client.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.tree.TreePath;

import ru.spb.client.entities.IChattable;
import ru.spb.client.entities.User;
import ru.spb.client.gui.trees.IRCTree;

public class ChattingListener
    implements
        MouseListener
{

    /**
     * дерево, за которым следим
     */
    private IRCTree _tree;

    public ChattingListener(
        IRCTree userTree )
    {
        _tree = userTree;
    }

    @Override
    public void mouseClicked(
        MouseEvent e )
    {
        if ( e.getClickCount() == 2 )
        { // начинаем чат
            TreePath selPath = _tree.getPathForLocation( e.getX(), e.getY() );
            Object selected = selPath.getLastPathComponent();
            startChatWithMe( selected );
        }

    }

    /**
     * текущий юзер начинает чат с выбранным узлом
     * 
     * @param selected выбранный для чата узел
     */
    private void startChatWithMe(
        Object selected )
    {
        if ( selected instanceof IChattable )
        {
            IChattable chattable = (IChattable) selected;
            if ( !chattable.isChattingWithMe() )
                chattable.startChat( User.getCurrentUser() );
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
