package ru.spb.client.entities;

/**
 * все, к чему можно подключиться
 * 
 * @author eav
 */
public interface IConnectable
{

    public abstract boolean isConnected();

    public abstract void disconnect();

    public abstract void connect();

    public void toggleConnection();

    public void register(
        User user );
}