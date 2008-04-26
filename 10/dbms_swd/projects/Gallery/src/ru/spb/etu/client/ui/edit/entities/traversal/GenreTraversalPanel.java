package ru.spb.etu.client.ui.edit.entities.traversal;

import ru.spb.etu.client.ui.edit.entities.edit.EntityEditPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class GenreTraversalPanel
    extends TraversalPanel
{

    public GenreTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        super( entityEditPanel );
    }

    protected void retreiveEntities(
        AsyncCallback callback )
    {
        async.getGenres( callback );
    }

    protected String entityTypeName()
    {
        return "Genre";
    }

}
