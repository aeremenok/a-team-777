package ru.spb.etu.client.ui.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * меню выбора критерия
 * 
 * @author eav
 */
public class ChoiceMenu
    extends DockPanel
{

    public ChoiceMenu(
        final EntityProcessor entityProcessor )
    {
        setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

        add( new Label( "Choose filter" ), DockPanel.NORTH );
        Button artists = new Button( "Artists" );
        add( artists, DockPanel.WEST );
        Button genres = new Button( "Genres" );
        add( genres, DockPanel.CENTER );
        Button museums = new Button( "Museums" );
        add( museums, DockPanel.EAST );

        artists.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                entityProcessor.processArtists();
            }
        } );

        genres.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                entityProcessor.processGenres();
            }
        } );

        museums.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                entityProcessor.processMuseums();
            }
        } );
    }

}
