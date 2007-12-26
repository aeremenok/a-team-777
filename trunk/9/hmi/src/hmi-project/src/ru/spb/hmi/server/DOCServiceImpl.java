package ru.spb.hmi.server;

import java.util.ArrayList;

import ru.spb.hmi.client.DOCService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DOCServiceImpl
    extends RemoteServiceServlet
    implements
        DOCService
{
    public String getContent(
        String id )
    {
        if ( id.equalsIgnoreCase( "1" ) )
        {
            return "777";
        }
        else
        {
            return "888";
        }
    }

    @Override
    public ArrayList getDocList()
    {
        ArrayList res = new ArrayList();
        res.add( "111" );
        res.add( "222" );
        res.add( "333" );
        return res;
    }
}
