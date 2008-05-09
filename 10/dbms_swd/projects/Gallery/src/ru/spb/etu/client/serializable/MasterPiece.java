package ru.spb.etu.client.serializable;

public class MasterPiece
    extends EntityWrapper
{
    Artist           artist;
    Genre            genre;
    Museum           museum;

    ReflectiveString creationYear = new ReflectiveString( this );

    public MasterPiece()
    {
    }

    public MasterPiece(
        int creationYear,
        String imageUrl,
        String title,
        String description )
    {
        super( description, imageUrl, title );
        setCreationYear( creationYear );
        setCreationYear( creationYear );
    }

    public Artist getArtist()
    {
        return artist;
    }

    public ReflectiveString getCreationYear()
    {
        return creationYear;
    }

    public Genre getGenre()
    {
        return genre;
    }

    public Museum getMuseum()
    {
        return museum;
    }

    public void requestMasterPieces()
    {
    }

    public void setArtist(
        Artist artist )
    {
        this.artist = artist;
    }

    public void setCreationYear(
        int creationYear )
    {
        this.creationYear.setInt( creationYear );
    }

    public void setGenre(
        Genre genre )
    {
        this.genre = genre;
    }

    public void setMuseum(
        Museum museum )
    {
        this.museum = museum;
    }

    public void applyToMasterPiece(
        MasterPiece masterPiece )
    {
    }
}
