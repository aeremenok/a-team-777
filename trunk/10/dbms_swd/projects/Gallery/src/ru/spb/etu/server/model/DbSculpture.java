package ru.spb.etu.server.model;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import ru.spb.etu.server.model.auto._DbSculpture;

public class DbSculpture extends _DbSculpture {
	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
	
}



