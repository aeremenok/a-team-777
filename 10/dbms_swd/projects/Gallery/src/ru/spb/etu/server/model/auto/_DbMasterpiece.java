package ru.spb.etu.server.model.auto;

/** Class _DbMasterpiece was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _DbMasterpiece extends org.apache.cayenne.CayenneDataObject {

    public static final String CREATION_YEAR_PROPERTY = "creationYear";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String ID_TYPE_PROPERTY = "idType";
    public static final String IMAGE_URL_PROPERTY = "imageURL";
    public static final String PHOTO_PROPERTY = "photo";
    public static final String PICTURE_PROPERTY = "picture";
    public static final String TITLE_PROPERTY = "title";
    public static final String MY_ARTIST_PROPERTY = "myArtist";
    public static final String MY_GENRE_PROPERTY = "myGenre";
    public static final String MY_MUSEUM_PROPERTY = "myMuseum";
    public static final String MY_PAINTING_PROPERTY = "myPainting";
    public static final String MY_SCULPTURE_PROPERTY = "mySculpture";

    public static final String MASTERPIECE_ID_PK_COLUMN = "masterpieceID";

    public void setCreationYear(Integer creationYear) {
        writeProperty("creationYear", creationYear);
    }
    public Integer getCreationYear() {
        return (Integer)readProperty("creationYear");
    }
    
    
    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }
    
    
    public void setIdType(Integer idType) {
        writeProperty("idType", idType);
    }
    public Integer getIdType() {
        return (Integer)readProperty("idType");
    }
    
    
    public void setImageURL(String imageURL) {
        writeProperty("imageURL", imageURL);
    }
    public String getImageURL() {
        return (String)readProperty("imageURL");
    }
    
    
    public void setPhoto(String photo) {
        writeProperty("photo", photo);
    }
    public String getPhoto() {
        return (String)readProperty("photo");
    }
    
    
    public void setPicture(byte[] picture) {
        writeProperty("picture", picture);
    }
    public byte[] getPicture() {
        return (byte[])readProperty("picture");
    }
    
    
    public void setTitle(String title) {
        writeProperty("title", title);
    }
    public String getTitle() {
        return (String)readProperty("title");
    }
    
    
    public void setMyArtist(ru.spb.etu.server.model.DbArtist myArtist) {
        setToOneTarget("myArtist", myArtist, true);
    }

    public ru.spb.etu.server.model.DbArtist getMyArtist() {
        return (ru.spb.etu.server.model.DbArtist)readProperty("myArtist");
    } 
    
    
    public void setMyGenre(ru.spb.etu.server.model.DbGenre myGenre) {
        setToOneTarget("myGenre", myGenre, true);
    }

    public ru.spb.etu.server.model.DbGenre getMyGenre() {
        return (ru.spb.etu.server.model.DbGenre)readProperty("myGenre");
    } 
    
    
    public void setMyMuseum(ru.spb.etu.server.model.DbMuseum myMuseum) {
        setToOneTarget("myMuseum", myMuseum, true);
    }

    public ru.spb.etu.server.model.DbMuseum getMyMuseum() {
        return (ru.spb.etu.server.model.DbMuseum)readProperty("myMuseum");
    } 
    
    
    public void setMyPainting(ru.spb.etu.server.model.DbPainting myPainting) {
        setToOneTarget("myPainting", myPainting, true);
    }

    public ru.spb.etu.server.model.DbPainting getMyPainting() {
        return (ru.spb.etu.server.model.DbPainting)readProperty("myPainting");
    } 
    
    
    public void setMySculpture(ru.spb.etu.server.model.DbSculpture mySculpture) {
        setToOneTarget("mySculpture", mySculpture, true);
    }

    public ru.spb.etu.server.model.DbSculpture getMySculpture() {
        return (ru.spb.etu.server.model.DbSculpture)readProperty("mySculpture");
    } 
    
    
}
