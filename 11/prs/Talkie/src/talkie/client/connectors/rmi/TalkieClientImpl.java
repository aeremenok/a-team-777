package talkie.client.connectors.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import talkie.client.connectors.MyRMIConnector;

public class TalkieClientImpl
    extends UnicastRemoteObject
    implements
        TalkieClient,
        Serializable
{
    private String               login;
    private String               pass;
    private final MyRMIConnector connector;

    public TalkieClientImpl(
        MyRMIConnector connector )
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
