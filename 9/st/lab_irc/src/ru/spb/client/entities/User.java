package ru.spb.client.entities;

import ru.spb.client.gui.logpanels.ServiceLogPanel;

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

    @Override
    public void startChat(
        IChattable chattable )
    {
        if ( !_currentUser.equals( _currentUser ) )
        { // сами с собой не разговариваем
            // todo заглушка
        }
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
            _currentUser = new User( "eav" );
            _currentUser._emailLogin = "eav1986@gmail.com";
            _currentUser._hostAlias = "yeremenok";
            _currentUser._fullName = "eav";
            _currentUser._passWord = "777";
        }
        return _currentUser;
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
}
