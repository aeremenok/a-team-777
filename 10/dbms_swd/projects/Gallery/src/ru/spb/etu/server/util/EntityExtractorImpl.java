package ru.spb.etu.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Collections;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.RelationshipQuery;
import org.apache.cayenne.query.SelectQuery;
import org.apache.commons.logging.Log;
//import org.apache.log4j.Logger;



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

public class EntityExtractorImpl implements EntityExtractor
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
	
    @SuppressWarnings("unchecked")
	@Override
    public ArrayList<Artist> getArtists()
    {
    	log(" getArtists() ");
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Artist>();
		}
    	
    	//get objects
    	List<DbArtist> actorsDB;
		try {
			SelectQuery select1 = new SelectQuery(DbArtist.class);
			actorsDB = context.performQuery(select1);
			log(" getArtists() returned ", actorsDB );
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Artist>();
		}

    	// convert objects 
    	// and return 
        return ObjectsConverter.convertArtists(actorsDB);
    }

    
    @SuppressWarnings("unchecked")
	@Override
    public ArrayList<MasterPiece> getMasterPieces( Artist artist )
    {
    	log(" getMasterPieces(Artist) ");
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (RuntimeException e) {			
			e.printStackTrace();
			return new ArrayList<MasterPiece>();
		}
    	
    	//get objects
    	List<DbMasterpiece> resultDB;
		try {
			//TODO filter by artist!!!
			if(artist == null || artist.getId() == null || artist.getId() <= 0) {
				log(" No Artist ID ");
				return new ArrayList<MasterPiece>();
			}
			
			log(" artist id = " + artist.getId() );
			
			
			DbArtist a = (DbArtist) DataObjectUtils.objectForPK(context, DbArtist.class, artist.getId());
			if(a == null){
				log(" No Artist in DataBase ");
				return new ArrayList<MasterPiece>();
			}
			log(" got artist from database ");
			
			
			try{
				
				resultDB = a.getArtistMasterpiece();
				
				log( " resultDB is:" + resultDB.isEmpty() + " | size:" + resultDB.size() );
				log(" getMasterPieces() returned ", resultDB);
				
				return ObjectsConverter.convertMasterpieces(resultDB);
				
			}catch(Exception e){
				e.printStackTrace();
				log("got exception on a.getArtistMasterpiece() ");
			}
			log("trying another way...");
			
			
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
			Expression qualifier3 = Expression.fromString(q);
			
			//Map m = Collections.singletonMap("artistid", artist.getId()); //Collections.singletonMap("genreid", genre.getId()
			//qualifier3 = qualifier3.expWithParameters(m);
			
			SelectQuery select3 = new SelectQuery(DbMasterpiece.class, qualifier3);
			
			resultDB = context.performQuery(select3);
			
			log(" getMasterPieces() returned ",resultDB);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ArrayList<MasterPiece>();
		}

    	// convert objects s
    	// and return 
        return ObjectsConverter.convertMasterpieces(resultDB);
    }
    

    @Override
    public ArrayList<Museum> getMuseums()
    {
    	log("  getMuseums()  ");
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (RuntimeException e) {			
			e.printStackTrace();
			return new ArrayList<Museum>();
		}
    	
    	//get objects
    	List<DbMuseum> resultDB;
		try {
			SelectQuery select1 = new SelectQuery(DbMuseum.class);
			resultDB = context.performQuery(select1);
			log("  getMuseums() returned ", resultDB);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ArrayList<Museum>();
		}

    	// convert objects s
    	// and return 
        return ObjectsConverter.convertMuseums(resultDB);
    }

    
    @Override
    public ArrayList<Genre> getGenres()
    {
    	log("  getGenres() ");
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (RuntimeException e) {			
			e.printStackTrace();
			return new ArrayList<Genre>();
		}
    	
    	//get objects
    	List<DbGenre> resultDB;
		try {
			SelectQuery select1 = new SelectQuery(DbGenre.class);
			resultDB = context.performQuery(select1);
			log("  getGenres() returned ", resultDB);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ArrayList<Genre>();
		}

    	// convert objects s
    	// and return 
        return ObjectsConverter.convertGenres(resultDB);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ArrayList<Artist> getArtistsByGenre( Genre genre )
    {
		log("  getArtistsByGenre()  ");
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Artist>();
		}
    	
    	//get objects
    	List<DbArtist> actorsDB;
		try {
			//TODO add filter
			SelectQuery select1 = new SelectQuery(DbArtist.class);
			if(genre != null && genre.getId() != null && genre.getId() > 0) {
					
					String q = "DbGenre.id = $genreid";
					Expression qualifier3 = Expression.fromString(q);
					Map m = new HashMap();
					m.put("genreid", genre.getId()); //Collections.singletonMap("genreid", genre.getId()
					qualifier3 = qualifier3.expWithParameters(m);
					SelectQuery select3 = new SelectQuery(DbArtist.class, qualifier3);
					List paintings3 = context.performQuery(select3);
					
					/*Map params = new HashMap();
					params.put(DbArtist.MYGENRE_PROPERTY, picasso.getId() );
					select1 = select1.queryWithParameters(params);*/
				
			} else{
				
				log(" No GENRE ID");
				return new ArrayList<Artist>();
			}
			
			actorsDB = context.performQuery(select1 );
			
			log("  getArtistsByGenre() returned ", actorsDB);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Artist>();
		}

    	// convert objects 
    	// and return 
        return ObjectsConverter.convertArtists(actorsDB);
    }
    
    
    @Override
    public ArrayList<Artist> getArtistsByMuseum( Museum museum )
    {
    	log(  " getArtistsByMuseum() " );
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Artist>();
		}
    	
    	//get objects
    	List<DbArtist> actorsDB = null;
		try {
			//TODO add filter
			SelectQuery select1 = new SelectQuery(DbArtist.class);
			if(museum != null){
				DbMuseum picasso  = null;
				if(museum.getId() != null && museum.getId() > 0) {
					picasso = (DbMuseum) DataObjectUtils.objectForPK(context, DbMuseum.class, museum.getId());
			    }
				if(museum != null){
					ObjectId id = new ObjectId("Artist", "ARTIST_ID", 55);
					RelationshipQuery query = new RelationshipQuery(id, "paintingArray", true);
					actorsDB = context.performQuery(query);

					/*Map params = new HashMap();
					params.put(DbArtist.MYGENRE_PROPERTY, picasso.getId() );
					select1 = select1.queryWithParameters(params);*/
				}
			} else {
				actorsDB = context.performQuery(select1 );
			}


			log("  getArtistsByGenre() returned ", actorsDB);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Artist>();
		}

    	// convert objects 
    	// and return 
        return ObjectsConverter.convertArtists(actorsDB);
    }

    @Override
    public ArrayList<Painting> getPaintings(
        Artist artist )
    {
    	log("  getPaintings()  ");
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Painting>();
		}
    	
    	//get objects
    	List<DbPainting> resultDB;
		try {
			//TODO add filter
			SelectQuery select1 = new SelectQuery(DbPainting.class);
			if(artist != null){
				DbArtist picasso  = null;
				if(artist.getId() != null && artist.getId() > 0) {
					picasso = (DbArtist) DataObjectUtils.objectForPK(context, DbArtist.class, artist.getId() );
			    }
				if(picasso != null){
					Map params = new HashMap();
					params.put(DbPainting.MY_ARTIST_PROPERTY, picasso.getId() );
					select1 = select1.queryWithParameters(params);
				}
			}
			
			resultDB = context.performQuery(select1 );




			log("  getPaintings() returned ", resultDB);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Painting>();
		}

    	// convert objects 
    	// and return 
        return ObjectsConverter.convertPaintings(resultDB);
    }

    @Override
    public ArrayList<Sculpture> getSculptures(
        Artist artist )
    {
    	log("  getSculptures()  ");
    	//init
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Sculpture>();
		}
    	
    	//get objects
    	List<DbSculpture> resultDB;
		try {
			//TODO add filter
			
			SelectQuery select1 = new SelectQuery(DbPainting.class);
			if(artist != null){
				DbArtist picasso  = null;
				if(artist.getId() != null && artist.getId() > 0) {
					picasso = (DbArtist) DataObjectUtils.objectForPK(context, DbArtist.class, artist.getId());
			    }
				if(picasso != null){
					Map params = new HashMap();
					params.put(DbSculpture.MY_ARTIST_PROPERTY, picasso.getId() );
					select1 = select1.queryWithParameters(params);
				}
			}
			
			resultDB = context.performQuery(select1 );
			log("  getSculptures() returned ", resultDB);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Sculpture>();
		}

    	// convert objects 
    	// and return 
        return ObjectsConverter.convertSculptures(resultDB);
    }

    private void log(String str){
    	System.out.println("[EntityExtractorImpl] " + str);
    	//log.info("[EntityExtractorImpl] " + str);
    }
    
    private void log(String str, List l){
    	System.out.print("[EntityExtractorImpl] " + str);
    	//log.info("[EntityExtractorImpl] " + str);
    	if(l == null) {
    		System.out.println(" NULL ");
    		//log.info(" NULL ");
    	} else {
    		System.out.println(l.size() + ". ");
    		//log.info(l.size() + ". ");
    	}
    	
    }
    
}
