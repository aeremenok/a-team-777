package ru.spb.client.connection;

import java.io.BufferedReader;
import java.util.StringTokenizer;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.Server;
import ru.spb.client.entities.User;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.messages.NumericReply;
import ru.spb.messages.PrivateMessage;
import ru.spb.messages.ReplyListener;
import ru.spb.messages.ServiceMessage;

/**
 * прослушивает входящие сообщения
 * 
 * @author eav
 */
public class MessageReceiver
    extends Thread
{
    private boolean _run = true;

    @Override
    public void interrupt()
    {
        _run = false;
    }

    /**
     * сервер, с которым работаем
     */
    private Server         _host;

    /**
     * прослушиваемый поток
     */
    private BufferedReader _reader;

    private ReplyListener  _currentReplyListener;

    private ServiceMessage _currentRequest;

    MessageReceiver(
        BufferedReader reader,
        Server host,
        IRCSocketWrapper socketWrapper )
    {
        _reader = reader;
        _host = host;
    }

    @Override
    public void run()
    {
        while ( _run )
        {
            try
            {
                String message = _reader.readLine();

                ServiceMessage serviceMessage = parse( message );
                if ( serviceMessage != null )
                { // ошибки не было
                    if ( serviceMessage instanceof PrivateMessage )
                    { // записываем в лог чата

                        PrivateMessage privateMessage = (PrivateMessage) serviceMessage;
                        // ищем, в какой канал это сказано
                        // todo обощить для пользователей
                        for ( Channel channel : _host.getChannels() )
                        {
                            if ( channel.equals( privateMessage.getTo() ) )
                            {
                                channel.say( privateMessage );
                            }
                        }
                    }
                    else
                    { // передаем для разбора
                        NumericReply numericReply = (NumericReply) serviceMessage;
                        if ( _currentRequest.hasError( numericReply ) )
                        {
                            _currentReplyListener.onFailure( numericReply );
                        }
                        else if ( _currentRequest.mayCause( numericReply ) )
                        {
                            _currentReplyListener.onSuccess( numericReply );
                        }
                    }
                }
            }
            catch ( Throwable e )
            {
                ServiceLogPanel.getInstance().error( e.getMessage() );
                e.printStackTrace();
            }
        }
    }

    /**
     * восстанавливает сообщение
     * 
     * @param message источник
     * @return сообщение
     */
    private ServiceMessage parse(
        String message )
    {
        if ( message != null )
        {
            StringTokenizer stringTokenizer = new StringTokenizer( message );
            try
            {
                String type = stringTokenizer.nextToken();
                if ( type.equalsIgnoreCase( "PRIVMSG" ) )
                {
                    return parsePrivateMessage( stringTokenizer );
                }
                return parseNumericReply( stringTokenizer, type );
            }
            catch ( Throwable e )
            {
                ServiceLogPanel.getInstance().error( e.getMessage() );
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * восстанавливает личное сообщение
     * 
     * @param stringTokenizer источник
     * @return личное сообщение
     * @throws Throwable здесь может быть ошибка на каждом этапе, поэтому
     *             проверок не делаем, а ловим эксепшен %)
     */
    private PrivateMessage parsePrivateMessage(
        StringTokenizer stringTokenizer )
        throws Throwable
    {
        String from = stringTokenizer.nextToken();
        String to = stringTokenizer.nextToken();
        Channel channel = _host.getChannelByName( to );
        User user = channel.getUserByName( from );

        String content = stringTokenizer.nextToken();
        while ( stringTokenizer.hasMoreTokens() )
        {
            content += stringTokenizer.nextToken() + " ";
        }

        return new PrivateMessage( user, channel, content );
    }

    /**
     * восстанавливает ответ сервера
     * 
     * @param stringTokenizer источник
     * @param type тип
     * @return ответ сервера
     */
    private NumericReply parseNumericReply(
        StringTokenizer stringTokenizer,
        String type )
        throws Throwable
    {
        int numericType = Integer.parseInt( type );
        String description = stringTokenizer.nextToken();
        while ( stringTokenizer.hasMoreTokens() )
        {
            description += " " + stringTokenizer.nextToken();
        }

        return new NumericReply( numericType, description );
    }

    /**
     * @param request отправленный запрос
     * @param replyListener обработчик овтета
     */
    public void getReply(
        ServiceMessage request,
        ReplyListener replyListener )
    {
        _currentRequest = request;
        _currentReplyListener = replyListener;
    }

    @Override
    protected void finalize()
        throws Throwable
    {
        this.interrupt();
        super.finalize();
    }
}
