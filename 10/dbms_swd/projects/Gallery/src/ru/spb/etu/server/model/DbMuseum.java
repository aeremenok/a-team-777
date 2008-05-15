package ru.spb.etu.server.model;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import ru.spb.etu.server.model.auto._DbMuseum;

public class DbMuseum extends _DbMuseum {
	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
	
}



