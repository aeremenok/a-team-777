package ru.spb.etu.server.util;

import java.util.ArrayList;
import java.util.List;

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
        Artist b = new Artist( a.getBirthdate(), a.getCountry(), a.getDescription(), a.getName(), a.getImageUrl() );
        Integer i = a.getId();
        // System.out.println( "Artist i=" + i );
        b.setId( i );
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
        int year = (a.getCreationYear() == null) ? 0 : a.getCreationYear();
        MasterPiece b = new MasterPiece( year, a.getImageURL(), a.getTitle(), a.getDescription() );
        b.setId( a.getId() );
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
        Museum b = new Museum( a.getDescription(), a.getName(), a.getImageURL() );
        b.setId( a.getId() );
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
        Genre b = new Genre( a.getName(), a.getImageURL(), a.getDescrption() );
        b.setId( a.getId() );
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
        b.setDescription( a.getDescription() );
        b.setCreationYear( a.getCreationYear() == null ? 0 : a.getCreationYear() );
        b.setGenre( convertGenre( a.getMyGenre() ) );
        b.setHeight( a.getHeight() == null ? 0 : a.getHeight() );
        b.setImageUrl( a.getImageURL() );
        b.setMuseum( convertMuseum( a.getMyMuseum() ) );
        b.setTitle( a.getTitle() );
        b.setWidth( a.getWidth() == null ? 0 : a.getWidth() );
        b.setId( a.getId() );
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
        b.setDescription( a.getDescription() );
        b.setCreationYear( a.getCreationYear() == null ? 0 : a.getCreationYear() );
        b.setGenre( convertGenre( a.getMyGenre() ) );
        b.setImageUrl( a.getImageURL() );
        b.setMuseum( convertMuseum( a.getMyMuseum() ) );
        b.setTitle( a.getTitle() );
        b.setMass( a.getMass() == null ? 0 : a.getMass() );
        b.setMaterial( a.getMaterial() );
        b.setId( a.getId() );
        return b;
    }

}
