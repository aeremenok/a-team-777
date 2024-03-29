package ru.spb.etu.auto;

import java.util.List;

/** Class _Artist was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _Artist extends org.apache.cayenne.CayenneDataObject {

    public static final String BIRTH_PROPERTY = "birth";
    public static final String NAME_PROPERTY = "name";
    public static final String PAINTINGS_PROPERTY = "paintings";

    public static final String ID_PK_COLUMN = "ID";

    public void setBirth(java.util.Date birth) {
        writeProperty("birth", birth);
    }
    public java.util.Date getBirth() {
        return (java.util.Date)readProperty("birth");
    }
    
    
    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }
    
    
    public void addToPaintings(ru.spb.etu.Painting obj) {
        addToManyTarget("paintings", obj, true);
    }
    public void removeFromPaintings(ru.spb.etu.Painting obj) {
        removeToManyTarget("paintings", obj, true);
    }
    public List getPaintings() {
        return (List)readProperty("paintings");
    }
    
    
}
