package ru.spb.etu.client;

import ru.spb.etu.client.ui.edit.EditPanel;
import ru.spb.etu.client.ui.view.ViewPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class Gallery
    implements
        EntryPoint
{
    private static ScrollPanel panel   = new ScrollPanel();
    private static MenuBar     menuBar = null;

    public void onModuleLoad()
    {
        Window.enableScrolling( false );

        RootPanel.get().add( getMenu() );

        panel.setSize( "100%", "98%" );
        RootPanel.get().add( panel );
    }

    private Widget getMenu()
    {
        if ( menuBar == null )
        {
            menuBar = new MenuBar();
            menuBar.addStyleName( "gwt-MenuBar" );
            MenuItem edit = new MenuItem( "Edit", new Command()
            {
                public void execute()
                {
                    setPanel( EditPanel.getInstance() );
                }
            } );
            menuBar.addItem( edit );

            MenuItem view = new MenuItem( "View", new Command()
            {
                public void execute()
                {
                    setPanel( ViewPanel.getInstance() );
                }
            } );
            menuBar.addItem( view );

            MenuItem close = new MenuItem( "Close", new Command()
            {
                public void execute()
                {
                    closeWindow();
                }
            } );
            menuBar.addItem( close );
        }

        return menuBar;
    }

    public static Panel getPanel()
    {
        return (Panel) panel.getWidget();
    }

    public static void setPanel(
        Panel panel )
    {
        Gallery.panel.setWidget( panel );
    }

    // в firefox'e не работает %)
    public native void closeWindow()
    /*-{
          $wnd.close();
    }-*/;
}
