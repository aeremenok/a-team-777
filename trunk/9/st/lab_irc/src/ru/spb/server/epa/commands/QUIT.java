package epa.commands;

import epa.Client;
import epa.exceptions.CommandExecutionExcetion;

/**
 * User: Павел
 * Date: 09.12.2007
 * Time: 20:32:52
 */
public class QUIT extends Command{
    public void execute(Client c) throws CommandExecutionExcetion {
        c.quit();
    }
    //================================================================================================================
    // COMMON
    //================================================================================================================

    protected QUIT() {
       super();
    }

    protected QUIT(String message){
       super(message);
    }

    protected Command getInstance(String message){
       return new QUIT(message);
    }

}
