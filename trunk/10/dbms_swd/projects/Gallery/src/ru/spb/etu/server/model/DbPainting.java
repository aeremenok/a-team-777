package ru.spb.etu.server.model;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.PersistenceState;

import ru.spb.etu.server.model.auto._DbArtist;
import ru.spb.etu.server.model.auto._DbPainting;

public class DbPainting extends _DbPainting {
	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
	
	/** Read-only access to PK */
	   public Integer getPaintingId() {
	      return (getObjectId() != null && !getObjectId().isTemporary()) 
	               ? (Integer)getObjectId().getIdSnapshot().get(MASTERPIECE_ID_PK_COLUMN) 
	               : null;
	   }
	   
	   /** Read-only access to FK */
	   public Integer getArtistId() {
	      DbArtist artist = getMyArtist();
	      return (artist != null) 
	      			? (Integer)artist.getObjectId().getIdSnapshot().get(_DbArtist.ARTIST_ID_PK_COLUMN) 
	               : null;
	   }
	  
	   public void setPersistenceState(int state) {
	        super.setPersistenceState(state);
	        
	        // if object was just created
	        if(state == PersistenceState.NEW) {
	           //setPersonType("CUSTOMER");
	        	
	        }
	    }
	
}



