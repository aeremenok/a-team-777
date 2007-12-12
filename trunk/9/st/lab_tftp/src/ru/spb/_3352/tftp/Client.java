package ru.spb._3352.tftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import ru.spb._3352.tftp.client.TFTPClient;
import ru.spb._3352.tftp.common.RRQ;
import ru.spb._3352.tftp.common.WRQ;

/**
 * ÎÁÎËÎ×ÊÀ ÄËß ÇÀÏÓÑÊÀ ÊËÈÅÍÒÀ ÇÀÏÓÑÊ: Client host server_port
 */
/**
 * @author ssv
 */
public class Client
{
    /**
     * ÄÎÌÀØÍßß ÄÈĞÅÊÒÎĞÈß ÄËß ÊËÈÅÍÒÀ, ÌÎÆÅÒ ÈÇÌÅÍßÒÜÑß ÂÎ ÂĞÅÌß ĞÀÁÎÒÛ
     */
    private File _home = null;

    /**
     * ÂÛÂÎÄÈÒ ÈÍÔÎĞÌÀÖÈŞ ÏÎ ÇÀÏÓÑÊÓ
     */
    public static void printUsage()
    {
        System.out.println( "Program usage:" );
        System.out.println( "\tclient SERVER_HOST SERVER_PORT HOME_DIR\n" );
        System.out.println( "Example:" );
        System.out.println( "\tclient localhost 69 d:\\temp\\\n" );
    }

    /**
     * ÂÛÂÎÄ ÑÏÈÑÊÀ ÄÎÑÒÓÏÍÛÕ ÏÎËÜÇÎÂÀÒÅËŞ ÊÎÌÀÍÄ
     */
    public static void printCommands()
    {
        System.out.println( "Valid commands are:\n" );
        System.out.println( "help\tgetting this help" );
        System.out.println( "home\tchanging home directory" );
        System.out.println( "get\tdownload file from server" );
        System.out.println( "put\tupload file to a server" );
        System.out.println( "exit\tquit program" );
        System.out.println( "\nUse 'help <command>' to get help on commands syntax.\n" );
    }

    /**
     * ÂÛÂÎÄ ÄÅÒÀËÜÍÎÉ ÑÏĞÀÂÊÈ ÏÎ ÑÈÍÒÀÊÑÈÑÓ ÊÎÌÀÍÄÛ
     * 
     * @param commandName ÊÎÌÀÍÄÀ
     */
    public static void printDetailedCommandInfo(
        String commandName )
    {
        if ( "help".equalsIgnoreCase( commandName ) )
        {
            System.out.println( "Command syntax:" );
            System.out.println( "\thelp\t getting command list" );
            System.out.println( "\thelp <command>\t getting detailed iformation on <command> syntax" );
            System.out.println( "\nExample:\n\thelp get\n" );
        }
        else if ( "home".equalsIgnoreCase( commandName ) )
        {
            System.out.println( "Command syntax:" );
            System.out.println( "\thome <dir>\n" );
            System.out.println( "\t\t* <dir> is a new home directory\n" );
            System.out.println( "\nExample\n\thome d:\\temp\n" );
        }
        else if ( "get".equalsIgnoreCase( commandName ) )
        {
            System.out.println( "Command syntax:" );
            System.out.println( "\tget <srcFileName>\n" );
            System.out.println( "\tget <srcFileName> <dstFileName>\n" );
            System.out.println( "\t\t* <srcFileName> is name of the file to be downloaded\n" );
            System.out.println( "\t\t* <dstFileName> if specified is a name for result storing"
                                + " (may be useful if you want to download few copies of file"
                                + " without manual renaming)\n" );
            System.out.println( "\nExample\n\tget openoffice.7z oo1.7z\n" );
        }
        else if ( "put".equalsIgnoreCase( commandName ) )
        {
            System.out.println( "Command syntax:" );
            System.out.println( "\tput <srcFileName>\n" );
            System.out.println( "\tput <srcFileName> <dstFileName>\n" );
            System.out.println( "\t\t* <srcFileName> is name of the file to be uploaded\n" );
            System.out.println( "\t\t* <dstFileName> if specified is a name for result storing"
                                + " (may be useful if you want to upload few copies of file"
                                + " without manual renaming)\n" );
            System.out.println( "\nExample\n\tput oo.7z openoffice.7z\n" );
        }
        else if ( "exit".equalsIgnoreCase( commandName ) )
        {
            System.out.println( "Command syntax:" );
            System.out.println( "\texit\n" );
            System.out.println( "\tNo additional parameters are required\n" );
        }
    }

