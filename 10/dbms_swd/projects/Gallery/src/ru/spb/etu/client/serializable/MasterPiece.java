package ru.spb.etu.client.serializable;

public class MasterPiece
    implements
        EntityWrapper
{
    int              creationYear;
    ReflectiveString description = new ReflectiveString();
    String           imageUrl;
    ReflectiveString title       = new ReflectiveString();

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
        setCreationYear( creationYear );
        setImageUrl( imageUrl );
        setCreationYear( creationYear );
        setDescription( description );
    }

    public int getCreationYear()
    {
        return creationYear;
    }

    public ReflectiveString getDescription()
    {
        return description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public ReflectiveString getTitle()
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
        this.description.setString( description );
    }

    public void setImageUrl(
        String imageUrl )
    {
        this.imageUrl = imageUrl;
    }

    public void setTitle(
        String title )
    {
        this.title.setString( title );
    }

    public void requestMasterPieces()
    {
    }
}
