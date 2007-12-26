package ru.spb.hmi.client.popups;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WaitingPopup
    extends PopupPanel

{
    private static WaitingPopup instance = new WaitingPopup();
    private String              _message;

    public static void wait(
        String message )
    {
        instance.setMessage( message );
        RootPanel.get().add( instance );
        instance.show();
        instance.center();
    }

    public WaitingPopup()
    {
        super();
        setStyleName( "gwt-DisclosurePanel .header" );
        VerticalPanel panel = new VerticalPanel();
        panel.add( new Label( "PLEASE WAIT..." ) );
        panel.add( new Label( _message ) );
    }

    private void setMessage(
        String message )
    {
        _message = message;
    }

    public static void finish()
    {
        instance.hide();
        RootPanel.get().remove( instance );
    }
}
