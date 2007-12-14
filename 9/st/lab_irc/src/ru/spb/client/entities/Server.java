package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.IRCSocketWrapper;
import ru.spb.client.gui.IRCTabbedPanel;
import ru.spb.client.gui.logpanels.ServiceLogPanel;

/**
 * содержит данные о сервере
 * 
 * @author eav
 */
public class Server
    implements
        IConnectable
{
    private String             _name;
    private String             _host               = "localhost";
    private int                _port               = 6667;
    /**
     * пользователи, зарегистрировавшиеся на сервере
     */
    private ArrayList<User>    _registeredUsers    = new ArrayList<User>();
    /**
     * каналы, зарегистрированные пользователями на сервере
     */
    private ArrayList<Channel> _registeredChannels = new ArrayList<Channel>();

    public Server(
        String name )
    {
        _name = name;
    }

    /**
     * подключены ли
     */
    boolean _isConnected = false;

    /**
     * подключиться к этому серверу
     */
    public void connect()
    {
        ServiceLogPanel.getInstance().info( this, "connecting" );

        IRCSocketWrapper.connect( this, User.getCurrentUser() );
        _isConnected = true;
        IRCTabbedPanel.getInstance().addChannelTree( this );

        ServiceLogPanel.getInstance().info( this, "connected" );
    }

    /**
     * получить список каналов, созданных на сервере
     * 
     * @return
     */
    public ArrayList<Channel> getChannels()
    {
        ServiceLogPanel.getInstance().info( this, "getting cnannel list" );

        _registeredChannels = IRCSocketWrapper.getChannels();
        return _registeredChannels;
    }

    /**
     * создать новый канал на текущем сервере
     * 
     * @param channel новый канал
     */
    public void createChannel(
        Channel channel )
    {
        ServiceLogPanel.getInstance().info( this, "registering channel" );

        IRCSocketWrapper.createChannel( channel );
        _registeredChannels.add( channel );
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect()
    {
        ServiceLogPanel.getInstance().info( this, "disconnecting" );

        IRCSocketWrapper.disconnect( this );
        _isConnected = false;
        IRCTabbedPanel.getInstance().removeChannelTree( this );

        ServiceLogPanel.getInstance().info( this, "disconnected" );
    }

    public boolean isConnected()
    {
        return _isConnected;
    }

    public String getName()
    {
        return _name;
    }

    public void toggleConnection()
    {
        if ( isConnected() )
            disconnect();
        else
            connect();
    }

    @Override
    public void register(
        User user )
    {
        ServiceLogPanel.getInstance().info( this, "registering user " + user.getName() );
        try
        {
            IRCSocketWrapper.register( user );
            _registeredUsers.add( user );
        }
        catch ( Throwable e )
        {
            ServiceLogPanel.getInstance().error( e.getMessage() );
            e.printStackTrace();
        }
    }

    public String getHost()
    {
        return _host;
    }

    public int getPort()
    {
        return _port;
    }
}
