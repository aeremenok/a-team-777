package epa.exceptions;

/**
 * User: Павел
 * Date: 09.12.2007
 * Time: 18:40:17
 */
public class CommandExecutionExcetion extends  IRCServerException{

    public CommandExecutionExcetion() {
        this.message = "Error exetuting command";
    }

    public CommandExecutionExcetion(String message) {
        super("Error exetuting command:" + message);
    }

    public CommandExecutionExcetion(String message, Throwable cause) {
        super("Error exetuting command:" + message, cause);
    }
}
