package ru.spb.hmi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main
    implements
        EntryPoint
{
    private Button          clickMeButton;

    private DOCServiceAsync _service = DOCService.Util.getInstance();

    public void onModuleLoad()
    {
        RootPanel rootPanel = RootPanel.get();

        clickMeButton = new Button();
        rootPanel.add( clickMeButton );
        clickMeButton.setText( "Click me!" );
        clickMeButton.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget sender )
            {
                _service.getString( new AsyncCallback()
                {
                    public void onFailure(
                        Throwable caught )
                    {
                    }

                    public void onSuccess(
                        Object result )
                    {
                        Window.alert( (String) result );
                    }
                } );
            }
        } );
    }
}
