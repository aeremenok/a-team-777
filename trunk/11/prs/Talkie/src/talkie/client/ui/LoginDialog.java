package talkie.client.ui;

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

import talkie.client.Client;
import talkie.client.connect.Connection;
import talkie.client.process.ClientListener;
import talkie.common.ui.MyDialog;
import talkie.common.ui.SelectableTextField;

public class LoginDialog
    extends MyDialog
{
    private JLabel               lblFailed;
    private ArrayList<Component> toLock = new ArrayList<Component>();
    private ClientListener       clientListener;

    public LoginDialog(
        final Client owner )
    {
        super( owner );

        clientListener = new ClientListener( owner );

        initInterface( owner, clientListener );
    }

    private void initInterface(
        final Client owner,
        final ClientListener clientListener )
    {
        setLayout( new GridLayout( 4, 1 ) );

        final JTextField tbLogin = new SelectableTextField( 10 );
        add( tbLogin );
        toLock.add( tbLogin );

        final JTextField tbPass = new SelectableTextField( 10 );
        add( tbPass );
        toLock.add( tbPass );

        lblFailed = new JLabel( "јутентификаци€ не удалась, проверьте логин и пароль", SwingConstants.CENTER );
        lblFailed.setForeground( Color.RED );
        lblFailed.setVisible( false );
        add( lblFailed );

        JButton btnLogin = new JButton( "¬ход" );
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

                // todo поток-слушатель

                if ( success )
                {
                    new Thread( clientListener ).start();
                    unlock();
                    setVisible( false );
                    owner.setTitle( login );
                    owner.display();
                }
                else
                {
                    unlock();
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
