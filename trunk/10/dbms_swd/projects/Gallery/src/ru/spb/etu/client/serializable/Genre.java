package ru.spb.etu.client.serializable;

public class Genre
    extends EntityWrapper
{
    public Genre()
    {
    }

    public Genre(
        String name,
        String imageUrl,
        String description )
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
        getName().setString( name );
    }

    public void requestMasterPieces()
    {
        getAsync().getArtistsByGenre( this, asyncCallback );
    }
}
