package ru.spb.client.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author eav
 */
public class ReadOnlyTable
    extends JTable
{
    private static final long serialVersionUID = -7774893625618427543L;

    public ReadOnlyTable(
        String[][] sTableContent,
        String[] sColNames )
    {
        super( sTableContent, sColNames );
    }

    public ReadOnlyTable()
    {
        super();
    }

    public ReadOnlyTable(
        DefaultTableModel tableModel )
    {
        super( tableModel );
    }

    public void setValueAt(
        Object value,
        int row,
        int col )
    {
        // do nothing
    }

    public boolean isCellEditable(
        int row,
        int col )
    {
        return false;
    }
}
