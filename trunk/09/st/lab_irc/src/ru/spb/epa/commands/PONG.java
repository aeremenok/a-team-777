package ru.spb.epa.commands;

import ru.spb.epa.Client;
import ru.spb.epa.ServerLogger;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * User: Павел
 * Date: 14.12.2007
 * Time: 0:34:30
 *
 * Command: PONG
   Parameters: <server> [ <server2> ]

   PONG message is a reply to ping message.  If parameter <server2> is
   given, this message MUST be forwarded to given target.  The <server>
   parameter is the name of the entity who has responded to PING message
   and generated this message.

   Numeric Replies:

           ERR_NOORIGIN                  ERR_NOSUCHSERVER

   Example:

   PONG csd.bu.edu tolsun.oulu.fi  ; PONG message from csd.bu.edu to
                                   tolsun.oulu.fi


 */
public class PONG extends Command {
    
    public void execute(Client c) throws CommandExecutionException {
        //c.sendToClient(  );
        ServerLogger.log(" GOT PONG ");
    }

    // ================================================================================================================
    // COMMON
    // ================================================================================================================

    protected PONG()
    {
        super();
    }

    protected PONG(
        String message )
    {
        super( message );
    }

    protected Command getInstance(
        String message )
    {
        return new PONG( message );
    }
}
