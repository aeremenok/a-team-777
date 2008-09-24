package ru.spb.epa.commands;

import ru.spb.epa.Client;
import ru.spb.epa.exceptions.CommandExecutionException;

/**
 * User: ����� Date: 09.12.2007 Time: 20:32:52
 */
public class QUIT
    extends Command
{
    public void execute(
        Client c )
        throws CommandExecutionException
    {
        c.quit();
    }

    // ================================================================================================================
    // COMMON
    // ================================================================================================================

    protected QUIT()
    {
        super();
    }

    protected QUIT(
        String message )
    {
        super( message );
    }

    protected Command getInstance(
        String message )
    {
        return new QUIT( message );
    }

}
