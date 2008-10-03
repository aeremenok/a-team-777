package talkie;

import java.awt.Button;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import talkie.connect.Connection;
import talkie.connect.UDPConnection;
import talkie.ui.MyFrame;
import talkie.ui.dialogs.LoginDialog;

public class Client
    extends MyFrame
{
    private Connection connection = null;
    private TextArea   textArea   = null;

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
        new LoginDialog( client ).display();
    }

    public Client()
        throws HeadlessException
    {
        super();

        textArea = new TextArea( "enter text", 5, 40 );
        add( "Center", textArea );

        Button btnSend = new Button( "Send" );
        btnSend.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                send( textArea.getText() );
            }
        } );
        add( "South", btnSend );
    }

    public Connection getConnection()
    {
        return connection;
    }

    private void send(
        String text )
    {
        connection.splitAndSend( text );
    }
}
