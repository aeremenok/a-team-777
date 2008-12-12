package talkie.client.connectors;

import talkie.client.Client;
import talkie.common.constants.Message;

public abstract class ClientConnector
    implements
        Runnable
{
    private final Client client;
    protected String     serverName;
    protected String     login;
    protected String     pass;
    protected boolean    valid = true;

    public ClientConnector(
        Client client )
    {
        this.client = client;
    }

    public boolean attemptToLogin()
    {
        getClient().getLoginDialog().lock();

        if ( establishConnection() )
        {
            valid = true;
            getClient().getLoginDialog().unlock();
            getClient().getLoginDialog().setVisible( false );
            getClient().setTitle( getLogin() );
            getClient().display();

        }
        else
        {
            valid = false;
            getClient().getLoginDialog().unlock();
            getClient().getLoginDialog().getFailedLabel().setVisible( true );
        }

        return valid;
    }

    public abstract void close();

    public abstract boolean establishConnection();

    public Client getClient()
    {
        return client;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPass()
    {
        return pass;
    }

    public abstract void logout();

    public abstract boolean needsStopping();

    public void process(
        String msg )
    {
        if ( msg.startsWith( Message.LOGOUT ) )
        {
            getClient().setVisible( false );
            getClient().getTextArea().setText( "" );
            getClient().getLoginDialog().setVisible( true );
            stop();
        }
        else
        {
            getClient().getTextArea().append( msg + "\n" );
            getClient().getTextArea().setCaretPosition( getClient().getTextArea().getText().length() );
        }
    }

    public abstract void send(
        String message );

    public void setLoginAndPass(
        String login,
        String pass )
    {
        this.login = login;
        this.pass = pass;
    }

    public void setServerName(
        String serverName )
    {
        this.serverName = serverName;
    }

    public final void stop()
    {
        valid = false;
        logout();
        close();
        if ( needsStopping() )
        {
            Thread.currentThread().interrupt();
        }
    }
}
