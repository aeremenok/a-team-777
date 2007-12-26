package server;


import java.util.*;


/**
 * User: pemman Date: 26.12.2007 Time: 10:23:01
 */
public class ConsoleDoc implements IDoc{

    private String id = null;
    private Map properties = new HashMap();

    public static List getDocumentsList(){
        List l = new ArrayList();
        l.add("1");
        l.add("2");
        return l;
    }

    public String getProperty(String name) {
        String out = (String) this.properties.get(name);
        System.out.println("[ConsoleDoc] #" + this.id + ": getProperty " + name + " = " + out );
        return out;
    }

    public ConsoleDoc(){
        this.id = "id1";
        System.out.println("[ConsoleDoc] #" + id + " default create ");

    }

    public  ConsoleDoc(String id){
        System.out.println("[ConsoleDoc] #" + id + " create ");

        this.id = id;
    }
    public void setProperty(String name, String value) {
        System.out.println("[ConsoleDoc] #" + this.id + ": setProperty " + name + " = " + value );
        this.properties.put(name,value);
    }

    public void delete() {
        System.out.println("[ConsoleDoc] #" + this.id + ":  delete");
        this.id = null;
        this.properties = new HashMap();
    }

    public String getXML() {
        System.out.println("[ConsoleDoc] #" + this.id + ":  getXML");

        StringBuffer stb = new StringBuffer();
        stb.append("<xml>");
        for (Object o : this.properties.keySet()) {
            String name = (String) o;
            String value = (String) this.properties.get(o);
            stb.append("<").append(name).append(">").append(value).append("</").append(name).append(">");
        }
        stb.append("</xml>");
        return stb.toString();
    }
}
