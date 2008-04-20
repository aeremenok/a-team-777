package ru.spb.etu.client.serializable;

public class Museum
    implements
        EntityWrapper
{
    private String description;

    private String imageUrl;

    private String name;

    public Museum()
    {
    }

    public Museum(
        String description,
        String name,
        String imageUrl )
    {
        super();
        this.description = description;
        this.name = name;
        this.imageUrl = imageUrl;
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
        async.getArtistsByMuseum( this, asyncCallback );
    }
}
