package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Sculpture;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SculptureEditPanel
    extends MasterpieceEditPanel
{
    MyTextBox mass     = new MyTextBox();
    MyTextBox material = new MyTextBox();

    public SculptureEditPanel(
        Artist artist )
    {
        super( artist );

        createRow( "Mass", mass );
        createRow( "Material", material );
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

    public void showEntity(
        EntityWrapper entityWrapper )
    {
        super.showEntity( entityWrapper );

        Sculpture sculpture = (Sculpture) entityWrapper;
        mass.bindField( sculpture.getMass() );
        material.bindField( sculpture.getMaterial() );
    }
}
