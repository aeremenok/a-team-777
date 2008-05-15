package ru.spb.etu.server.model;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import ru.spb.etu.server.model.auto._DbArtist;

public class DbArtist extends _DbArtist {

	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
	
	
	
}



