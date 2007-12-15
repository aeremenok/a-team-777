package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.gui.IRCTabbedPanel;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.messages.PrivateMessage;

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
    /**
     * местонахождение канала
     */
    private Server          host;

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
        _isConnected = false;
        ServiceLogPanel.getInstance().info( this, "disconnected" );
    }

    public void connect()
    {
        ServiceLogPanel.getInstance().info( this, "connecting" );
        register( User.getCurrentUser() );
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
        host.join( this );
        IRCTabbedPanel.getInstance().addChat( this );
    }

    public ArrayList<User> getUsers()
    {
        _registeredUsers = host.getRegisteredUsers( this );
        return _registeredUsers;
    }

    @Override
    public void quitChat(
        IChattable chattable )
    {
        ServiceLogPanel.getInstance().info( this, "=exiting=" );
        // todo что послать?
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

    public void setTopic(
        String topic )
    {
        _topic = topic;
    }

    @Override
    public void say(
        PrivateMessage message )
    {
        for ( MessageListener listener : listeners )
        {
            listener.onMessage( message );
        }
    }

    public Server getHost()
    {
        return host;
    }

    public void setHost(
        Server host )
    {
        this.host = host;
    }

    private ArrayList<MessageListener> listeners = new ArrayList<MessageListener>();

    @Override
    public void addMessageListener(
        MessageListener messageListener )
    {
        listeners.add( messageListener );
    }

    public User getUserByName(
        String from )
    {
        for ( User user : _registeredUsers )
        {
            if ( user.getName().equalsIgnoreCase( from ) )
            {
                return user;
            }
        }
        return null;
    }
}
