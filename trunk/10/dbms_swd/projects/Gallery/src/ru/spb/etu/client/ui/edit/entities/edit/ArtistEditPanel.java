package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.ui.edit.entities.traversal.ArtistTraversalPanel;
import ru.spb.etu.client.ui.edit.entities.traversal.TraversalPanel;

public class ArtistEditPanel
    extends EntityEditPanel
{
    protected String getDefaultImageUrl()
    {
        return "images/artist.jpg";
    }

    protected TraversalPanel createTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        return new ArtistTraversalPanel( entityEditPanel );
    }
}
