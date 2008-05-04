package ru.spb.etu.client.serializable;

public class Museum
    extends EntityWrapper
{
    public Museum()
    {
    }

    public Museum(
        String description,
        String name,
        String imageUrl )
    {
        super( description, imageUrl, name );
    }

    public ReflectiveString getName()
    {
        return getTitle();
    }

    public void setName(
        String name )
    {
        this.getTitle().setString( name );
    }

    public void requestMasterPieces()
    {
        getAsync().getArtistsByMuseum( this, asyncCallback );
    }
}
