package talkie.client.speakers;

import talkie.common.constants.Message;

/**
 * Обобщённое соединение между сервером и клиентом Talkie
 * 
 * @author ssv
 */
public abstract class ClientSpeaker
{
    protected boolean active = false;

    abstract public void close();

    public boolean isActive()
    {
        return active;
    }

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

    public void setActive(
        boolean active )
    {
        this.active = active;
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
