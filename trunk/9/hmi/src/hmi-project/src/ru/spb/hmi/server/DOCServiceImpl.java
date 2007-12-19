package ru.spb.hmi.server;

import ru.spb.hmi.client.DOCService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DOCServiceImpl
    extends RemoteServiceServlet
    implements
        DOCService
{
    public String getString()
    {
        return "777";
    }
}
