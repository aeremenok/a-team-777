package ru.spb.messages;

import ru.spb.client.entities.Channel;

public class JoinMessage
    extends ServiceMessage
{
    /**
     * @param channel если null - выход из всех каналов
     */
    public JoinMessage(
        Channel channel )
    {
        if ( channel != null )
        {
            _message = "JOIN " + channel.getName();
        }
        else
        {
            _message = "JOIN 0";
        }
    }

    static
    {
        _possibleErrors.add( ERR_NEEDMOREPARAMS );
        _possibleErrors.add( ERR_INVITEONLYCHAN );
        _possibleErrors.add( ERR_CHANNELISFULL );
        _possibleErrors.add( ERR_NOSUCHCHANNEL );
        _possibleErrors.add( ERR_BANNEDFROMCHAN );
        _possibleErrors.add( ERR_BADCHANNELKEY );
        _possibleErrors.add( ERR_BADCHANMASK );
        _possibleErrors.add( ERR_TOOMANYCHANNELS );
        _possibleErrors.add( ERR_UNAVAILRESOURCE );
        _possibleErrors.add( ERR_TOOMANYTARGETS );
    }
}
