package ru.spb.messages.constants;

public abstract class Replies {
    // The server sends Replies 001 to 004 to a user upon
    // successful registration.
    /**
     * "Welcome to the Internet Relay Network <nick>!<user>@<host>"
     */
    static String RPL_WELCOME = "001";

    /**
     * "Your host is <servername>, running version <ver>"
     */
    static String RPL_YOURHOST = "002";
    /**
     * "This server was created <date>"
     */
    static String RPL_CREATED = "003";
    /**
     * "<servername> <version> <available user modes> <available channel modes>"
     */
    static String RPL_MYINFO = "004";
}
