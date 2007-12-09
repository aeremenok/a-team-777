package epa.commands;

import epa.Client;
import epa.exceptions.CommandExecutionExcetion;
import epa.exceptions.IRCServerException;
import epa.exceptions.UnParseableCommandException;

import java.util.*;

/**
 * User: Павел
 * Date: 09.12.2007
 * Time: 14:32:13
 */
public class Command {

    protected String message;
    protected List<Tok> parameters = new ArrayList<Tok>();

    private static Map<String, Command> l = new TreeMap<String, Command>();



    static{
        l.put("NICK" , new NICK());
        l.put("USER" , new USER());
        l.put("QUIT" , new QUIT());
        //l.put("LIST", new )

    }

    public static Command getCommand(String s) throws IRCServerException {

        int cLen = s.indexOf(" ");
        if(cLen < 0 || cLen> s.length()) throw new UnParseableCommandException();
        String commandStr = s.substring(0,cLen);
        Command c = l.get(commandStr);
        if(c==null) throw new CommandExecutionExcetion("No such command");
        return c.getInstance(s);
    }

    protected Command() {

    }

    public Command(String message) {
        this.message = message;
        parseMessage();
    }

    protected Command getInstance(String s){
        return new Command(s);
    }

    public void execute(Client c)throws CommandExecutionExcetion
    {
        throw new CommandExecutionExcetion("unsupported command =(");
    }

    protected void parseMessage(){

        StringTokenizer tok = new StringTokenizer(this.message);
        int position = 0;

        while(tok.hasMoreTokens()){
            Tok t = new Tok(tok.nextToken(),position);
            this.parameters.add(t);
            position += t.token.length()+1;
        }

        System.out.print("PARSED mess:");
        for(Tok t:this.parameters){
            System.out.print(t.token + "-" + t.pos + "; ") ;
        }
        System.out.println("!");
    }




    /** Used in parsing. */
    protected class Tok {
        public Tok(String token, int pos) {
            this.token = token;
            this.pos = pos;
        }
        public String token;
        public int pos;
    }
    
}
