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
    static User    _currentUser;

    /**
     * имя пользователя
     */
    private String _name;

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
        // TODO Auto-generated method stub
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
}
