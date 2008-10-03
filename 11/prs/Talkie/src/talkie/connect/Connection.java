package talkie.connect;

import java.util.ArrayList;

import talkie.constants.Talkie;

/**
 * Обобщённое соединение между сервером и клиентом Talkie
 * 
 * @author ssv
 */
public abstract class Connection
{
    /**
     * @param login логин (имя пользователя)
     * @param pass пароль пользователя
     * @return true, если пользователь существует, false - иначе
     */
    public boolean login(
        String login,
        String pass )
    {
        String s = "l " + login + " " + pass + " ";
        return establish( s.getBytes() );
    }

    /**
     * обработать сообщение
     * 
     * @param text
     */
    public void splitAndSend(
        String text )
    {
        byte[] buffer = text.getBytes();

        ArrayList<byte[]> msgs = new ArrayList<byte[]>();
        int fullMsgNumber = buffer.length / Talkie.MSG_SIZE;
        int lastMsgLen = buffer.length % Talkie.MSG_SIZE;
        int msgNumber = fullMsgNumber + (lastMsgLen > 0 ? 1 : 0);
        for ( int i = 0; i < msgNumber; i++ )
        {
            int length = i == msgNumber - 1 ? lastMsgLen : Talkie.MSG_SIZE;
            byte[] buf = new byte[length];
            System.arraycopy( buffer, i * Talkie.MSG_SIZE, buf, 0, length );
            msgs.add( buf );
        }

        for ( byte[] msg : msgs )
        {
            send( msg );
        }
    }

    /**
     * установка соединения
     * 
     * @param bytes
     */
    abstract protected boolean establish(
        byte[] bytes );

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
     * @param bytes
     */
    abstract protected void send(
        byte[] bytes );
}
