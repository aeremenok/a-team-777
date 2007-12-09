package ru.spb.epa;

/**
 * User: Павел
 * Date: 08.12.2007
 * Time: 21:25:35
 *
 * for all settings
 * http://tools.ietf.org/html/rfc2813
 */
public class ServerConfig {

    public static final boolean IS_DEBUG = true;
    
    public static final int port = 6667;

    /**
     * Global server name. max 63 chars - by http://tools.ietf.org/html/rfc2813
     */
    public static final String SERVER_NAME = "epa";

    public static final String WELLCOME_message = "Wellcome to epa IRCserver!";


    /**
     * ServerSocket timeout
     */
    public static final int SERVER_SOCKET_TIMEOUT = 1000;






    
}
