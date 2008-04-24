package ru.spb.etu.client.ui.edit.entities;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;

public class ArtistEditPanel
    extends EntityEditPanel
{
    protected EntityWrapper createEntityWrapper()
    {
        return new Artist();
    }

    protected String getDefaultImageUrl()
    {
        return "images/artist.jpg";
    }

}
