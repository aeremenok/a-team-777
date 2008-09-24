/**
 * Irbis_Service.java This file was auto-generated from WSDL by the Apache Axis
 * 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.irbis;

public interface Irbis_Service
    extends
        javax.xml.rpc.Service
{
    public java.lang.String getirbisSOAPAddress();

    public org.example.www.irbis.Irbis_PortType getirbisSOAP()
        throws javax.xml.rpc.ServiceException;

    public org.example.www.irbis.Irbis_PortType getirbisSOAP(
        java.net.URL portAddress )
        throws javax.xml.rpc.ServiceException;
}
