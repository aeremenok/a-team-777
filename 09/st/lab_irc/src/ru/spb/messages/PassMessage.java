package ru.spb.messages;

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
        _possibleErrors.add( ERR_NEEDMOREPARAMS );
        _possibleErrors.add( ERR_ALREADYREGISTRED );

        _possibleReplies.add( RPL_WELCOME );

        _message = "PASS " + user.getPassWord();
    }
}
