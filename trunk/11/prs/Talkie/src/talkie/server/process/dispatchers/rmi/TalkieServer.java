package talkie.server.process.dispatchers.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import talkie.client.connectors.rmi.TalkieClient;

public interface TalkieServer
    extends
        Remote
{
    boolean login(
        TalkieClient client )
        throws RemoteException;

    void logout(
        TalkieClient client )
        throws RemoteException;

    void postMessage(
        TalkieClient client,
        String message )
        throws RemoteException;

    void whoIsHere(
        TalkieClient client )
        throws RemoteException;
}
