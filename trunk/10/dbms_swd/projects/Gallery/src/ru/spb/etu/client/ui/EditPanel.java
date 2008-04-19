package ru.spb.etu.client.ui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;

public class EditPanel
    extends DockPanel
{
    private static EditPanel instance = null;

    public static EditPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new EditPanel();
        }
        return instance;
    }

    public EditPanel()
    {
        super();
        add( new Label( "edit" ), CENTER );
    }
}
