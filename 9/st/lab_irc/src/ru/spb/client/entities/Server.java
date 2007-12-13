package ru.spb.client.entities;

import java.util.ArrayList;

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
    private SocketConnector    _connector;

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
        _connector = new SocketConnector( this );
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
        if ( !isRegistered( User.getCurrentUser() ) )
        {
            register( User.getCurrentUser() );
        }

        _connector.connect();
        _isConnected = true;
        ServiceLogPanel.getInstance().info( this, "connected" );
        IRCTabbedPanel.getInstance().addChannelTree( this );
    }

    /**
     * получить список каналов, созданных на сервере
     * 
     * @return
     */
    public ArrayList<Channel> getChannels()
    {
        ServiceLogPanel.getInstance().info( this, "getting cnannel list" );
        _registeredChannels = _connector.getChannels();
        return _registeredChannels;
    }

    public void createChannel(
        Channel channel )
    {
        ServiceLogPanel.getInstance().info( this, "registering channel" );
        _connector.createChannel();
        _registeredChannels.add( channel );
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect()
    {
        ServiceLogPanel.getInstance().info( this, "disconnecting" );
        _connector.disconnect();
        _isConnected = false;
        ServiceLogPanel.getInstance().info( this, "disconnected" );
        IRCTabbedPanel.getInstance().removeChannelTree( this );
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
        {
            disconnect();
        }
        else
        {
            connect();
        }
    }

    @Override
    public boolean isRegistered(
        User user )
    {
        _registeredUsers = _connector.getRegisteredUsers();
        return _registeredUsers.contains( user );
    }

    @Override
    public void register(
        User user )
    {
        ServiceLogPanel.getInstance().info( this, "registering user " + user.getName() );
        _registeredUsers.add( user );
        _connector.register( user );
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
