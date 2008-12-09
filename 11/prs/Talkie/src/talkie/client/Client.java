package talkie.client;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import talkie.client.connect.Connection;
import talkie.client.connect.UDPConnection;
import talkie.client.process.ClientListener;
import talkie.client.ui.LoginDialog;
import talkie.common.ui.MyFrame;

public class Client
    extends MyFrame
{
    private Connection     connection     = null;
    private TextArea       textArea       = null;
    private TextArea       input          = null;
    private Button         btnSend        = null;
    private ClientListener clientListener = new ClientListener( this );
    private LoginDialog    loginDialog    = null;
    {
        try
        {
            connection = new UDPConnection( "localhost", 7777 );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(
        String[] args )
    {
        Client client = new Client();
        client.loginDialog = new LoginDialog( client );
        client.loginDialog.display();
    }

    public Client()
        throws HeadlessException
    {
        super();
        setSize( 500, 400 );
        setLayout( new GridBagLayout() );
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        textArea = new TextArea( "", 20, 40 );
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        add( textArea, c );

        input = new TextArea( "", 2, 50 );
        c.gridwidth = 1;
        c.gridy = 1;
        add( input, c );

        btnSend = new Button( "Send" );
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.weightx = 0.5;
        btnSend.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                connection.sendText( input.getText() );
            }
        } );
        add( btnSend, c );
    }

    public ClientListener getClientListener()
    {
        return clientListener;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public LoginDialog getLoginDialog()
    {
        return loginDialog;
    }

    public TextArea getTextArea()
    {
        return textArea;
    }

    public void setClientListener(
        ClientListener clientListener )
    {
        this.clientListener = clientListener;
    }
}
