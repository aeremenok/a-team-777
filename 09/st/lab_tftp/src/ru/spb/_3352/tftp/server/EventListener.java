package ru.spb._3352.tftp.server;

import java.net.InetAddress;

/**
 * яксьюрекэ янашрхи яйювхбюмхъ х гюкхбйх
 */
public interface EventListener
    extends
        java.util.EventListener
{
    /**
     * бшбнд хмтнплюжхх н яйювхбюмхх тюикю
     * 
     * @param addr юдпея йкхемрю
     * @param port онпр
     * @param fileName хлъ тюикю
     * @param ok яйювхбюмхе опнькн сяоеьмн?
     */
    public void onAfterDownload(
        InetAddress addr,
        int port,
        String fileName,
        boolean ok );

    /**
     * бшбнд хмтнплюжхх н гюкхбйе тюикю
     * 
     * @param addr юдпея йкхемрю
     * @param port онпр
     * @param fileName хлъ тюикю
     * @param ok гюкхбйю опнькю сяоеьмн?
     */
    public void onAfterUpload(
        InetAddress addr,
        int port,
        String fileName,
        boolean ok );
}
