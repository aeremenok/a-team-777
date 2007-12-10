package ru.spb.client.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author eav
 */
public class LogPanel
    extends JScrollPane
{
    private static final String[] _colNames = new String[] { "Time", "Message" };

    private DefaultTableModel     _logTableModel;
    private ReadOnlyTable         _logTable;

    private static LogPanel       _instance = null;

    private LogPanel()
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
        // panel.add( button );
        add( button );

        _logTableModel = new DefaultTableModel();
        _logTable = new ReadOnlyTable( _logTableModel );
        _logTableModel.addColumn( "Date" );
        _logTableModel.addColumn( "Message" );
        add( _logTable );
        setViewportView( _logTable );
    }

    /**
     * вывести служебное сообщение
     * 
     * @param message служебное сообщение
     */
    public void info(
        String message )
    {
        Date current = new Date();
        Object[] rowData = new Object[] { current, message };
        _logTableModel.addRow( rowData );
    }

    /**
     * вывести служебное сообщение об ошибке
     * 
     * @param message сообщение об ошибке
     */
    public void error(
        String message )
    {
        // todo подсветить красным
        info( message );
    }

    /**
     * очистить лог
     */
    public void clear()
    {
        _logTable.removeAll();
    }

    public static LogPanel getInstance()
    {
        if ( _instance == null )
        {
            _instance = new LogPanel();
        }
        return _instance;
    }
}
