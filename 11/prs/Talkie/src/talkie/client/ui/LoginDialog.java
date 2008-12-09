package talkie.client.ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
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

    public LoginDialog(
        final Client owner )
    {
        super( owner );
        initInterface( owner );
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
        setLayout( new GridLayout( 4, 1 ) );

        final TextField tbLogin = new TextField( 10 );
        add( tbLogin );
        toLock.add( tbLogin );

        final TextField tbPass = new TextField( 10 );
        tbPass.setEchoChar( '*' );
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
                owner.getClientListener().setLoginAndPass( tbLogin.getText(), tbPass.getText() );
                if ( owner.getClientListener().attemptToLogin() )
                {
                    new Thread( owner.getClientListener() ).start();
                }
            }
        } );
        add( btnLogin );
        toLock.add( btnLogin );
    }
}
