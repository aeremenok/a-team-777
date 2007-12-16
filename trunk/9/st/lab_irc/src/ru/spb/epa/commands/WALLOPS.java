package ru.spb.epa.commands;

import ru.spb.epa.Channel;
import ru.spb.epa.Client;
import ru.spb.epa.MainThread;
import ru.spb.epa.exceptions.CommandExecutionException;

public class WALLOPS
    extends Command
{

    private String _message;

    public WALLOPS()
    {
        super();
    }

    public WALLOPS(
        String message )
    {
        super( message );
        _message = message;
    }

    @Override
    public void execute(
        Client c )
        throws CommandExecutionException
    {
        Channel selectedChannel = null;
        for ( Channel channel : MainThread.getChannels() )
        {
            if ( channel.getUsers().contains( c ) )
            {
                selectedChannel = channel;
                break;
            }
        }
        if ( selectedChannel != null )
        {
            for ( Client client : selectedChannel.getUsers() )
            {
                if ( !client.equals( c ) )
                {
                    client.sendToClient( "WALLOPS " + _message + " from " + c.getNickname() );
                }
            }
        }

    }

    @Override
    protected Command getInstance(
        String s )
    {
        return new WALLOPS( s );
    }

}
