package ru.spb.messages;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
    public static PrivateMessage parsePrivateMessage(
        StringTokenizer stringTokenizer )
        throws Throwable
    {
        String from = stringTokenizer.nextToken();
        String to = stringTokenizer.nextToken();

        String content = stringTokenizer.nextToken();
        while ( stringTokenizer.hasMoreTokens() )
        {
            content += stringTokenizer.nextToken() + " ";
        }

        return new PrivateMessage( from, to, content );
    }

    /**
     * восстанавливает ответ сервера
     * 
     * @param stringTokenizer источник
     * @param type тип
     * @return ответ сервера
     */
    public static NumericReply parseNumericReply(
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
}
