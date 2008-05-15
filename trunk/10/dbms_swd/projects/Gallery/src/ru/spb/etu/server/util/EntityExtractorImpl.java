package ru.spb.etu.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.commons.logging.Log;

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

	
    @SuppressWarnings("unchecked")
	@Override
    public ArrayList<Artist> getArtists()
    {
    	log(" getArtists() ");
    	//init
    	DataContext context;
		try {
			context = DataContext.createDataContext();
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
    	log(" getMasterPieces() ");
    	//init
    	DataContext context;
		try {
			context = DataContext.createDataContext();
		} catch (RuntimeException e) {			
			e.printStackTrace();
			return new ArrayList<MasterPiece>();
		}
    	
    	//get objects
    	List<DbMasterpiece> resultDB;
		try {
			//TODO filter by artist!!!
			
			
			Map params = new HashMap();
			
			if(artist.getBirthDate() != null ) params.put("BirthDate", artist.getBirthDate());
			if(artist.getCountry() != null ) params.put("Country", artist.getCountry());
			if(artist.getDescription() != null )params.put("Description", artist.getDescription());
			if(artist.getImageUrl() != null )params.put("ImageUrl", artist.getImageUrl());
			if(artist.getName() != null )params.put("Name", artist.getName());
			if(artist.getTitle() != null )params.put("Title", artist.getTitle());
			
			SelectQuery select1 = new SelectQuery(DbMasterpiece.class);
			SelectQuery query2 = select1.queryWithParameters(params);
			
			
			resultDB = context.performQuery(query2);
			
			log("  getMasterPieces() returned ", resultDB);
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
			context = DataContext.createDataContext();
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
			context = DataContext.createDataContext();
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

    @Override
    public ArrayList<Artist> getArtistsByGenre(
        Genre genre )
    {
		log("  getArtistsByGenre()  ");
    	//init
    	DataContext context;
		try {
			context = DataContext.createDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Artist>();
		}
    	
    	//get objects
    	List<DbArtist> actorsDB;
		try {
			//TODO add filter

			Map params = new HashMap();
			
			if(genre.getDescription() != null )params.put("Description", genre.getDescription());
			if(genre.getImageUrl() != null )params.put("ImageUrl", genre.getImageUrl());
			if(genre.getName() != null )params.put("Name", genre.getName());
			if(genre.getTitle() != null )params.put("Title", genre.getTitle());
			
			SelectQuery select1 = new SelectQuery(DbArtist.class);
			SelectQuery query2 = select1.queryWithParameters(params);
			
			actorsDB = context.performQuery(query2);
			
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
    public ArrayList<Artist> getArtistsByMuseum(
        Museum museum )
    {
    	log(  "  getArtistsByMuseum()  " );
    	//init
    	DataContext context;
		try {
			context = DataContext.createDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Artist>();
		}
    	
    	//get objects
    	List<DbArtist> actorsDB;
		try {
			//TODO add filter
			
			
			Map params = new HashMap();
			
			if(museum.getDescription() != null )params.put("Description", museum.getDescription());
			if(museum.getImageUrl() != null )params.put("ImageUrl", museum.getImageUrl());
			if(museum.getName() != null )params.put("Name", museum.getName());
			if(museum.getTitle() != null )params.put("Title", museum.getTitle());
			
			SelectQuery select1 = new SelectQuery(DbArtist.class);
			SelectQuery query2 = select1.queryWithParameters(params);
			
			
			actorsDB = context.performQuery(query2);
			
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
			context = DataContext.createDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Painting>();
		}
    	
    	//get objects
    	List<DbPainting> resultDB;
		try {
			//TODO add filter
			

			Map params = new HashMap();
			

			if(artist.getBirthDate() != null ) params.put("BirthDate", artist.getBirthDate());
			if(artist.getCountry() != null ) params.put("Country", artist.getCountry());
			if(artist.getDescription() != null )params.put("Description", artist.getDescription());
			if(artist.getImageUrl() != null )params.put("ImageUrl", artist.getImageUrl());
			if(artist.getName() != null )params.put("Name", artist.getName());
			if(artist.getTitle() != null )params.put("Title", artist.getTitle());
			
			
			SelectQuery select1 = new SelectQuery(DbPainting.class);
			SelectQuery query2 = select1.queryWithParameters(params);
			
			
			resultDB = context.performQuery(query2);
			
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
			context = DataContext.createDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return new ArrayList<Sculpture>();
		}
    	
    	//get objects
    	List<DbSculpture> resultDB;
		try {
			//TODO add filter
			Map params = new HashMap();
			

			if(artist.getBirthDate() != null ) params.put("BirthDate", artist.getBirthDate());
			if(artist.getCountry() != null ) params.put("Country", artist.getCountry());
			if(artist.getDescription() != null )params.put("Description", artist.getDescription());
			if(artist.getImageUrl() != null )params.put("ImageUrl", artist.getImageUrl());
			if(artist.getName() != null )params.put("Name", artist.getName());
			if(artist.getTitle() != null )params.put("Title", artist.getTitle());
			
			
			SelectQuery select1 = new SelectQuery(DbPainting.class);
			SelectQuery query2 = select1.queryWithParameters(params);
			
			resultDB = context.performQuery(query2);
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
    }
    
    private void log(String str, List l){
    	System.out.print("[EntityExtractorImpl] " + str);
    	if(l == null) {
    		System.out.println(" NULL ");
    	} else {
    		System.out.println(l.size() + ". ");
    	}
    }
    
}