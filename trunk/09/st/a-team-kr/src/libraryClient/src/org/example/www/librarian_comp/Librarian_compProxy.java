package org.example.www.librarian_comp;

public class Librarian_compProxy implements org.example.www.librarian_comp.Librarian_comp_PortType {
  private String _endpoint = null;
  private org.example.www.librarian_comp.Librarian_comp_PortType librarian_comp_PortType = null;
  
  public Librarian_compProxy() {
    _initLibrarian_compProxy();
  }
  
  public Librarian_compProxy(String endpoint) {
    _endpoint = endpoint;
    _initLibrarian_compProxy();
  }
  
  private void _initLibrarian_compProxy() {
    try {
      librarian_comp_PortType = (new org.example.www.librarian_comp.Librarian_comp_ServiceLocator()).getlibrarian_compSOAP();
      if (librarian_comp_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)librarian_comp_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)librarian_comp_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (librarian_comp_PortType != null)
      ((javax.xml.rpc.Stub)librarian_comp_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.example.www.librarian_comp.Librarian_comp_PortType getLibrarian_comp_PortType() {
    if (librarian_comp_PortType == null)
      _initLibrarian_compProxy();
    return librarian_comp_PortType;
  }
  
  public java.lang.String getShelfByName(java.lang.String in) throws java.rmi.RemoteException{
    if (librarian_comp_PortType == null)
      _initLibrarian_compProxy();
    return librarian_comp_PortType.getShelfByName(in);
  }
  
  public java.lang.String getShelfByCipher(java.lang.String in) throws java.rmi.RemoteException{
    if (librarian_comp_PortType == null)
      _initLibrarian_compProxy();
    return librarian_comp_PortType.getShelfByCipher(in);
  }
  
  
}