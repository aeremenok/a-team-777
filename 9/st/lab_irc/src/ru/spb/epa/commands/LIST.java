package ru.spb.epa.commands;

import ru.spb.epa.Channel;
import ru.spb.epa.Client;
import ru.spb.epa.MainThread;
import ru.spb.epa.exceptions.CommandExecutionExcetion;

import java.util.List;

/**
 * User: �����
 * Date: 13.12.2007
 * Time: 23:33:45
 *
 * Command: LIST
   Parameters: [ <channel> *( "," <channel> ) [ <target> ] ]

   The list command is used to list channels and their topics.  If the
   <channel> parameter is used, only the status of that channel is
   displayed.

   If the <target> parameter is specified, the request is forwarded to
   that server which will generate the reply.

   Wildcards are allowed in the <target> parameter.

   Numeric Replies:

    ERR_TOOMANYMATCHES
    ERR_NOSUCHSERVER

 322    RPL_LIST
               "<channel> <# visible> :<topic>"

    RPL_LISTEND

   Examples:

   LIST                            ; Command to list all channels.

   LIST #twilight_zone,#42         ; Command to list channels
                                   #twilight_zone and #42
 */
public class LIST extends Command{


    public void execute(Client c) throws CommandExecutionExcetion {

        if(this.parameters.size() > 1 ) {
            
        }else{
            // all
            List<Channel> l = MainThread.getChannels();
            for(Channel ch: l){
                c.sendToClient("332 " + ch.getName() + " :" + ch.getTopic());
            }
        }
    }


    //================================================================================================================
    // COMMON
    //================================================================================================================
     protected LIST() {
        super();
    }

    protected LIST(String message){
        super(message);
    }

    protected Command getInstance(String message){
        return new LIST(message);
    }
}
