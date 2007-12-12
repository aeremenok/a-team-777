package ru.spb.client.entities;

/**
 * все, с чем можно чатиться
 * 
 * @author eav
 */
public interface IChattable
{

    /**
     * начать чат с другим
     * 
     * @param chattable другой охотник поболтать
     */
    void startChat(
        IChattable chattable );

    /**
     * ведет ли пользователь программы разговор с этим
     * 
     * @return
     */
    boolean isChattingWithMe();

}
