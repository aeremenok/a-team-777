package ru.spb.client.gui.logpanels;

import java.util.Date;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.Server;
import ru.spb.client.entities.User;

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
        _logTableModel.addColumn( "Who" );
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
        Object[] rowData = new Object[] { current, "---", message };
        _logTableModel.addRow( rowData );
    }

    /**
     * авторизованное сообщение в лог
     * 
     * @param sender сущность, от которой пришло
     * @param message служебное сообщение
     */
    public void info(
        Object sender,
        String message )
    {
        Date current = new Date();

        // todo возможно обобщить
        String who = "---";
        if ( sender instanceof Server )
        {
            who = "server " + ((Server) sender).getName();
        }
        else if ( sender instanceof User )
        {
            who = "user " + ((User) sender).getName();
        }
        else if ( sender instanceof Channel )
        {
            who = "channel " + ((Channel) sender).getName();
        }
        else if ( sender instanceof String )
        {
            who = (String) sender;
        }

        Object[] rowData = new Object[] { current, who, message };
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
