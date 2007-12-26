package ru.spb.hmi.client;

import ru.spb.hmi.client.popups.AboutDialog;
import ru.spb.hmi.client.service.DocList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main
    implements
        EntryPoint
{
    VerticalPanel _mainPanel = new VerticalPanel();

    public void onModuleLoad()
    {
        RootPanel rootPanel = RootPanel.get();
        rootPanel.setSize( "100%", "100%" );
        rootPanel.setTitle( "DocRedactor" );
        _mainPanel.setSize( "100%", "100%" );
        rootPanel.add( _mainPanel );

        {
            final MenuBar menuBar = new MenuBar();

            _mainPanel.add( menuBar );
            menuBar.setWidth( "100%" );

            final MenuBar menuBar_1 = new MenuBar( true );

            menuBar_1.addItem( "Get List", new Command()
            {
                DocList docList = new DocList( _mainPanel );

                public void execute()
                {
                    System.out.println( "loading doc list" );

                    docList.onModuleLoad();
                }
            } );

            {
                menuBar_1.addItem( "Load", (Command) null );
            }

            {
                menuBar_1.addItem( "Save", (Command) null );
            }

            {
                menuBar_1.addItem( "<hr>", true, (Command) null );
            }

            {
                menuBar_1.addItem( "PrintPreview", (Command) null );
            }

            {
                menuBar.addItem( "Document", menuBar_1 );
            }

            final MenuBar menuBar_2 = new MenuBar( true );

            {
                menuBar_2.addItem( "Copy", (Command) null );
            }

            {
                menuBar_2.addItem( "Paste", (Command) null );
            }

            {
                menuBar.addItem( "Edit", menuBar_2 );
            }

            final MenuBar menuBar_3 = new MenuBar( true );

            {
                menuBar.addItem( "View", menuBar_3 );
            }

            final MenuBar menuBar_4 = new MenuBar( true );

            {
                menuBar_4.addItem( "Index", (Command) null );
            }

            {
                menuBar_4.addItem( "About", new Command()
                {
                    public void execute()
                    {
                        AboutDialog.getInstance().onModuleLoad();
                    }
                } );
            }

            {
                menuBar.addItem( "Help", menuBar_4 );
            }
        }
    }
}
