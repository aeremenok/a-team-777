package ru.spb.epa.exceptions;

/**
 * User: �����
 * Date: 09.12.2007
 * Time: 18:40:17
 */
public class CommandExecutionException extends  IRCServerException{

    public CommandExecutionException() {
        this.message = "Error exetuting command";
    }

    public CommandExecutionException(String message) {
        super("Error exetuting command:" + message);
    }

    public CommandExecutionException(String message, Throwable cause) {
        super("Error exetuting command:" + message, cause);
    }
}
