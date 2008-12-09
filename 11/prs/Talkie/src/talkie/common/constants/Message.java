package talkie.common.constants;

/**
 * константы типов сообщений
 * 
 * @author ssv
 */
public abstract class Message
{
    /**
     * авторизация
     */
    public static final String LOGIN   = "l";
    /**
     * получение списка подключенных пользователей
     */
    public static final String LIST    = "/w";
    /**
     * сообщение
     */
    public static final String MESSAGE = "m";
    /**
     * выход
     */
    public static final String LOGOUT  = "o";
}
