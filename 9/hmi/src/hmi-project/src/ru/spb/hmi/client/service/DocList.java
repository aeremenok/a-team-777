package ru.spb.hmi.client.service;

import java.util.ArrayList;

import ru.spb.hmi.client.DOCService;
import ru.spb.hmi.client.DOCServiceAsync;
import ru.spb.hmi.client.Unloadable;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocList
    extends VerticalPanel
    implements
        Unloadable
{
    private DOCServiceAsync _service = DOCService.Util.getInstance();
    Tree                    _tree    = new Tree();

    public DocList()
    {
        super();
        add( _tree );
    }

    public void onModuleUnLoad()
    {
        retrieveDocs();
        RootPanel.get().add( this );
    }

    private void retrieveDocs()
    {
        _service.getDocList( new AsyncCallback()
        {
            public void onFailure(
                Throwable caught )
            {
                caught.printStackTrace();
            }

            public void onSuccess(
                Object result )
            {
                ArrayList res = (ArrayList) result;
                _tree.clear();
                for ( int i = 0; i < res.size(); i++ )
                {
                    _tree.addItem( (String) res.get( i ) );
                }
            }
        } );
    }

    public void onModuleLoad()
    {
        RootPanel.get().remove( this );
    }

}
