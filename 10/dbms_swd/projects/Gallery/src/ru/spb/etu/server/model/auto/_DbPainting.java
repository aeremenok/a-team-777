package ru.spb.etu.server.model.auto;

/** Class _DbPainting was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _DbPainting extends ru.spb.etu.server.model.DbMasterpiece {

    public static final String HEIGHT_PROPERTY = "height";
    public static final String WIDTH_PROPERTY = "width";

    public static final String MASTERPIECE_ID_PK_COLUMN = "masterpieceID";

    public void setHeight(Integer height) {
        writeProperty("height", height);
    }
    public Integer getHeight() {
        return (Integer)readProperty("height");
    }
    
    
    public void setWidth(Integer width) {
        writeProperty("width", width);
    }
    public Integer getWidth() {
        return (Integer)readProperty("width");
    }
    
    
}