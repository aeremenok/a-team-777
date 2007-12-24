/**
 * PetrovichSOAPSkeleton.java This file was auto-generated from WSDL by the
 * Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.petrovich;

public class PetrovichSOAPSkeleton
    implements
        org.example.www.petrovich.Petrovich_PortType,
        org.apache.axis.wsdl.Skeleton
{
    private org.example.www.petrovich.Petrovich_PortType impl;
    private static java.util.Map                         _myOperations     = new java.util.Hashtable();
    private static java.util.Collection                  _myOperationsList = new java.util.ArrayList();

    /**
     * Returns List of OperationDesc objects with this name
     */
    public static java.util.List getOperationDescByName(
        java.lang.String methodName )
    {
        return (java.util.List) _myOperations.get( methodName );
    }

    /**
     * Returns Collection of OperationDescs
     */
    public static java.util.Collection getOperationDescs()
    {
        return _myOperationsList;
    }

    static
    {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc[] _params;
        _params =
                  new org.apache.axis.description.ParameterDesc[] { new org.apache.axis.description.ParameterDesc(
                                                                                                                   new javax.xml.namespace.QName(
                                                                                                                                                  "",
                                                                                                                                                  "in" ),
                                                                                                                   org.apache.axis.description.ParameterDesc.IN,
                                                                                                                   new javax.xml.namespace.QName(
                                                                                                                                                  "http://www.w3.org/2001/XMLSchema",
                                                                                                                                                  "string" ),
                                                                                                                   java.lang.String.class,
                                                                                                                   false,
                                                                                                                   false ), };
        _oper =
                new org.apache.axis.description.OperationDesc( "doSomething", _params,
                                                               new javax.xml.namespace.QName( "", "out" ) );
        _oper.setReturnType( new javax.xml.namespace.QName( "http://www.w3.org/2001/XMLSchema", "string" ) );
        _oper.setElementQName( new javax.xml.namespace.QName( "http://www.example.org/petrovich/", "doSomething" ) );
        _oper.setSoapAction( "http://www.example.org/petrovich/doSomething" );
        _myOperationsList.add( _oper );
        if ( _myOperations.get( "doSomething" ) == null )
        {
            _myOperations.put( "doSomething", new java.util.ArrayList() );
        }
        ((java.util.List) _myOperations.get( "doSomething" )).add( _oper );
        _params =
                  new org.apache.axis.description.ParameterDesc[] { new org.apache.axis.description.ParameterDesc(
                                                                                                                   new javax.xml.namespace.QName(
                                                                                                                                                  "",
                                                                                                                                                  "in" ),
                                                                                                                   org.apache.axis.description.ParameterDesc.IN,
                                                                                                                   new javax.xml.namespace.QName(
                                                                                                                                                  "http://www.w3.org/2001/XMLSchema",
                                                                                                                                                  "string" ),
                                                                                                                   java.lang.String.class,
                                                                                                                   false,
                                                                                                                   false ), };
        _oper =
                new org.apache.axis.description.OperationDesc( "punish", _params, new javax.xml.namespace.QName( "",
                                                                                                                 "out" ) );
        _oper.setReturnType( new javax.xml.namespace.QName( "http://www.w3.org/2001/XMLSchema", "string" ) );
        _oper.setElementQName( new javax.xml.namespace.QName( "http://www.example.org/petrovich/", "punish" ) );
        _oper.setSoapAction( "http://www.example.org/petrovich/punish" );
        _myOperationsList.add( _oper );
        if ( _myOperations.get( "punish" ) == null )
        {
            _myOperations.put( "punish", new java.util.ArrayList() );
        }
        ((java.util.List) _myOperations.get( "punish" )).add( _oper );
        _params =
                  new org.apache.axis.description.ParameterDesc[] { new org.apache.axis.description.ParameterDesc(
                                                                                                                   new javax.xml.namespace.QName(
                                                                                                                                                  "",
                                                                                                                                                  "in" ),
                                                                                                                   org.apache.axis.description.ParameterDesc.IN,
                                                                                                                   new javax.xml.namespace.QName(
                                                                                                                                                  "http://www.w3.org/2001/XMLSchema",
                                                                                                                                                  "string" ),
                                                                                                                   java.lang.String.class,
                                                                                                                   false,
                                                                                                                   false ), };
        _oper =
                new org.apache.axis.description.OperationDesc( "reward", _params, new javax.xml.namespace.QName( "",
                                                                                                                 "out" ) );
        _oper.setReturnType( new javax.xml.namespace.QName( "http://www.w3.org/2001/XMLSchema", "string" ) );
        _oper.setElementQName( new javax.xml.namespace.QName( "http://www.example.org/petrovich/", "reward" ) );
        _oper.setSoapAction( "http://www.example.org/petrovich/reward" );
        _myOperationsList.add( _oper );
        if ( _myOperations.get( "reward" ) == null )
        {
            _myOperations.put( "reward", new java.util.ArrayList() );
        }
        ((java.util.List) _myOperations.get( "reward" )).add( _oper );
    }

    public PetrovichSOAPSkeleton()
    {
        this.impl = new org.example.www.petrovich.PetrovichSOAPImpl();
    }

    public PetrovichSOAPSkeleton(
        org.example.www.petrovich.Petrovich_PortType impl )
    {
        this.impl = impl;
    }

    public java.lang.String doSomething(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.doSomething( in );
        return ret;
    }

    public java.lang.String punish(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.punish( in );
        return ret;
    }

    public java.lang.String reward(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.reward( in );
        return ret;
    }

}
