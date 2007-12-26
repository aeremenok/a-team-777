package ru.spb.hmi.client.service;

import java.util.ArrayList;

import ru.spb.hmi.client.DOCService;
import ru.spb.hmi.client.DOCServiceAsync;
import ru.spb.hmi.client.Unloadable;
import ru.spb.hmi.client.docs.shiporder.ShipOrder;
import ru.spb.hmi.client.popups.WaitingPopup;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocList
    extends VerticalPanel
    implements
        Unloadable
{
    private DOCServiceAsync _service = DOCService.Util.getInstance();
    Tree                    _tree    = new Tree();
    private Panel           _mainPanel;

    public DocList(
        Panel panel )
    {
        super();
        _mainPanel = panel;
        add( _tree );
    }

    public void onModuleUnLoad()
    {
        getMainPanel().remove( this );
    }

    private void retrieveDocs()
    {
        System.out.println( "retrieving doc list" );

        WaitingPopup.wait( "retrieving doc list" );
        _service.getDocList( new AsyncCallback()
        {
            public void onFailure(
                Throwable caught )
            {
                caught.printStackTrace();
                WaitingPopup.finish();
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

                _tree.addTreeListener( new TreeListener()
                {
                    public void onTreeItemSelected(
                        TreeItem item )
                    {
                        // выбран элемент - грузим новый
                        ShipOrder shipOrder = new ShipOrder( item.getText() );
                        DocList.this.onModuleUnLoad();
                        shipOrder.onModuleLoad();
                    }

                    public void onTreeItemStateChanged(
                        TreeItem item )
                    {
                    }
                } );
                WaitingPopup.finish();
            }
        } );
    }

    public void onModuleLoad()
    {
        retrieveDocs();
        getMainPanel().add( this );
    }

    public Panel getMainPanel()
    {
        return _mainPanel;
    }

}
