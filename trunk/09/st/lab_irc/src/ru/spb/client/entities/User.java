package ru.spb.client.entities;

import java.util.ArrayList;

import ru.spb.client.gui.logpanels.ChatLogPanel;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.messages.PrivateMessage;

/**
 * содержит данные о пользователе
 * 
 * @author eav
 */
public class User
    implements
        IChattable
{
    /**
     * юзер, который использует программу
     */
    static User    _currentUser;

    /**
     * имя пользователя (nickname)
     */
    private String _name;

    private String _emailLogin;

    private String _hostAlias;

    private String _fullName;

    private String _passWord;

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
        final User other = (User) obj;
        if ( _name == null )
        {
            if ( other._name != null )
                return false;
        }
        else if ( !_name.equals( other._name ) )
            return false;
        return true;
    }

    public User(
        String name )
    {
        super();
        _name = name;
    }

    public String getName()
    {
        return _name;
    }

    /**
     * создать профиль пользователя, пользующегося программой
     * 
     * @return текущий пользователь
     */
    public static User getCurrentUser()
    {
        if ( _currentUser == null )
        {
            // todo читать подробный конфиг юзера
            _currentUser = new User( "eav" + System.currentTimeMillis() );
            _currentUser._emailLogin = "eav1986@gmail.com";
            _currentUser._hostAlias = "yeremenok";
            _currentUser._fullName = "eav";
            _currentUser._passWord = "777";
        }
        return _currentUser;
    }

    public String getEmailLogin()
    {
        return _emailLogin;
    }

    public String getHostAlias()
    {
        return _hostAlias;
    }

    public String getFullName()
    {
        return _fullName;
    }

    public String getPassWord()
    {
        return _passWord;
    }

    @Override
    public void startChat(
        IChattable chattable )
    {
        if ( !_currentUser.equals( _currentUser ) )
        { // сами с собой не разговариваем
            // todo заглушка
        }
    }

    @Override
    public void quitChat(
        IChattable chattable )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void toggleChat(
        IChattable chattable )
    {
        // TODO Auto-generated method stub
        ServiceLogPanel.getInstance().info( "USER-TO-USER chatting will be implemented later" );
    }

    @Override
    public void say(
        PrivateMessage message )
    {
        for ( MessageListener listener : _listeners )
        {
            listener.onMessage( message );
        }
    }

    private ArrayList<MessageListener> _listeners = new ArrayList<MessageListener>();

    private ChatLogPanel               _chatLogPanel;

    @Override
    public void addMessageListener(
        MessageListener messageListener )
    {
        _listeners.add( messageListener );
    }

    @Override
    public void setChatLogPanel(
        ChatLogPanel chatLogPanel )
    {
        _chatLogPanel = chatLogPanel;
        addMessageListener( new MessageListener()
        {
            @Override
            public void onMessage(
                PrivateMessage message )
            {
                _chatLogPanel.logMessage( message.getFrom(), message.getContent() );
            }
        } );
    }
}
