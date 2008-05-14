package ru.spb.etu.server.util;

import java.util.ArrayList;
import java.util.Date;

import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.DeleteDenyException;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Artist;
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

    @Override
    public void saveOrUpdate( EntityWrapper entityWrapper )
    {
    	log(" saveOrUpdate " + entityWrapper.getClass() );
    	DataContext context;
		try {
			context = DataContext.createDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return;
		} 
				
		//TODO check if no - then create
		if( entityWrapper instanceof Artist ){
			Artist e = (Artist) (entityWrapper);
			DbArtist a;
			
			Expression qualifier = ExpressionFactory.matchExp( DbArtist.NAME_PROPERTY, e.getName() );
			SelectQuery select = new SelectQuery(DbArtist.class, qualifier);
			a = (DbArtist) DataObjectUtils.objectForQuery(context, select);
			
			if(a == null) {
				log(" new object ");
	            a = (DbArtist) context.newObject(DbArtist.class);
	        }

			a.setBirthdate(e.getBirthDate());
			a.setCountry(e.getCountry().toString());
			a.setDescription(e.getDescription().toString());
			a.setImageUrl(e.getImageUrl());
			a.setName(e.getName().toString());
		
			context.commitChanges();
		    log(" updated ! ");
		
		} else if( entityWrapper instanceof Museum ){
			Museum e = (Museum) (entityWrapper);
			DbMuseum a;
			
			Expression qualifier = ExpressionFactory.matchExp( DbMuseum.NAME_PROPERTY, e.getName() );
			SelectQuery select = new SelectQuery(DbMuseum.class, qualifier);
			a = (DbMuseum) DataObjectUtils.objectForQuery(context, select);
			
			if(a == null) {
				log(" new object ");
	            a = (DbMuseum) context.newObject(DbMuseum.class);
	        }

			a.setName(e.getName().toString());
			a.setDescription(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			
			context.commitChanges();
		    log(" updated ! ");
			
		} else  if( entityWrapper instanceof Genre ) {
			Genre e = (Genre) (entityWrapper);
			DbGenre a;
			
			Expression qualifier = ExpressionFactory.matchExp( DbGenre.NAME_PROPERTY, e.getName() );
			SelectQuery select = new SelectQuery(DbGenre.class, qualifier);
			a = (DbGenre) DataObjectUtils.objectForQuery(context, select);
			
			if(a == null) {
				log(" new object ");
	            a = (DbGenre) context.newObject(DbGenre.class);
	        }

			a.setDescrption(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			a.setName(e.getName().toString());
		
			context.commitChanges();
		    log(" updated ! ");
		} else  if( entityWrapper instanceof Painting ) {
			Painting e = (Painting) (entityWrapper);
			DbPainting a;
			
			Expression qualifier = ExpressionFactory.matchExp( DbPainting.TITLE_PROPERTY, e.getTitle() );
			SelectQuery select = new SelectQuery(DbPainting.class, qualifier);
			a = (DbPainting) DataObjectUtils.objectForQuery(context, select);
			
			if(a == null) {
				log(" new object ");
	            a = (DbPainting) context.newObject(DbPainting.class);
	        }

			
			a.setDescription(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			a.setTitle(e.getTitle().toString() );
			
			int year = 0;
			try{year = Integer.parseInt(e.getCreationYear().toString());}catch(Exception ex){}
			if(year > 0) a.setCreationYear( year ); else a.setCreationYear( null );  
			
			
			int w = 0;
			try{w = Integer.parseInt(e.getWidth().toString());}catch(Exception ex){}
			if(w > 0) a.setWidth( w ); else a.setWidth( null );  
			
			int h = 0;
			try{h = Integer.parseInt(e.getHeight() .toString());}catch(Exception ex){}
			if(h > 0) a.setHeight( h ); else a.setHeight( null );  
			
			Expression qualifierM = ExpressionFactory.matchExp( DbMuseum.NAME_PROPERTY, e.getMuseum() );
			SelectQuery selectM = new SelectQuery(DbMuseum.class, qualifierM);
			DbMuseum m = (DbMuseum) DataObjectUtils.objectForQuery(context, selectM);
			
			if(m == null) {
				log(" no real Museum! ");
	        }
			a.setMymuseum(m);

			
			
			Expression qualifierG = ExpressionFactory.matchExp( DbGenre.NAME_PROPERTY, e.getGenre() );
			SelectQuery selectG = new SelectQuery(DbGenre.class, qualifierG);
			DbGenre g = (DbGenre) DataObjectUtils.objectForQuery(context, selectG);
			
			if(g == null) {
				log(" no real genre! ");
	        }
			a.setMygenre( g );
			
			
			
			Expression qualifierA = ExpressionFactory.matchExp( DbArtist.NAME_PROPERTY, e.getArtist() );
			SelectQuery selectA = new SelectQuery(DbArtist.class, qualifierA);
			DbArtist ar = (DbArtist) DataObjectUtils.objectForQuery(context, selectA);
			
			if(ar == null) {
				log(" no real artist! ");
	        }
			a.setMyartist( ar );
			
			
			context.commitChanges();
		    log(" updated ! ");
		} else  if( entityWrapper instanceof Sculpture ) {
			Sculpture e = (Sculpture) (entityWrapper);
			DbSculpture a;
			
			Expression qualifier = ExpressionFactory.matchExp( DbSculpture.TITLE_PROPERTY, e.getTitle() );
			SelectQuery select = new SelectQuery(DbSculpture.class, qualifier);
			a = (DbSculpture) DataObjectUtils.objectForQuery(context, select);
			
			if(a == null) {
				log(" new object ");
	            a = (DbSculpture) context.newObject(DbSculpture.class);
	        }

			
			a.setDescription(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			a.setTitle(e.getTitle().toString() );
			
			int year = 0;
			try{year = Integer.parseInt(e.getCreationYear().toString());}catch(Exception ex){}
			if(year > 0) a.setCreationYear( year ); else a.setCreationYear( null );  
			
			
			int w = 0;
			try{w = Integer.parseInt(e.getMass().toString());}catch(Exception ex){}
			if(w > 0) a.setMass( w ); else a.setMass( null );  
			
			
			Expression qualifierM = ExpressionFactory.matchExp( DbMuseum.NAME_PROPERTY, e.getMuseum() );
			SelectQuery selectM = new SelectQuery(DbMuseum.class, qualifierM);
			DbMuseum m = (DbMuseum) DataObjectUtils.objectForQuery(context, selectM);
			
			if(m == null) {
				log(" no real Museum! ");
	        }
			a.setMymuseum(m);

			
			
			Expression qualifierG = ExpressionFactory.matchExp( DbGenre.NAME_PROPERTY, e.getGenre() );
			SelectQuery selectG = new SelectQuery(DbGenre.class, qualifierG);
			DbGenre g = (DbGenre) DataObjectUtils.objectForQuery(context, selectG);
			
			if(g == null) {
				log(" no real genre! ");
	        }
			a.setMygenre( g );
			
			
			
			Expression qualifierA = ExpressionFactory.matchExp( DbArtist.NAME_PROPERTY, e.getArtist() );
			SelectQuery selectA = new SelectQuery(DbArtist.class, qualifierA);
			DbArtist ar = (DbArtist) DataObjectUtils.objectForQuery(context, selectA);
			
			if(ar == null) {
				log(" no real artist! ");
	        }
			a.setMyartist( ar );
			
			
			context.commitChanges();
		    log(" updated ! ");
		} else {
			log("Unknown class");
		}

		
		//TODO commit
		context.commitChanges();
    }

    @Override
    public EntityWrapper create( String type )
    {
    	log(" create ");
    	
    	if("Artist".equalsIgnoreCase(type)) return new Artist();
    	if("Genre".equalsIgnoreCase(type)) return new Genre();
    	if("MasterPiece".equalsIgnoreCase(type)) return new MasterPiece();
    	if("Museum".equalsIgnoreCase(type)) return new Museum();
    	if("Painting".equalsIgnoreCase(type)) return new Painting();
    	if("Sculpture".equalsIgnoreCase(type)) return new Sculpture();
    	
        return null;
    }

    @Override
    public void remove( EntityWrapper entityWrapper )
    {
    	log(" remove " + entityWrapper.getClass() + ".");

    	DataContext context;
		try {
			context = DataContext.createDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return;
		} 
    	
    	if(entityWrapper instanceof Artist){
    		try {
				Artist cur = (Artist) entityWrapper;

				Expression qualifier = ExpressionFactory.matchExp( DbArtist.NAME_PROPERTY, cur.getName() );
				SelectQuery select = new SelectQuery(DbArtist.class, qualifier);

				//context.performQuery(select2);
				//DbArtist picasso1 = (DbArtist) context.performQuery(select);
				DbArtist picasso = (DbArtist) DataObjectUtils.objectForQuery(context, select);
				
				if (picasso != null) {
				    context.deleteObject(picasso);
				    context.commitChanges();
				    log(" removed! ");
				} else log(" NOT found! ");
			} catch (DeleteDenyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (CayenneRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
    	} else if(entityWrapper instanceof Museum){
    		try {
    			Museum cur = (Museum) entityWrapper;

				Expression qualifier = ExpressionFactory.matchExp( DbMuseum.NAME_PROPERTY, cur.getName() );
				SelectQuery select = new SelectQuery(DbMuseum.class, qualifier);

				//context.performQuery(select2);
				//DbArtist picasso1 = (DbArtist) context.performQuery(select);
				DbMuseum picasso = (DbMuseum) DataObjectUtils.objectForQuery(context, select);
				
				if (picasso != null) {
				    context.deleteObject(picasso);
				    context.commitChanges();
				    log(" removed! ");
				} else log(" NOT found! ");
			} catch (DeleteDenyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (CayenneRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
    	} else if(entityWrapper instanceof Genre){
    		try {
    			Genre cur = (Genre) entityWrapper;

				Expression qualifier = ExpressionFactory.matchExp( DbGenre.NAME_PROPERTY, cur.getName() );
				SelectQuery select = new SelectQuery(DbGenre.class, qualifier);

				//context.performQuery(select2);
				//DbArtist picasso1 = (DbArtist) context.performQuery(select);
				DbGenre picasso = (DbGenre) DataObjectUtils.objectForQuery(context, select);
				
				if (picasso != null) {
				    context.deleteObject(picasso);
				    context.commitChanges();
				    log(" removed! ");
				} else log(" NOT found! ");
			} catch (DeleteDenyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (CayenneRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
    	}else if(entityWrapper instanceof Painting){
    		try {
    			Painting cur = (Painting) entityWrapper;

				Expression qualifier = ExpressionFactory.matchExp( DbPainting.TITLE_PROPERTY, cur.getTitle() );
				SelectQuery select = new SelectQuery(DbPainting.class, qualifier);

				//context.performQuery(select2);
				//DbArtist picasso1 = (DbArtist) context.performQuery(select);
				DbPainting picasso = (DbPainting) DataObjectUtils.objectForQuery(context, select);
				
				if (picasso != null) {
				    context.deleteObject(picasso);
				    context.commitChanges();
				    log(" removed! ");
				} else log(" NOT found! ");
			} catch (DeleteDenyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (CayenneRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
    	}else if(entityWrapper instanceof Sculpture){
    		try {
    			Sculpture cur = (Sculpture) entityWrapper;

				Expression qualifier = ExpressionFactory.matchExp( DbSculpture.TITLE_PROPERTY, cur.getTitle() );
				SelectQuery select = new SelectQuery(DbSculpture.class, qualifier);

				//context.performQuery(select2);
				//DbArtist picasso1 = (DbArtist) context.performQuery(select);
				DbSculpture picasso = (DbSculpture) DataObjectUtils.objectForQuery(context, select);
				
				if (picasso != null) {
				    context.deleteObject(picasso);
				    context.commitChanges();
				    log(" removed! ");
				} else log(" NOT found! ");
			} catch (DeleteDenyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (CayenneRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
    	} else {
    		log(" unkonwn type ");
    	}
    	
    	
    	
    	


    	
    }

    
    @Override
    public void updateBlob(
        EntityWrapper entityWrapper,
        byte[] bytes )
    {
        // TODO Auto-generated method stub

    	log(" updateBlob for " + entityWrapper.getClass() );

    }
    

    private void log(String str){
    	System.out.println("[EntityBackuperImpl] " + str);
    }

}
