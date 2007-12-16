package ru.spb.messages;

import java.util.ArrayList;

import ru.spb.client.IRCStringTokenizer;
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
    protected ArrayList              _possibleErrors  = new ArrayList();
    protected ArrayList              _possibleReplies = new ArrayList();
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
     * @param messageReceiver TODO
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
                String type = stringTokenizer.nextToken();
                if ( type.equalsIgnoreCase( "PRIVMSG" ) )
                {
                    return parsePrivateMessage( stringTokenizer );
                }
                else if ( type.equalsIgnoreCase( "WALLOPS" ) )
                {
                    return parseWallopsMessage( stringTokenizer );
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

    private static ServiceMessage parseWallopsMessage(
        IRCStringTokenizer stringTokenizer )
    {
        if ( stringTokenizer.nextToken().equalsIgnoreCase( "JOIN" ) )
        {
            String[] tokens = stringTokenizer.getString().split( " " );
            String channelName = tokens[2];
            String author = tokens[4];
            return new WallopsMessage( new JoinMessage( channelName ), author, channelName );
        }
        // todo реализовать остальные команды
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
    public static PrivateMessage parsePrivateMessage(
        IRCStringTokenizer stringTokenizer )
        throws Throwable
    {
        String from = stringTokenizer.nextToken();
        String to = stringTokenizer.nextToken();

        String content = stringTokenizer.getRest();

        return new PrivateMessage( from, to, content );
    }

    /**
     * восстанавливает ответ сервера
     * 
     * @param stringTokenizer источник
     * @param type тип
     * @return ответ сервера
     * @throws Throwable здесь может быть ошибка на каждом этапе, поэтому
     *             проверок не делаем, а ловим эксепшен %)
     */
    public static NumericReply parseNumericReply(
        IRCStringTokenizer stringTokenizer,
        String type )
        throws Throwable
    {
        int numericType = Integer.parseInt( type );
        String description = stringTokenizer.getRest();

        return new NumericReply( numericType, description );
    }
}
