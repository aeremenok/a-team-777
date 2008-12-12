package talkie.client.connectors.rmi;

import java.rmi.RemoteException;

import talkie.client.connectors.RMIConnector;

public class TalkieClientImpl
    implements
        TalkieClient
{
    private String             login;
    private String             pass;
    private final RMIConnector connector;

    public TalkieClientImpl(
        RMIConnector connector )
    {
        this.connector = connector;
        this.login = connector.getLogin();
        this.pass = connector.getPass();
    }

    public void deliverMessage(
        String string )
        throws RemoteException
    {
        connector.process( string );
    }

    public String getLogin()
        throws RemoteException
    {
        return login;
    }

    public String getPass()
        throws RemoteException
    {
        return pass;
    }
}
