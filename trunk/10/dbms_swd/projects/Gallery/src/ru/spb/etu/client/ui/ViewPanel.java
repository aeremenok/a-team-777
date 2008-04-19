package ru.spb.etu.client.ui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;

public class ViewPanel
    extends DockPanel
{
    private static ViewPanel instance = null;

    public static ViewPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new ViewPanel();
        }
        return instance;
    }

    public ViewPanel()
    {
        super();
        add( new Label( "view" ), CENTER );
    }
}
