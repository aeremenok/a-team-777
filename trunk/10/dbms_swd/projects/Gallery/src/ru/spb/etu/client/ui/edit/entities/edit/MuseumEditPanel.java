package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.ui.edit.entities.traversal.MuseumTraversalPanel;
import ru.spb.etu.client.ui.edit.entities.traversal.TraversalPanel;

public class MuseumEditPanel
    extends EntityEditPanel
{

    protected String getDefaultImageUrl()
    {
        return "images/museum.jpg";
    }

    protected TraversalPanel createTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        return new MuseumTraversalPanel( entityEditPanel );
    }

}
