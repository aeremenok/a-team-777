package ru.spb.etu.server.model;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import ru.spb.etu.server.model.auto._DbMasterpiece;

public class DbMasterpiece extends _DbMasterpiece {
	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
	
}



