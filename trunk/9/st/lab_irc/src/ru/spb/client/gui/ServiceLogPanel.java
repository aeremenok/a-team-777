package ru.spb.client.gui;

import java.util.Date;

/**
 * @author eav
 */
public class ServiceLogPanel
    extends IRCLogPanel
{
    private static ServiceLogPanel _instance = null;

    private ServiceLogPanel()
    {
        super();
        setColumns();
    }

    /**
     * добавляет столбцы в таблицу. каждой реализации - свои
     */
    protected void setColumns()
    {
        _logTableModel.addColumn( "Date" );
        _logTableModel.addColumn( "Message" );
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

    public static ServiceLogPanel getInstance()
    {
        if ( _instance == null )
        {
            _instance = new ServiceLogPanel();
        }
        return _instance;
    }
}
