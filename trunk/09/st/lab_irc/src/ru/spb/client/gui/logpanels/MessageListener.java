package ru.spb.client.gui.logpanels;

import ru.spb.messages.PrivateMessage;

/**
 * обработчик поступающих сообщений
 * 
 * @author eav
 */
public interface MessageListener
{
    void onMessage(
        PrivateMessage message );
}
