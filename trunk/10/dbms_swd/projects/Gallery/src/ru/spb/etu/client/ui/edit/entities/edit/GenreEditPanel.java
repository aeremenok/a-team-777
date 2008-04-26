package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.ui.edit.entities.traversal.GenreTraversalPanel;
import ru.spb.etu.client.ui.edit.entities.traversal.TraversalPanel;

public class GenreEditPanel
    extends EntityEditPanel
{
    protected String getDefaultImageUrl()
    {
        return "images/genre.gif";
    }

    protected TraversalPanel createTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        return new GenreTraversalPanel( entityEditPanel );
    }
}
