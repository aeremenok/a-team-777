package ru.spb.etu.client.ui.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
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
        final QueryBuilder queryBuilder )
    {
        add( new Label( "Choose filter" ), NORTH );
        Button artists = new Button( "Artists" );
        add( artists, WEST );
        Button genres = new Button( "Genres" );
        add( genres, CENTER );
        Button museums = new Button( "Museums" );
        add( museums, EAST );

        artists.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                queryBuilder.queryArtists();
            }
        } );

        genres.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                queryBuilder.queryGenres();
            }
        } );

        museums.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                queryBuilder.queryMuseums();
            }
        } );
    }

}
