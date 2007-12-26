package server;


import java.util.List;


/**
 * User: pemman Date: 26.12.2007 Time: 10:19:13
 */
public interface IDoc {

    public String getProperty(String name);
    public void setProperty(String name, String value);

    public void delete();
    public String getXML();

}
