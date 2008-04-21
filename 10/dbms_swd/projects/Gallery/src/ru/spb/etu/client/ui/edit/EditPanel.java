package ru.spb.etu.client.ui.edit;

import com.google.gwt.user.client.ui.DockPanel;

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
        add( FileUploadPanel.getInstance(), CENTER );
    }

}
