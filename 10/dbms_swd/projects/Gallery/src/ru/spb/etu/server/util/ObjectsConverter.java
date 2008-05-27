package ru.spb.etu.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.serializable.Museum;
import ru.spb.etu.client.serializable.Painting;
import ru.spb.etu.client.serializable.Sculpture;
import ru.spb.etu.server.model.DbArtist;
import ru.spb.etu.server.model.DbGenre;
import ru.spb.etu.server.model.DbMasterpiece;
import ru.spb.etu.server.model.DbMuseum;
import ru.spb.etu.server.model.DbPainting;
import ru.spb.etu.server.model.DbSculpture;

public class ObjectsConverter
{
    protected static Logger log = Logger.getLogger( ObjectsConverter.class );
    static
    {
        log.setLevel( org.apache.log4j.Level.ALL );
    }

    // ======================================================================================================================
    // Actors
    // ======================================================================================================================

    /**
     * Converting array from DB to array for client
     * 
     * @param actorsDB
     * @return
     */
    public static ArrayList<Artist> convertArtists(
        List<DbArtist> actorsDB )
    {

        ArrayList<Artist> artists = new ArrayList<Artist>();

        if ( actorsDB != null )
            try
            {

                for ( DbArtist a : actorsDB )
                {

                    artists.add( convertArtist( a ) );
                }

            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

        return artists;
    }

    /**
     * converting database artist to client artist
     * 
     * @param a
     * @return
     */
    public static Artist convertArtist(
        DbArtist a )
    {
        Artist b = new Artist();
        if ( a.getImageUrl() != null )
            b.setImageUrl( a.getImageUrl() );
        if ( a.getBirthdate() != null )
            b.setBirthDate( a.getBirthdate() );
        if ( a.getCountry() != null )
            b.setCountry( a.getCountry() );
        if ( a.getDescription() != null )
            b.setDescription( a.getDescription() );
        if ( a.getName() != null )
            b.setName( a.getName() );
        b.setId( a.getId() );

        try
        {
            ru.spb.etu.server.FileUploadServlet.writeBytes( a.getImageUrl(), a.getPicture() );
        }
        catch ( IOException e )
        {
            log.error( "Con not create img for DbArtist:" + a.getId() + "." );
        }
        return b;
    }

    // ======================================================================================================================
    // Masterpiece
    // ======================================================================================================================

    /**
     * Converting array from DB to array for client
     * 
     * @param DbMasterpiece
     * @return
     */
    public static ArrayList<MasterPiece> convertMasterpieces(
        List<DbMasterpiece> what )
    {

        ArrayList<MasterPiece> artists = new ArrayList<MasterPiece>();

        if ( what != null )
            try
            {

                for ( DbMasterpiece a : what )
                {

                    artists.add( convertMasterpiece( a ) );
                }

            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

        return artists;
    }

    /**
     * converting database MasterPiece to client MasterPiece
     * 
     * @param a
     * @return
     */
    public static MasterPiece convertMasterpiece(
        DbMasterpiece a )
    {
        MasterPiece b = new MasterPiece();

        int year = (a.getCreationYear() == null) ? 0 : a.getCreationYear();
        b.setCreationYear( year );
        if ( a.getImageURL() != null )
            b.setImageUrl( a.getImageURL() );
        if ( a.getTitle() != null )
            b.setTitle( a.getTitle() );
        if ( a.getDescription() != null )
            b.setDescription( a.getDescription() );
        b.setId( a.getId() );

        try
        {
            ru.spb.etu.server.FileUploadServlet.writeBytes( a.getImageURL(), a.getPicture() );
        }
        catch ( IOException e )
        {
            log.error( "Con not create img for DbMasterpiece:" + a.getId() + "." );
        }
        return b;
    }

    // ======================================================================================================================
    // Masterpiece
    // ======================================================================================================================

    /**
     * Converting array from "DbMuseum" to array "Museum" for client
     * 
     * @param actorsDB
     * @return
     */
    public static ArrayList<Museum> convertMuseums(
        List<DbMuseum> what )
    {

        ArrayList<Museum> result = new ArrayList<Museum>();

        if ( what != null )
            try
            {

                for ( DbMuseum a : what )
                {

                    result.add( convertMuseum( a ) );
                }

            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

        return result;
    }

    /**
     * converting database DbMuseum to client Museum
     * 
     * @param a
     * @return
     */
    public static Museum convertMuseum(
        DbMuseum a )
    {
        Museum b = new Museum();
        if ( a.getDescription() != null )
            b.setDescription( a.getDescription() );
        if ( a.getName() != null )
            b.setName( a.getName() );
        if ( a.getImageURL() != null )
            b.setImageUrl( a.getImageURL() );
        b.setId( a.getId() );

        try
        {
            ru.spb.etu.server.FileUploadServlet.writeBytes( a.getImageURL(), a.getPicture() );
        }
        catch ( IOException e )
        {
            log.error( "Con not create img for DbMuseum:" + a.getId() + "." );
        }
        return b;
    }

    // ======================================================================================================================
    // Genre
    // ======================================================================================================================

    /**
     * Converting array from "DbMuseum" to array "Museum" for client
     * 
     * @param actorsDB
     * @return
     */
    public static ArrayList<Genre> convertGenres(
        List<DbGenre> what )
    {

        ArrayList<Genre> result = new ArrayList<Genre>();

        if ( what != null )
            try
            {

                for ( DbGenre a : what )
                {

                    result.add( convertGenre( a ) );
                }

            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

        return result;
    }

    /**
     * converting database DbMuseum to client Museum
     * 
     * @param a
     * @return
     */
    public static Genre convertGenre(
        DbGenre a )
    {
        Genre b = new Genre();

        if ( a.getDescrption() != null )
            b.setDescription( a.getDescrption() );
        if ( a.getImageURL() != null )
            b.setImageUrl( a.getImageURL() );
        if ( a.getName() != null )
            b.setName( a.getName() );
        b.setId( a.getId() );

        try
        {
            ru.spb.etu.server.FileUploadServlet.writeBytes( a.getImageURL(), a.getPicture() );
        }
        catch ( IOException e )
        {
            log.error( "Con not create img for DbGenre:" + a.getId() + "." );
        }
        return b;
    }

    // ======================================================================================================================
    // Genre
    // ======================================================================================================================

    /**
     * Converting array from "DbMuseum" to array "Museum" for client
     * 
     * @param actorsDB
     * @return
     */
    public static ArrayList<Painting> convertPaintings(
        List<DbPainting> what )
    {

        ArrayList<Painting> result = new ArrayList<Painting>();

        if ( what != null )
            try
            {

                for ( DbPainting a : what )
                {

                    result.add( convertPainting( a ) );
                }

            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

        return result;
    }

    /**
     * converting database DbMuseum to client Museum
     * 
     * @param a
     * @return
     */
    public static Painting convertPainting(
        DbPainting a )
    {
        Painting b = new Painting();
        b.setArtist( convertArtist( a.getMyArtist() ) );
        if ( a.getDescription() != null )
            b.setDescription( a.getDescription() );
        b.setCreationYear( a.getCreationYear() == null ? 0 : a.getCreationYear() );
        b.setGenre( convertGenre( a.getMyGenre() ) );
        b.setHeight( a.getHeight() == null ? 0 : a.getHeight() );
        if ( a.getImageURL() != null )
            b.setImageUrl( a.getImageURL() );
        b.setMuseum( convertMuseum( a.getMyMuseum() ) );
        if ( a.getTitle() != null )
            b.setTitle( a.getTitle() );
        b.setWidth( a.getWidth() == null ? 0 : a.getWidth() );
        b.setId( a.getId() );
        try
        {
            ru.spb.etu.server.FileUploadServlet.writeBytes( a.getImageURL(), a.getPicture() );
        }
        catch ( IOException e )
        {
            log.error( "Con not create img for DbPainting:" + a.getId() + "." );
        }
        return b;
    }

    // ======================================================================================================================
    // Genre
    // ======================================================================================================================

    /**
     * Converting array from "DbMuseum" to array "Museum" for client
     * 
     * @param actorsDB
     * @return
     */
    public static ArrayList<Sculpture> convertSculptures(
        List<DbSculpture> what )
    {

        ArrayList<Sculpture> result = new ArrayList<Sculpture>();

        if ( what != null )
            try
            {

                for ( DbSculpture a : what )
                {

                    result.add( convertSculpture( a ) );
                }

            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

        return result;
    }

    /**
     * converting database DbMuseum to client Museum
     * 
     * @param a
     * @return
     */
    public static Sculpture convertSculpture(
        DbSculpture a )
    {
        Sculpture b = new Sculpture();
        b.setArtist( convertArtist( a.getMyArtist() ) );
        if ( a.getDescription() != null )
            b.setDescription( a.getDescription() );
        b.setCreationYear( a.getCreationYear() == null ? 0 : a.getCreationYear() );
        b.setGenre( convertGenre( a.getMyGenre() ) );
        if ( a.getImageURL() != null )
            b.setImageUrl( a.getImageURL() );
        b.setMuseum( convertMuseum( a.getMyMuseum() ) );
        if ( a.getTitle() != null )
            b.setTitle( a.getTitle() );
        b.setMass( a.getMass() == null ? 0 : a.getMass() );
        if ( a.getMaterial() != null )
            b.setMaterial( a.getMaterial() );
        b.setId( a.getId() );
        try
        {
            ru.spb.etu.server.FileUploadServlet.writeBytes( a.getImageURL(), a.getPicture() );
        }
        catch ( IOException e )
        {
            log.error( "Con not create img for DbSculpture:" + a.getId() + "." );
        }
        return b;
    }
}
