package ru.spb.epa.commands;

import java.util.List;

import ru.spb.epa.Channel;
import ru.spb.epa.Client;
import ru.spb.epa.MainThread;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * Command: NAMES Parameters: [ <channel> *( "," <channel> ) [ <target> ] ] By
 * using the NAMES command, a user can list all nicknames that are visible to
 * him. For more details on what is visible and what is not, see "Internet Relay
 * Chat: Channel Management" [IRC-CHAN]. The <channel> parameter specifies which
 * channel(s) to return information about. There is no error reply for bad
 * channel names. If no <channel> parameter is given, a list of all channels and
 * their occupants is returned. At the end of this list, a list of users who are
 * visible but either not on any channel or not on a visible channel are listed
 * as being on `channel' "*". If the <target> parameter is specified, the
 * request is forwarded to that server which will generate the reply. Wildcards
 * are allowed in the <target> parameter. Numerics: ERR_TOOMANYMATCHES
 * ERR_NOSUCHSERVER RPL_NAMREPLY RPL_ENDOFNAMES Kalt Informational [Page 20] RFC
 * 2812 Internet Relay Chat: Client Protocol April 2000 Examples: NAMES
 * #twilight_zone,#42 ; Command to list visible users on #twilight_zone and #42
 * NAMES ; Command to list all visible channels and users
 * 
 * @author eav
 */
public class NAMES
    extends Command
{

    public NAMES(
        String s )
    {
        super( s );
    }

    @Override
    public void execute(
        Client c )
        throws CommandExecutionException
    {
        // todo пока запрос только для одного канала
        String channelName = parameters.get( 1 ).token;

        List<Channel> channelList = MainThread.getChannels();
        Channel selectedChannel = MainThread.getChannelByName( channelName );
        if ( selectedChannel != null )
        { // канал найден - выдаем пользователей
            String reply = RPL_NAMREPLY + " " + selectedChannel.getName() + ":";

            for ( Client client : selectedChannel.getUsers() )
            {
                reply += " " + client.getNickname();
            }
            c.sendToClient( reply, true);
            c.sendToClient( RPL_ENDOFNAMES + " :End of NAMES list", true);
        }
        else
        { // канал не найден - выдаем ошибку
            c.sendToClient( ERR_NOSUCHCHANNEL + " " + channelName + " :No such channel", true);
        }
    }

    public NAMES()
    {
        super();
    }

    protected Command getInstance(
        String s )
    {
        return new NAMES( s );
    }
}
