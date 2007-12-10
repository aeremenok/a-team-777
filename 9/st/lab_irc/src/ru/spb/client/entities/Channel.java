package ru.spb.client.entities;

import ru.spb.client.gui.LogPanel;

/**
 * содержит данные о канале
 * 
 * @author eav
 */
public class Channel
    implements
        IConnectable
{

    private boolean isConnected;
    private String  name;

    public Channel(
        String name )
    {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#isConnected()
     */
    public boolean isConnected()
    {
        return isConnected;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#disconnect()
     */
    public void disconnect()
    {
        isConnected = true;
        LogPanel.getInstance().info( "connected" );
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#connect()
     */
    public void connect()
    {
        isConnected = true;
        LogPanel.getInstance().info( "connected" );
    }

    public String getName()
    {
        return name;
    }

}
