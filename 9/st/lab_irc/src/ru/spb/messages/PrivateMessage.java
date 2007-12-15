package ru.spb.messages;


public class PrivateMessage
    extends ServiceMessage
{
    private String _from;
    private String _to;
    private String _content;

    public PrivateMessage(
        String from,
        String to,
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
        _message = "PRIVMSG " + from + " " + to + " " + message;
    }

    public String getFrom()
    {
        return _from;
    }

    public String getTo()
    {
        return _to;
    }

    public String getContent()
    {
        return _content;
    }
}
