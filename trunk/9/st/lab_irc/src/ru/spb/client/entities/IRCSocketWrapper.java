package ru.spb.client.entities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import ru.spb.client.entities.messages.ListMessage;
import ru.spb.client.entities.messages.NickMessage;
import ru.spb.client.entities.messages.NumericReply;
import ru.spb.client.entities.messages.PassMessage;
import ru.spb.client.entities.messages.ServiceMessage;
import ru.spb.client.entities.messages.UserMessage;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.messages.constants.Errors;
import ru.spb.messages.constants.Replies;

/**
 * класс, осуществляющий сокетное соединение. в любой момент соединение возможно
 * только с одним сервером
 * 
 * @author eav
 */
public class IRCSocketWrapper
    implements
        Replies,
        Errors

{
    private static Server         _currentServer = null;

    private static Socket         _socket        = null;

    private static BufferedReader _reader;

    private static PrintWriter    _writer;

    public IRCSocketWrapper(
        Server server )
    {

    }

    public static ArrayList<User> getRegisteredUsers()
    {
        return null;
    }

    public static void connect(
        Server server,
        User user )
    {
        if ( _currentServer != server )
        { // соединяемся с новым сервером
            // отсоединяемся от старого
            disconnect( _currentServer );

            _currentServer = server;
            try
            {
                _socket = new Socket( _currentServer.getHost(), _currentServer.getPort() );

                _reader = new BufferedReader( new InputStreamReader( new DataInputStream( _socket.getInputStream() ) ) );
                _writer = new PrintWriter( _socket.getOutputStream(), true );

                register( user );
            }
            catch ( Throwable e )
            {
                e.printStackTrace();
                ServiceLogPanel.getInstance().error( e.getMessage() );
            }
        }
    }

    /**
     * получить с сервера список каналов
     * 
     * @return список каналов
     */
    public static ArrayList<Channel> getChannels()
    {
        ListMessage listMessage = new ListMessage( _currentServer );
        sendCommand( listMessage );

        try
        {
            ArrayList<Channel> result = new ArrayList<Channel>();

            NumericReply numericReply;
            while ( (numericReply = listMessage.getNumericReply( _reader )).getType() != RPL_LISTEND )
            { // пока не пришло сообщение о конце списка каналов
                Channel channel =
                                  new Channel( numericReply.getProperty( NumericReply.CHANNEL ),
                                               numericReply.getProperty( NumericReply.TOPIC ),
                                               // о хозяине канала ничего не
                                               // известно
                                               null );
                result.add( channel );
            }
            return result;
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            ServiceLogPanel.getInstance().error( e.getMessage() );
        }

        return null;
    }

    public static void disconnect(
        Server server )
    {
        if ( _currentServer != null )
        {
            if ( _currentServer != server )
            {
                // todo послать команду
                _currentServer = null;
                try
                {
                    _socket.close();
                    _socket = null;
                    _reader = null;
                    _writer = null;
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                    ServiceLogPanel.getInstance().error( e.getMessage() );
                }
            }
            else
            {
                ServiceLogPanel.getInstance().error( "cannot disconnect another server!" );
            }
        }
    }

    public static void register(
        User user )
        throws IOException,
            ErrorReplyReceivedException
    {
        PassMessage pass = new PassMessage( user );
        sendCommand( pass );

        NickMessage nick = new NickMessage( user );
        sendCommand( nick );

        UserMessage userMessage = new UserMessage( user );
        sendCommand( userMessage );

        NumericReply numericReply = userMessage.getNumericReply( _reader );
        ServiceLogPanel.getInstance().info( "received", numericReply.getDescription() );
    }

    private static void sendCommand(
        ServiceMessage serviceMessage )
    {
        _writer.println( serviceMessage.getMessageString() );
    }

    public static void createChannel(
        Channel channel )
    {
        // TODO Auto-generated method stub

    }
}
