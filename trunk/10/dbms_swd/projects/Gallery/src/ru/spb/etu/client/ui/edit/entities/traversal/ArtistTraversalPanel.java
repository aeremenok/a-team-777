package ru.spb.etu.client.ui.edit.entities.traversal;

import ru.spb.etu.client.ui.edit.entities.edit.EntityEditPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ArtistTraversalPanel
    extends TraversalPanel
{

    public ArtistTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        super( entityEditPanel );
    }

    protected void retreiveEntities(
        AsyncCallback callback )
    {
        async.getArtists( callback );
    }

    protected String entityTypeName()
    {
        return "Artist";
    }
}
