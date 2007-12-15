package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.connection.IRCSocketWrapper;
import ru.spb.client.gui.IRCTabbedPanel;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.client.gui.trees.ChannelTree;

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
    // todo в конфиг
    private String             _host               = "localhost";
    private int                _port               = 6667;
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
     * обертка, удерживающая сокет за этим сервером
     */
    private IRCSocketWrapper socketWrapper;

    /**
     * подключиться к этому серверу
     */
    public void connect()
    {
        ServiceLogPanel.getInstance().info( this, "connecting" );

        socketWrapper = new IRCSocketWrapper( this, User.getCurrentUser() );

        IRCTabbedPanel.getInstance().addChannelTree( this );

        ServiceLogPanel.getInstance().info( this, "connected" );
    }

    /**
     * получить список каналов, созданных на сервере
     * 
     * @param treeToUpdate дерево, в котором каналы отобразятся
     */
    public void retrieveChannels(
        ChannelTree treeToUpdate )
    {
        ServiceLogPanel.getInstance().info( this, "retreiving cnannel list" );
        socketWrapper.getChannels( treeToUpdate );
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

        socketWrapper.createChannel( channel );

        _registeredChannels.add( channel );
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect()
    {
        ServiceLogPanel.getInstance().info( this, "disconnecting" );

        try
        {
            socketWrapper.finalize();
            socketWrapper = null;
            IRCTabbedPanel.getInstance().removeChannelTree( this );
            ServiceLogPanel.getInstance().info( this, "disconnected" );
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            ServiceLogPanel.getInstance().error( e.getMessage() );
        }
    }

    public boolean isConnected()
    {
        return socketWrapper != null;
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
            socketWrapper.register( user );
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_host == null) ? 0 : _host.hashCode());
        result = prime * result + _port;
        return result;
    }

    @Override
    public boolean equals(
        Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        final Server other = (Server) obj;
        if ( _host == null )
        {
            if ( other._host != null )
                return false;
        }
        else if ( !_host.equals( other._host ) )
            return false;
        if ( _port != other._port )
            return false;
        return true;
    }

    public ArrayList<User> getRegisteredUsers(
        Channel channel )
    {
        return socketWrapper.getRegisteredUsers( channel );
    }

    public void join(
        Channel channel )
    {
        socketWrapper.join( channel );
    }

    public void privmsg(
        Channel channel,
        String message )
    {
        socketWrapper.privmsg( channel, message );
    }

    public Channel getChannelByName(
        String to )
    {
        for ( Channel channel : _registeredChannels )
        {
            if ( channel.getName().equalsIgnoreCase( to ) )
            {
                return channel;
            }
        }
        return null;
    }

    public void addChannel(
        Channel channel )
    {
        _registeredChannels.add( channel );
    }

    public ArrayList<Channel> getChannels()
    {
        return _registeredChannels;
    }
}
