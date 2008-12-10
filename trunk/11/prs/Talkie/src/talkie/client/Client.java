package talkie.client;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import talkie.client.listeners.ClientListener;
import talkie.client.listeners.UDPListener;
import talkie.client.speakers.ClientSpeaker;
import talkie.client.speakers.UDPSpeaker;
import talkie.client.ui.LoginDialog;
import talkie.common.constants.Message;
import talkie.common.ui.MyFrame;

public class Client
    extends MyFrame
{
    private ClientSpeaker  speaker     = null;
    private ClientListener listener    = null;
    private TextArea       textArea    = null;
    private TextArea       input       = null;
    private Button         btnSend     = null;
    private LoginDialog    loginDialog = null;
    {
        try
        {
            speaker = new UDPSpeaker( "localhost", 7777 );
            listener = new UDPListener( this );
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

        textArea = new TextArea( "", 20, 40, TextArea.SCROLLBARS_VERTICAL_ONLY );
        textArea.setFocusable( false );
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        add( textArea, c );

        input = new TextArea( "", 2, 50, TextArea.SCROLLBARS_NONE );
        input.setFocusTraversalKeysEnabled( true );
        c.gridwidth = 1;
        c.gridy = 1;
        add( input, c );

        btnSend = new Button( "Send" );
        btnSend.setFocusTraversalKeysEnabled( true );
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.weightx = 0.5;
        btnSend.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                String sent = input.getText().trim();
                input.setText( "" );
                if ( !"".equals( sent ) && !Message.LOGOUT.equals( sent ) )
                {
                    speaker.sendText( sent );
                }
            }
        } );
        add( btnSend, c );
    }

    @Override
    public void dispose()
    {
        onExit();
        super.dispose();
    }

    public ClientSpeaker getSpeaker()
    {
        return speaker;
    }

    public ClientListener getListener()
    {
        return listener;
    }

    public LoginDialog getLoginDialog()
    {
        return loginDialog;
    }

    public TextArea getTextArea()
    {
        return textArea;
    }

    public void onExit()
    {
        if ( speaker.isActive() )
        {
            speaker.sendText( Message.LOGOUT );
            speaker.close();
        }
        listener.interruptIfRunning();
        listener.close();
        System.exit( 0 );
    }

    public void setClientListener(
        UDPListener clientListener )
    {
        this.listener = clientListener;
    }
}