    /**
     * ÒÎ×ÊÀ ÂÕÎÄÀ
     * 
     * @param args ÀĞÃÓÌÅÍÒÛ ÊÎÌÀÍÄÍÎÉ ÑÒĞÎÊÈ
     * @throws IOException
     * @throws InstantiationException
     */
    public static void main(
        String[] args )
        throws Exception
    {
        // ÈÍÈÖÈÀËÈÇÀÖÈß Ñ ÏÎËÓ×ÅÍÍÛÌÈ ÈÇ ÊÎÌÀÍÄÍÎÉ ÑÒĞÎÊÈ ÏÀĞÀÌÅÒĞÀÌÈ
        Client _this = new Client();

        String hostName = null;
        int port = 69;
        String homePath = null;
        BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );

        if ( args.length != 3 )
        {
            printUsage();
            return;
        }
        else
        {
            hostName = args[0];
            port = Integer.parseInt( args[1] );
            homePath = args[2];
        }

        if ( !_this.setHome( homePath ) )
        {
            System.out.println( "Invalid home directory, failed to start" );
            return;
        }

        // ÇÀÏÓÑÊ ÊËÈÅÍÒÀ
        TFTPClient client = new TFTPClient( hostName );

        System.out.println( "TFTP client started" );
        System.out.println( "host=" + hostName );
        System.out.println( "port=" + port );
        System.out.println( "file dir is '" + _this.getHome().getPath() + "'" );
        System.out.println( "\nUse 'help' to list available commands or 'quit' to quit." );

        // ĞÀÁÎ×ÈÉ ÁÅÑÊÎÍÅ×ÍÛÉ ÖÈÊË
        for ( ;; )
        {
            System.out.print( ">" );
            String command = in.readLine();

            // ĞÀÑÏÎÇÍÀ¸Ì ÂÂÅÄ¨ÍÍÓŞ ÊÎÌÀÍÄÓ
            if ( command.equals( "exit" ) )
            {
                // ÂÛÕÎÄ

                break;
            }
            else if ( command.equals( "help" ) )
            {
                // ÑÏÈÑÎÊ ÊÎÌÀÍÄ

                printCommands();
            }
            else if ( command.startsWith( "help" ) )
            {
                // ÑÏĞÀÂÊÀ ÏÎ ÊÎÌÀÍÄÀÌ

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() != 2 )
                {
                    System.out.println( "Wrong syntax, see 'help'" );
                    continue;
                }

                st.nextToken(); // ÏĞÎÏÓÑÊÀÅÌ ÊÎÌÀÍÄÓ help

                printDetailedCommandInfo( st.nextToken() );
            }
            else if ( command.startsWith( "home" ) )
            {
                // ÑÌÅÍÀ ÄÎÌÀØÍÅÉ ÄÈĞÅÊÒÎĞÈÈ

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() != 2 )
                {
                    System.out.println( "Wrong syntax, see 'help home'" );
                    continue;
                }

                st.nextToken(); // ÏĞÎÏÓÑÊÀÅÌ ÊÎÌÀÍÄÓ home

