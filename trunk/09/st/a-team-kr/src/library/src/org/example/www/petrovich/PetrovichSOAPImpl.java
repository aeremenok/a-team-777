/**
 * PetrovichSOAPImpl.java This file was auto-generated from WSDL by the Apache
 * Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.petrovich;

public class PetrovichSOAPImpl
    implements
        org.example.www.petrovich.Petrovich_PortType
{
    public java.lang.String doSomething(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        return "something done with " + in;
    }

    public java.lang.String punish(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        return "oh no! take " + in + " away from me!";
    }

    public java.lang.String reward(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        return "oh yes! please more " + in;
    }
}
