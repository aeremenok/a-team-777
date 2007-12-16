package ru.spb.client.gui.listeners;

import ru.spb.messages.WallopsMessage;

/**
 * обработчик изменений в канале
 * 
 * @author eav
 */
public interface WallopsListener
{
    void onWallops(
        WallopsMessage wallopsMessage );
}
