package ru.spb.client.entities;

/**
 * содержит данные о канале
 * 
 * @author eav
 */
public class Channel implements IConnectable {

    private boolean isConnected;
    private String name;

    public Channel(String name) {
	this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#isConnected()
     */
    public boolean isConnected() {
	return isConnected;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#disconnect()
     */
    public void disconnect() {
	isConnected = true;
	System.out.println("connected");
    }

    /*
     * (non-Javadoc)
     * 
     * @see ru.spb.client.entities.IConnectable#connect()
     */
    public void connect() {
	isConnected = true;
	System.out.println("connected");
    }

    public String getName() {
	return name;
    }

}
