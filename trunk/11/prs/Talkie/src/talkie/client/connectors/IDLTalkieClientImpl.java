package talkie.client.connectors;

import talkie.common.corba._IDLTalkieClientImplBase;

public class IDLTalkieClientImpl
    extends _IDLTalkieClientImplBase
{
    private final CORBAConnector connector;

    public IDLTalkieClientImpl(
        CORBAConnector connector )
    {
        this.connector = connector;
    }

    public void deliverMessage(
        String message )
    {
        connector.process( message );
    }

    public String getLogin()
    {
        return connector.getLogin();
    }

    public String getPass()
    {
        return connector.getPass();
    }
}
