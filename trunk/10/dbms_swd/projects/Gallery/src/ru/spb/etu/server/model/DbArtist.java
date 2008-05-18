package ru.spb.etu.server.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;

import ru.spb.etu.server.model.auto._DbArtist;

public class DbArtist extends _DbArtist {

	public static final Integer MASTERPICE_ID_TYPE = 0;
	public static final Integer PICTURE_ID_TYPE = 1;
	public static final Integer SCULPTURE_ID_TYPE = 2;
	
	
	public Integer  getId(){
		return DataObjectUtils.intPKForObject(this);
	}
	
	

   
   
	static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

    /**
     * Sets date of birth using a string in format yyyyMMdd.
     */
    public void setDateOfBirthString(String yearMonthDay) {
        if (yearMonthDay == null) {
            setBirthdate(null);
        }
        else {
            
            Date date;
            try {
                date = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(yearMonthDay);
            }
            catch (ParseException e) {
                throw new IllegalArgumentException("A date argument must be in format '"
                        + DEFAULT_DATE_FORMAT
                        + "': "
                        + yearMonthDay);
            }

            setBirthdate(date);
        }
    }
	
	
}



