package ru.spb.hmi.server;

import java.util.ArrayList;

import ru.spb.hmi.client.DOCService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DOCServiceImpl
    extends RemoteServiceServlet
    implements
        DOCService
{
    IDoc                        _docHandler;

    private static final Object _syncronizer = new Object();

    public DOCServiceImpl()
    {
        super();
        _docHandler = new DataBaseDoc();
    }

    public String getDocContent(
        String id )
    {
        if ( id.equalsIgnoreCase( "1" ) )
        {
            return "777";
        }
        return "888";
    }

    @Override
    public ArrayList getDocList()
    {
        synchronized ( _syncronizer )
        {
            // todo
            ArrayList res = // _docHandler.getDocList();
                            new ArrayList();
            res.add( "111" );
            res.add( "222" );
            res.add( "333" );
            return res;
        }
    }
}
