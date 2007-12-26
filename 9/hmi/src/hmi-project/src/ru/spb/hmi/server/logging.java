package ru.spb.hmi.server;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * User: Павел
 * Date: 29.07.2006
 * Time: 16:24:53
 */
public class logging {

    private       static Logger log = Logger.getLogger("WebMobile");
    protected     static FileHandler fh;
    private final static String fileName = "C:\\LOG_main.xml";

    static {
        try {
            if(fileName != null){
                FileHandler fh = new FileHandler(fileName, true);
                fh.setFormatter(new SimpleFormatter());
                log.addHandler(fh);
                log.setUseParentHandlers(false);
                log.setLevel(Level.ALL);
            }
        } catch (Exception err) {
            System.err.println("Can't initialize logger! Reason: ");
            err.printStackTrace(System.err);
        }
    }

    public static Logger getLogger()
    {
        return log;
    }

}

