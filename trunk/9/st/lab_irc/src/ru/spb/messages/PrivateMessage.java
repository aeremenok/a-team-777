package ru.spb.messages;

import ru.spb.client.entities.IChattable;

public class PrivateMessage
    extends ServiceMessage
{
    private IChattable _from;
    private IChattable _to;
    private String     _content;

    public PrivateMessage(
        IChattable from,
        IChattable to,
        String message )
    {
        _possibleErrors.add( ERR_NORECIPIENT );
        _possibleErrors.add( ERR_WILDTOPLEVEL );
        _possibleErrors.add( ERR_CANNOTSENDTOCHAN );
        _possibleErrors.add( ERR_NOSUCHNICK );
        _possibleErrors.add( ERR_NOTEXTTOSEND );
        _possibleErrors.add( ERR_NOTOPLEVEL );
        _possibleErrors.add( ERR_TOOMANYTARGETS );

        _possibleErrors.add( RPL_AWAY );

        _from = from;
        _to = to;
        _content = message;
        _message = "PRIVMSG " + from.getName() + " " + to.getName() + " " + message;
    }

    public IChattable getFrom()
    {
        return _from;
    }

    public IChattable getTo()
    {
        return _to;
    }

    public String getContent()
    {
        return _content;
    }
}
