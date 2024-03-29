package ru.spb.messages;

import java.util.ArrayList;

import ru.spb.client.IRCStringTokenizer;
import ru.spb.client.entities.User;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.messages.constants.Errors;
import ru.spb.messages.constants.Replies;

/**
 * содержит данные сообщения
 * 
 * @author eav
 */
public abstract class ServiceMessage
    implements
        Replies,
        Errors
{
    protected ArrayList<Integer>     _possibleErrors  = new ArrayList<Integer>();
    protected ArrayList<Integer>     _possibleReplies = new ArrayList<Integer>();
    /**
     * содержимое сообщения
     */
    protected String                 _message         = "";
    /**
     * обработчики сообщения
     */
    private ArrayList<ReplyListener> _listeners       = new ArrayList<ReplyListener>();

    /**
     * @return сообщение в виде, удобном для просмотра
     */
    public String getMessageString()
    {
        return _message;
    }

    /**
     * @param numericReply ответ
     * @return может ли сообщение вызвать заданный ответ
     */
    public boolean mayCause(
        NumericReply numericReply )
    {
        return _possibleReplies.contains( numericReply.getType() ) || hasError( numericReply );
    }

    /**
     * @param numericReply ответ
     * @return ошибка ли этот ответ
     */
    public boolean hasError(
        NumericReply numericReply )
    {
        return _possibleErrors.contains( numericReply.getType() );
    }

    /**
     * подвесить обработчик
     * 
     * @param replyListener
     */
    public void addReplyListener(
        ReplyListener replyListener )
    {
        _listeners.add( replyListener );
    }

    /**
     * получить сообщение и оповестить обработчики
     * 
     * @param numericReply
     */
    public void receive(
        NumericReply numericReply )
    {
        if ( hasError( numericReply ) )
        {
            for ( ReplyListener listener : _listeners )
            {
                listener.onFailure( numericReply );
            }
        }
        else if ( mayCause( numericReply ) )
        {
            for ( ReplyListener listener : _listeners )
            {
                listener.onSuccess( numericReply );
            }
        }
        // todo игнорируем, если неверное сообщение
    }

    /**
     * восстанавливает сообщение
     * 
     * @param message источник
     * @return сообщение
     */
    public static ServiceMessage parse(
        String message )
    {
        if ( message != null )
        {
            IRCStringTokenizer stringTokenizer = new IRCStringTokenizer( message, " " );
            try
            {
                String prefix = "";
                int startPos = getStartPos( stringTokenizer );
                String type = stringTokenizer.get( startPos );

                if ( type.equalsIgnoreCase( "PRIVMSG" ) )
                {
                    return parsePrivateMessage( stringTokenizer );
                }
                else if ( type.equalsIgnoreCase( "WALLOPS" ) || type.equalsIgnoreCase( "JOIN" ) ||
                          type.equalsIgnoreCase( "PART" ) || type.equalsIgnoreCase( "QUIT" ) )
                {
                    return parseWallopsMessage( stringTokenizer );
                }
                return parseNumericReply( stringTokenizer, type, startPos );
            }
            catch ( Throwable e )
            {
                ServiceLogPanel.getInstance().error( e.getMessage() );
                e.printStackTrace();
            }
        }
        return null;
    }

    private static ServiceMessage parseWallopsMessage(
        IRCStringTokenizer stringTokenizer )
    {
        ServiceMessage serviceMessage = null;
        String author = null;
        ArrayList<String> channelNames = new ArrayList<String>();
        String channelName = null;

        // todo организовать, сгруппировать функции
        int startPos = getStartPos( stringTokenizer );
        String messageType = stringTokenizer.get( startPos );

        if ( messageType.equalsIgnoreCase( "WALLOPS" ) )
        {
            author = getAuthor( stringTokenizer );
        }
        else
        { // формат уведомлений стандартных серверов
            author = getAuthorSimple( stringTokenizer ).substring( 1 );
            if ( author.equalsIgnoreCase( User.getCurrentUser().getName() ) )
            { // о себе сообщения не рассылаем
                return null;
            }

            if ( messageType.equals( "QUIT" ) )
            { // обновить все каналы
                channelNames = null;
            }
            else
            {
                if ( messageType.equalsIgnoreCase( "JOIN" ) )
                {
                    channelName = stringTokenizer.get( stringTokenizer.indexOf( messageType ) + 1 ).substring( 1 );
                    serviceMessage = new JoinMessage( channelName );
                }
                else if ( messageType.equalsIgnoreCase( "PART" ) )
                {
                    channelName = stringTokenizer.get( stringTokenizer.indexOf( messageType ) + 1 );
                    serviceMessage = new PartMessage( channelName );
                }
                channelNames.add( channelName );
            }
        }

        // todo реализовать остальные команды
        return new WallopsMessage( serviceMessage, author, channelNames );
    }

    private static int getStartPos(
        IRCStringTokenizer stringTokenizer )
    {
        String prefix = "";
        int startPos = 0;
        if ( stringTokenizer.get( 0 ).startsWith( ":" ) )
        { // есть префикс
            prefix = stringTokenizer.get( 0 );
            startPos = 1;
        }
        return startPos;
    }

    private static String getAuthor(
        IRCStringTokenizer stringTokenizer )
    {
        int authorIndex = stringTokenizer.indexOf( "from" ) + 1;
        return stringTokenizer.get( authorIndex );
    }

    private static String getAuthorSimple(
        IRCStringTokenizer stringTokenizer )
    {
        String container = stringTokenizer.get( 0 );
        int start = container.indexOf( ':' );
        int end = container.indexOf( '!' );
        return container.substring( start, end );
    }

    /**
     * восстанавливает личное сообщение
     * 
     * @param stringTokenizer источник
     * @return личное сообщение
     * @throws Throwable здесь может быть ошибка на каждом этапе, поэтому
     *             проверок не делаем, а ловим эксепшен %)
     */
    public static PrivateMessage parsePrivateMessage(
        IRCStringTokenizer stringTokenizer )
        throws Throwable
    {
        String prefix = "";
        int startPos = 0;
        if ( stringTokenizer.get( 0 ).startsWith( ":" ) )
        { // есть префикс
            prefix = stringTokenizer.get( 0 );
            startPos = 1;
        }

        String from = prefix.substring( 1, prefix.indexOf( "!" ) );
        String to = stringTokenizer.get( startPos + 1 );

        String content = stringTokenizer.getRest( startPos + 2 ).substring( 1 );

        return new PrivateMessage( from, to, content );
    }

    /**
     * восстанавливает ответ сервера
     * 
     * @param stringTokenizer источник
     * @param type тип
     * @param startPos
     * @return ответ сервера
     * @throws Throwable здесь может быть ошибка на каждом этапе, поэтому
     *             проверок не делаем, а ловим эксепшен %)
     */
    public static NumericReply parseNumericReply(
        IRCStringTokenizer stringTokenizer,
        String type,
        int startPos )
        throws Throwable
    {
        int numericType = Integer.parseInt( type );
        String description = stringTokenizer.getRest();

        return new NumericReply( numericType, description, startPos );
    }
}
