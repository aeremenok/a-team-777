package ru.spb.messages;

public class JoinMessage
    extends ServiceMessage
{
    private String _channelName;

    /**
     * @param channelName если null - выход из всех каналов
     */
    public JoinMessage(
        String channelName )
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

        _possibleReplies.add( RPL_TOPIC );

        if ( channelName != null )
        {
            _message = "JOIN " + channelName;
            _channelName = channelName;
        }
        else
        {
            _message = "JOIN 0";
        }
    }

    public String getChannelName()
    {
        return _channelName;
    }
}
