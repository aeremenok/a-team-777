package ru.spb.messages;

import ru.spb.client.entities.User;

/**
 * сообщение о регистрации на сервере<br>
 * NICK <nickname>
 * 
 * @author eav
 */
public class NickMessage
    extends ServiceMessage
{

    static
    {
        _possibleErrors.add( ERR_NONICKNAMEGIVEN );
        _possibleErrors.add( ERR_NICKNAMEINUSE );
        _possibleErrors.add( ERR_UNAVAILRESOURCE );
        _possibleErrors.add( ERR_NICKCOLLISION );
        _possibleErrors.add( ERR_NICKNAMEINUSE );
        _possibleErrors.add( ERR_RESTRICTED );
    }

    /**
     * @param user существующий пользователь
     */
    public NickMessage(
        User user )
    {
        super();
        _message = "NICK " + user.getName();
    }

}
