package ru.spb.client;

import ru.spb.client.gui.IRCWindow;

public class IRCStarter
{
    /**
     * @param args
     */
    public static void main(
        String[] args )
    {
        IRCWindow.getInstance().run();
    }
}
