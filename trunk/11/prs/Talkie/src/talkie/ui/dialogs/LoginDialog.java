package talkie.ui.dialogs;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import talkie.Client;
import talkie.connect.Connection;
import talkie.ui.MyDialog;
import talkie.ui.widgets.SelectableTextField;

public class LoginDialog
    extends MyDialog
{
    private Label                lblFailed;
    private ArrayList<Component> toLock = new ArrayList<Component>();

    public LoginDialog(
        final Client owner )
    {
        super( owner );
        setLayout( new GridLayout( 4, 1 ) );

        final TextField tbLogin = new SelectableTextField( 10 );
        add( tbLogin );
        toLock.add( tbLogin );

        final TextField tbPass = new SelectableTextField( 10 );
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
                Connection connection = owner.getConnection();

                String login = tbLogin.getText().replaceAll( " ", "_" );
                String pass = tbPass.getText();

                lock();
                boolean success = connection.login( login, pass );
                unlock();

                if ( success )
                {
                    setVisible( false );
                    owner.display();
                }
                else
                {
                    LoginDialog.this.lblFailed.setVisible( true );
                    LoginDialog.this.display();
                }
            }
        } );
        add( btnLogin );
        toLock.add( btnLogin );
    }

    private void lock()
    {
        for ( Component comp : toLock )
        {
            comp.setEnabled( false );
        }
    }

    private void unlock()
    {
        for ( Component comp : toLock )
        {
            comp.setEnabled( true );
        }
    }
}
