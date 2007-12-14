package ru.spb.client.entities.messages;

import ru.spb.client.entities.User;

public class PassMessage
    extends ServiceMessage
{
    /**
     * @param user существующий пользователь
     */
    public PassMessage(
        User user )
    {
        _message = "PASS " + user.getPassWord();
    }

    static
    {
        _possibleErrors.add( ERR_NEEDMOREPARAMS );
        _possibleErrors.add( ERR_ALREADYREGISTRED );
    }
}
