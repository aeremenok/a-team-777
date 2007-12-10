package ru.spb.client.entities;

import ru.spb.client.gui.LogPanel;

/**
 * содержит данные о сервере
 * 
 * @author eav
 */
public class Server
    implements
        IConnectable
{

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
        LogPanel.getInstance().info( "connected" );

        getChannels();
    }

    public Channel[] getChannels()
    {
        LogPanel.getInstance().info( "getting cnannel list" );
        return new Channel[] { new Channel( "channel1" ), new Channel( "channel2" ) };
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect()
    {
        isConnected = false;
        LogPanel.getInstance().info( "disconnected" );
    }

    public boolean isConnected()
    {
        return isConnected;
    }

}
