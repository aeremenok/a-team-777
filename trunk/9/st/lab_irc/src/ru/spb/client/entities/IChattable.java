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
     * завершить чат
     * 
     * @param chattable
     */
    void quitChat(
        IChattable chattable );

    String getName();

    /**
     * включить/выключить чат
     * 
     * @param chattable
     */
    void toggleChat(
        IChattable chattable );

}
