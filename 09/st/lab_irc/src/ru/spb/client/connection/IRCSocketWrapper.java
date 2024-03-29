package ru.spb.client.connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.Server;
import ru.spb.client.entities.User;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.messages.JoinMessage;
import ru.spb.messages.ListMessage;
import ru.spb.messages.NamesMessage;
import ru.spb.messages.NickMessage;
import ru.spb.messages.NumericReply;
import ru.spb.messages.PartMessage;
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
    implements
        Replies,
        Errors
{
    /**
     * сервер, для которого заняли сокет
     */
    private Server          _host   = null;

    /**
     * получатель сообщений
     */
    private MessageReceiver _receiver;

    /**
     * сокет общего пользования
     */
    private static Socket   _socket = null;

    /**
     * занимает сокет для заданного сервера от имени заданного пользователя
     * 
     * @param server сервер
     */
    public IRCSocketWrapper(
        Server server )
    {
        _host = server;

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
            _receiver = new MessageReceiver( reader, _host );

            enter( User.getCurrentUser() );
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            ServiceLogPanel.getInstance().error( e.getMessage() );
        }
    }

    /**
     * получить список пользователей с сервера по каналу, указанному в дереве и
     * заполнить это дерево
     * 
     * @param channel дерево
     */
    public void retrieveUsers(
        final Channel channel )
    {
        // отправляем запрос
        NamesMessage namesMessage = new NamesMessage( channel );

        waitReply( namesMessage, new ReplyListener()
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
                processNamesReply( channel, numericReply );
            }

        } );
    }

    /**
     * ждать ответа, не посылая сообщения(если ответ должен прийти сам)
     * 
     * @param serviceMessage сообщение, которое подразумевается
     * @param replyListener обработчик
     */
    private void waitReply(
        ServiceMessage serviceMessage,
        ReplyListener replyListener )
    {
        serviceMessage.addReplyListener( replyListener );
        _receiver.reply( serviceMessage );
    }

    void processNamesReply(
        Channel channel,
        NumericReply numericReply )
    {
        String names = numericReply.getProperty( NumericReply.NICKNAMES );
        if ( names != null )
        { // список не пуст - разбираем полученные имена
            StringTokenizer stringTokenizer = new StringTokenizer( names, " " );
            while ( stringTokenizer.hasMoreTokens() )
            {
                String userName = stringTokenizer.nextToken();
                if ( userName.startsWith( ":" ) )
                {
                    userName = userName.substring( 1 );
                }
                User user = new User( userName );
                channel.addUser( user );
            }
        }
    }

    /**
     * получить с сервера список каналов
     * 
     * @param channelTree дерево каналов для о
     * @return список каналов
     */
    public void retrieveChannels(
        final Server server )
    {
        // отправляем запрос
        ListMessage listMessage = new ListMessage( _host );

        sendCommand( listMessage, new ReplyListener()
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
                                                   numericReply.getProperty( NumericReply.TOPIC ) );
                    channel.setHost( _host );
                    server.addChannel( channel );
                }
            }
        } );
    }

    /**
     * представиться серверу, с которым соединены
     * 
     * @param user кого представляем
     */
    public void enter(
        User user )
    {
        PassMessage pass = new PassMessage( user );
        sendCommand( pass );

        NickMessage nick = new NickMessage( user );
        sendCommand( nick );

        UserMessage userMessage = new UserMessage( user );
        sendCommand( userMessage, new ReplyListener()
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
     * отправить команду на сервер, указав обработчик ответа
     * 
     * @param serviceMessage команда
     * @param replyListener обработчик
     */
    private void sendCommand(
        ServiceMessage serviceMessage,
        ReplyListener replyListener )
    {
        sendCommand( serviceMessage );

        waitReply( serviceMessage, replyListener );
    }

    /**
     * отправить команду на сервер, не ожидая ответа
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
            ServiceLogPanel.getInstance().info( "sent command", serviceMessage.getMessageString() );
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
        JoinMessage joinMessage = new JoinMessage( channel.getName() );
        sendCommand( joinMessage, new JoinReplyListener( channel ) );
    }

    /**
     * обрабатывает ответ на присоединение к каналу
     * 
     * @author eav
     */
    private class JoinReplyListener
        implements
            ReplyListener
    {
        Channel _channel;

        public JoinReplyListener(
            Channel channel )
        {
            _channel = channel;
            _channel.addMessageListener( new MessageListener()
            {
                public void onMessage(
                    PrivateMessage message )
                {
                    privmsg( _channel, message.getContent() );
                }
            } );
        }

        @Override
        public void onFailure(
            NumericReply numericReply )
        {
        }

        @Override
        public void onSuccess(
            NumericReply numericReply )
        {
            _channel.setTopic( numericReply.getProperty( NumericReply.TOPIC ) );
            ServiceLogPanel.getInstance().info( _channel, "entered on topic " + _channel.getTopic() );
        }
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
        PrivateMessage privateMessage =
                                        new PrivateMessage( User.getCurrentUser().getName(), channel.getName(), message );
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

    /**
     * оповестить о выходе из канала
     * 
     * @param channel
     */
    public void part(
        Channel channel )
    {
        PartMessage partMessage = new PartMessage( channel.getName() );
        sendCommand( partMessage );
    }
}
