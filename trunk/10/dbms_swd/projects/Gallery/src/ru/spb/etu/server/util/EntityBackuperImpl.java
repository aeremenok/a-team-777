package ru.spb.etu.server.util;

import java.text.SimpleDateFormat;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.access.DataContext;
import org.apache.log4j.Logger;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.serializable.Museum;
import ru.spb.etu.client.serializable.Painting;
import ru.spb.etu.client.serializable.Sculpture;
import ru.spb.etu.server.EntityBackuper;
import ru.spb.etu.server.model.DbArtist;
import ru.spb.etu.server.model.DbGenre;
import ru.spb.etu.server.model.DbMuseum;
import ru.spb.etu.server.model.DbPainting;
import ru.spb.etu.server.model.DbSculpture;

public class EntityBackuperImpl
    implements
        EntityBackuper
{

    protected static Logger log = Logger.getLogger( EntityBackuperImpl.class );
    static
    {
        log.setLevel( org.apache.log4j.Level.ALL );
    }

    @Override
    public int saveOrUpdate(
        EntityWrapper entityWrapper )
    {
        log.info( " SaveOrUpdate " + entityWrapper.getClass() );

        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();

            if ( entityWrapper instanceof Artist )
            {
                Artist e = (Artist) entityWrapper;
                DbArtist a = null;

                if ( e.getId() != null && e.getId() > 0 )
                {
                    a = (DbArtist) DataObjectUtils.objectForPK( context, DbArtist.class, e.getId() );
                }

                if ( a == null )
                {
                    log.info( " Creating new object... " );
                    a = (DbArtist) context.newObject( DbArtist.class );
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                a.setBirthdate( simpleDateFormat.parse( e.getBirthDate().toString() ) );
                a.setCountry( e.getCountry().toString() );
                a.setDescription( e.getDescription().toString() );
                a.setImageUrl( e.getImageUrl() );
                a.setName( e.getName().toString() );

                context.commitChanges();

                log.info( " Updated ! " );
                return a.getId();

            }
            else if ( entityWrapper instanceof Museum )
            {
                Museum e = (Museum) entityWrapper;
                DbMuseum a = null;

                if ( e.getId() != null && e.getId() > 0 )
                {
                    a = (DbMuseum) DataObjectUtils.objectForPK( context, DbMuseum.class, e.getId() );
                }

                if ( a == null )
                {
                    log.info( " Creating new object... " );
                    a = (DbMuseum) context.newObject( DbMuseum.class );
                }

                a.setName( e.getName().toString() );
                a.setDescription( e.getDescription().toString() );
                a.setImageURL( e.getImageUrl() );

                context.commitChanges();

                log.info( " Updated! " );
                return a.getId();

            }
            else if ( entityWrapper instanceof Genre )
            {
                Genre e = (Genre) entityWrapper;
                DbGenre a = null;

                if ( e.getId() != null && e.getId() > 0 )
                {
                    a = (DbGenre) DataObjectUtils.objectForPK( context, DbGenre.class, e.getId() );
                }

                if ( a == null )
                {
                    log.info( " Creating new object... " );
                    a = (DbGenre) context.newObject( DbGenre.class );
                }

                a.setDescrption( e.getDescription().toString() );
                a.setImageURL( e.getImageUrl() );
                a.setName( e.getName().toString() );

                context.commitChanges();

                log.info( " Updated ! " );
                return a.getId();

            }
            else if ( entityWrapper instanceof Painting )
            {
                Painting e = (Painting) entityWrapper;
                DbPainting a = null;

                if ( e.getArtist() == null || e.getGenre() == null || e.getMuseum() == null )
                {
                    log.info( " No artist id or genre id or museum id for painting!  " );
                    return 0;
                }

                if ( e.getId() != null && e.getId() > 0 )
                {
                    a = (DbPainting) DataObjectUtils.objectForPK( context, DbPainting.class, e.getId() );
                }

                if ( a == null )
                {
                    log.info( " Creating new object... " );
                    a = (DbPainting) context.newObject( DbPainting.class );

                }

                a.setDescription( e.getDescription().toString() );
                a.setImageURL( e.getImageUrl() );
                a.setTitle( e.getTitle().toString() );
                a.setIdType( DbArtist.PICTURE_ID_TYPE );

                int year = 0;
                try
                {
                    year = Integer.parseInt( e.getCreationYear().toString() );
                }
                catch ( Exception ex )
                {
                }
                if ( year > 0 )
                    a.setCreationYear( year );
                else
                    a.setCreationYear( null );

                int w = 0;
                try
                {
                    w = Integer.parseInt( e.getWidth().toString() );
                }
                catch ( Exception ex )
                {
                }
                if ( w > 0 )
                    a.setWidth( w );
                else
                    a.setWidth( null );

                int h = 0;
                try
                {
                    h = Integer.parseInt( e.getHeight().toString() );
                }
                catch ( Exception ex )
                {
                }
                if ( h > 0 )
                    a.setHeight( h );
                else
                    a.setHeight( null );

                DbMuseum m = null;
                if ( e.getMuseum() != null && e.getMuseum().getId() > 0 )
                {
                    m = (DbMuseum) DataObjectUtils.objectForPK( context, DbMuseum.class, e.getMuseum().getId() );
                }
                if ( m == null )
                {
                    log.info( " No Museum id in database!" );
                    return 0;
                }
                a.setMyMuseum( m );

                DbGenre g = null;
                if ( e.getGenre() != null && e.getGenre().getId() > 0 )
                {
                    g = (DbGenre) DataObjectUtils.objectForPK( context, DbGenre.class, e.getGenre().getId() );
                }
                if ( g == null )
                {
                    log.info( " No genre id in database! " );
                    return 0;
                }
                a.setMyGenre( g );

                DbArtist ar = null;
                if ( e.getMuseum() != null && e.getArtist().getId() > 0 )
                {
                    ar = (DbArtist) DataObjectUtils.objectForPK( context, DbArtist.class, e.getArtist().getId() );
                }

                if ( ar == null )
                {
                    log.info( " no real artist! " );
                    return 0;
                }
                a.setMyArtist( ar );

                context.commitChanges();
                log.info( " updated ! " );
                return a.getId();

            }
            else if ( entityWrapper instanceof Sculpture )
            {
                Sculpture e = (Sculpture) entityWrapper;
                DbSculpture a = null;

                if ( e.getArtist() == null || e.getGenre() == null || e.getMuseum() == null )
                {
                    log.info( " No artist id or genre id or museum id for painting!" );
                    return 0;
                }

                if ( e.getId() != null && e.getId() > 0 )
                {
                    a = (DbSculpture) DataObjectUtils.objectForPK( context, DbSculpture.class, e.getId() );
                }

                if ( a == null )
                {
                    log.info( " Creating new object... " );
                    a = (DbSculpture) context.newObject( DbSculpture.class );
                }

                a.setDescription( e.getDescription().toString() );
                a.setImageURL( e.getImageUrl() );
                a.setTitle( e.getTitle().toString() );
                a.setIdType( DbArtist.SCULPTURE_ID_TYPE );

                int year = 0;
                try
                {
                    year = Integer.parseInt( e.getCreationYear().toString() );
                }
                catch ( Exception ex )
                {
                }
                if ( year > 0 )
                    a.setCreationYear( year );
                else
                    a.setCreationYear( null );

                int w = 0;
                try
                {
                    w = Integer.parseInt( e.getMass().toString() );
                }
                catch ( Exception ex )
                {
                }
                if ( w > 0 )
                    a.setMass( w );
                else
                    a.setMass( null );

                DbMuseum m = null;
                if ( e.getId() != null && e.getId() > 0 )
                {
                    m = (DbMuseum) DataObjectUtils.objectForPK( context, DbMuseum.class, e.getId() );
                }

                if ( m == null )
                {
                    log.info( " no real Museum! " );
                    return 0;
                }
                a.setMyMuseum( m );

                DbGenre g = null;
                if ( e.getId() != null && e.getId() > 0 )
                {
                    g = (DbGenre) DataObjectUtils.objectForPK( context, DbGenre.class, e.getId() );
                }

                if ( g == null )
                {
                    log.info( " no real genre! " );
                    return 0;
                }
                a.setMyGenre( g );

                DbArtist ar = null;
                if ( e.getId() != null && e.getId() > 0 )
                {
                    ar = (DbArtist) DataObjectUtils.objectForPK( context, DbArtist.class, e.getId() );
                }
                if ( ar == null )
                {
                    log.info( " no real artist! " );
                    return 0;
                }
                a.setMyArtist( ar );

                context.commitChanges();
                log.info( " updated ! " );
                return a.getId();
            }

            log.info( "Unknown class!!! " );
            return 0;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public EntityWrapper create(
        String type )
    {
        log.info( " Creating...  " );

        if ( "Artist".equalsIgnoreCase( type ) )
            return new Artist();
        if ( "Genre".equalsIgnoreCase( type ) )
            return new Genre();
        if ( "MasterPiece".equalsIgnoreCase( type ) )
            return new MasterPiece();
        if ( "Museum".equalsIgnoreCase( type ) )
            return new Museum();
        if ( "Painting".equalsIgnoreCase( type ) )
            return new Painting();
        if ( "Sculpture".equalsIgnoreCase( type ) )
            return new Sculpture();

        log.info( " Unsupported type! " );
        return null;
    }

    @Override
    public void remove(
        EntityWrapper entityWrapper )
    {
        log.info( " Removing " + entityWrapper.getClass() + ", id:" + entityWrapper.getId() );

        DataContext context;
        try
        {
            context = DataContext.getThreadDataContext();

            if ( entityWrapper instanceof Artist )
            {
                DbArtist dbEntity = null;
                if ( entityWrapper.getId() != null && entityWrapper.getId() > 0 )
                {
                    dbEntity = (DbArtist) DataObjectUtils.objectForPK( context, DbArtist.class, entityWrapper.getId() );
                }

                if ( dbEntity != null )
                {
                    context.deleteObject( dbEntity );
                    context.commitChanges();
                    log.info( " removed! " );
                }
                else
                    log.info( " NOT found! " );

            }
            else if ( entityWrapper instanceof Museum )
            {
                DbMuseum dbEntity = null;
                if ( entityWrapper.getId() != null && entityWrapper.getId() > 0 )
                {
                    dbEntity = (DbMuseum) DataObjectUtils.objectForPK( context, DbMuseum.class, entityWrapper.getId() );
                }

                if ( dbEntity != null )
                {
                    context.deleteObject( dbEntity );
                    context.commitChanges();
                    log.info( " removed! " );
                }
                else
                    log.info( " NOT found! " );

            }
            else if ( entityWrapper instanceof Genre )
            {
                DbGenre dbEntity = null;
                if ( entityWrapper.getId() != null && entityWrapper.getId() > 0 )
                {
                    dbEntity = (DbGenre) DataObjectUtils.objectForPK( context, DbGenre.class, entityWrapper.getId() );
                }

                if ( dbEntity != null )
                {
                    context.deleteObject( dbEntity );
                    context.commitChanges();
                    log.info( " removed! " );
                }
                else
                    log.info( " NOT found! " );

            }
            else if ( entityWrapper instanceof Painting )
            {
                DbPainting picasso = null;
                if ( entityWrapper.getId() != null && entityWrapper.getId() > 0 )
                {
                    picasso =
                              (DbPainting) DataObjectUtils.objectForPK( context, DbPainting.class,
                                                                        entityWrapper.getId() );
                }

                if ( picasso != null )
                {
                    context.deleteObject( picasso );
                    context.commitChanges();
                    log.info( " removed! " );
                }
                else
                    log.info( " NOT found! " );

            }
            else if ( entityWrapper instanceof Sculpture )
            {
                DbSculpture picasso = null;
                if ( entityWrapper.getId() != null && entityWrapper.getId() > 0 )
                {
                    picasso =
                              (DbSculpture) DataObjectUtils.objectForPK( context, DbSculpture.class,
                                                                         entityWrapper.getId() );
                }

                if ( picasso != null )
                {
                    context.deleteObject( picasso );
                    context.commitChanges();
                    log.info( " removed! " );
                }
                else
                    log.info( " NOT found! " );

            }
            else
            {
                log.info( " unkonwn type " );
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return;
        }

    }

    @Override
    public void updateBlob(
        EntityWrapper entityWrapper,
        byte[] bytes )
    {
        // TODO Auto-generated method stub

        log.info( " updateBlob for " + entityWrapper.getClass() );

    }

}
