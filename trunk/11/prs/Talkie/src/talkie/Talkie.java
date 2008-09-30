package talkie;

/**
 * Константы
 * 
 * @author ssv
 */
public interface Talkie
{
    /**
     * максимальный размер сообщения в байтах, если размер превышает указанный - сообщение разбивается на нужное
     * количество сообщений и пересылается по очереди
     */
    public static final int    MSG_SIZE = 100;
    /**
     * авторизация
     */
    public static final String LOGIN    = "l";
    /**
     * пользователя нет в сети
     */
    public static final int    AWAY     = 0;
    /**
     * пользователь в сети
     */
    public static final int    ONLINE   = 1;
}
