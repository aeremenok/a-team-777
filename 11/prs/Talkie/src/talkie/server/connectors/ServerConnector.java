package talkie.server.connectors;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import talkie.common.constants.Message;
import talkie.common.constants.Status;
import talkie.server.Server;
import talkie.server.data.User;

public abstract class ServerConnector
    implements
        Runnable
{
    protected Server  server = null;
    protected User    user   = null;
    protected boolean valid  = true;

    public ServerConnector(
        Server server )
    {
        this.server = server;
    }

    public void close()
    {
        send( Message.LOGOUT );
    }

    public boolean login(
        String login,
        String pass )
    {
        valid = false;
        user = server.getUsers().get( login );
        if ( user != null && pass.equals( user.getPass() ) )
        {
            synchronized ( user )
            {
                valid = true;
                user.setStatus( Status.ONLINE );
                user.setHandler( this );
            }
        }
        send( String.valueOf( valid ) );
        return valid;
    }

    public void process(
        String message )
    {
        StringTokenizer tokenizer = new StringTokenizer( message, " " );
        if ( tokenizer.countTokens() > 0 )
        {
            String operation = tokenizer.nextToken();

            if ( Message.LOGIN.equalsIgnoreCase( operation ) )
            {
                String login = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
                String pass = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
                boolean yes = login( login, pass );
                if ( yes )
                {
                    String date = DateFormat.getDateTimeInstance().format( new Date() );
                    HashMap<String, User> users = server.getUsers();
                    for ( String key : users.keySet() )
                    {
                        User u = users.get( key );
                        if ( u.getStatus() == Status.ONLINE )
                        {
                            u.getHandler().send( "[" + date + "] В чат приходит пользователь " + user.getLogin() );
                        }
                    }
                }
            }
            else if ( Message.LIST.equalsIgnoreCase( operation ) )
            {
                String toSend = "Сейчас в чате: " + user.getLogin();
                HashMap<String, User> users = server.getUsers();
                for ( String key : users.keySet() )
                {
                    User u = users.get( key );
                    if ( u.getStatus() == Status.ONLINE && u != user )
                    {
                        toSend += ", " + u.getLogin();
                    }
                }
                send( toSend );
            }
            else if ( Message.MESSAGE.equalsIgnoreCase( operation ) )
            {
                HashMap<String, User> users = server.getUsers();
                String date = DateFormat.getDateTimeInstance().format( new Date() );
                for ( String key : users.keySet() )
                {
                    User u = users.get( key );
                    if ( u.getStatus() == Status.ONLINE )
                    {
                        String toSend = "[" + date + "] " + user.getLogin() + " говорит: " + message.substring( 3 );
                        u.getHandler().send( toSend );
                    }
                }
            }
            else if ( Message.LOGOUT.equalsIgnoreCase( operation ) )
            {
                synchronized ( user )
                {
                    user.setHandler( null );
                    user.setStatus( Status.AWAY );
                }
                String date = DateFormat.getDateTimeInstance().format( new Date() );
                HashMap<String, User> users = server.getUsers();
                for ( String key : users.keySet() )
                {

                    User u = users.get( key );
                    if ( u.getStatus() == Status.ONLINE )
                    {
                        u.getHandler().send( "[" + date + "] Пользователь " + user.getLogin() + " покинул чат!" );
                    }
                }
                stop();
            }
        }
    }

    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() && valid )
        {
            mainLoopStep();
        }
    }

    public final void stop()
    {
        valid = false;
        try
        {
            close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }

    protected abstract void mainLoopStep();

    protected abstract void send(
        String string );
}
