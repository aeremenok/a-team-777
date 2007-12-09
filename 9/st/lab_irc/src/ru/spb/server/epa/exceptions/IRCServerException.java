package epa.exceptions;

import epa.ServerLogger;

/**
 * User: Павел
 * Date: 09.12.2007
 * Time: 14:56:16
 */
public class IRCServerException extends Exception {

    protected String message;
    protected Throwable cause;


    public String getMessage() {
        return message;
    }

    public IRCServerException() {

    }

    public IRCServerException(String message) {
        this.message = message;
    }

    public IRCServerException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }


    public IRCServerException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public void Log()
    {
        ServerLogger.log("{EXCEPTION}" + this.message);
    }





}
