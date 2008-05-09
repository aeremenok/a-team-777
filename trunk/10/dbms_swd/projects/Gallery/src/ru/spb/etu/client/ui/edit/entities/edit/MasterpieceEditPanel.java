package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.edit.entities.traversal.LightTraversalPanel;
import ru.spb.etu.client.ui.edit.entities.traversal.MasterpieceTraversalPanel;
import ru.spb.etu.client.ui.edit.entities.traversal.TraversalPanel;
import ru.spb.etu.client.ui.view.forms.EntityForm;
import ru.spb.etu.client.ui.view.forms.MasterPieceForm;
import ru.spb.etu.client.ui.widgets.MyTextBox;

public abstract class MasterpieceEditPanel
    extends EntityEditPanel
{
    protected Artist    artist;

    MyTextBox           creationYear    = new MyTextBox();

    LightTraversalPanel artistTraversal = new LightTraversalPanel( this, new ArtistEditPanel() );
    LightTraversalPanel genreTraversal  = new LightTraversalPanel( this, new GenreEditPanel() );
    LightTraversalPanel museumTraversal = new LightTraversalPanel( this, new MuseumEditPanel() );

    public MasterpieceEditPanel(
        Artist artist )
    {
        super();
        this.artist = artist;

        createRow( "Author", artistTraversal );
        createRow( "Genre", genreTraversal );
        createRow( "Museum", museumTraversal );

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

    public EntityWrapper getMasterpieceReference(
        MasterPiece masterpiece )
    {
        return null;
    }

    public void showEntity(
        EntityWrapper entityWrapper )
    {
        super.showEntity( entityWrapper );
        MasterPiece masterPiece = (MasterPiece) entityWrapper;
        creationYear.bindField( masterPiece.getCreationYear() );

        applyReference( new LightTraversalPanel[] { artistTraversal, genreTraversal, museumTraversal } );
    }

    private void applyReference(
        LightTraversalPanel[] lightTraversalPanels )
    {
        for ( int i = 0; i < lightTraversalPanels.length; i++ )
        {
            lightTraversalPanels[i].applyReference();
        }
    }

    protected TraversalPanel createTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        return new MasterpieceTraversalPanel( entityEditPanel );
    }
}
