package ru.spb.epa.commands;

import ru.spb.epa.Client;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * User: �����
 * Date: 14.12.2007
 * Time: 0:34:12
 *
 * Command: PING
   Parameters: <server1> [ <server2> ]

   The PING command is used to test the presence of an active client or
   server at the other end of the connection.  Servers send a PING
   message at regular intervals if no other activity detected coming
   from a connection.  If a connection fails to respond to a PING
   message within a set amount of time, that connection is closed.  A
   PING message MAY be sent even if the connection is active.

   When a PING message is received, the appropriate PONG message MUST be
   sent as reply to <server1> (server which sent the PING message out)
   as soon as possible.  If the <server2> parameter is specified, it
   represents the target of the ping, and the message gets forwarded
   there.

   Numeric Replies:

           ERR_NOORIGIN                  ERR_NOSUCHSERVER

   Examples:

   PING tolsun.oulu.fi             ; Command to send a PING message to
                                   server

   PING WiZ tolsun.oulu.fi         ; Command from WiZ to send a PING
                                   message to server "tolsun.oulu.fi"

   PING :irc.funet.fi              ; Ping message sent by server
                                   "irc.funet.fi"


 
 */
public class PING extends Command{
    public void execute(Client c) throws CommandExecutionException {
        c.sendToClient("PONG ", true);
    }

    // ================================================================================================================
    // COMMON
    // ================================================================================================================

    protected PING()
    {
        super();
    }

    protected PING(
        String message )
    {
        super( message );
    }

    protected Command getInstance(
        String message )
    {
        return new PING( message );
    }
}
