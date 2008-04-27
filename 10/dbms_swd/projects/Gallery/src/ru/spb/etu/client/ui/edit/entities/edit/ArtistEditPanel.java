package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.ui.edit.EditPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ArtistEditPanel
    extends EntityEditPanel
{
    HorizontalPanel masterPieces = new HorizontalPanel();

    public ArtistEditPanel()
    {
        super();

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
}
