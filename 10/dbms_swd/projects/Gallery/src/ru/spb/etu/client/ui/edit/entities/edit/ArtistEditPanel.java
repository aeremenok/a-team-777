package ru.spb.etu.client.ui.edit.entities.edit;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ArtistEditPanel
    extends EntityEditPanel
{
    public String getDefaultImageUrl()
    {
        return "images/artist.jpg";
    }

    public void retreiveEntities(
        AsyncCallback callback )
    {
        async.getArtists( callback );
    }

    public String entityTypeName()
    {
        return "Artist";
    }
}
