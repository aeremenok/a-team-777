package ru.spb._3352.tftp.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * меяйнкэйн наыху тсмйжхи дкъ ртро
 */
public class TFTPUtils
{

    /**
     * опнбепъер онксвеммши оюйер ондрбепфдемхъ, йнд ноепюжхх х мнлеп акнйю
     * 
     * @param expecting нфхдюелши оюйер
     * @param received онксвеммши оюйер
     * @return TRUE еякх оюйерш янбоюдючр, FALSE хмюве
     */
    public static boolean correctAnswer(
        ACK expecting,
        ACK received )
    {
        if ( expecting == null || received == null )
        {
            return false;
        }

        if ( expecting.getOpCode() != received.getOpCode() )
        {
            return false;
        }

        if ( expecting.getBlockNr() != received.getBlockNr() )
        {
            return false;
        }

        return true;
    }

    /**
     * оепедювю дюммшу
     * 
     * @param tftpSock янйер
     * @param send онякюммши оюйер
     * @param recv онксвеммши оюйер
     * @return
     */
    public static ACK dataTransfer(
        TFTPSocket tftpSock,
        ACK send,
        ACK recv )
        throws IOException
    {
        int retransmits = 0;
        int spamcount = 0;

        // ткюц гюукюлк╗ммнцн йюмюкю, сярюмюбкхбюеряъ б яксвюе гюахбюмхъ йюмюкю
        // йсвеи онбрнпмн нрнякюммшу оюйернб. нвхыюеряъ опх сдбнемхх рюилюсрю,
        // врнаш пюгцпсгхрэ йюмюк нр аеяонкегмшу оюйернб
        boolean stuffedLink = false;
        int timeout = tftpSock.getSocketTimeOut();

        // оняшкюел оюйер
        tftpSock.write( send );

        TFTPPacket tftpP;
        boolean receiving = true;

        // фд╗л ондрбепфдемхъ
        while ( receiving )
        {
            tftpP = tftpSock.read();

            // еякх мхвецн ме онксвхкх
            if ( tftpP == null )
            {
                // х ме фд╗л оюйерю, онрнлс врн нрнякюкх онякедмее
                // ондрбепфдемхе. реоепэ дпсфекчамн фд╗л онйю нмн днид╗р, мю
                // яксвюи днонкмхрекэмни оепеяшкйх, бдпсц нмн онрепъкняэ.
                if ( recv == null )
                {
                    return null;
                }

                // еякх хявепоюкх пегепб оношрнй - ядю╗ляъ
                if ( retransmits++ > 5 )
                {
                    throw new IOException( getClient( tftpSock ) + " Maximum retransmit count exceeded" );
                }

                // оепеяшкюел оюйер х ямнбю фд╗л
                System.out.println( getClient( tftpSock ) + " expected packet before time out, sending ACK/DATA again" );
                tftpSock.write( send );

                // сярюмюбкхбюел ткюц, врн йюмюк дн онкэгнбюрекъ гюахр, асдел
                // фдюрэ мелмнцн днкэье, врнаш нябнандхрэ йюмюк
                stuffedLink = true;

                try
                {
                    tftpSock.setSocketTimeOut( tftpSock.getSocketTimeOut() * 2 );
                }
                catch ( SocketException e )
                {
                    System.out
                              .println( getClient( tftpSock ) + "Could not change timeout on socket. " + e.getMessage() );
                    // опнднкфюел
                }

                continue;
            }

            // еякх онксвхкх ньхайс
            if ( tftpP instanceof ERROR )
            {
                throw new IOException( getClient( tftpSock ) + " " + ((ERROR) tftpP).getErrorMessage() );
            }

            // онксвхкх врн унрекх
            if ( (tftpP instanceof ACK) && correctAnswer( recv, (ACK) tftpP ) )
            {
                // сярюмюбкхбюел рюилюср напюрмн
                try
                {
                    if ( stuffedLink )
                    {
                        stuffedLink = false;
                        tftpSock.setSocketTimeOut( timeout );
                    }
                }
                catch ( SocketException e )
                {
                    System.out
                              .println( getClient( tftpSock ) + "Could not change timeout on socket. " + e.getMessage() );
                    // опнднкфюел
                }
                return (ACK) tftpP;
            }

            // еякх якхьйнл лмнцн аеяонкегмшу оюйернб - явхрюел, врн мхвецн ме
            // опхькн
            if ( spamcount++ > 5 )
            {
                return null;
            }
        }
        return null;
    }

    /**
     * бнгбпюыюер IP х онпр йкхемрю, я йнрнпшл хд╗р пюанрю
     * 
     * @param tftpSock янйер
     * @return ярпнйю, я IP х онпрнл йкхемрю
     */
    public static String getClient(
        TFTPSocket tftpSock )
    {
        String client = "";
        InetAddress addr = tftpSock.getAddress();
        int port = tftpSock.getPort();

        if ( addr != null )
        {
            client += addr.getHostAddress();
        }

        if ( port != 0 )
        {
            client += ":";
            client += port;
        }

        return client;
    }

    /**
     * оняшкйю яннаыемхъ на ньхайе
     * 
     * @param tftpSock янйер
     * @param errorCode йнд ньхайх
     * @param errorMsg нохяюмхе ньхайх
     */
    public static void sendErrPacket(
        TFTPSocket tftpSock,
        int errorCode,
        String errorMsg )
    {
        try
        {
            TFTPPacket tftpP = new ERROR( errorCode, errorMsg );
            tftpSock.write( tftpP );
        }
        catch ( Exception e )
        {
            System.out.println( getClient( tftpSock ) + " UDP send ERROR packet failure." );
            System.out.println( e.toString() );
            return;
        }

        System.out.println( "SEND ERROR" + " [" + getClient( tftpSock ) + "] EC = [" + errorCode + "] " + errorMsg );
    }

    private TFTPUtils()
    {
        // щйгелокъпш щрнцн йкюяяю мекэгъ янгдюбюрэ
    }
}
