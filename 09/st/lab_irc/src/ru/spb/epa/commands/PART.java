package ru.spb.epa.commands;

import ru.spb.epa.Client;
import ru.spb.epa.Channel;
import ru.spb.epa.MainThread;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * User: Павел
 * Date: 13.12.2007
 * Time: 23:33:16
 *
 * 3.2.2 Part message

      Command: PART
   Parameters: <channel> *( "," <channel> ) [ <Part Message> ]

   The PART command causes the user sending the message to be removed
   from the list of active members for all given channels listed in the
   parameter string.  If a "Part Message" is given, this will be sent
   instead of the default message, the nickname.  This request is always
   granted by the server.

   Servers MUST be able to parse arguments in the form of a list of
   target, but SHOULD NOT use lists when sending PART messages to
   clients.

   Numeric Replies:

           ERR_NEEDMOREPARAMS              ERR_NOSUCHCHANNEL
           ERR_NOTONCHANNEL

   Examples:

   PART #twilight_zone             ; Command to leave channel
                                   "#twilight_zone"

   PART #oz-ops,&group5            ; Command to leave both channels
                                   "&group5" and "#oz-ops".

   :WiZ!jto@tolsun.oulu.fi PART #playzone :I lost
                                   ; User WiZ leaving channel
                                   "#playzone" with the message "I
                                   lost".

 */
public class PART extends Command{
    public void execute(Client c) throws CommandExecutionException {
        if(this.parameters.get( 1 ) != null) c.part(this.parameters.get( 1 ).token);

        String channelName = parameters.get( 1 ).token;
        Channel channel = MainThread.getChannelByName( channelName );
        if ( channel != null )
        {
            String fullMessage = ":" + c.getNameCombined() + " PART " + channelName ;
            for ( Client client : channel.getUsers() )
            {
                if ( !c.equals( client ) )
                {
                    client.sendToClient( fullMessage, false);
                }
            }
        }
    }

    // ================================================================================================================
    // COMMON
    // ================================================================================================================

    protected PART()
    {
        super();
    }

    protected PART(
        String message )
    {
        super( message );
    }

    protected Command getInstance(
        String message )
    {
        return new PART( message );
    }
}
