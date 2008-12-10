package talkie.client.listeners;

import talkie.client.Client;
import talkie.common.constants.Message;

public abstract class ClientListener
    implements
        Runnable
{
    private final Client client;
    protected String     serverName;
    protected boolean    isRunning = false;
    protected String     login;
    protected String     pass;

    public ClientListener(
        Client client )
    {
        this.client = client;
    }

    public boolean attemptToLogin()
    {
        getClient().getLoginDialog().lock();

        boolean result = establishConnection();

        if ( result )
        {
            getClient().getLoginDialog().unlock();
            getClient().getLoginDialog().setVisible( false );
            getClient().setTitle( getLogin() );
            getClient().display();
            return true;
        }
        else
        {
            getClient().getLoginDialog().unlock();
            getClient().getLoginDialog().getFailedLabel().setVisible( true );
            return false;
        }
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

    public void interruptIfRunning()
    {
        if ( isRunning )
        {
            Thread.currentThread().interrupt();
        }
    }

    public void setLoginAndPass(
        String login,
        String pass )
    {
        this.login = login;
        this.pass = pass;
    }

    public void setServerName(
        String text )
    {
        this.serverName = serverName;
    }

    protected void processMsg(
        String msg )
    {
        if ( msg.startsWith( Message.LOGOUT ) )
        {
            getClient().setVisible( false );
            getClient().getTextArea().setText( "" );
            getClient().getLoginDialog().setVisible( true );
        }
        else
        {
            getClient().getTextArea().append( msg + "\n" );
            getClient().getTextArea().setCaretPosition( getClient().getTextArea().getText().length() );
        }
    }
}
