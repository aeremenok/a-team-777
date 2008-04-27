package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SculptureEditPanel
    extends MasterpieceEditPanel
{

    public SculptureEditPanel(
        Artist artist )
    {
        super( artist );
    }

    public String entityTypeName()
    {
        return "Sculpture";
    }

    public String getDefaultImageUrl()
    {
        return "images/sculpture.jpg";
    }

    public void retreiveEntities(
        AsyncCallback callback )
    {
        async.getSculptures( artist, callback );
    }
}
