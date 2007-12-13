package ru.spb.client.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class IRCLogPanel
    extends JScrollPane
{

    /**
     * данные текущей таблицы
     */
    protected DefaultTableModel _logTableModel;
    /**
     * представление текущей таблицы
     */
    protected ReadOnlyTable     _logTable;

    public IRCLogPanel()
    {
        super();

        JButton button = new JButton( "Очистить" );
        button.addMouseListener( new MouseListener()
        {
            @Override
            public void mouseClicked(
                MouseEvent e )
            {
                clear();
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
        } );
        // todo а как ее показать?
        add( button );

        _logTableModel = new DefaultTableModel();
        _logTable = new ReadOnlyTable( _logTableModel );
        _logTable.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
        add( _logTable );
        setViewportView( _logTable );
    }

    /**
     * добавляет столбцы в таблицу. каждой реализации - свои
     */
    protected abstract void setColumns();

    /**
     * очистить лог
     */
    public void clear()
    {
        _logTable.removeAll();
    }

}
