package ru.spb.client.entities;

/**
 * содержит данные о сервере
 * 
 * @author eav
 */
public class Server implements IConnectable {

    /**
     * подключены ли
     */
    boolean isConnected = false;

    /**
     * подключиться к этому серверу
     */
    public void connect() {
	isConnected = true;
	System.out.println("connected");

	getChannels();
    }

    public Channel[] getChannels() {
	System.out.println("getting cnannel list");
	return new Channel[] { new Channel("channel1"), new Channel("channel2") };
    }

    /**
     * отключиться от этого сервера
     */
    public void disconnect() {
	isConnected = false;
	System.out.println("disconnected");
    }

    public boolean isConnected() {
	return isConnected;
    }

}
