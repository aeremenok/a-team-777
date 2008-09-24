package ru.spb.messages;

import ru.spb.client.entities.Server;

public class ListMessage
    extends ServiceMessage
{
    /**
     * @param server существующий сервер
     */
    public ListMessage(
        Server server )
    {
        _possibleErrors.add( ERR_TOOMANYMATCHES );
        _possibleErrors.add( ERR_NOSUCHSERVER );

        _possibleReplies.add( RPL_LIST );
        _possibleReplies.add( RPL_LISTEND );

        _message = "LIST ";// + server.getName();
    }
}
