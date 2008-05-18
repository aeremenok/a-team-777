package ru.spb.etu.server;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.Museum;
import ru.spb.etu.server.util.EntityBackuperImpl;
import ru.spb.etu.server.util.EntityExtractorImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImageServiceImpl
    extends RemoteServiceServlet
    implements
        ImageService
{
    static final String   LINE_DELIM =
                                       "*****************************************************************************************";
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
            entityBackuper = new EntityBackuperImpl();
        }
        return entityBackuper;
    }

    public EntityExtractor getEntityExtractor()
    {
        if ( entityExtractor == null )
        {
            entityExtractor = new EntityExtractorImpl();
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
    public Integer saveOrUpdate(
        EntityWrapper entityWrapper )
    {
        System.out.println( LINE_DELIM );
        System.out.println( "[ImageServiceImpl] saveOrUpdate " + entityWrapper.getId() + "." );
        int id = getEntityBackuper().saveOrUpdate( entityWrapper );
        System.out.println( "[ImageServiceImpl] saveOrUpdated! " + entityWrapper.getId() + "." );
        if ( entityWrapper.getId() == null || entityWrapper.getId() <= 0 )
        {
            entityWrapper.setId( id );
            System.out.println( "[ImageServiceImpl] saveOrUpdate set new id " + entityWrapper.getId() + "." );
        }
        System.out.println( LINE_DELIM );
        return entityWrapper.getId();
    }

    @Override
    public void setBaseUrl(
        String url )
    {
        ImageServiceImpl.baseUrl = url;
    }

    @Override
    public Integer updateImage(
        EntityWrapper entityWrapper )
        throws Exception
    {
        try
        {
            System.out.println( LINE_DELIM );
            System.out.println( "[ImageServiceImpl] saveOrUpdate " + entityWrapper.getId() + "." );
            int id = getEntityBackuper().saveOrUpdate( entityWrapper );
            System.out.println( "[ImageServiceImpl] saveOrUpdated! " + entityWrapper.getId() + "." );
            if ( entityWrapper.getId() == null || entityWrapper.getId() <= 0 )
            {
                entityWrapper.setId( id );
                System.out.println( "[ImageServiceImpl] saveOrUpdate set new id " + entityWrapper.getId() + "." );
            }
            System.out.println( LINE_DELIM );

            byte[] bytes = FileUploadServlet.readBytes( entityWrapper.getImageUrl() );
            getEntityBackuper().updateBlob( entityWrapper, bytes );
            return entityWrapper.getId();
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            throw new Exception( e );
        }
    }
}
