package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MuseumEditPanel
    extends EntityEditPanel
{

    public String getDefaultImageUrl()
    {
        return "images/museum.gif";
    }

    public void retreiveEntities(
        AsyncCallback callback )
    {
        async.getMuseums( callback );
    }

    public String entityTypeName()
    {
        return "Museum";
    }

    public EntityWrapper getMasterpieceReference(
        MasterPiece masterpiece )
    {
        return masterpiece.getMuseum();
    }
}
