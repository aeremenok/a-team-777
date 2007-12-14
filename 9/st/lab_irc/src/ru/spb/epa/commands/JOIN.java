package ru.spb.epa.commands;

import java.util.List;

import ru.spb.epa.Channel;
import ru.spb.epa.Client;
import ru.spb.epa.MainThread;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * User: ����� Date: 13.12.2007 Time: 23:25:34 3.2.1 Join message Command: JOIN
 * Parameters: ( <channel> *( "," <channel> ) [ <key> *( "," <key> ) ] ) / "0"
 * The JOIN command is used by a user to request to start listening to the
 * specific channel. Servers MUST be able to parse arguments in the form of a
 * list of target, but SHOULD NOT use lists when sending JOIN messages to
 * clients. Once a user has joined a channel, he receives information about all
 * commands his server receives affecting the channel. This includes JOIN, MODE,
 * KICK, PART, QUIT and of course PRIVMSG/NOTICE. This allows channel members to
 * keep track of the other channel members, as well as channel modes. If a JOIN
 * is successful, the user receives a JOIN message as confirmation and is then
 * sent the channel's topic (using RPL_TOPIC) and the list of users who are on
 * the channel (using RPL_NAMREPLY), which MUST include the user joining. Note
 * that this message accepts a special argument ("0"), which is a special
 * request to leave all channels the user is currently a member of. The server
 * will process this message as if the user had sent a PART command (See Section
 * 3.2.2) for each channel he is a member of. Numeric Replies:
 * ERR_NEEDMOREPARAMS ERR_BANNEDFROMCHAN ERR_INVITEONLYCHAN ERR_BADCHANNELKEY
 * ERR_CHANNELISFULL ERR_BADCHANMASK ERR_NOSUCHCHANNEL ERR_TOOMANYCHANNELS
 * ERR_TOOMANYTARGETS ERR_UNAVAILRESOURCE RPL_TOPIC Examples: JOIN #foobar ;
 * Command to join channel #foobar. JOIN &foo fubar ; Command to join channel
 * &foo using key "fubar". Kalt Informational [Page 16] RFC 2812 Internet Relay
 * Chat: Client Protocol April 2000 JOIN #foo,&bar fubar ; Command to join
 * channel #foo using key "fubar" and &bar using no key. JOIN #foo,#bar
 * fubar,foobar ; Command to join channel #foo using key "fubar", and channel
 * #bar using key "foobar". JOIN #foo,#bar ; Command to join channels #foo and
 * #bar. JOIN 0 ; Leave all currently joined channels. :WiZ!jto@tolsun.oulu.fi
 * JOIN #Twilight_zone ; JOIN message from WiZ on channel #Twilight_zone
 */
public class JOIN
    extends Command
{

    public void execute(
        Client c )
        throws CommandExecutionException
    {
        // todo это для чего?
        // String userName = this.parameters.get( 1 ).token;
        // String hostName = this.parameters.get( 2 ).token;
        // String ipAdress = this.parameters.get( 3 ).token;
        // String fullname = this.parameters.get( 4 ).token;
        //
        // c.setHostname( hostName );
        // c.setUsername( userName );
        // c.setFullname( fullname );
        // c.setIpAdress( ipAdress );

        // todo можно обобщить
        // ищем канал, который запросили
        String channelName = parameters.get( 1 ).token;
        List<Channel> channeList = MainThread.getChannels();
        if ( !channelName.equals( "0" ) )
        {
            Channel selectedChannel = null;
            for ( Channel channel : channeList )
            {
                if ( channel.getName().equalsIgnoreCase( channelName ) )
                {
                    selectedChannel = channel;
                    break;
                }
            }
            if ( selectedChannel != null )
            { // канал найден - выдаем тему
                selectedChannel.addUser( c );
                c.sendToClient( RPL_TOPIC + " " + selectedChannel.getName() + " :" + selectedChannel.getTopic() );
            }
            else
            { // канал не найден - выдаем ошибку
                c.sendToClient( ERR_NOSUCHCHANNEL + " " + channelName + " :No such channel" );
            }
        }
        else
        { // 0 - отключаемся отовсюду
            // todo о чем еще нужно уведомить?
            for ( Channel channel : channeList )
            {
                channel.removeUser( c );
            }
        }
    }

    // ================================================================================================================
    // COMMON
    // ================================================================================================================
    protected JOIN()
    {
        super();
    }

    protected JOIN(
        String message )
    {
        super( message );
    }

    protected Command getInstance(
        String message )
    {
        return new JOIN( message );
    }
}
