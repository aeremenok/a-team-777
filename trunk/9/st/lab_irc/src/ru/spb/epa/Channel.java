package ru.spb.epa;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ����� Date: 14.12.2007 Time: 0:38:59
 */
public class Channel
{

    List<Client>   users = new ArrayList<Client>();

    private String name;
    private String topic;

    public Channel(
        String name )
    {
        this.name = name;
        this.topic = "Default_topic" + name;
    }

    public void connect(
        Client c )
    {
        users.add( c );
        ServerLogger.log( "Client " + c.getFullname() + " is on channel " + this.name );
    }

    public void sendMsg(
        Client c,
        String mess )
    {
        for ( Client chater : this.users )
        {
            if ( !chater.equals( c ) )
            {
                chater.sendToClient( mess );
            }
        }
    }

    public String getName()
    {
        return name;
    }

    public List<Client> getUsers()
    {
        return users;
    }

    public String getTopic()
    {
        return topic;
    }

    public void addUser(
        Client client )
    {
        users.add( client );
    }

    public void removeUser(
        Client c )
    {
        users.remove( c );
    }

    public int getNumbersersOnChannel(){
        return this.users.size();
    }
}
