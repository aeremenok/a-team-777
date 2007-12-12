package ru.spb.client.entities;

import ru.spb.client.gui.ServiceLogPanel;

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
    static User     _currentUser;

    /**
     * имя пользователя
     */
    private String  _name;

    private boolean isChattingWithMe = false;

    public User(
        String _name )
    {
        super();
        this._name = _name;
    }

    public String getName()
    {
        return _name;
    }

    @Override
    public void startChat(
        IChattable chattable )
    {
        // todo заглушка - с юзерами пока не общаемся
        ServiceLogPanel.getInstance().info( "USER-TO-USER chatting will be implemented later" );
        if ( chattable.equals( getCurrentUser() ) )
        {
            isChattingWithMe = true;
        }
    }

    public static User getCurrentUser()
    {
        if ( _currentUser == null )
        {
            // todo читать подробный конфиг юзера
            _currentUser = new User( "eav" );
            // =)
            _currentUser.isChattingWithMe = true;
        }
        return _currentUser;
    }

    @Override
    public boolean isChattingWithMe()
    {
        return isChattingWithMe;
    }
}
