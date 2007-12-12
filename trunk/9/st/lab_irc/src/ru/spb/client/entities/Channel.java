package ru.spb.client.entities;

import ru.spb.client.gui.IRCTabbedPanel;
import ru.spb.client.gui.ServiceLogPanel;

/**
 * содержит данные о канале
 * 
 * @author eav
 */
public class Channel
    implements
        IConnectable,
        IChattable
{

    private boolean isConnected;
    private String  name;
    private boolean isChattingWithMe = false;

    public Channel(
        String name )
    {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#isConnected()
     */
    public boolean isConnected()
    {
        return isConnected;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#disconnect()
     */
    public void disconnect()
    {
        isConnected = true;
        ServiceLogPanel.getInstance().info( "connected" );
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#connect()
     */
    public void connect()
    {
        isConnected = true;
        ServiceLogPanel.getInstance().info( "connected" );
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void startChat(
        IChattable chattable )
    {
        if ( User.getCurrentUser().equals( chattable ) )
        {
            isChattingWithMe = true;
        }
        ServiceLogPanel.getInstance().info( "starting chat on channel =" + name + "=" );
        IRCTabbedPanel.getInstance().addChat( this );
    }

    @Override
    public boolean isChattingWithMe()
    {
        return isChattingWithMe;
    }

    public User[] getUsers()
    {
        // todo заглушка
        return new User[] { new User( "user1" ), new User( "user2" ) };
    }
}
