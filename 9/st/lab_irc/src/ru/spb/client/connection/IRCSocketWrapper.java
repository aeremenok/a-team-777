package ru.spb.client.connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.Server;
import ru.spb.client.entities.User;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.client.gui.trees.ChannelTree;
import ru.spb.messages.JoinMessage;
import ru.spb.messages.ListMessage;
import ru.spb.messages.NamesMessage;
import ru.spb.messages.NickMessage;
import ru.spb.messages.NumericReply;
import ru.spb.messages.PassMessage;
import ru.spb.messages.PrivateMessage;
import ru.spb.messages.QuitMessage;
import ru.spb.messages.ReplyListener;
import ru.spb.messages.ServiceMessage;
import ru.spb.messages.UserMessage;
import ru.spb.messages.constants.Errors;
import ru.spb.messages.constants.Replies;

/**
 * класс, осуществляющий сокетное соединение. в любой момент соединение возможно
 * только с одним сервером
 * 
 * @author eav
 */
public class IRCSocketWrapper
    extends Thread
    implements
        Replies,
        Errors
{
    /**
     * сервер, для которого заняли сокет
     */
    private Server          _host   = null;

    private User            _currentUser;

    private MessageReceiver _receiver;

    /**
     * сокет общего пользования
     */
    private static Socket   _socket = null;

    @Override
    public void run()
    {
        try
        {
            _socket = new Socket( _host.getHost(), _host.getPort() );

            BufferedReader reader =
                                    new BufferedReader(
                                                        new InputStreamReader(
                                                                               new DataInputStream(
                                                                                                    _socket
                                                                                                           .getInputStream() ) ) );
            // создаем слушателя
            _receiver = new MessageReceiver( reader, _host, this );
            _receiver.start();

            register( _currentUser );
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            ServiceLogPanel.getInstance().error( e.getMessage() );
        }
    }

    /**
     * занимает сокет для заданного сервера от имени заданного пользователя
     * 
     * @param server сервер
     * @param user пользователь
     */
    public IRCSocketWrapper(
        Server server,
        User user )
    {
        _host = server;
        _currentUser = user;
        run();
    }

    public ArrayList<User> getRegisteredUsers(
        Channel channel )
    {
        // отправляем запрос
        NamesMessage namesMessage = new NamesMessage( channel );
        sendCommand( namesMessage );

        final ArrayList<User> result = new ArrayList<User>();

        _receiver.getReply( namesMessage, new ReplyListener()
        {
            @Override
            public void onFailure(
                NumericReply numericReply )
            {
            }

            @Override
            public void onSuccess(
                NumericReply numericReply )
            {
                String names = numericReply.getProperty( NumericReply.NICKNAMES );
                if ( names != null )
                { // список не пуст - разбираем полученные имена
                    StringTokenizer stringTokenizer = new StringTokenizer( names, " " );
                    while ( stringTokenizer.hasMoreTokens() )
                    {
                        User user = new User( stringTokenizer.nextToken() );
                        result.add( user );
                    }
                }

            }
        } );
        return result;
    }

    /**
     * получить с сервера список каналов
     * 
     * @param result
     * @return список каналов
     */
    public void getChannels(
        final ChannelTree result )
    {
        // отправляем запрос
        ListMessage listMessage = new ListMessage( _host );
        sendCommand( listMessage );

        _receiver.getReply( listMessage, new ReplyListener()
        {
            @Override
            public void onFailure(
                NumericReply numericReply )
            {
            }

            @Override
            public void onSuccess(
                NumericReply numericReply )
            {
                if ( numericReply.getType() != RPL_LISTEND )
                {

                    Channel channel =
                                      new Channel( numericReply.getProperty( NumericReply.CHANNEL ),
                                                   numericReply.getProperty( NumericReply.TOPIC ),
                                                   // о хозяине канала ничего
                                                   // не известно
                                                   null );
                    // todo убрать лишнее
                    channel.setHost( _host );
                    result.addChannel( channel );
                }
            }
        } );
    }

    /**
     * представиться серверу, с которым соединены
     * 
     * @param user кого представляем
     */
    public void register(
        User user )
    {
        PassMessage pass = new PassMessage( user );
        sendCommand( pass );

        NickMessage nick = new NickMessage( user );
        sendCommand( nick );

        UserMessage userMessage = new UserMessage( user );
        sendCommand( userMessage );

        _receiver.getReply( userMessage, new ReplyListener()
        {
            @Override
            public void onFailure(
                NumericReply numericReply )
            {
            }

            @Override
            public void onSuccess(
                NumericReply numericReply )
            {
                ServiceLogPanel.getInstance().info( "received", numericReply.getDescription() );
            }
        } );
    }

    /**
     * отправить команду на сервер
     * 
     * @param serviceMessage служебное сообщение
     */
    private void sendCommand(
        ServiceMessage serviceMessage )
    {
        try
        {
            PrintWriter writer = new PrintWriter( _socket.getOutputStream(), true );
            writer.println( serviceMessage.getMessageString() );
        }
        catch ( IOException e )
        {
            ServiceLogPanel.getInstance().error( e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * создать на сервере, с которым соединены, канал
     * 
     * @param channel новый канал
     */
    public void createChannel(
        Channel channel )
    {
        // todo заглушка
    }

    /**
     * присоединиться к каналу
     * 
     * @param channel канал
     */
    public void join(
        final Channel channel )
    {
        // отправляем запрос
        JoinMessage joinMessage = new JoinMessage( channel );
        sendCommand( joinMessage );

        _receiver.getReply( joinMessage, new ReplyListener()
        {
            @Override
            public void onFailure(
                NumericReply numericReply )
            {
            }

            @Override
            public void onSuccess(
                NumericReply numericReply )
            {
                channel.setTopic( numericReply.getProperty( NumericReply.TOPIC ) );
                ServiceLogPanel.getInstance().info( channel, "entered on topic " + channel.getTopic() );

                channel.addMessageListener( new MessageListener()
                {
                    public void onMessage(
                        PrivateMessage message )
                    {
                        privmsg( channel, message.getMessageString() );
                    }
                } );
            }
        } );

    }

    /**
     * отправить сообщение в канал
     * 
     * @param channel канал
     * @param message сообщение
     */
    public void privmsg(
        Channel channel,
        String message )
    {
        PrivateMessage privateMessage = new PrivateMessage( User.getCurrentUser(), channel, message );
        sendCommand( privateMessage );
    }

    @Override
    public void finalize()
        throws Throwable
    {
        QuitMessage quitMessage = new QuitMessage();
        sendCommand( quitMessage );

        try
        {
            _receiver.finalize();
            _socket.close();
            _socket = null;
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            ServiceLogPanel.getInstance().error( e.getMessage() );
        }
        super.finalize();
    }
}
