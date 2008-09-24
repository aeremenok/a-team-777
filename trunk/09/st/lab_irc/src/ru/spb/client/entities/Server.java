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
    private String _name;
    // todo в конфиг
    private String _host = "localhost";
    private int    _port = 6667;

    public Server(
        String name )
    {
        _name = name;
    }

    public Server(
        String name,
        String host )
    {
        _name = name;
        _host = host;
    }

    /**
     * обертка, удерживающая сокет за этим сервером
     */
    private IRCSocketWrapper _socketWrapper;
    private ChannelTree      _channelContainer;

    /**
     * подключиться к этому серверу
     */
    public void connect()
    {
        ServiceLogPanel.getInstance().info( this, "connecting" );

        _socketWrapper = new IRCSocketWrapper( this );

        _channelContainer = IRCTabbedPanel.getInstance().addChannelTree( this );

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
        _channelContainer = treeToUpdate;
        _socketWrapper.retrieveChannels( this );
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
        _socketWrapper.createChannel( channel );
        channel.setHost( this );
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect()
    {
        ServiceLogPanel.getInstance().info( this, "disconnecting" );

        try
        {
            _socketWrapper.finalize();
            _socketWrapper = null;
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
        return _socketWrapper != null;
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

    /**
     * получить для канала список пользователей, присоединеннных к каналу
     * 
     * @param channel канал
     */
    public void retrieveUsers(
        Channel channel )
    {
        _socketWrapper.retrieveUsers( channel );
    }

    /**
     * отправить запрос на присоединение к заданному каналу
     * 
     * @param channel канал для присоединение
     */
    public void join(
        Channel channel )
    {
        _socketWrapper.join( channel );
    }

    /**
     * получить канал по его имени
     * 
     * @param channelName имя канала
     * @return канал
     */
    public Channel getChannelByName(
        String channelName )
    {
        for ( Channel channel : _channelContainer.getChannels() )
        {
            if ( channel.getName().equalsIgnoreCase( channelName ) )
            {
                return channel;
            }
        }
        return null;
    }

    /**
     * добавить канал, полученный с сервера
     * 
     * @param channel канал, полученный с сервера
     */
    public void addChannel(
        Channel channel )
    {
        ServiceLogPanel.getInstance().info( this, "adding channel" );
        channel.setHost( this );
        _channelContainer.addChannel( channel );
    }

    public void quitChat(
        Channel channel )
    {
        _socketWrapper.part( channel );
    }

    public ArrayList<Channel> getChannels()
    {
        return _channelContainer.getChannels();
    }

    public void setHost(
        String host )
    {
        this._host = host;
    }

    public void setPort(
        int port )
    {
        this._port = port;
    }
}
