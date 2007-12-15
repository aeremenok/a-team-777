package ru.spb.messages;

import ru.spb.client.entities.Channel;

public class NamesMessage
    extends ServiceMessage
{
    public NamesMessage(
        Channel channel )
    {
        _possibleErrors.add( ERR_TOOMANYMATCHES );
        _possibleErrors.add( ERR_NOSUCHSERVER );

        _possibleReplies.add( RPL_NAMREPLY );
        _possibleReplies.add( RPL_ENDOFNAMES );

        _message = "NAMES " + channel.getName();
    }
}
