package talkie.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextArea;

import talkie.client.connect.Connection;
import talkie.client.connect.UDPConnection;
import talkie.client.ui.LoginDialog;
import talkie.common.ui.MyFrame;

public class Client
    extends MyFrame
{
    private Connection connection  = null;
    private JTextArea  textArea    = null;
    private JTextArea  contactList = null;
    private JTextArea  input       = null;
    private JButton    btnSend     = null;

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
        setLayout( new GridBagLayout() );
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        contactList = new JTextArea( "contact list", 10, 10 );
        add( contactList, c );

        textArea = new JTextArea( "enter text", 15, 40 );
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 1;
        add( textArea, c );

        input = new JTextArea( "input some text", 3, 30 );
        c.gridwidth = 1;
        c.gridy = 1;
        add( input, c );

        btnSend = new JButton( "Send" );
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.weightx = 0.5;
        btnSend.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                send( textArea.getText() );
            }
        } );
        add( btnSend, c );
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
