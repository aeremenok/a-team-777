package org.example.www.irbis;

public class IrbisProxy implements org.example.www.irbis.Irbis_PortType {
  private String _endpoint = null;
  private org.example.www.irbis.Irbis_PortType irbis_PortType = null;
  
  public IrbisProxy() {
    _initIrbisProxy();
  }
  
  public IrbisProxy(String endpoint) {
    _endpoint = endpoint;
    _initIrbisProxy();
  }
  
  private void _initIrbisProxy() {
    try {
      irbis_PortType = (new org.example.www.irbis.Irbis_ServiceLocator()).getirbisSOAP();
      if (irbis_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)irbis_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)irbis_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (irbis_PortType != null)
      ((javax.xml.rpc.Stub)irbis_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.example.www.irbis.Irbis_PortType getIrbis_PortType() {
    if (irbis_PortType == null)
      _initIrbisProxy();
    return irbis_PortType;
  }
  
  public java.lang.String getCipherByName(java.lang.String in) throws java.rmi.RemoteException{
    if (irbis_PortType == null)
      _initIrbisProxy();
    return irbis_PortType.getCipherByName(in);
  }
  
  
}