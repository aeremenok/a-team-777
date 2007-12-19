package ru.spb.epa.commands;

import ru.spb.epa.Client;
import ru.spb.epa.IRCConstants;
import ru.spb.epa.exceptions.CommandExecutionException;
import ru.spb.epa.exceptions.UserExistException;

/**
 * User: �����
 * Date: 09.12.2007
 * Time: 18:39:16
 *
 * 3.1.2 Nick message
 *
 * Command: NICK
   Parameters: <nickname>

   NICK command is used to give user a nickname or change the existing
   one.
   Numeric Replies:

           ERR_NONICKNAMEGIVEN             ERR_ERRONEUSNICKNAME
           ERR_NICKNAMEINUSE               ERR_NICKCOLLISION
           ERR_UNAVAILRESOURCE             ERR_RESTRICTED

   Examples:

   NICK Wiz                ; Introducing new nick "Wiz" if session is
                           still unregistered, or user changing his
                           nickname to "Wiz"

   :WiZ!jto@tolsun.oulu.fi NICK Kilroy
                           ; Server telling that WiZ changed his
                           nickname to Kilroy.
 */
public class NICK extends Command {

    public void execute(Client c)throws CommandExecutionException
    {
        String nick = this.parameters.get(1).token;

        try {
            c.changeNick(nick);
        } catch (UserExistException e) {
            c.sendToClient("" + IRCConstants.ERR_NICKNAMEINUSE, true);
            return;
        }catch (IllegalArgumentException e){
            c.sendToClient("" + IRCConstants.ERR_NONICKNAMEGIVEN, true);
            return;
        }

        //c.sendBroadcastmessage();
                   //c.sendToClient();
    }



    //================================================================================================================
    // COMMON
    //================================================================================================================

     protected NICK() {
        super();
    }

    protected NICK(String message){
        super(message);
    }

    protected Command getInstance(String message){
        return new NICK(message);
    }

}
