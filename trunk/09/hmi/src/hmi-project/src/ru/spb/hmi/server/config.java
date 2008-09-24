package ru.spb.hmi.server;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Павел
 * Date: 26.12.2007
 * Time: 22:10:25
 */
public class config {

    private static Map properties = new HashMap();

    static{
        properties.put("MYSQL_USER",  "3352");
        properties.put("MYSQL_PWD",   "3352");
        properties.put("MYSQL_HOST",  "127.0.0.1");
        properties.put("MYSQL_SCHEMA","3352");

    }


    public static String getEntryValue(String s) {
        return (String) properties.get(s);
    }
}
