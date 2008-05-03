package ru.spb.etu.client.ui.edit.entities.traversal;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.edit.entities.edit.EntityEditPanel;

public class MasterpieceTraversalPanel
    extends TraversalPanel
{
    private Artist artist;

    public MasterpieceTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        super( entityEditPanel );

        artist = (Artist) entityEditPanel.getEntityForm().getEntityWrapper();
    }

    protected void processNewEntity(
        EntityWrapper entityWrapper )
    {
        MasterPiece masterPiece = (MasterPiece) entityWrapper;
        masterPiece.setArtist( artist );
        super.processNewEntity( entityWrapper );
    }
}
