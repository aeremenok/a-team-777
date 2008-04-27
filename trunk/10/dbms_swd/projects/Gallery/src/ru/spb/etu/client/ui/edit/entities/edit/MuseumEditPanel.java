package ru.spb.etu.client.ui.edit.entities.edit;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MuseumEditPanel
    extends EntityEditPanel
{

    protected String getDefaultImageUrl()
    {
        return "images/museum.jpg";
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
}
