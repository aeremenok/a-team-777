package ru.spb.etu.client.serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Criteria
    implements
        IsSerializable
{
    Artist artist;

    Genre  genre;

    Museum museum;

    public Criteria()
    {
    }

    public Criteria(
        Artist artist,
        Museum museum,
        Genre genre )
    {
        super();
        this.artist = artist;
        this.museum = museum;
        this.genre = genre;
    }

    public Artist getArtist()
    {
        return artist;
    }

    public Genre getGenre()
    {
        return genre;
    }

    public Museum getMuseum()
    {
        return museum;
    }

    public void setArtist(
        Artist artist )
    {
        this.artist = artist;
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
}
