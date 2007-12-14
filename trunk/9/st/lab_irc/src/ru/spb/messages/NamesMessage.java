package ru.spb.messages;

import ru.spb.client.entities.Channel;

public class NamesMessage
    extends ServiceMessage
{
    public NamesMessage(
        Channel channel )
    {
        _message = "NAMES " + channel.getName();
    }

    static
    {
        _possibleErrors.add( ERR_TOOMANYMATCHES );
        _possibleErrors.add( ERR_NOSUCHSERVER );
    }

}
