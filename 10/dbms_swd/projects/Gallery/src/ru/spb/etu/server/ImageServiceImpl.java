package ru.spb.etu.server;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.Museum;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImageServiceImpl
    extends RemoteServiceServlet
    implements
        ImageService
{
    EntityExtractor entityExtractor;

    @Override
    public ArrayList getArtists()
    {
        return getEntityExtractor().getArtists();
    }

    @Override
    public ArrayList getMuseums()
    {
        return getEntityExtractor().getMuseums();
    }

    @Override
    public ArrayList getGenres()
    {
        return getEntityExtractor().getGenres();
    }

    @Override
    public ArrayList getMasterPieces(
        Artist artist )
    {
        return getEntityExtractor().getMasterPieces( artist );
    }

    @Override
    public ArrayList getArtistsByGenre(
        Genre genre )
    {
        return getEntityExtractor().getArtistsByGenre( genre );
    }

    @Override
    public ArrayList getArtistsByMuseum(
        Museum museum )
    {
        return getEntityExtractor().getArtistsByMuseum( museum );
    }

    public EntityExtractor getEntityExtractor()
    {
        if ( entityExtractor == null )
        {
            entityExtractor = new EntityExtractorStub();
        }
        return entityExtractor;
    }
}
