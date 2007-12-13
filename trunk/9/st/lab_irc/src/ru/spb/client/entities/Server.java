package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.gui.IRCTabbedPanel;
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
    private String             _name;

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

        // todo заглушка
        // createChannel( new Channel( "channel1", User.getCurrentUser() ) );
        // createChannel( new Channel( "channel2", new User( "user1" ) ) );
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

        // todo послать команду
        _isConnected = true;
        ServiceLogPanel.getInstance().info( this, "connected" );
        IRCTabbedPanel.getInstance().addChannelTree( this );
    }

    /**
     * получить список каналов, созданных на сервере
     * 
     * @return
     */
    public Channel[] getChannels()
    {
        ServiceLogPanel.getInstance().info( this, "getting cnannel list" );
        // todo послать команду
        Channel[] channels = new Channel[_registeredChannels.size()];
        return _registeredChannels.toArray( channels );
    }

    public void createChannel(
        Channel channel )
    {
        ServiceLogPanel.getInstance().info( this, "registering channel" );
        // todo послать команду
        _registeredChannels.add( channel );
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect()
    {
        ServiceLogPanel.getInstance().info( this, "disconnecting" );
        // todo послать команду
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
        // todo послать команду
        return _registeredUsers.contains( user );
    }

    @Override
    public void register(
        User user )
    {
        ServiceLogPanel.getInstance().info( this, "registering user " + user.getName() );
        _registeredUsers.add( user );
        // todo послать команду
    }
}
