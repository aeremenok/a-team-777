package ru.spb.etu.server.model.auto;

import java.util.List;

/** Class _DbGenre was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _DbGenre extends org.apache.cayenne.CayenneDataObject {

    public static final String DESCRPTION_PROPERTY = "descrption";
    public static final String IMAGE_URL_PROPERTY = "imageURL";
    public static final String NAME_PROPERTY = "name";
    public static final String MASTERPIECES_PROPERTY = "masterpieces";

    public static final String ID_PK_COLUMN = "ID";

    public void setDescrption(String descrption) {
        writeProperty("descrption", descrption);
    }
    public String getDescrption() {
        return (String)readProperty("descrption");
    }
    
    
    public void setImageURL(String imageURL) {
        writeProperty("imageURL", imageURL);
    }
    public String getImageURL() {
        return (String)readProperty("imageURL");
    }
    
    
    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }
    
    
    public void addToMasterpieces(ru.spb.etu.server.model.DbMasterpiece obj) {
        addToManyTarget("masterpieces", obj, true);
    }
    public void removeFromMasterpieces(ru.spb.etu.server.model.DbMasterpiece obj) {
        removeToManyTarget("masterpieces", obj, true);
    }
    public List getMasterpieces() {
        return (List)readProperty("masterpieces");
    }
    
    
}
