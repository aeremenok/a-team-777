package ru.spb.hmi.server;

import java.util.ArrayList;
import java.util.List;

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
    }

    @Override
    public String getDocContent(
        String id )
    {
        synchronized ( _syncronizer )
        {
            return _docHandler.getXML();
        }
    }

    @Override
    public List getDocList()
    {
        synchronized ( _syncronizer )
        {
            List res;
            try
            {
                res = DataBaseDoc.getDocumentsList();
            }
            catch ( Throwable e )
            {
                e.printStackTrace();
                res = new ArrayList();
            }
            return res;
        }
    }

    @Override
    public void prepareDoc(
        String id )
    {
        synchronized ( _syncronizer )
        {
            try
            {
                _docHandler = new DataBaseDoc( id );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getProperty(
        String path )
    {
        synchronized ( _syncronizer )
        {
            return _docHandler.getProperty( path );
        }
    }

    @Override
    public void setProperty(
        String parentType,
        String text )
    {
        synchronized ( _syncronizer )
        {
            _docHandler.setProperty( parentType, text );
        }
    }
}
