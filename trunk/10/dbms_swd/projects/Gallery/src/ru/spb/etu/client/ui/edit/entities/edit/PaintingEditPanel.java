package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class PaintingEditPanel
    extends MasterpieceEditPanel
{

    public PaintingEditPanel(
        Artist artist )
    {
        super( artist );
    }

    public String entityTypeName()
    {
        return "Painting";
    }

    public String getDefaultImageUrl()
    {
        return "images/painting.jpg";
    }

    public void retreiveEntities(
        AsyncCallback callback )
    {
        async.getPaintings( artist, callback );
    }

}
