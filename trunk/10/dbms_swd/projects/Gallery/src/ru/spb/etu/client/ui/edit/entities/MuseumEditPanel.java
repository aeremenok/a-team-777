package ru.spb.etu.client.ui.edit.entities;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Museum;

public class MuseumEditPanel
    extends EntityEditPanel
{

    protected EntityWrapper createEntityWrapper()
    {
        return new Museum();
    }

    protected String getDefaultImageUrl()
    {
        return "images/museum.jpg";
    }

}
