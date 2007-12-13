package ru.spb.client.gui.trees;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

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
        super( root );
        _root = root;
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
}
