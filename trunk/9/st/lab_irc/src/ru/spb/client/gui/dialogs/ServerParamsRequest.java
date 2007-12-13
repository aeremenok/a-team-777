package ru.spb.client.gui.dialogs;

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

    private ServerParamsRequest()
    {
        super( "Input server parameters" );
        _contentPane.add( _serverName );
        pack();
    }

    public static Server getNewServer()
    {
        ServerParamsRequest dialog = new ServerParamsRequest();
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
