package ru.spb.epa.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import ru.spb.epa.Client;
import ru.spb.epa.IRCConstants;
import ru.spb.epa.exceptions.CommandExecutionException;
import ru.spb.epa.exceptions.IRCServerException;

/**
 * User: ����� Date: 09.12.2007 Time: 14:32:13
 */
public class Command
    implements
        IRCConstants
{

    protected String                    message;
    protected List<Tok>                 parameters = new ArrayList<Tok>();

    private static Map<String, Command> l          = new TreeMap<String, Command>();

    static
    {
        l.put( "NICK", new NICK() );
        l.put( "USER", new USER() );
        l.put( "QUIT", new QUIT() );
        l.put( "LIST", new LIST() );
        l.put( "JOIN", new JOIN() );
        l.put( "NAMES", new NAMES() );
        l.put( "PRIVMSG", new PRIVMSG() );
    }

    public static Command getCommand(
        String s )
        throws IRCServerException
    {

        int cLen = s.indexOf( " " );
        if ( cLen < 0 || cLen > s.length() )
            cLen = s.length();
        String commandStr = s.substring( 0, cLen );
        Command c = l.get( commandStr );
        if ( c == null )
            throw new CommandExecutionException( "No such command" );
        return c.getInstance( s );
    }

    protected Command()
    {

    }

    public Command(
        String message )
    {
        this.message = message;
        parseMessage();
    }

    protected Command getInstance(
        String s )
    {
        return new Command( s );
    }

    public void execute(
        Client c )
        throws CommandExecutionException
    {
        throw new CommandExecutionException( "unsupported command =(" );
    }

    protected void parseMessage()
    {
        StringTokenizer tok = new StringTokenizer( this.message );
        int position = 0;

        while ( tok.hasMoreTokens() )
        {
            Tok t = new Tok( tok.nextToken(), position );
            this.parameters.add( t );
            position += t.token.length() + 1;
        }

        System.out.print( "PARSED mess:" );
        for ( Tok t : this.parameters )
        {
            System.out.print( t.token + "-" + t.pos + "; " );
        }
        System.out.println( "!" );
    }

    protected void parseStrings(
        String mess,
        String delimeter )
    {
        List<Tok> out = new ArrayList<Tok>();

        StringTokenizer tok = new StringTokenizer( mess );
        int position = 0;

        while ( tok.hasMoreTokens() )
        {
            Tok t = new Tok( tok.nextToken(), position );
            out.add( t );
            position += t.token.length() + 1;
        }

        // for(Tok t:out){
        // System.out.print(t.token + "-" + t.pos + "; ") ;
        // }
    }

    /** Used in parsing. */
    protected class Tok
    {
        public Tok(
            String token,
            int pos )
        {
            this.token = token;
            this.pos = pos;
        }

        public String token;
        public int    pos;
    }

}
