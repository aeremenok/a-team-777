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
import talkie.common.ui.MyDialog;

public class LoginDialog
    extends MyDialog
{
    private Label                lblFailed;
    private ArrayList<Component> toLock = new ArrayList<Component>();
    private Client               owner;
    private TextField            tfServer;

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
                owner.getListener().setServerName( tfServer.getText() );
                owner.getListener().setLoginAndPass( tbLogin.getText(), tbPass.getText() );
                if ( owner.getListener().attemptToLogin() )
                {
                    new Thread( owner.getListener() ).start();
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
        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox udp = new Checkbox( "UDP", cbg, true );
        Checkbox tcp = new Checkbox( "TCP", cbg, false );
        Checkbox rmi = new Checkbox( "RMI", cbg, false );
        Checkbox corba = new Checkbox( "CORBA", cbg, false );
        p.add( udp );
        p.add( tcp );
        p.add( rmi );
        p.add( corba );

        rmi.setEnabled( false );
        corba.setEnabled( false );
    }
}
