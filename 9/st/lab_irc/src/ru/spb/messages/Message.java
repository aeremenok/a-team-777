package ru.spb.messages;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ru.spb.messages.constants.Errors;
import ru.spb.messages.constants.Replies;

/**
 * содержит данные сообщения
 * 
 * @author eav
 */
public abstract class Message
    implements
        Replies,
        Errors
{
    protected ArrayList _possibleErrors  = new ArrayList();
    protected ArrayList _possibleReplies = new ArrayList();
    /**
     * содержимое сообщения
     */
    protected String    _message         = "";

    /**
     * @return сообщение в виде, удобном для просмотра
     */
    public String getMessageString()
    {
        return _message;
    }

    /**
     * @return сообщение в виде, удобном для посылки в сокет
     */
    public byte[] getMessageBytes()
    {
        try
        {
            return _message.getBytes( "ascii" );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        return null;
    }

}
