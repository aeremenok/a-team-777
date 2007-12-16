package ru.spb.client.entities;

import ru.spb.client.gui.logpanels.ChatLogPanel;
import ru.spb.client.gui.logpanels.MessageListener;
import ru.spb.messages.PrivateMessage;

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

    /**
     * сказать в чат
     * 
     * @param message сообщение
     */
    void say(
        PrivateMessage message );

    /**
     * подвесить обрабочик сообщений
     * 
     * @param messageListener
     */
    void addMessageListener(
        MessageListener messageListener );

    void setChatLogPanel(
        ChatLogPanel chatLogPanel );

}
