package ru.spb.etu.server.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.serializable.Museum;
import ru.spb.etu.client.serializable.Painting;
import ru.spb.etu.client.serializable.Sculpture;
import ru.spb.etu.server.EntityExtractor;
import ru.spb.etu.server.model.DbArtist;
import ru.spb.etu.server.model.DbGenre;
import ru.spb.etu.server.model.DbMasterpiece;
import ru.spb.etu.server.model.DbMuseum;
import ru.spb.etu.server.model.DbPainting;
import ru.spb.etu.server.model.DbSculpture;

public class EntityExtractorImpl
    implements
        EntityExtractor
{

    /*protected static Logger log;
    private final static String fileName = "C:\\Temp\\Gallery\\EntityExtractorImpl.log";

    static {
    	log = Logger.getLogger("EntityExtractorImpl");
    	
    	try {
            if(fileName != null){
                FileHandler fh = new FileHandler(fileName, true);
                Formatter f = new MySimpleFormatter();
                fh.setFormatter(f);   //чтобы писать простым текстом
                log.addHandler(fh);
                log.setUseParentHandlers(true);
                log.setLevel(Level.ALL);
            }
        } catch (Exception err) {
            System.out.println("ERROR: Can't initialize logger for AQTransportAdapter! : ");
            err.printStackTrace();
        }
    
    
    }*/

    @SuppressWarnings( "unchecked" )
    @Override
    public ArrayList<Artist> getArtists()
    {
        log( " getArtists() " );
        // init
        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Artist>();
        }

        // get objects
        List<DbArtist> actorsDB;
        try
        {
            SelectQuery select1 = new SelectQuery( DbArtist.class );
            actorsDB = context.performQuery( select1 );
            log( " getArtists() returned ", actorsDB );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Artist>();
        }

        // convert objects
        // and return
        return ObjectsConverter.convertArtists( actorsDB );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public ArrayList<MasterPiece> getMasterPieces(
        Artist artist )
    {
        // log
        log( " getMasterPieces(Artist) " );

        // init
        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();
        }
        catch ( RuntimeException e )
        {
            e.printStackTrace();
            return new ArrayList<MasterPiece>();
        }

        // validation
        if ( artist == null || artist.getId() == null || artist.getId() <= 0 )
        {
            logError( " No Artist ID " );
            return new ArrayList<MasterPiece>();
        }
        log( " Artist id:" + artist.getId() );

        // getting objects
        List<DbMasterpiece> resultDB;
        try
        {
            DbArtist a = (DbArtist) DataObjectUtils.objectForPK( context, DbArtist.class, artist.getId() );
            if ( a == null )
            {
                logError( " No Artist in DataBase " );
                return new ArrayList<MasterPiece>();
            }

            log( " got artist from database " );

            try
            {

                resultDB = a.getArtistMasterpiece();

                log( " resultDB is:" + resultDB.isEmpty() + " | size:" + resultDB.size() );
                log( " getMasterPieces() returned ", resultDB );

                return ObjectsConverter.convertMasterpieces( resultDB );

            }
            catch ( Exception e )
            {
                e.printStackTrace();
                logError( "got exception on a.getArtistMasterpiece() " );
            }
            log( "trying another way..." );

            // obtain a list of paintings
            /*
             * // object path
            		Expression e1 = Expression.fromString("artistName = 'Salvador Dali'");
            		
            		// same object path
            		Expression e2 = Expression.fromString("obj:artistName = 'Salvador Dali'");
            		
            		// database path, "db:" prefix is mandatory
            		Expression e3 = Expression.fromString("db:ARTIST_NAME = 'Salvador Dali'");
             * 
             * */

            String q = DbMasterpiece.MY_ARTIST_PROPERTY + " = " + artist.getId();
            Expression qualifier3 = Expression.fromString( q );

            // Map m = Collections.singletonMap("artistid", artist.getId()); //Collections.singletonMap("genreid",
            // genre.getId()
            // qualifier3 = qualifier3.expWithParameters(m);

            SelectQuery select3 = new SelectQuery( DbMasterpiece.class, qualifier3 );

            resultDB = context.performQuery( select3 );

            log( " getMasterPieces() returned ", resultDB );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<MasterPiece>();
        }

        // convert objects s
        // and return
        return ObjectsConverter.convertMasterpieces( resultDB );
    }

    @Override
    public ArrayList<Museum> getMuseums()
    {
        log( "  getMuseums()  " );
        // init
        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();
        }
        catch ( RuntimeException e )
        {
            e.printStackTrace();
            return new ArrayList<Museum>();
        }

        // get objects
        List<DbMuseum> resultDB;
        try
        {
            SelectQuery select1 = new SelectQuery( DbMuseum.class );
            resultDB = context.performQuery( select1 );
            log( "  getMuseums() returned ", resultDB );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Museum>();
        }

        // convert objects s
        // and return
        return ObjectsConverter.convertMuseums( resultDB );
    }

    @Override
    public ArrayList<Genre> getGenres()
    {
        log( "  getGenres() " );
        // init
        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();
        }
        catch ( RuntimeException e )
        {
            e.printStackTrace();
            return new ArrayList<Genre>();
        }

        // get objects
        List<DbGenre> resultDB;
        try
        {
            SelectQuery select1 = new SelectQuery( DbGenre.class );
            resultDB = context.performQuery( select1 );
            log( "  getGenres() returned ", resultDB );
        }
        catch ( RuntimeException e )
        {
            e.printStackTrace();
            return new ArrayList<Genre>();
        }

        // convert objects s
        // and return
        return ObjectsConverter.convertGenres( resultDB );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public ArrayList<Artist> getArtistsByGenre(
        Genre genre )
    {

        // log
        log( "  getArtistsByGenre()  " );

        // init
        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Artist>();
        }

        // validation
        if ( genre == null || genre.getId() == null || genre.getId() <= 0 )
        {
            logError( " No GENRE ID" );
            return new ArrayList<Artist>();
        }
        log( "Genre id:" + genre.getId() );

        // get objects
        List<DbArtist> actorsDB = new ArrayList<DbArtist>();
        try
        {

            DbGenre g = (DbGenre) DataObjectUtils.objectForPK( context, DbGenre.class, genre.getId() );
            if ( g == null )
            {
                logError( " No Genre in DataBase " );
                return new ArrayList<Artist>();
            }

            log( " got DbGenre from database " );

            Expression qualifier = ExpressionFactory.matchExp( DbPainting.MY_GENRE_PROPERTY, g.getId() );
            SelectQuery select = new SelectQuery( DbMasterpiece.class, qualifier );
            List<DbMasterpiece> mp = context.performQuery( select );

            for ( DbMasterpiece m : mp )
            {
                actorsDB.add( m.getMyArtist() );
            }

            log( "  getArtistsByGenre() returned ", actorsDB );
            // convert objects
            // and return
            return ObjectsConverter.convertArtists( actorsDB );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Artist>();
        }
    }

    @Override
    public ArrayList<Artist> getArtistsByMuseum(
        Museum museum )
    {
        // init
        try
        {
            log( " getArtistsByMuseum() " );
            DataContext context = DataContext.getThreadDataContext();
            // validation
            Assert.assertNotNull( "museum not correct", museum );
            Assert.assertNotNull( "museum not correct", museum.getId() );
            Assert.assertTrue( "museum not correct", museum.getId() > 0 );

            DbMuseum dbMuseum = (DbMuseum) DataObjectUtils.objectForPK( context, DbMuseum.class, museum.getId() );
            log( " got DbMuseum from database " );

            // все произведения музея
            List<DbMasterpiece> masterpieces = dbMuseum.getMasterpieces( context );

            // художники, которые их создали
            ArrayList<Artist> artists = new ArrayList<Artist>();
            for ( DbMasterpiece dbMasterpiece : masterpieces )
            {
                Artist artist = ObjectsConverter.convertArtist( dbMasterpiece.getMyArtist() );
                if ( !artists.contains( artist ) )
                {
                    artists.add( artist );
                }
            }

            return artists;
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            return new ArrayList<Artist>();
        }
    }

    @Override
    public ArrayList<Painting> getPaintings(
        Artist artist )
    {
        // log
        log( "  getPaintings(artist)  " );

        // init
        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Painting>();
        }

        // validation
        if ( artist == null || artist.getId() == null || artist.getId() <= 0 )
        {
            logError( " No Artist ID " );
            return new ArrayList<Painting>();
        }
        log( "artist id:" + artist.getId() );

        // get objects
        List<DbPainting> resultDB;
        try
        {
            DbArtist a = (DbArtist) DataObjectUtils.objectForPK( context, DbArtist.class, artist.getId() );

            if ( a == null )
            {
                logError( " No Artist in DataBase " );
                return new ArrayList<Painting>();
            }

            resultDB = a.getArtistPaintings();

            /*SelectQuery select1 = new SelectQuery(DbPainting.class);
            
            Map params = new HashMap();
            params.put(DbPainting.MY_ARTIST_PROPERTY, a.getId() );
            select1 = select1.queryWithParameters(params);
            
            
            resultDB = context.performQuery(select1 ); */

            log( "  getPaintings() returned ", resultDB );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Painting>();
        }

        // convert objects
        // and return
        return ObjectsConverter.convertPaintings( resultDB );
    }

    @Override
    public ArrayList<Sculpture> getSculptures(
        Artist artist )
    {
        // log
        log( "  getPaintings(artist)  " );

        // init
        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Sculpture>();
        }

        // validation
        if ( artist == null || artist.getId() == null || artist.getId() <= 0 )
        {
            logError( " No Artist ID " );
            return new ArrayList<Sculpture>();
        }
        log( "artist id:" + artist.getId() );

        // get objects
        List<DbSculpture> resultDB;
        try
        {
            DbArtist a = (DbArtist) DataObjectUtils.objectForPK( context, DbArtist.class, artist.getId() );

            if ( a == null )
            {
                logError( " No Artist in DataBase " );
                return new ArrayList<Sculpture>();
            }

            resultDB = a.getArtistPaintings();

            /*
            	if(picasso != null){
            		Map params = new HashMap();
            		params.put(DbSculpture.MY_ARTIST_PROPERTY, picasso.getId() );
            		select1 = select1.queryWithParameters(params);
            	}
            }
            
            resultDB = context.performQuery(select1 );*/

            log( "  getSculptures() returned ", resultDB );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return new ArrayList<Sculpture>();
        }

        // convert objects
        // and return
        return ObjectsConverter.convertSculptures( resultDB );
    }

    private void logError(
        String str )
    {
        log( "ERROR: " + str );
    }

    private void log(
        String str )
    {
        System.out.println( "[EntityExtractorImpl] " + str );
        // log.info("[EntityExtractorImpl] " + str);
    }

    private void log(
        String str,
        List l )
    {
        System.out.print( "[EntityExtractorImpl] " + str );
        // log.info("[EntityExtractorImpl] " + str);
        if ( l == null )
        {
            System.out.println( " NULL " );
            // log.info(" NULL ");
        }
        else
        {
            System.out.println( l.size() + ". " );
            // log.info(l.size() + ". ");
        }

    }

}
