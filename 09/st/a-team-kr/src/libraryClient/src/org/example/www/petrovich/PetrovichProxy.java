package org.example.www.petrovich;

public class PetrovichProxy
    implements
        org.example.www.petrovich.Petrovich_PortType
{
    private String                                       _endpoint          = null;
    private org.example.www.petrovich.Petrovich_PortType petrovich_PortType = null;

    public PetrovichProxy()
    {
        _initPetrovichProxy();
    }

    public PetrovichProxy(
        String endpoint )
    {
        _endpoint = endpoint;
        _initPetrovichProxy();
    }

    private void _initPetrovichProxy()
    {
        try
        {
            petrovich_PortType = (new org.example.www.petrovich.Petrovich_ServiceLocator()).getpetrovichSOAP();
            if ( petrovich_PortType != null )
            {
                if ( _endpoint != null )
                    ((javax.xml.rpc.Stub) petrovich_PortType)._setProperty( "javax.xml.rpc.service.endpoint.address",
                                                                            _endpoint );
                else
                    _endpoint =
                                (String) ((javax.xml.rpc.Stub) petrovich_PortType)
                                                                                  ._getProperty( "javax.xml.rpc.service.endpoint.address" );
            }

        }
        catch ( javax.xml.rpc.ServiceException serviceException )
        {
        }
    }

    public String getEndpoint()
    {
        return _endpoint;
    }

    public void setEndpoint(
        String endpoint )
    {
        _endpoint = endpoint;
        if ( petrovich_PortType != null )
            ((javax.xml.rpc.Stub) petrovich_PortType)
                                                     ._setProperty( "javax.xml.rpc.service.endpoint.address", _endpoint );

    }

    public org.example.www.petrovich.Petrovich_PortType getPetrovich_PortType()
    {
        if ( petrovich_PortType == null )
            _initPetrovichProxy();
        return petrovich_PortType;
    }

    public java.lang.String doSomething(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        if ( petrovich_PortType == null )
            _initPetrovichProxy();
        return petrovich_PortType.doSomething( in );
    }

    public java.lang.String punish(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        if ( petrovich_PortType == null )
            _initPetrovichProxy();
        return petrovich_PortType.punish( in );
    }

    public java.lang.String reward(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        if ( petrovich_PortType == null )
            _initPetrovichProxy();
        return petrovich_PortType.reward( in );
    }

}