package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class GenreEditPanel
    extends EntityEditPanel
{
    public String getDefaultImageUrl()
    {
        return "images/genre.gif";
    }

    public void retreiveEntities(
        AsyncCallback callback )
    {
        async.getGenres( callback );
    }

    public String entityTypeName()
    {
        return "Genre";
    }

    public EntityWrapper getMasterpieceReference(
        MasterPiece masterpiece )
    {
        return masterpiece.getGenre();
    }
}
