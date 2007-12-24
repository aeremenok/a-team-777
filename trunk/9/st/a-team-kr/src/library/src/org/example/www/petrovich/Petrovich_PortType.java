/**
 * Petrovich_PortType.java This file was auto-generated from WSDL by the Apache
 * Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.petrovich;

public interface Petrovich_PortType
    extends
        java.rmi.Remote
{
    public java.lang.String doSomething(
        java.lang.String in )
        throws java.rmi.RemoteException;

    public java.lang.String punish(
        java.lang.String in )
        throws java.rmi.RemoteException;

    public java.lang.String reward(
        java.lang.String in )
        throws java.rmi.RemoteException;
}
