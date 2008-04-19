package ru.spb.etu.client.serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Image;

public class MasterPiece
    implements
        IsSerializable
{
    int    creationYear;
    Image  image;
    String title;

    public MasterPiece()
    {
    }

    public MasterPiece(
        String title,
        int creationYear,
        Image image )
    {
        this.title = title;
        this.creationYear = creationYear;
        this.image = image;
    }

    public int getCreationYear()
    {
        return creationYear;
    }

    public Image getImage()
    {
        return image;
    }

    public String getTitle()
    {
        return title;
    }

    public void setCreationYear(
        int creationYear )
    {
        this.creationYear = creationYear;
    }

    public void setImage(
        Image image )
    {
        this.image = image;
    }

    public void setTitle(
        String title )
    {
        this.title = title;
    }
}
