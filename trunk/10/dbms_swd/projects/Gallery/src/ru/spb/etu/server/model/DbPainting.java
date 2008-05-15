package ru.spb.etu.server.model;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import ru.spb.etu.server.model.auto._DbPainting;

public class DbPainting extends _DbPainting {
	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
	
}



