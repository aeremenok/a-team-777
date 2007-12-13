package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.gui.IRCTabbedPanel;
import ru.spb.client.gui.ServiceLogPanel;

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
    private boolean         isConnected;
    /**
     * имя канала
     */
    private String          _name;

    private ArrayList<User> _registeredUsers = new ArrayList<User>();

    /**
     * хозяин канала
     */
    private User            _owner;

    public Channel(
        String name,
        User user )
    {
        _name = name;
        _owner = user;
        register( _owner );

        // todo заглушка
        register( new User( "user1" ) );
    }

    public boolean isConnected()
    {
        return isConnected;
    }

    public void disconnect()
    {
        // todo послать команду
        isConnected = false;
        ServiceLogPanel.getInstance().info( "connected" );
    }

    public void connect()
    {
        if ( !isRegistered( User.getCurrentUser() ) )
        {
            register( User.getCurrentUser() );
        }

        // todo послать команду
        isConnected = true;
        ServiceLogPanel.getInstance().info( "connected" );
    }

    public String getName()
    {
        return _name;
    }

    @Override
    public void startChat(
        IChattable chattable )
    {
        ServiceLogPanel.getInstance().info( "starting chat on channel =" + _name + "=" );
        IRCTabbedPanel.getInstance().addChat( this );
    }

    public User[] getUsers()
    {
        User[] regusers = new User[_registeredUsers.size()];
        return _registeredUsers.toArray( regusers );
    }

    @Override
    public void quitChat(
        IChattable chattable )
    {
        ServiceLogPanel.getInstance().info( "exiting from channel =" + _name + "=" );
        IRCTabbedPanel.getInstance().removeChat( this );
    }

    @Override
    public void toggleChat(
        IChattable chattable )
    {
        /**
         * здесь - другая реализация, нежели в {@link User}
         */
        if ( isConnected )
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
        ServiceLogPanel.getInstance().info( "channel " + _name + ": registering user " + user.getName() );
        _registeredUsers.add( user );
        // todo послать команду
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
}
