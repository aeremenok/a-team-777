package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.gui.IRCTabbedPanel;
import ru.spb.client.gui.logpanels.ServiceLogPanel;

/**
 * содержит данные о канале
 * 
 * @author eav
 */
public class Channel
    implements
        IConnectable,
        IChattable
{

    /**
     * подключены ли к этому каналу
     */
    private boolean         _isConnected;
    /**
     * имя канала
     */
    private String          _name;
    private String          _topic;

    private ArrayList<User> _registeredUsers = new ArrayList<User>();

    /**
     * хозяин канала
     */
    private User            _owner;

    public Channel(
        String name,
        String topic,
        User user )
    {
        _name = name;
        _topic = topic;
        _owner = user;
        register( _owner );
    }

    public boolean isConnected()
    {
        return _isConnected;
    }

    public void disconnect()
    {
        ServiceLogPanel.getInstance().info( this, "disconnecting" );
        // todo послать команду
        _isConnected = false;
        ServiceLogPanel.getInstance().info( this, "disconnected" );
    }

    public void connect()
    {
        ServiceLogPanel.getInstance().info( this, "connecting" );
        register( User.getCurrentUser() );
        // todo послать команду
        _isConnected = true;
        ServiceLogPanel.getInstance().info( this, "connected" );
    }

    public String getName()
    {
        return _name;
    }

    @Override
    public void startChat(
        IChattable chattable )
    {
        ServiceLogPanel.getInstance().info( this, "=starting chat=" );
        IRCTabbedPanel.getInstance().addChat( this );
    }

    public ArrayList<User> getUsers()
    {
        return _registeredUsers;
    }

    @Override
    public void quitChat(
        IChattable chattable )
    {
        ServiceLogPanel.getInstance().info( this, "=exiting=" );
        IRCTabbedPanel.getInstance().removeChat( this );
    }

    @Override
    public void toggleChat(
        IChattable chattable )
    {
        /**
         * здесь - другая реализация, нежели в {@link User}
         */
        if ( _isConnected )
        {
            startChat( chattable );
        }
        else
        {
            quitChat( chattable );
        }
    }

    @Override
    public void toggleConnection()
    {
        /**
         * здесь метод нужен просто для удобства
         */
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
    public void register(
        User user )
    {
        if ( user != null )
        {
            ServiceLogPanel.getInstance().info( this, "registering user " + user.getName() );
            _registeredUsers.add( user );
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_name == null) ? 0 : _name.hashCode());
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
        final Channel other = (Channel) obj;
        if ( _name == null )
        {
            if ( other._name != null )
                return false;
        }
        else if ( !_name.equals( other._name ) )
            return false;
        return true;
    }

    public String getTopic()
    {
        return _topic;
    }
}
