package ru.spb.hmi.client.docs;

import ru.spb.hmi.client.Unloadable;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class DocumentPanel
    extends FlexTable
    implements
        Unloadable
{
    private RootPanel rootPanel;
    protected String  xmlContent;

    public DocumentPanel()
    {
        super();
    }

    public void onModuleLoad()
    {
        getRootPanel().add( this );
    }

    public void onModuleUnLoad()
    {
        getRootPanel().remove( this );
    }

    protected RootPanel getRootPanel()
    {
        if ( rootPanel == null )
        {
            rootPanel = RootPanel.get();
        }
        return rootPanel;
    }
}
