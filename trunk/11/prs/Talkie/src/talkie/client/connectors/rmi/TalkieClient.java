package talkie.client.connectors.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TalkieClient
    extends
        Remote
{
    public void deliverMessage(
        String string )
        throws RemoteException;

    public String getLogin()
        throws RemoteException;

    public String getPass()
        throws RemoteException;
}
