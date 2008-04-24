package ru.spb.etu.client.serializable;

public class Museum
    implements
        EntityWrapper
{
    private ReflectiveString description = new ReflectiveString();

    private String           imageUrl;

    private ReflectiveString name        = new ReflectiveString();

    public Museum()
    {
    }

    public Museum(
        String description,
        String name,
        String imageUrl )
    {
        super();
        setDescription( description );
        setName( name );
        setImageUrl( imageUrl );
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
        return getName();
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
        async.getArtistsByMuseum( this, asyncCallback );
    }
}
