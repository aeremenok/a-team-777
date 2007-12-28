/**
 * IrbisSOAPSkeleton.java This file was auto-generated from WSDL by the Apache
 * Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.irbis;

public class IrbisSOAPSkeleton
    implements
        org.example.www.irbis.Irbis_PortType,
        org.apache.axis.wsdl.Skeleton
{
    private org.example.www.irbis.Irbis_PortType impl;
    private static java.util.Map                 _myOperations     = new java.util.Hashtable();
    private static java.util.Collection          _myOperationsList = new java.util.ArrayList();

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
                new org.apache.axis.description.OperationDesc( "getCipherByName", _params,
                                                               new javax.xml.namespace.QName( "", "out" ) );
        _oper.setReturnType( new javax.xml.namespace.QName( "http://www.w3.org/2001/XMLSchema", "string" ) );
        _oper.setElementQName( new javax.xml.namespace.QName( "http://www.example.org/irbis/", "getCipherByName" ) );
        _oper.setSoapAction( "http://www.example.org/irbis/NewOperation" );
        _myOperationsList.add( _oper );
        if ( _myOperations.get( "getCipherByName" ) == null )
        {
            _myOperations.put( "getCipherByName", new java.util.ArrayList() );
        }
        ((java.util.List) _myOperations.get( "getCipherByName" )).add( _oper );
    }

    public IrbisSOAPSkeleton()
    {
        this.impl = new org.example.www.irbis.IrbisSOAPImpl();
    }

    public IrbisSOAPSkeleton(
        org.example.www.irbis.Irbis_PortType impl )
    {
        this.impl = impl;
    }

    public java.lang.String getCipherByName(
        java.lang.String in )
        throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCipherByName( in );
        return ret;
    }

}
