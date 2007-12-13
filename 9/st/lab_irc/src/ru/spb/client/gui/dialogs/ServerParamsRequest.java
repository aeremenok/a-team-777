package ru.spb.client.gui.dialogs;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import ru.spb.client.entities.Server;

/**
 * запрашивает параметры вновь создаваемого сервера
 * 
 * @author eav
 */
public class ServerParamsRequest
    extends ParamsRequest
{
    private Server      _server     = null;
    protected JTextArea _serverName = new JTextArea( "name" );

    private ServerParamsRequest(
        JFrame frame )
    {
        super( frame, "Input server parameters" );
        _contentPane.add( _serverName );
        pack();
    }

    public static Server getServer(
        JFrame frame )
    {
        ServerParamsRequest dialog = new ServerParamsRequest( frame );
        showOnCenter( dialog );

        return dialog.getServer();
    }

    /**
     * считать введенные параметры
     */
    protected void readParams()
    {
        _server = new Server( _serverName.getText() );
    }

    public Server getServer()
    {
        return _server;
    }

}
