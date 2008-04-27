package ru.spb.etu.client.ui.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.ui.edit.entities.edit.ArtistEditPanel;
import ru.spb.etu.client.ui.edit.entities.edit.GenreEditPanel;
import ru.spb.etu.client.ui.edit.entities.edit.MasterpieceEditPanel;
import ru.spb.etu.client.ui.edit.entities.edit.MuseumEditPanel;
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
        setWidget( new GenreEditPanel() );
    }

    public void processMuseums()
    {
        setWidget( new MuseumEditPanel() );
    }

    public void processPaintings(
        Artist artist )
    {
        setWidget( new MasterpieceEditPanel( artist ) );
    }

    public void processSculpture(
        Artist artitst )
    {
        setWidget( new MasterpieceEditPanel( artitst ) );
    }

    public static EditPanel reset()
    {
        getInstance().setWidget( getInstance().choiceMenu );
        return getInstance();
    }
}
