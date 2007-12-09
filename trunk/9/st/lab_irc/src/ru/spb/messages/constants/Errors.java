package ru.spb.messages.constants;

/**
 * Error replies are found in the range from 400 to 599
 * @author eav
 */
public abstract class Errors {
    /**
     * <b>nickname</b> :No such nick/channel <br>
     * Used to indicate the nickname parameter supplied to a command is currently unused.
     */
    static String ERR_NOSUCHNICK = "401";

    /**
     * <b>server name</b> :No such server <br>
     * Used to indicate the server name given currently does not exist
     */
    static String ERR_NOSUCHSERVER = "402";

    /**
     * <b>channel name</b> :No such channel <br>
     * Used to indicate the given channel name is invalid
     */
    static String ERR_NOSUCHCHANNEL = "403";

    /**
     * <b>channel name</b> :Cannot send to channel <br>
     * Sent to a user who is either (a) not on a channel which is mode +n or (b) not a chanop (or mode +v) on a channel
     * which has mode +m set or where the user is banned and is trying to send a PRIVMSG message to that channel
     */
    static String ERR_CANNOTSENDTOCHAN = "404";
}
