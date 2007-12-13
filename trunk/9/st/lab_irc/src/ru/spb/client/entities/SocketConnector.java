package ru.spb.client.entities;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * класс, осуществляющий сокетное соединение
 * 
 * @author eav
 */
public class SocketConnector
{
    private Server _server;

    public SocketConnector(
        Server server )
    {
        _server = server;
        try
        {
            _socket = new Socket( _server.getHost(), _server.getPort() );
        }
        catch ( UnknownHostException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private Socket _socket;

    public ArrayList<User> getRegisteredUsers()
    {
        // todo сформировать команду и прочитать ответ
        return null;
    }

    public void connect()
    {
        // TODO Auto-generated method stub

    }

    public ArrayList<Channel> getChannels()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void disconnect()
    {
        // TODO Auto-generated method stub

    }

    public void register(
        User user )
    {
        // TODO Auto-generated method stub

    }

    public void createChannel()
    {
        // TODO Auto-generated method stub

    }
}
