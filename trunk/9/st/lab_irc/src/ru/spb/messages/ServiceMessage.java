package ru.spb.messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import ru.spb.messages.exceptions.ErrorReplyReceivedException;

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
     * пытается получить ответ на заданное сообщение
     * 
     * @param reader откуда приходит ответ
     * @param serviceMessage отправленное сообщение
     * @return полученный ответ
     * @throws IOException
     * @throws ErrorReplyReceivedException
     */
    public NumericReply getNumericReply(
        BufferedReader reader )
        throws IOException,
            ErrorReplyReceivedException
    {
        // читаем сообщение от сервера
        String message = reader.readLine();

        // разбираем его
        StringTokenizer stringTokenizer = new StringTokenizer( message, " " );
        int type = Integer.parseInt( stringTokenizer.nextToken() );
        String description = stringTokenizer.nextToken();
        while ( stringTokenizer.hasMoreTokens() )
        {
            description += " " + stringTokenizer.nextToken();
        }
        NumericReply numericReply = new NumericReply( type, description );

        // проверяем, не ошибка ли это
        if ( _possibleErrors.contains( numericReply.getType() ) )
        {
            throw new ErrorReplyReceivedException( "receieved error " + numericReply.getType() );
        }
        return numericReply;
    }
}
