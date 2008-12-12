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
    protected Server server = null;
    protected User   user   = null;
    private boolean  valid  = true;

    public ServerConnector(
        Server server )
    {
        this.server = server;
    }

    public void close(
        boolean informOtherSide )
    {
        if ( informOtherSide )
        {
            send( Message.LOGOUT );
        }
    }

    public boolean isValid()
    {
        return valid;
    }

    public boolean login(
        String login,
        String pass )
    {
        setValid( false );
        user = server.getUsers().get( login );
        if ( user != null && pass.equals( user.getPass() ) )
        {
            synchronized ( user )
            {
                setValid( true );
                user.setStatus( Status.ONLINE );
                user.setHandler( this );
            }
        }
        return isValid();
    }

    public abstract boolean needsStopping();

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
                stop( false );
            }
        }
    }

    public void setValid(
        boolean valid )
    {
        this.valid = valid;
    }

    public final void stop(
        boolean informOtherSide )
    {
        setValid( false );
        close( informOtherSide );
        if ( needsStopping() )
        {
            Thread.currentThread().interrupt();
        }
    }

    protected abstract void send(
        String string );
}
