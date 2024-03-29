package ru.spb.epa.commands;

import ru.spb.epa.Client;
import ru.spb.epa.IRCConstants;
import ru.spb.epa.ServerConfig;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * User: �����
 * Date: 09.12.2007
 * Time: 20:00:10
 *
 * 3.1.3 User message


      Command: USER
   Parameters: <user> <mode> <unused> <realname>

   The USER command is used at the beginning of connection to specify
   the username, hostname and realname of a new user.

   The <mode> parameter should be a numeric, and can be used to
   automatically set user modes when registering with the server.  This
   parameter is a bitmask, with only 2 bits having any signification: if
   the bit 2 is set, the user mode 'w' will be set and if the bit 3 is
   set, the user mode 'i' will be set.  (See Section 3.1.5 "User
   Modes").

   The <realname> may contain space characters.

   Numeric Replies:

           ERR_NEEDMOREPARAMS              ERR_ALREADYREGISTRED

   Example:

   USER guest 0 * :Ronnie Reagan   ; User registering themselves with a
                                   username of "guest" and real name
                                   "Ronnie Reagan".

   USER guest 8 * :Ronnie Reagan   ; User registering themselves with a
                                   username of "guest" and real name
                                   "Ronnie Reagan", and asking to be set
                                   invisible.


 */
public class USER
    extends Command
{

    public void execute(
        Client c )
        throws CommandExecutionException
    {
        String userName = this.parameters.get( 1 ).token;
        String hostName = this.parameters.get( 2 ).token;
        String ipAdress = this.parameters.get( 3 ).token;
        String fullname = this.parameters.get( 4 ).token;

        if(fullname.startsWith(":"))fullname = fullname.substring(1);
        if(ipAdress.startsWith("\""))ipAdress = ipAdress.substring(1);
        if(ipAdress.endsWith("\""))ipAdress = ipAdress.substring(0,ipAdress.length()-1);
        if(hostName.startsWith("\""))hostName = hostName.substring(1);
        if(hostName.endsWith("\""))hostName = hostName.substring(0,hostName.length()-1);

        c.setHostname( hostName );
        c.setUsername( userName );
        c.setFullname( fullname );
        c.setIpAdress( ipAdress );

        c.sendToClient( IRCConstants.RPL_WELLCOME + " " + c.getNickname() + " " + ServerConfig.WELLCOME_message, true);
    }

    // ================================================================================================================
    // COMMON
    // ================================================================================================================

    protected USER()
    {
        super();
    }

    protected USER(
        String message )
    {
        super( message );
    }

    protected Command getInstance(
        String message )
    {
        return new USER( message );
    }

}
