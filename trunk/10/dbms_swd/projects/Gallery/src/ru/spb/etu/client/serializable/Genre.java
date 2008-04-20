package ru.spb.etu.client.serializable;

public class Genre
    implements
        EntityWrapper
{
    private String description;
    private String imageUrl;
    private String name;

    public Genre()
    {
    }

    public Genre(
        String name,
        String imageUrl,
        String description )
    {
        super();
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getName()
    {
        return name;
    }

    public String getTitle()
    {
        return name;
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

    public void setName(
        String name )
    {
        this.name = name;
    }

    public void requestMasterPieces()
    {
        async.getArtistsByGenre( this, asyncCallback );
    }
}
