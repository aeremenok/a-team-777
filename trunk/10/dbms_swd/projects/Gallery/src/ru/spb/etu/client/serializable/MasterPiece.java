package ru.spb.etu.client.serializable;

public class MasterPiece
    implements
        EntityWrapper
{
    String           imageUrl;

    ReflectiveString creationYear = new ReflectiveString( this );
    ReflectiveString description  = new ReflectiveString( this );
    ReflectiveString title        = new ReflectiveString( this );

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

    public ReflectiveString getCreationYear()
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
        this.creationYear.setInt( creationYear );
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
