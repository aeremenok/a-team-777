package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.gui.IRCTabbedPanel;
import ru.spb.client.gui.listeners.WallopsListener;
import ru.spb.client.gui.logpanels.ChatLogPanel;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.client.gui.trees.UserTree;
import ru.spb.messages.PrivateMessage;
import ru.spb.messages.WallopsMessage;

/**
 * содержит данные о канале
 * 
 * @author eav
 */
public class Channel
    implements
        IChattable
{

    /**
     * подключены ли к этому каналу
     */
    private boolean  _isChatting = false;
    /**
     * имя канала
     */
    private String   _name;
    /**
     * тема канала
     */
    private String   _topic;

    /**
     * местонахождение канала
     */
    private Server   _host;
    private UserTree _userContainer;

    public Channel(
        String name,
        String topic )
    {
        _name = name;
        _topic = topic;
    }

    public String getName()
    {
        return _name;
    }

    @Override
    public void startChat(
        IChattable chattable )
    {
        _isChatting = true;
        ServiceLogPanel.getInstance().info( this, "=starting chat=" );
        _host.join( this );
        IRCTabbedPanel.getInstance().addChat( this );
    }

    /**
     * получить список пользователей канала
     * 
     * @param userTree куда этот список отобразится
     */
    public void retrieveUsers(
        UserTree userTree )
    {
        _userContainer = userTree;
        _host.retrieveUsers( this );
    }

    private LoggingListener _loggingListener = null;

    /**
     * записывает пришедшее сообщение в лог
     * 
     * @author eav
     */
    private class LoggingListener
        implements
            MessageListener
    {
        private ChatLogPanel _chatLogPanel;

        public LoggingListener(
            ChatLogPanel chatLogPanel )
        {
            _chatLogPanel = chatLogPanel;
        }

        @Override
        public void onMessage(
            PrivateMessage message )
        {
            _chatLogPanel.logMessage( message.getFrom(), message.getContent() );
        }
    }

    /**
     * добавить пользователя в канал
     * 
     * @param user пользователь
     */
    public void addUser(
        User user )
    {
        _userContainer.addUser( user );
        user.addMessageListener( _loggingListener );
    }

    @Override
    public void quitChat(
        IChattable chattable )
    {
        ServiceLogPanel.getInstance().info( this, "=exiting=" );
        _isChatting = false;
        _host.quitChat( this );
        IRCTabbedPanel.getInstance().removeChat( this );
    }

    @Override
    public void toggleChat(
        IChattable chattable )
    {
        /**
         * здесь - другая реализация, нежели в {@link User}
         */
        if ( !_isChatting )
        {
            startChat( chattable );
        }
        else
        {
            quitChat( chattable );
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
        for ( MessageListener listener : _messageListeners )
        {
            listener.onMessage( message );
        }
    }

    public Server getHost()
    {
        return _host;
    }

    public void setHost(
        Server host )
    {
        _host = host;
    }

    private ArrayList<MessageListener> _messageListeners = new ArrayList<MessageListener>();
    private ArrayList<WallopsListener> _wallopsListeners = new ArrayList<WallopsListener>();

    @Override
    public void addMessageListener(
        MessageListener messageListener )
    {
        _messageListeners.add( messageListener );
    }

    /**
     * получить пользователя с заданным именем
     * 
     * @param userName имя пользователя
     * @return пользователь
     */
    public User getUserByName(
        String userName )
    {
        for ( User user : _userContainer.getUsers() )
        {
            if ( user.getName().equalsIgnoreCase( userName ) )
            {
                return user;
            }
        }
        return null;
    }

    @Override
    public void setChatLogPanel(
        ChatLogPanel chatLogPanel )
    {
        _loggingListener = new LoggingListener( chatLogPanel );
        addMessageListener( _loggingListener );
    }

    /**
     * оповещает об изменениях в канале
     * 
     * @param wallopsMessage сообщение об изменениях
     */
    public void fireWallops(
        WallopsMessage wallopsMessage )
    {
        for ( WallopsListener listener : _wallopsListeners )
        {
            listener.onWallops( wallopsMessage );
        }
    }

    /**
     * подвесить обработчик изменений в канале
     * 
     * @param wallopsListener обработчик
     */
    public void addWallopsListener(
        WallopsListener wallopsListener )
    {
        _wallopsListeners.add( wallopsListener );
    }
}
