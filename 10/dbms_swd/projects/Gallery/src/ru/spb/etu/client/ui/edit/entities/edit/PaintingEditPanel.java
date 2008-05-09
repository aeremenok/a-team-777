package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Painting;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class PaintingEditPanel
    extends MasterpieceEditPanel
{
    MyTextBox width  = new MyTextBox();
    MyTextBox height = new MyTextBox();

    public PaintingEditPanel(
        Artist artist )
    {
        super( artist );

        createRow( "Width", width );
        createRow( "Height", height );
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

    public void showEntity(
        EntityWrapper entityWrapper )
    {
        super.showEntity( entityWrapper );

        Painting painting = (Painting) entityWrapper;
        width.bindField( painting.getWidth() );
        height.bindField( painting.getHeight() );
    }

}
