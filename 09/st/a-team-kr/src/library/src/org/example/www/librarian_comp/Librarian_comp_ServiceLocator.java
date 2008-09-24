/**
 * Librarian_comp_ServiceLocator.java This file was auto-generated from WSDL by
 * the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.librarian_comp;

public class Librarian_comp_ServiceLocator
    extends org.apache.axis.client.Service
    implements
        org.example.www.librarian_comp.Librarian_comp_Service
{

    public Librarian_comp_ServiceLocator()
    {
    }

    public Librarian_comp_ServiceLocator(
        org.apache.axis.EngineConfiguration config )
    {
        super( config );
    }

    public Librarian_comp_ServiceLocator(
        java.lang.String wsdlLoc,
        javax.xml.namespace.QName sName )
        throws javax.xml.rpc.ServiceException
    {
        super( wsdlLoc, sName );
    }

    // Use to get a proxy class for librarian_compSOAP
    private java.lang.String librarian_compSOAP_address = "http://www.example.org/";

    public java.lang.String getlibrarian_compSOAPAddress()
    {
        return librarian_compSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String librarian_compSOAPWSDDServiceName = "librarian_compSOAP";

    public java.lang.String getlibrarian_compSOAPWSDDServiceName()
    {
        return librarian_compSOAPWSDDServiceName;
    }

    public void setlibrarian_compSOAPWSDDServiceName(
        java.lang.String name )
    {
        librarian_compSOAPWSDDServiceName = name;
    }

    public org.example.www.librarian_comp.Librarian_comp_PortType getlibrarian_compSOAP()
        throws javax.xml.rpc.ServiceException
    {
        java.net.URL endpoint;
        try
        {
            endpoint = new java.net.URL( librarian_compSOAP_address );
        }
        catch ( java.net.MalformedURLException e )
        {
            throw new javax.xml.rpc.ServiceException( e );
        }
        return getlibrarian_compSOAP( endpoint );
    }

    public org.example.www.librarian_comp.Librarian_comp_PortType getlibrarian_compSOAP(
        java.net.URL portAddress )
        throws javax.xml.rpc.ServiceException
    {
        try
        {
            org.example.www.librarian_comp.Librarian_compSOAPStub _stub =
                                                                          new org.example.www.librarian_comp.Librarian_compSOAPStub(
                                                                                                                                     portAddress,
                                                                                                                                     this );
            _stub.setPortName( getlibrarian_compSOAPWSDDServiceName() );
            return _stub;
        }
        catch ( org.apache.axis.AxisFault e )
        {
            return null;
        }
    }

    public void setlibrarian_compSOAPEndpointAddress(
        java.lang.String address )
    {
        librarian_compSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(
        Class serviceEndpointInterface )
        throws javax.xml.rpc.ServiceException
    {
        try
        {
            if ( org.example.www.librarian_comp.Librarian_comp_PortType.class
                                                                             .isAssignableFrom( serviceEndpointInterface ) )
            {
                org.example.www.librarian_comp.Librarian_compSOAPStub _stub =
                                                                              new org.example.www.librarian_comp.Librarian_compSOAPStub(
                                                                                                                                         new java.net.URL(
                                                                                                                                                           librarian_compSOAP_address ),
                                                                                                                                         this );
                _stub.setPortName( getlibrarian_compSOAPWSDDServiceName() );
                return _stub;
            }
        }
        catch ( java.lang.Throwable t )
        {
            throw new javax.xml.rpc.ServiceException( t );
        }
        throw new javax.xml.rpc.ServiceException( "There is no stub implementation for the interface:  " +
                                                  (serviceEndpointInterface == null ? "null"
                                                      : serviceEndpointInterface.getName()) );
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(
        javax.xml.namespace.QName portName,
        Class serviceEndpointInterface )
        throws javax.xml.rpc.ServiceException
    {
        if ( portName == null )
        {
            return getPort( serviceEndpointInterface );
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ( "librarian_compSOAP".equals( inputPortName ) )
        {
            return getlibrarian_compSOAP();
        }
        else
        {
            java.rmi.Remote _stub = getPort( serviceEndpointInterface );
            ((org.apache.axis.client.Stub) _stub).setPortName( portName );
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName()
    {
        return new javax.xml.namespace.QName( "http://www.example.org/librarian_comp/", "librarian_comp" );
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts()
    {
        if ( ports == null )
        {
            ports = new java.util.HashSet();
            ports.add( new javax.xml.namespace.QName( "http://www.example.org/librarian_comp/", "librarian_compSOAP" ) );
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(
        java.lang.String portName,
        java.lang.String address )
        throws javax.xml.rpc.ServiceException
    {

        if ( "librarian_compSOAP".equals( portName ) )
        {
            setlibrarian_compSOAPEndpointAddress( address );
        }
        else
        { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException( " Cannot set Endpoint Address for Unknown Port" + portName );
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(
        javax.xml.namespace.QName portName,
        java.lang.String address )
        throws javax.xml.rpc.ServiceException
    {
        setEndpointAddress( portName.getLocalPart(), address );
    }

}
