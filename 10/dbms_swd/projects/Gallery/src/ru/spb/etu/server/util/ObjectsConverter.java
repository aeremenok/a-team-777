package ru.spb.etu.server.util;

import java.util.ArrayList;
import java.util.List;

import ru.spb.etu.client.serializable.*;
import ru.spb.etu.server.model.*;



public class ObjectsConverter {

	//======================================================================================================================
	// Actors 
	//======================================================================================================================
	
	
	/**
	 * Converting array from DB to array for client
	 * @param actorsDB
	 * @return
	 */public static ArrayList<Artist> convertArtists(List<DbArtist> actorsDB) {
		
		ArrayList<Artist> artists = new ArrayList<Artist>();
		
		if(actorsDB != null ) 
			try {
			
				for(DbArtist a : actorsDB){
					
					artists.add(
								convertArtist(a)
								);
				}
			
			} catch (Exception e) {			
				e.printStackTrace();
			}
		
		return artists;
	}

	/**
	 * converting database artist to client artist
	 * @param a
	 * @return
	 */
	 public static Artist convertArtist(DbArtist a) {
		Artist b = new Artist(
				a.getBirthdate(),
		        a.getCountry(),
		        a.getDescription(),
		        a.getName(),
		        a.getImageUrl() 
		);
		Integer i = a.getId();
		System.out.println("Artist i=" + i);
		b.setId(i);
		return b;
	}
	 	
	 
	 
	 
	
	//======================================================================================================================
	// Masterpiece 
	//======================================================================================================================
	
	
	/**
	 * Converting array from DB to array for client
	 * @param DbMasterpiece
	 * @return
	 */public static ArrayList<MasterPiece> convertMasterpieces(List<DbMasterpiece> what) {
		
		ArrayList<MasterPiece> artists = new ArrayList<MasterPiece>();
		
		if(what != null ) 
			try {
			
				for(DbMasterpiece a : what){
					
					artists.add(
								convertMasterpiece(a)
								);
				}
			
			} catch (Exception e) {			
				e.printStackTrace();
			}
		
		return artists;
	}
	
	/**
	 * converting database MasterPiece to client MasterPiece
	 * @param a
	 * @return
	 */public  static MasterPiece convertMasterpiece(DbMasterpiece a) {
		 MasterPiece b = new MasterPiece( 
				 a.getCreationYear(), 
				 a.getImageURL(), 
				 a.getTitle(),
				 a.getDescription() 
		);
		 b.setId(a.getId());
		return b;
	}
	 
	 
	 
	 
	//======================================================================================================================
	// Masterpiece 
	//======================================================================================================================
	
	
	/**
	 * Converting array from "DbMuseum" to array "Museum" for client 
	 * @param actorsDB
	 * @return
	 */
	 public static ArrayList<Museum> convertMuseums(List<DbMuseum> what) {
		
		ArrayList<Museum> result = new ArrayList<Museum>();
		
		if(what != null ) 
			try {
			
				for(DbMuseum a : what){
					
					result.add(
								convertMuseum(a)
								);
				}
			
			} catch (Exception e) {			
				e.printStackTrace();
			}
		
		return result;
	}
	
	/**
	 * converting database DbMuseum to client Museum
	 * @param a
	 * @return
	 */
	 public  static Museum convertMuseum(DbMuseum a) {
		 Museum b = new Museum(  
				 a.getDescription(), 
				 a.getName(), 
				 a.getImageURL()				 
		 		);
		 b.setId(a.getId());
		return b;
	}
	 
	 
	 
	 
	//======================================================================================================================
	// Genre 
	//======================================================================================================================
	
	
	/**
	 * Converting array from "DbMuseum" to array "Museum" for client 
	 * @param actorsDB
	 * @return
	 */
	 public static ArrayList<Genre> convertGenres(List<DbGenre> what) {
		
		ArrayList<Genre> result = new ArrayList<Genre>();
		
		if(what != null ) 
			try {
			
				for(DbGenre a : what){
					
					result.add(
								convertGenre(a)
								);
				}
			
			} catch (Exception e) {			
				e.printStackTrace();
			}
		
		return result;
	}
	
	/**
	 * converting database DbMuseum to client Museum
	 * @param a
	 * @return
	 */
	 public  static Genre convertGenre(DbGenre a) {
		 Genre b = new Genre(
				 a.getName(), 
				 a.getImageURL(),	
				 a.getDescrption()			 
		 		);
		 b.setId(a.getId());
		return b;
	}
		
	 
	//======================================================================================================================
	// Genre 
	//======================================================================================================================
	
	
	/**
	 * Converting array from "DbMuseum" to array "Museum" for client 
	 * @param actorsDB
	 * @return
	 */
	 public static ArrayList<Painting> convertPaintings(List<DbPainting> what) {
		
		ArrayList<Painting> result = new ArrayList<Painting>();
		
		if(what != null ) 
			try {
			
				for(DbPainting a : what){
					
					result.add(
								convertPainting(a)
								);
				}
			
			} catch (Exception e) {			
				e.printStackTrace();
			}
		
		return result;
	}
	
	/**
	 * converting database DbMuseum to client Museum
	 * @param a
	 * @return
	 */
	 public  static Painting convertPainting(DbPainting a) {
		 Painting b = new Painting(
						 
		 		);
		 b.setId(a.getId());
		return b;
	}
	 
	 
	 
	//======================================================================================================================
	// Genre 
	//======================================================================================================================
	
	
	/**
	 * Converting array from "DbMuseum" to array "Museum" for client 
	 * @param actorsDB
	 * @return
	 */
	 public static ArrayList<Sculpture> convertSculptures(List<DbSculpture> what) {
		
		ArrayList<Sculpture> result = new ArrayList<Sculpture>();
		
		if(what != null ) 
			try {
			
				for(DbSculpture a : what){
					
					result.add(
								convertSculpture(a)
								);
				}
			
			} catch (Exception e) {			
				e.printStackTrace();
			}
		
		return result;
	}
	
	/**
	 * converting database DbMuseum to client Museum
	 * @param a
	 * @return
	 */
	 public  static Sculpture convertSculpture(DbSculpture a) {
		 Sculpture b = new Sculpture(	 
		 		);
		 b.setId(a.getId());
		return b;
	}
	 
 
 

}
