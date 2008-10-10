package talkie.common.constants;

/**
 * Глобальные константы
 * 
 * @author ssv
 */
public interface Talkie
{
    /**
     * максимальный размер сообщения в байтах, если размер превышает указанный - сообщение разбивается на нужное
     * количество сообщений и пересылается по очереди
     */
    public static final int MSG_SIZE = 100;
}
