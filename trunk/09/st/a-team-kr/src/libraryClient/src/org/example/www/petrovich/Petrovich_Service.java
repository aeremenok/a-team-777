/**
 * Petrovich_Service.java This file was auto-generated from WSDL by the Apache
 * Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.petrovich;

public interface Petrovich_Service
    extends
        javax.xml.rpc.Service
{
    public java.lang.String getpetrovichSOAPAddress();

    public org.example.www.petrovich.Petrovich_PortType getpetrovichSOAP()
        throws javax.xml.rpc.ServiceException;

    public org.example.www.petrovich.Petrovich_PortType getpetrovichSOAP(
        java.net.URL portAddress )
        throws javax.xml.rpc.ServiceException;
}
