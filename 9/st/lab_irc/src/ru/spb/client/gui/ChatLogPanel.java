package ru.spb.client.gui;

import java.util.Date;

import ru.spb.client.entities.User;

public class ChatLogPanel
    extends IRCLogPanel
{
    public ChatLogPanel()
    {
        super();
        setColumns();
    }

    @Override
    protected void setColumns()
    {
        _logTableModel.addColumn( "User" );
        _logTableModel.addColumn( "Date" );
        _logTableModel.addColumn( "Message" );
    }

    /**
     * отобразить сообщение от пользователя
     * 
     * @param user пользователь
     * @param message его сообщение
     */
    public void logMessage(
        User user,
        String message )
    {
        Date current = new Date();
        String name = user.getName();
        Object[] rowData = new Object[] { name, current, message };
        _logTableModel.addRow( rowData );
    }
}
