package ru.spb.messages;

import ru.spb.client.entities.User;

/**
 * ообщение о регистрации на сервере<br>
 * USER <email login> <computer name> <host alias> :<full name>
 * 
 * @author eav
 */
public class UserMessage
    extends ServiceMessage
{
    static
    {
        _possibleErrors.add( ERR_NEEDMOREPARAMS );
        _possibleErrors.add( ERR_ALREADYREGISTRED );
    }

    public UserMessage(
        User user )
    {
        _message = "USER " + user.getEmailLogin() + " <ignored> " + user.getHostAlias() + " :" + user.getFullName();
    }

}
