package ru.spb.etu.server;

import java.io.IOException;
import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.Museum;
import ru.spb.etu.server.stubs.EntityBackuperStub;
import ru.spb.etu.server.stubs.EntityExtractorStub;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImageServiceImpl
    extends RemoteServiceServlet
    implements
        ImageService
{
    EntityBackuper        entityBackuper;
    EntityExtractor       entityExtractor;
    private static String baseUrl;

    public static String getBaseUrl()
    {
        return baseUrl;
    }

    @Override
    public EntityWrapper create(
        String type )
        throws Exception
    {
        return getEntityBackuper().create( type );
    }

    @Override
    public ArrayList getArtists()
    {
        return getEntityExtractor().getArtists();
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

    public EntityBackuper getEntityBackuper()
    {
        if ( entityBackuper == null )
        {
            entityBackuper = new EntityBackuperStub();
        }
        return entityBackuper;
    }

    public EntityExtractor getEntityExtractor()
    {
        if ( entityExtractor == null )
        {
            entityExtractor = new EntityExtractorStub();
        }
        return entityExtractor;
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
    public ArrayList getMuseums()
    {
        return getEntityExtractor().getMuseums();
    }

    @Override
    public ArrayList getPaintings(
        Artist artist )
    {
        return getEntityExtractor().getPaintings( artist );
    }

    @Override
    public ArrayList getSculptures(
        Artist artist )
    {
        return getEntityExtractor().getSculptures( artist );
    }

    @Override
    public void remove(
        EntityWrapper entityWrapper )
    {
        getEntityBackuper().remove( entityWrapper );
    }

    @Override
    public void saveOrUpdate(
        EntityWrapper entityWrapper )
    {
        getEntityBackuper().saveOrUpdate( entityWrapper );
    }

    @Override
    public void setBaseUrl(
        String url )
    {
        ImageServiceImpl.baseUrl = url;
    }

    @Override
    public void updateImage(
        EntityWrapper entityWrapper )
    {
        getEntityBackuper().saveOrUpdate( entityWrapper );
        try
        {
            byte[] bytes = FileUploadServlet.readBytes( entityWrapper.getImageUrl() );
            getEntityBackuper().updateBlob( entityWrapper, bytes );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
