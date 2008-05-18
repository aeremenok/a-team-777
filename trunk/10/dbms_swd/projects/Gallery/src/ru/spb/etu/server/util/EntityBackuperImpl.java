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
    public int saveOrUpdate( EntityWrapper entityWrapper )
    {
    	log(" saveOrUpdate " + entityWrapper.getClass() );
    	DataContext context;
		try {
			context = DataContext.getThreadDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return 0;
		} 
				
		//TODO check if no - then create
		if( entityWrapper instanceof Artist ){
			Artist e = (Artist) (entityWrapper);
			DbArtist a = null;
			
			if(e.getId() != null && e.getId() > 0) {
		        a = (DbArtist) DataObjectUtils.objectForPK(context, DbArtist.class, e.getId());
		    }
						
			if(a == null) {
				log(" new object ");
	            a = (DbArtist) context.newObject(DbArtist.class);	            
	        }
			
			a.setBirthdate(e.getBirthDate());
			a.setCountry(e.getCountry().toString());
			a.setDescription(e.getDescription().toString());
			a.setImageUrl(e.getImageUrl());
			a.setName(e.getName().toString());
		
			try {
				context.commitChanges();
			} catch (CayenneRuntimeException e1) {
				log(" ERROR:   ");
				e1.printStackTrace();
				return 0;
			}
		    log(" updated ! ");
		    
		    return a.getId();
		    
		} else if( entityWrapper instanceof Museum ){
			Museum e = (Museum) (entityWrapper);
			DbMuseum a = null;
			
			if(e.getId() != null && e.getId() > 0) {
		        a = (DbMuseum) DataObjectUtils.objectForPK(context, DbMuseum.class, e.getId());
		    }
			
			if(a == null) {
				log(" new object ");
	            a = (DbMuseum) context.newObject(DbMuseum.class);
	            
	        }

			a.setName(e.getName().toString());
			a.setDescription(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			
			try {
				context.commitChanges();
			} catch (CayenneRuntimeException e1) {
				log(" ERROR:   ");
				e1.printStackTrace();
				return 0;
			}
		    log(" updated ! ");
		    
		    return a.getId();
			
		} else  if( entityWrapper instanceof Genre ) {
			Genre e = (Genre) (entityWrapper);
			DbGenre a = null;
			
			if(e.getId() != null && e.getId() > 0) {
		        a = (DbGenre) DataObjectUtils.objectForPK(context, DbGenre.class, e.getId());
		    }
			
			if(a == null) {
				log(" new object ");
	            a = (DbGenre) context.newObject(DbGenre.class);
	        }

			a.setDescrption(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			a.setName(e.getName().toString());
		
			try {
				context.commitChanges();
			} catch (CayenneRuntimeException e1) {
				log(" ERROR:   ");
				e1.printStackTrace();
				return 0;
			}
		    log(" updated ! ");
		    
		    return a.getId();
		} else  if( entityWrapper instanceof Painting ) {
			Painting e = (Painting) (entityWrapper);
			DbPainting a = null;
			
			if(e.getArtist() == null 
					|| e.getGenre()== null 
					|| e.getMuseum() == null   					
			){
				log(" incorrect! ");
				return 0;
			}
			
			if(e.getId() != null && e.getId() > 0) {
		        a = (DbPainting) DataObjectUtils.objectForPK(context, DbPainting.class, e.getId());
		    }
			
			if(a == null) {
				log(" new object ");
	            a = (DbPainting) context.newObject(DbPainting.class);
	           
	        }

			
			a.setDescription(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			a.setTitle(e.getTitle().toString() );
			a.setIdType( DbArtist.PICTURE_ID_TYPE );
			
			
			int year = 0;
			try{year = Integer.parseInt(e.getCreationYear().toString());}catch(Exception ex){}
			if(year > 0) a.setCreationYear( year ); else a.setCreationYear( null );  
			
			
			int w = 0;
			try{w = Integer.parseInt(e.getWidth().toString());}catch(Exception ex){}
			if(w > 0) a.setWidth( w ); else a.setWidth( null );  
			
			int h = 0;
			try{h = Integer.parseInt(e.getHeight() .toString());}catch(Exception ex){}
			if(h > 0) a.setHeight( h ); else a.setHeight( null );  
			
			DbMuseum m = null;
			if(e.getMuseum() != null && e.getMuseum().getId() > 0) {
		        m = (DbMuseum) DataObjectUtils.objectForPK(context, DbMuseum.class, e.getMuseum().getId() );
		    }
			
			if(m == null) {
				log(" no real Museum! ");
				return 0;
	        }
			a.setMyMuseum(m);

			DbGenre g = null;
			 if(e.getGenre() != null && e.getGenre().getId() > 0) {
		        g = (DbGenre) DataObjectUtils.objectForPK(context, DbGenre.class, e.getGenre().getId() );
		    } 
			 
			if(g == null) {
				log(" no real genre! ");
				return 0;
	        }
			a.setMyGenre( g );
			
			DbArtist ar = null;
			if(e.getMuseum() != null && e.getArtist().getId() > 0) {
		        ar = (DbArtist) DataObjectUtils.objectForPK(context, DbArtist.class, e.getArtist().getId() );
		    }

			if(ar == null) {
				log(" no real artist! ");
				return 0;
	        }
			a.setMyArtist( ar );
			
			
			
			
			try {
				context.commitChanges();
			} catch (CayenneRuntimeException e1) {
				log(" ERROR:   ");
				e1.printStackTrace();
				return 0;
			}
		    log(" updated ! ");
		    
		    return a.getId();
		    
		} else  if( entityWrapper instanceof Sculpture ) {
			Sculpture e = (Sculpture) (entityWrapper);
			DbSculpture a = null;
			
			if(e.getArtist() == null 
					|| e.getGenre()== null 
					|| e.getMuseum() == null   					
			){
				log(" incorrect! ");
				return 0;
			}
			
			if(e.getId() != null && e.getId() > 0) {
		        a = (DbSculpture) DataObjectUtils.objectForPK(context, DbSculpture.class, e.getId());
		    }
			
			if(a == null) {
				log(" new object ");
	            a = (DbSculpture) context.newObject(DbSculpture.class);	            
	        }
			
			a.setDescription(e.getDescription().toString());
			a.setImageURL(e.getImageUrl());
			a.setTitle(e.getTitle().toString() );
			a.setIdType( DbArtist.SCULPTURE_ID_TYPE );
			
			int year = 0;
			try{year = Integer.parseInt(e.getCreationYear().toString());}catch(Exception ex){}
			if(year > 0) a.setCreationYear( year ); else a.setCreationYear( null );  
			
			
			int w = 0;
			try{w = Integer.parseInt(e.getMass().toString());}catch(Exception ex){}
			if(w > 0) a.setMass( w ); else a.setMass( null );  
			
			DbMuseum m = null;
			if(e.getId() != null && e.getId() > 0) {
		        m = (DbMuseum) DataObjectUtils.objectForPK(context, DbMuseum.class, e.getId());
		    }

			if(m == null) {
				log(" no real Museum! ");
				return 0;
	        }
			a.setMyMuseum(m);

			
			
			DbGenre g = null;
			if(e.getId() != null && e.getId() > 0) {
		        g = (DbGenre) DataObjectUtils.objectForPK(context, DbGenre.class, e.getId());
		    }

			if(g == null) {
				log(" no real genre! ");
				return 0;
	        }
			a.setMyGenre( g );
			
			
			DbArtist ar  = null;
			if(e.getId() != null && e.getId() > 0) {
		        ar = (DbArtist) DataObjectUtils.objectForPK(context, DbArtist.class, e.getId());
		    }
			if(ar == null) {
				log(" no real artist! ");
				return 0;
	        }
			a.setMyArtist( ar );
						
			try {
				context.commitChanges();
			} catch (CayenneRuntimeException e1) {
				log(" ERROR:   ");
				e1.printStackTrace();
				return 0;
			}
		    log(" updated ! ");
		    
		    return a.getId();		    
		} 
			
		
		log("Unknown class");
		return 0;
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
			context = DataContext.getThreadDataContext();
		} catch (Exception e) {			
			e.printStackTrace();
			return;
		} 
    	
    	if(entityWrapper instanceof Artist){
    		try {
				Artist cur = (Artist) entityWrapper;

				log("ID = " + cur.getId() + "|" + entityWrapper.getId());
				
				DbArtist picasso  = null;
				if(entityWrapper.getId() != null && entityWrapper.getId() > 0) {
					picasso = (DbArtist) DataObjectUtils.objectForPK(context, DbArtist.class, entityWrapper.getId());
			    }
				
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
    			log("ID = " + cur.getId() + "|" + entityWrapper.getId());
				
    			DbMuseum picasso  = null;
				if(entityWrapper.getId() != null && entityWrapper.getId() > 0) {
					picasso = (DbMuseum) DataObjectUtils.objectForPK(context, DbMuseum.class, entityWrapper.getId());
			    }

				
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
    			log("ID = " + cur.getId() + "|" + entityWrapper.getId());
				
    			DbGenre picasso  = null;
				if(entityWrapper.getId() != null && entityWrapper.getId() > 0) {
					picasso = (DbGenre) DataObjectUtils.objectForPK(context, DbGenre.class, entityWrapper.getId());
			    }


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
    			log("ID = " + cur.getId() + "|" + entityWrapper.getId());
				
    			DbPainting picasso  = null;
				if(entityWrapper.getId() != null && entityWrapper.getId() > 0) {
					picasso = (DbPainting) DataObjectUtils.objectForPK(context, DbPainting.class, entityWrapper.getId());
			    }

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
    			log("ID = " + cur.getId() + "|" + entityWrapper.getId());
				
    			DbSculpture picasso  = null;
				if(entityWrapper.getId() != null && entityWrapper.getId() > 0) {
					picasso = (DbSculpture) DataObjectUtils.objectForPK(context, DbSculpture.class, entityWrapper.getId());
			    }

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
