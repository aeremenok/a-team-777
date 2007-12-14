package ru.spb.client.entities.messages;

import ru.spb.client.entities.Server;

public class ListMessage
    extends ServiceMessage
{
    static
    {
        _possibleErrors.add( ERR_TOOMANYMATCHES );
        _possibleErrors.add( ERR_NOSUCHSERVER );
    }

    /**
     * @param server существующий сервер
     */
    public ListMessage(
        Server server )
    {
        _message = "LIST ";// + server.getName();
    }
}
