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
    /**
     * @param user существующий пользователь
     */
    public NickMessage(
        User user )
    {
        _possibleErrors.add( ERR_NONICKNAMEGIVEN );
        _possibleErrors.add( ERR_NICKNAMEINUSE );
        _possibleErrors.add( ERR_UNAVAILRESOURCE );
        _possibleErrors.add( ERR_NICKCOLLISION );
        _possibleErrors.add( ERR_NICKNAMEINUSE );
        _possibleErrors.add( ERR_RESTRICTED );

        _possibleReplies.add( RPL_WELCOME );

        _message = "NICK " + user.getName();
    }
}
