/**
 * Librarian_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.librarian;

public interface Librarian_PortType extends java.rmi.Remote {
    public java.lang.String getBookByName(java.lang.String in) throws java.rmi.RemoteException;
    public java.lang.String getBookByCipher(java.lang.String in) throws java.rmi.RemoteException;
}
