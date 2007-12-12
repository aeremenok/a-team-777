package ru.spb.client.entities;

import ru.spb.client.gui.ServiceLogPanel;

/**
 * содержит данные о сервере
 * 
 * @author eav
 */
public class Server
    implements
        IConnectable
{
    private String name;

    public Server(
        String name )
    {
        this.name = name;
    }

    /**
     * подключены ли
     */
    boolean isConnected = false;

    /**
     * подключиться к этому серверу
     */
    public void connect()
    {
        isConnected = true;
        ServiceLogPanel.getInstance().info( "connected" );

        getChannels();
    }

    public Channel[] getChannels()
    {
        ServiceLogPanel.getInstance().info( "getting cnannel list" );
        return new Channel[] { new Channel( "channel1" ), new Channel( "channel2" ) };
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect()
    {
        isConnected = false;
        ServiceLogPanel.getInstance().info( "disconnected" );
    }

    public boolean isConnected()
    {
        return isConnected;
    }

    public String getName()
    {
        return name;
    }

}
