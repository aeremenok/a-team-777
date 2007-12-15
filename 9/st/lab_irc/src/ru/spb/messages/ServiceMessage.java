package ru.spb.messages;

import java.util.ArrayList;

/**
 * служебное сообщение
 * 
 * @author eav
 */
public abstract class ServiceMessage
    extends Message
{
    public ServiceMessage()
    {
    }

    /**
     * @param numericReply ответ
     * @return может ли сообщение вызвать заданный ответ
     */
    public boolean mayCause(
        NumericReply numericReply )
    {
        return hasError( numericReply ) || _possibleReplies.contains( numericReply.getType() );
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

    private ArrayList<ReplyListener> listeners = new ArrayList<ReplyListener>();

    public void addReplyListener(
        ReplyListener replyListener )
    {
        listeners.add( replyListener );
    }
}
