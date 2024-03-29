package ru.spb.etu.client;

import java.util.ArrayList;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.Museum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ImageService
    extends
        RemoteService
{
    public static class App
    {
        private static ImageServiceAsync ourInstance = null;

        public static synchronized ImageServiceAsync getInstance()
        {
            if ( ourInstance == null )
            {
                ourInstance = (ImageServiceAsync) GWT.create( ImageService.class );
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint( "/Gallery/ImageService" );
            }
            return ourInstance;
        }
    }

    /**
     * @gwt.typeArgs <ru.spb.etu.client.serializable.Artist>
     */
    ArrayList getMasterPieces(
        Artist artist );

    /**
     * @gwt.typeArgs <ru.spb.etu.client.serializable.MasterPiece>
     */
    ArrayList getArtists();

    /**
     * @gwt.typeArgs <ru.spb.etu.client.serializable.Museum>
     */
    ArrayList getMuseums();

    /**
     * @gwt.typeArgs <ru.spb.etu.client.serializable.Genre>
     */
    ArrayList getGenres();

    /**
     * @gwt.typeArgs <ru.spb.etu.client.serializable.Artist>
     */
    ArrayList getArtistsByGenre(
        Genre genre );

    /**
     * @gwt.typeArgs <ru.spb.etu.client.serializable.Artist>
     */
    ArrayList getArtistsByMuseum(
        Museum museum );

    void setBaseUrl(
        String url );

    Integer saveOrUpdate(
        EntityWrapper entityWrapper );

    void remove(
        EntityWrapper entityWrapper );

    EntityWrapper create(
        String type )
        throws Exception;

    ArrayList getPaintings(
        Artist artist );

    ArrayList getSculptures(
        Artist artist );

    Integer updateImage(
        EntityWrapper entityWrapper )
        throws Exception;
}
