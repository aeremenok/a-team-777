package ru.spb.hmi.client.docs;

import ru.spb.hmi.client.DOCService;
import ru.spb.hmi.client.DOCServiceAsync;
import ru.spb.hmi.client.Unloadable;
import ru.spb.hmi.client.popups.WaitingPopup;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class DocumentPanel
    extends VerticalPanel
    implements
        Unloadable
{
    private RootPanel              rootPanel;
    protected String               xmlContent;

    private static DOCServiceAsync _service = DOCService.Util.getInstance();

    public DocumentPanel(
        String id )
    {
        super();
        WaitingPopup.wait( "preparing document" );
        // готовим док-т к работе
        _service.prepareDoc( id, new NotifyCallBack( "document prepared" ) );
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
