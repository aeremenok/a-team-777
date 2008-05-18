package ru.spb.etu.server.model.auto;

/** Class _DbPainting was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _DbPainting extends ru.spb.etu.server.model.DbMasterpiece {

    public static final String HEIGHT_PROPERTY = "height";
    public static final String WIDTH_PROPERTY = "width";
    public static final String PAINTING_MASTERPIECE_PROPERTY = "paintingMasterpiece";

    public static final String PAINTING_ID_PK_COLUMN = "paintingID";

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
    
    
    public void setPaintingMasterpiece(ru.spb.etu.server.model.DbMasterpiece paintingMasterpiece) {
        setToOneTarget("paintingMasterpiece", paintingMasterpiece, true);
    }

    public ru.spb.etu.server.model.DbMasterpiece getPaintingMasterpiece() {
        return (ru.spb.etu.server.model.DbMasterpiece)readProperty("paintingMasterpiece");
    } 
    
    
}
