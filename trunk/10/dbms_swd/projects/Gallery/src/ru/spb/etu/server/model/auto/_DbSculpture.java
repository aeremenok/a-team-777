package ru.spb.etu.server.model.auto;

/** Class _DbSculpture was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _DbSculpture extends ru.spb.etu.server.model.DbMasterpiece {

    public static final String MASS_PROPERTY = "mass";
    public static final String MATERIAL_PROPERTY = "material";

    public static final String MASTERPIECE_ID_PK_COLUMN = "masterpieceID";

    public void setMass(Integer mass) {
        writeProperty("mass", mass);
    }
    public Integer getMass() {
        return (Integer)readProperty("mass");
    }
    
    
    public void setMaterial(String material) {
        writeProperty("material", material);
    }
    public String getMaterial() {
        return (String)readProperty("material");
    }
    
    
}