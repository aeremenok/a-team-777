package ru.spb.epa.commands;

import java.util.List;

import ru.spb.epa.Channel;
import ru.spb.epa.Client;
import ru.spb.epa.IRCConstants;
import ru.spb.epa.MainThread;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * User: ����� Date: 13.12.2007 Time: 23:33:45 Command: LIST Parameters: [
 * <channel> *( "," <channel> ) [ <target> ] ] The list command is used to list
 * channels and their topics. If the <channel> parameter is used, only the
 * status of that channel is displayed. If the <target> parameter is specified,
 * the request is forwarded to that server which will generate the reply.
 * Wildcards are allowed in the <target> parameter. Numeric Replies:
 * ERR_TOOMANYMATCHES ERR_NOSUCHSERVER 322 RPL_LIST "<channel> <# visible> :<topic>"
 * RPL_LISTEND Examples: LIST ; Command to list all channels. LIST
 * #twilight_zone,#42 ; Command to list channels #twilight_zone and #42
 */
public class LIST
    extends Command
    implements
        IRCConstants
{

    public void execute(
        Client c )
        throws CommandExecutionException
    {

        if ( this.parameters.size() > 1 )
        {

        }
        else
        {
            // all
            List<Channel> l = MainThread.getChannels();
            c.sendToClient( RPL_LISTSTART + "", true);
            for ( Channel ch : l )
            {
                c.sendToClient( RPL_LIST + " " + c.getName() + " " + ch.getName() + " " + ch.getNumbersersOnChannel() + " :" + ch.getTopic(), true);
            }
            c.sendToClient( RPL_LISTEND +"", true);// + " :End of LIST" );
        }
    }

    // ================================================================================================================
    // COMMON
    // ================================================================================================================
    protected LIST()
    {
        super();
    }

    protected LIST(
        String message )
    {
        super( message );
    }

    protected Command getInstance(
        String message )
    {
        return new LIST( message );
    }
}
