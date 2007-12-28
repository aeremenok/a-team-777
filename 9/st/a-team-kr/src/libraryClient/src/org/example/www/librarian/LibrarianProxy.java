package org.example.www.librarian;

public class LibrarianProxy implements org.example.www.librarian.Librarian_PortType {
  private String _endpoint = null;
  private org.example.www.librarian.Librarian_PortType librarian_PortType = null;
  
  public LibrarianProxy() {
    _initLibrarianProxy();
  }
  
  public LibrarianProxy(String endpoint) {
    _endpoint = endpoint;
    _initLibrarianProxy();
  }
  
  private void _initLibrarianProxy() {
    try {
      librarian_PortType = (new org.example.www.librarian.Librarian_ServiceLocator()).getlibrarianSOAP();
      if (librarian_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)librarian_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)librarian_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (librarian_PortType != null)
      ((javax.xml.rpc.Stub)librarian_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.example.www.librarian.Librarian_PortType getLibrarian_PortType() {
    if (librarian_PortType == null)
      _initLibrarianProxy();
    return librarian_PortType;
  }
  
  public java.lang.String getBookByName(java.lang.String in) throws java.rmi.RemoteException{
    if (librarian_PortType == null)
      _initLibrarianProxy();
    return librarian_PortType.getBookByName(in);
  }
  
  public java.lang.String getBookByCipher(java.lang.String in) throws java.rmi.RemoteException{
    if (librarian_PortType == null)
      _initLibrarianProxy();
    return librarian_PortType.getBookByCipher(in);
  }
  
  
}