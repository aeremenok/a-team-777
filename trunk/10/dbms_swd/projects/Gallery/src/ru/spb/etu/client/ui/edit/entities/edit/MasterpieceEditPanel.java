package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.view.forms.EntityForm;
import ru.spb.etu.client.ui.view.forms.MasterPieceForm;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MasterpieceEditPanel
    extends EntityEditPanel
{
    private Artist artist;

    MyTextBox      creationYear = new MyTextBox();

    public MasterpieceEditPanel(
        Artist artist )
    {
        super();
        this.artist = artist;

        creationYear.setMaxLength( 4 );
        creationYear.setVisibleLength( 4 );
        createRow( "Creation Year", creationYear );
    }

    public String entityTypeName()
    {
        return "MasterPiece";
    }

    public String getDefaultImageUrl()
    {
        return "images/painting.jpg";
    }

    public void retreiveEntities(
        AsyncCallback callback )
    {
        async.getMasterPieces( artist, callback );
    }

    public EntityForm getEntityForm()
    {
        if ( entityForm == null )
        {
            entityForm = new MasterPieceForm();
        }
        return entityForm;
    }

    public void showEntity(
        EntityWrapper entityWrapper )
    {
        super.showEntity( entityWrapper );
        MasterPiece masterPiece = (MasterPiece) entityWrapper;
        creationYear.bindField( masterPiece.getCreationYear() );
    }
}
