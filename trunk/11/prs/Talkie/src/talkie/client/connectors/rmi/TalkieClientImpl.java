package talkie.client.connectors.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import talkie.client.connectors.RMIConnector;

public class TalkieClientImpl
    extends UnicastRemoteObject
    implements
        TalkieClient
{
    private String             login;
    private String             pass;
    private final RMIConnector connector;

    public TalkieClientImpl(
        RMIConnector connector )
        throws RemoteException
    {
        super();
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
