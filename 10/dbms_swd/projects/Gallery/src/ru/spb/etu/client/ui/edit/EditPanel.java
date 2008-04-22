package ru.spb.etu.client.ui.edit;

import ru.spb.etu.client.ui.view.ChoiceMenu;
import ru.spb.etu.client.ui.view.EntityProcessor;

import com.google.gwt.user.client.ui.SimplePanel;

public class EditPanel
    extends SimplePanel
    implements
        EntityProcessor
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

    ChoiceMenu choiceMenu = new ChoiceMenu( this );

    public EditPanel()
    {
        super();
        setWidget( choiceMenu );
    }

    public void processArtists()
    {
        setWidget( new ArtistEditPanel() );
    }

    public void processGenres()
    {
        // TODO Auto-generated method stub

    }

    public void processMuseums()
    {
        // TODO Auto-generated method stub

    }

    public static EditPanel reset()
    {
        getInstance().setWidget( getInstance().choiceMenu );
        return getInstance();
    }
}
