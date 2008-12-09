package talkie.client.connect;

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
    public void sendText(
        String text )
    {
        send( text );
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
     * @param message
     */
    abstract protected void send(
        String message );
}
