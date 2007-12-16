package ru.spb.epa.commands;

import ru.spb.epa.Channel;
import ru.spb.epa.Client;
import ru.spb.epa.MainThread;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * Command: PRIVMSG Parameters: <msgtarget> <text to be sent> PRIVMSG is used to
 * send private messages between users, as well as to send messages to channels.
 * <msgtarget> is usually the nickname of the recipient of the message, or a
 * channel name. The <msgtarget> parameter may also be a host mask (#<mask>) or
 * server mask ($<mask>). In both cases the server will only send the PRIVMSG
 * to those who have a server or host matching the mask. The mask MUST have at
 * least 1 (one) "." in it and no wildcards following the last ".". This
 * requirement exists to prevent people sending messages to "#*" or "$*", which
 * would broadcast to all users. Wildcards are the '*' and '?' characters. This
 * extension to the PRIVMSG command is only available to operators. Numeric
 * Replies: ERR_NORECIPIENT ERR_NOTEXTTOSEND ERR_CANNOTSENDTOCHAN ERR_NOTOPLEVEL
 * ERR_WILDTOPLEVEL ERR_TOOMANYTARGETS ERR_NOSUCHNICK RPL_AWAY
 * 
 * @author eav
 */
public class PRIVMSG
    extends Command
{

    public PRIVMSG()
    {
    }

    public PRIVMSG(
        String message )
    {
        super( message );
    }

    @Override
    public void execute(
        Client c )
        throws CommandExecutionException
    {
        String channelName = parameters.get( 1 ).token;
        String content = parameters.get( 2 ).token;
        Channel channel = MainThread.getChannelByName( channelName );
        if ( channel != null )
        {
            String fullMessage = "PRIVMSG " + c.getNickname() + " " + channelName + " " + content;
            for ( Client client : channel.getUsers() )
            {
                if ( !client.equals( c ) )
                {
                    client.sendToClient( fullMessage );
                }
            }
        }
    }

    @Override
    protected Command getInstance(
        String s )
    {
        return new PRIVMSG( s );
    }
}
