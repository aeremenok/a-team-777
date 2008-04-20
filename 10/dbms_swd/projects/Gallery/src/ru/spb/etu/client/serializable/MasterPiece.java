package ru.spb.etu.client.serializable;

public class MasterPiece
    implements
        EntityWrapper
{
    int    creationYear;
    String description;
    String imageUrl;
    String title;

    public MasterPiece()
    {
    }

    public MasterPiece(
        int creationYear,
        String imageUrl,
        String title,
        String description )
    {
        super();
        this.creationYear = creationYear;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

    public int getCreationYear()
    {
        return creationYear;
    }

    public String getDescription()
    {
        return description;
    }

    public String getImageUrl()
    {
        return imageUrl;
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

    public void setDescription(
        String description )
    {
        this.description = description;
    }

    public void setImageUrl(
        String imageUrl )
    {
        this.imageUrl = imageUrl;
    }

    public void setTitle(
        String title )
    {
        this.title = title;
    }

    public void requestMasterPieces()
    {
    }
}
