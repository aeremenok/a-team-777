package ru.spb.client.gui.trees;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * дерево, хранящее корень
 * 
 * @author eav
 */
public abstract class IRCTree
    extends JTree
{

    /**
     * 
     */
    private static final long        serialVersionUID = -772240636464834482L;
    protected DefaultMutableTreeNode _root;

    public IRCTree(
        DefaultMutableTreeNode root )
    {
        super( new DefaultTreeModel( root ) );
        _root = root;
        this.addKeyListener( new KeyListener()
        {
            String _criterion = "";

            @Override
            public void keyPressed(
                KeyEvent e )
            {
                if ( e.getKeyChar() == 'f' )
                {
                    // todo добавить символ к криетрию отбора
                    filterNodes( _criterion );
                }
            }

            @Override
            public void keyReleased(
                KeyEvent e )
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyTyped(
                KeyEvent e )
            {
                // TODO Auto-generated method stub

            }
        } );
    }

    public void clear()
    {
        _root.removeAllChildren();
    }

    public boolean isRoot(
        DefaultMutableTreeNode selected )
    {
        return _root.equals( selected );
    }

    private void filterNodes(
        String string )
    {
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        Enumeration iter = _root.children();
        while ( iter.hasMoreElements() )
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) iter.nextElement();
            String title = (String) node.getUserObject();
            if ( title.startsWith( string ) )
            {
                // todo показать узел
            }
        }
    }
}
