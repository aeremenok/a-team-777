package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.view.forms.EntityForm;
import ru.spb.etu.client.ui.view.forms.MasterPieceForm;
import ru.spb.etu.client.ui.widgets.MyTextBox;

public abstract class MasterpieceEditPanel
    extends EntityEditPanel
{
    protected Artist artist;

    MyTextBox        creationYear = new MyTextBox();

    public MasterpieceEditPanel(
        Artist artist )
    {
        super();
        this.artist = artist;

        creationYear.setMaxLength( 4 );
        creationYear.setVisibleLength( 4 );
        createRow( "Creation Year", creationYear );
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
