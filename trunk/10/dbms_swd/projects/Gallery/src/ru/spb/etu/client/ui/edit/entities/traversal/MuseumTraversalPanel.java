package ru.spb.etu.client.ui.edit.entities.traversal;

import ru.spb.etu.client.ui.edit.entities.edit.EntityEditPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MuseumTraversalPanel
    extends TraversalPanel
{
    public MuseumTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        super( entityEditPanel );
    }

    protected void retreiveEntities(
        AsyncCallback callback )
    {
        async.getMuseums( callback );
    }

    protected String entityTypeName()
    {
        return "Museum";
    }
}
