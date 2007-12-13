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

    /**
     * подключены ли к этому каналу
     */
    private boolean isConnected;
    /**
     * имя канала
     */
    private String  name;

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
        isConnected = false;
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
        ServiceLogPanel.getInstance().info( "starting chat on channel =" + name + "=" );
        IRCTabbedPanel.getInstance().addChat( this );
    }

    public User[] getUsers()
    {
        // todo заглушка
        return new User[] { new User( "user1" ), new User( "user2" ) };
    }

    @Override
    public void quitChat(
        IChattable chattable )
    {
        ServiceLogPanel.getInstance().info( "exiting from channel =" + name + "=" );
        IRCTabbedPanel.getInstance().removeChat( this );
    }

    @Override
    public void toggleChat(
        IChattable chattable )
    {
        /**
         * здесь - другая реализация, нежели в {@link User}
         */
        if ( isConnected )
        {
            startChat( chattable );
        }
        else
        {
            quitChat( chattable );
        }
    }

    @Override
    public void toggleConnection()
    {
        /**
         * здесь метод нужен просто для удобства
         */
        if ( isConnected() )
        {
            disconnect();
        }
        else
        {
            connect();
        }
    }
}
