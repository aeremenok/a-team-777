package talkie.client.connect;

import talkie.common.constants.Message;

/**
 * Обобщённое соединение между сервером и клиентом Talkie
 * 
 * @author ssv
 */
public abstract class Connection
{
    /**
     * обработать сообщение
     * 
     * @param text
     */
    public void sendText(
        String text )
    {
        if ( Message.LOGOUT.equals( text ) )
        {
            send( text );
        }
        else if ( Message.LIST.equals( text ) )
        {
            send( text );
        }
        else
        {
            send( Message.MESSAGE + " " + text );
        }
    }

    /**
     * @return ответ с сервера
     */
    abstract protected String receive();

    /**
     * @param millis максимальный срок ожидания
     * @return ответ с сервера
     */
    abstract protected String receive(
        int millis );

    /**
     * отправить массив байт (зависит от реализации)
     * 
     * @param message
     */
    abstract protected void send(
        String message );
}
