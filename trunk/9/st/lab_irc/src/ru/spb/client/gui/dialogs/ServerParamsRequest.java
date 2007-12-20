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
    protected JTextArea _serverHost = new JTextArea( "localhost" );
    protected JTextArea _serverPort = new JTextArea( "6667" );

    private ServerParamsRequest()
    {
        super( "Input server parameters" );
        _contentPane.add( _serverName );
        _contentPane.add( _serverHost );
        _contentPane.add( _serverPort );
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
        _server.setHost( _serverHost.getText() );
        _server.setPort( Integer.parseInt( _serverPort.getText() ) );
    }

    public Server getServer()
    {
        return _server;
    }

}
