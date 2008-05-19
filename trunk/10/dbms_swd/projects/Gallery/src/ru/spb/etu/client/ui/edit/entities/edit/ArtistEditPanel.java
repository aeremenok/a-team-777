package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.edit.EditPanel;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ArtistEditPanel
    extends EntityEditPanel
{
    HorizontalPanel   masterPieces = new HorizontalPanel();
    // доп. поля художника
    private MyTextBox birthDate    = new MyTextBox();
    private MyTextBox country      = new MyTextBox();

    public ArtistEditPanel()
    {
        super();

        createRow( "Birth Date", birthDate );
        createRow( "Country", country );

        masterPieces.setHorizontalAlignment( ALIGN_CENTER );
        add( masterPieces );
        masterPieces.setVisible( false );

        Button paintings = new Button( "Paintings" );
        masterPieces.add( paintings );

        Button sculpture = new Button( "Sculpture" );
        masterPieces.add( sculpture );

        paintings.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                EditPanel.getInstance().processPaintings( (Artist) getEntityForm().getEntityWrapper() );
            }
        } );
        sculpture.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                EditPanel.getInstance().processSculpture( (Artist) getEntityForm().getEntityWrapper() );
            }
        } );
    }

    public String getDefaultImageUrl()
    {
        return "images/artist.jpg";
    }

    public void retreiveEntities(
        AsyncCallback callback )
    {
        async.getArtists( callback );
    }

    public String entityTypeName()
    {
        return "Artist";
    }

    public void showEditTable(
        boolean show )
    {
        super.showEditTable( show );
        masterPieces.setVisible( show );
    }

    public EntityWrapper getMasterpieceReference(
        MasterPiece masterpiece )
    {
        return masterpiece.getArtist();
    }

    public void showEntity(
        EntityWrapper entityWrapper )
    {
        Artist artist = (Artist) entityWrapper;
        country.bindField( artist.getCountry() );
        birthDate.bindField( artist.getBirthDate() );
        super.showEntity( entityWrapper );
    }
}
