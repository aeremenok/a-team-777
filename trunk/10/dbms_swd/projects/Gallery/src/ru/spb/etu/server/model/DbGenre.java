package ru.spb.etu.server.model;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import ru.spb.etu.server.model.auto._DbGenre;

public class DbGenre extends _DbGenre {
	
	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
}



