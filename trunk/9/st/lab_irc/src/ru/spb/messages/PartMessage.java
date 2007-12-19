package ru.spb.messages;

public class PartMessage
    extends ServiceMessage
{

    private String _channelName;

    public PartMessage(
        String channelName )
    {
        _possibleErrors.add( ERR_NEEDMOREPARAMS );
        _possibleErrors.add( ERR_NOTONCHANNEL );
        _possibleErrors.add( ERR_NOSUCHCHANNEL );

        _channelName = channelName;
        _message = "PART " + channelName;
    }

    public String getChannelName()
    {
        return _channelName;
    }
}
