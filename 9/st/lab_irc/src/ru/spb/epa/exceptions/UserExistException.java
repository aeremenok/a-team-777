package ru.spb.epa.exceptions;

/**
 * User: Павел
 * Date: 09.12.2007
 * Time: 19:35:40
 */
public class UserExistException extends CommandExecutionExcetion{
    public UserExistException() {
        this.message = "User already exist";
    }

    public UserExistException(String message) {
        super("User already exist:" + message);
    }

    public UserExistException(String message, Throwable cause) {
        super("User already exist:" + message, cause);
    }
}
