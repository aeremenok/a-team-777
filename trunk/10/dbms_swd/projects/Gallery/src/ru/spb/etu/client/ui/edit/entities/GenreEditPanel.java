package ru.spb.etu.client.ui.edit.entities;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Genre;

public class GenreEditPanel
    extends EntityEditPanel
{
    protected EntityWrapper createEntityWrapper()
    {
        return new Genre();
    }

    protected String getDefaultImageUrl()
    {
        return "images/genre.gif";
    }

}
