package talkie.ui.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import talkie.Client;
import talkie.connect.Connection;
import talkie.ui.MyDialog;
import talkie.ui.widgets.SelectableTextField;

public class LoginDialog
    extends MyDialog
{
    private JLabel               lblFailed;
    private ArrayList<Component> toLock = new ArrayList<Component>();

    public LoginDialog(
        final Client owner )
    {
        super( owner );
        setLayout( new GridLayout( 4, 1 ) );

        final JTextField tbLogin = new SelectableTextField( 10 );
        add( tbLogin );
        toLock.add( tbLogin );

        final JTextField tbPass = new SelectableTextField( 10 );
        // todo tbPass.setEchoChar( '*' );
        add( tbPass );
        toLock.add( tbPass );

        lblFailed = new JLabel( "Аутентификация не удалась, проверьте логин и пароль", SwingConstants.CENTER );
        lblFailed.setForeground( Color.RED );
        lblFailed.setVisible( false );
        add( lblFailed );

        JButton btnLogin = new JButton( "Вход" );
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
                    owner.setTitle( login );
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
