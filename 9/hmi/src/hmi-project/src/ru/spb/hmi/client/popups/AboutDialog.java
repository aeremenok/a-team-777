package ru.spb.hmi.client.popups;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AboutDialog
    extends PopupPanel
    implements
        EntryPoint
{

    static AboutDialog  instance;

    static final String about = "eav, epa, 3351 (c) 2007";

    public AboutDialog()
    {
        super( true, true );
        VerticalPanel verticalPanel = new VerticalPanel();
        final Label label = new Label( about );
        verticalPanel.add( label );
        verticalPanel.setCellVerticalAlignment( label, HasVerticalAlignment.ALIGN_MIDDLE );
        verticalPanel.setCellHorizontalAlignment( label, HasHorizontalAlignment.ALIGN_CENTER );
        add( verticalPanel );
        verticalPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
        verticalPanel.setStyleName( "gwt-HorizontalSplitPanel" );

        final Button okButton = new Button();
        verticalPanel.add( okButton );
        verticalPanel.setCellVerticalAlignment( okButton, HasVerticalAlignment.ALIGN_MIDDLE );
        verticalPanel.setCellHorizontalAlignment( okButton, HasHorizontalAlignment.ALIGN_CENTER );
        okButton.setText( "OK" );

        okButton.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget sender )
            {
                hide();
            }
        } );

    }

    public void onModuleLoad()
    {
        RootPanel.get().add( instance );
        show();
        instance.center();
    }

    public static AboutDialog getInstance()
    {
        if ( instance == null )
        {
            instance = new AboutDialog();
        }
        return instance;
    }

}
