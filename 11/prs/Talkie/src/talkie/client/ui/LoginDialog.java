package talkie.client.ui;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import talkie.client.Client;
import talkie.client.connectors.RMIConnector;
import talkie.client.connectors.TCPConnector;
import talkie.client.connectors.UDPConnector;
import talkie.common.ui.MyDialog;

public class LoginDialog
    extends MyDialog
{
    private Label                lblFailed;
    private ArrayList<Component> toLock = new ArrayList<Component>();
    private Client               owner;
    private TextField            tfServer;
    private CheckboxGroup        protocolsGroup;

    public LoginDialog(
        final Client owner )
    {
        super( owner );
        this.owner = owner;
        initInterface( owner );
    }

    @Override
    public void dispose()
    {
        owner.onExit();
        super.dispose();
    }

    public Label getFailedLabel()
    {
        return lblFailed;
    }

    public void lock()
    {
        for ( Component comp : toLock )
        {
            comp.setEnabled( false );
        }
    }

    public void unlock()
    {
        for ( Component comp : toLock )
        {
            comp.setEnabled( true );
        }
    }

    private void initInterface(
        final Client owner )
    {
        setLayout( new GridLayout( 8, 1 ) );

        final Label lblUser = new Label( "Данные пользователя" );
        add( lblUser );

        final TextField tbLogin = new TextField( 10 );
        add( tbLogin );
        tbLogin.setFocusTraversalKeysEnabled( true );
        toLock.add( tbLogin );

        final TextField tbPass = new TextField( 10 );
        tbPass.setEchoChar( '*' );
        tbPass.setFocusTraversalKeysEnabled( true );
        add( tbPass );
        toLock.add( tbPass );

        lblFailed = new Label( "Аутентификация не удалась, проверьте логин и пароль" );
        lblFailed.setForeground( Color.RED );
        lblFailed.setVisible( false );
        add( lblFailed );

        Button btnLogin = new Button( "Вход" );
        btnLogin.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                Checkbox protocol = protocolsGroup.getSelectedCheckbox();
                String label = protocol.getLabel();
                if ( "UDP".equalsIgnoreCase( label ) )
                {
                    owner.setConnector( new UDPConnector( owner ) );
                }
                else if ( "TCP".equalsIgnoreCase( label ) )
                {
                    owner.setConnector( new TCPConnector( owner ) );
                }
                else if ( "RMI".equalsIgnoreCase( label ) )
                {
                    owner.setConnector( new RMIConnector( owner ) );
                }
                else if ( "CORBA".equalsIgnoreCase( label ) )
                {
                    // owner.setConnector( new CORBAConnector( owner ) );
                }
                owner.getConnector().setServerName( tfServer.getText() );
                owner.getConnector().setLoginAndPass( tbLogin.getText(), tbPass.getText() );
                if ( owner.getConnector().attemptToLogin() )
                {
                    new Thread( owner.getConnector() ).start();
                }
            }
        } );
        add( btnLogin );
        toLock.add( btnLogin );

        final Label lblServer = new Label( "Имя сервера" );
        add( lblServer );

        tfServer = new TextField( 10 );
        tfServer.setText( "localhost" );
        tfServer.setFocusTraversalKeysEnabled( true );
        add( tfServer );
        toLock.add( tfServer );

        Panel p = new Panel();
        add( p );
        protocolsGroup = new CheckboxGroup();
        Checkbox udp = new Checkbox( "UDP", protocolsGroup, true );
        Checkbox tcp = new Checkbox( "TCP", protocolsGroup, false );
        Checkbox rmi = new Checkbox( "RMI", protocolsGroup, false );
        Checkbox corba = new Checkbox( "CORBA", protocolsGroup, false );
        p.add( udp );
        p.add( tcp );
        p.add( rmi );
        p.add( corba );

        corba.setEnabled( false );
    }
}