                if ( !_this.setHome( st.nextToken() ) )
                {
                    System.out.println( "Failed to change home directory, try another" );
                    continue;
                }
                else
                {
                    System.out.println( "Home directory changed successfully, go ahead" );
                }
            }
            else if ( command.startsWith( "get " ) )
            {
                // ÇÀÃĞÓÇÊÀ

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() < 2 )
                {
                    System.out.println( "Wrong path" );
                    continue;
                }

                st.nextToken(); // ÏĞÎÏÓÑÊÀÅÌ ÑËÎÂÎ get

                // ÈÌß ÔÀÉËÀ ÍÀ ÑÅĞÂÅĞÅ
                String srcFileName = st.nextToken();

                // ÈÌß ÔÀÉËÀ ÄËß ÑÎÕĞÀÍÅÍÈß
                String dstFileName = srcFileName;

                if ( st.hasMoreTokens() )
                {
                    dstFileName = st.nextToken();
                }

                // ÄÅËÀÅÌ ÇÀÏĞÎÑ ÍÀ ×ÒÅÍÈÅ Ê ÑÅĞÂÅĞÓ
                RRQ rrq = client.initializeDownload( srcFileName, 5, 0 );
                rrq.setPort( port );

                // ÃÎÒÎÂÈÌ ÔÀÉË ÄËß ÇÀÏÈÑÈ
                File fileToWrite = new File( _this.getHome().getPath() + "\\" + dstFileName );

                // ÑÎÇÄÀ¨Ì ÔÀÉË ÅÑËÈ ÒÀÊÎÃÎ ÍÅÒ
                if ( !fileToWrite.exists() )
                {
                    fileToWrite.createNewFile();
                }
                else
                {
                    System.out.println( "File already exists, try another name" );
                    continue;
                }

                // ÑÎÇÄÀ¨Ì ÏÎÒÎÊ ÇÀÏÈÑÈ
                FileOutputStream fos = new FileOutputStream( fileToWrite );

                // ÑÊÀ×ÈÂÀÅÌ ÔÀÉË
                if ( !client.download( rrq, fos ) )
                {
                    System.out.println( "Download failed, deleting allocated file..." );
                    fos.close();

                    if ( !fileToWrite.delete() )
                    {
                        System.out.println( "Deletion dailed, please delete file manually" );
                    }
                }
            }
            else if ( command.startsWith( "put " ) )
            {
                // ÇÀËÈÂÊÀ

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() < 2 )
                {
                    System.out.println( "Wrong path" );
                    continue;
                }

                st.nextToken(); // ÏĞÎÏÓÑÊÀÅÌ ÑËÎÂÎ put

                // ÈÌß ÔÀÉËÀ ÍÀ ÊËÈÅÍÒÅ
                String srcFileName = st.nextToken();

                // ÈÌß ÔÀÉËÀ ÍÀ ÑÅĞÂÅĞÅ
                String dstFileName = srcFileName;

                if ( st.hasMoreTokens() )
                {
                    dstFileName = st.nextToken();
                }

                // ÄÅËÀÅÌ ÇÀÏĞÎÑ ÍÀ ÇÀÏÈÑÜ
                WRQ wrq = client.initializeUpload( dstFileName, 5, 0 );
                wrq.setPort( port );

                // ÃÎÒÎÂÈÌ ÔÀÉË ÄËß ×ÒÅÍÈß
                File fileToRead = new File( _this.getHome().getPath() + "\\" + srcFileName );

                // ÅÑËÈ ÒÀÊÎÃÎ ÔÀÉËÀ ÍÅÒ
                if ( !fileToRead.exists() )
                {
                    System.out.println( "File does not exist, try another" );
                    continue;
                }

                if ( !fileToRead.canRead() )
                {
                    System.out.println( "Cannot read this file, trying to make it readable..." );
                    if ( !fileToRead.setReadable( true ) )
                    {
                        System.out.println( "Cannot make this file readable, free it manually or try later" );
                        continue;
                    }
                    else
                    {
                        System.out.println( "File is readable now, continuing upload" );
                    }
                }

                // ÑÎÇÄÀ¨Ì ÏÎÒÎÊ ×ÒÅÍÈß
                FileInputStream fis = new FileInputStream( fileToRead );
                client.upload( wrq, fis );
            }
            else
            {
                // ÍÅÒ ÒÀÊÎÉ ÊÎÌÀÍÄÛ

                System.out.println( "Unknown command '" + command + "', try using 'help'" );
            }
        }

        System.out.println( "Client application has quit..." );
    }

    /**
     * ÂÎÇÂĞÀÙÀÅÒ ÄÎÌÀØÍŞŞ ÄÈĞÅÊÒÎĞÈŞ
     * 
     * @return ÄÎÌÀØÍßß ÄÈĞÅÊÒÎĞÈß
     */
    public File getHome()
    {
        return _home;
    }

    /**
     * ÓÑÒÀÍÀÂËÈÂÀÅÒ ÄÎÌÀØÍŞŞ ÄÈĞÅÊÒÎĞÈŞ Ñ ÏĞÎÂÅĞÊÎÉ Å¨ ÑÓÙÅÑÒÂÎÂÀÍÈß
     * 
     * @param homeDir ÄÎÌÀØÍßß ÄÈĞÅÊÒÎĞÈß
     * @return TRUE ÅÑËÈ ÓÄÀËÎÑÜ, FALSE ÈÍÀ×Å
     */
    public boolean setHome(
        String homeDir )
    {
        File homeFile = new File( homeDir );

        if ( !homeFile.isDirectory() )
        {
            return false;
        }

        this._home = homeFile;

        return true;
    }
}
