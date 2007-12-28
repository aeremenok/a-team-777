/**
 * Librarian_comp_PortType.java This file was auto-generated from WSDL by the
 * Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.librarian_comp;

public interface Librarian_comp_PortType
    extends
        java.rmi.Remote
{
    public java.lang.String getShelfByName(
        java.lang.String in )
        throws java.rmi.RemoteException;

    public java.lang.String getShelfByCipher(
        java.lang.String in )
        throws java.rmi.RemoteException;
}
