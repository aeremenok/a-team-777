package ru.spb.etu.client.serializable;

public class Genre
    implements
        EntityWrapper
{
    private ReflectiveString description = new ReflectiveString();
    private String           imageUrl;
    private ReflectiveString name        = new ReflectiveString();

    public Genre()
    {
    }

    public Genre(
        String name,
        String imageUrl,
        String description )
    {
        super();
        setName( name );
        setImageUrl( imageUrl );
        setDescription( description );
    }

    public ReflectiveString getDescription()
    {
        return description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public ReflectiveString getName()
    {
        return name;
    }

    public ReflectiveString getTitle()
    {
        return name;
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

    public void setName(
        String name )
    {
        this.name.setString( name );
    }

    public void requestMasterPieces()
    {
        async.getArtistsByGenre( this, asyncCallback );
    }
}
