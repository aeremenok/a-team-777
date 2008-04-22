package ru.spb.etu.client.ui.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;

public class ArtistEditPanel
    extends EntityEditPanel
{
    protected EntityWrapper createEntityWrapper()
    {
        Artist artist = new Artist();
        artist.setImageUrl( getDefaultImageUrl() );
        return artist;
    }

    protected String getDefaultImageUrl()
    {
        return "images/artist.jpg";
    }

}
