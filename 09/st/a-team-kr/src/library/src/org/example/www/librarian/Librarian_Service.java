/**
 * Librarian_Service.java This file was auto-generated from WSDL by the Apache
 * Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.librarian;

public interface Librarian_Service
    extends
        javax.xml.rpc.Service
{
    public java.lang.String getlibrarianSOAPAddress();

    public org.example.www.librarian.Librarian_PortType getlibrarianSOAP()
        throws javax.xml.rpc.ServiceException;

    public org.example.www.librarian.Librarian_PortType getlibrarianSOAP(
        java.net.URL portAddress )
        throws javax.xml.rpc.ServiceException;
}
