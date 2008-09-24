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
 * нанкнвйю дкъ гюосяйю йкхемрю
 */
public class Client
{
    /**
     * рнвйю бундю
     * 
     * @param args юпцслемрш йнлюмдмни ярпнйх
     * @throws IOException
     * @throws InstantiationException
     */
    public static void main(
        String[] args )
        throws Exception
    {
        // хмхжхюкхгюжхъ я онксвеммшлх хг йнлюмдмни ярпнйх оюпюлерпюлх
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

        // гюосяй йкхемрю
        TFTPClient client = new TFTPClient( hostName );

        System.out.println( "TFTP client started" );
        System.out.println( "host=" + hostName );
        System.out.println( "port=" + port );
        System.out.println( "file dir is '" + _this.getHome().getPath() + "'" );
        System.out.println( "\nUse 'help' to list available commands or 'quit' to quit." );

        // пюанвхи аеяйнмевмши жхйк
        for ( ;; )
        {
            System.out.print( ">" );
            String command = in.readLine();

            // пюяонгмю╦л ббед╗ммсч йнлюмдс
            if ( command.equals( "exit" ) )
            {
                // бшунд

                break;
            }
            else if ( command.equals( "help" ) )
            {
                // яохянй йнлюмд

                printCommands();
            }
            else if ( command.startsWith( "help" ) )
            {
                // яопюбйю он йнлюмдюл

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() != 2 )
                {
                    System.out.println( "Wrong syntax, see 'help'" );
                    continue;
                }

                st.nextToken(); // опносяйюел йнлюмдс help

                printDetailedCommandInfo( st.nextToken() );
            }
            else if ( command.startsWith( "home" ) )
            {
                // ялемю днлюьмеи дхпейрнпхх

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() != 2 )
                {
                    System.out.println( "Wrong syntax, see 'help home'" );
                    continue;
                }

                st.nextToken(); // опносяйюел йнлюмдс home

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
                // гюцпсгйю

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() < 2 )
                {
                    System.out.println( "Wrong path" );
                    continue;
                }

                st.nextToken(); // опносяйюел якнбн get

                // хлъ тюикю мю яепбепе
                String srcFileName = st.nextToken();

                // хлъ тюикю дкъ янупюмемхъ
                String dstFileName = srcFileName;

                if ( st.hasMoreTokens() )
                {
                    dstFileName = st.nextToken();
                }

                // декюел гюопня мю времхе й яепбепс
                RRQ rrq = client.initializeDownload( srcFileName, 0, 0, 0 );
                rrq.setPort( port );

                // цнрнбхл тюик дкъ гюохях
                File fileToWrite = new File( _this.getHome().getPath() + "\\" + dstFileName );

                // янгдю╗л тюик еякх рюйнцн мер
                if ( !fileToWrite.exists() )
                {
                    fileToWrite.createNewFile();
                }
                else
                {
                    System.out.println( "File already exists, try another name" );
                    continue;
                }

                // янгдю╗л онрнй гюохях
                FileOutputStream fos = new FileOutputStream( fileToWrite );

                // яйювхбюел тюик
                if ( !client.download( rrq, fos ) )
                {
                    System.out.println( "Download failed, deleting allocated file..." );
                    fos.close();

                    if ( !fileToWrite.delete() )
                    {
                        System.out.println( "Deletion failed, please delete file manually" );
                    }
                }
            }
            else if ( command.startsWith( "put " ) )
            {
                // гюкхбйю

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() < 2 )
                {
                    System.out.println( "Wrong path" );
                    continue;
                }

                st.nextToken(); // опносяйюел якнбн put

                // хлъ тюикю мю йкхемре
                String srcFileName = st.nextToken();

                // хлъ тюикю мю яепбепе
                String dstFileName = srcFileName;

                if ( st.hasMoreTokens() )
                {
                    dstFileName = st.nextToken();
                }

                // декюел гюопня мю гюохяэ
                WRQ wrq = client.initializeUpload( dstFileName, 0, 0, 0 );
                wrq.setPort( port );

                // цнрнбхл тюик дкъ времхъ
                File fileToRead = new File( _this.getHome().getPath() + "\\" + srcFileName );

                // еякх рюйнцн тюикю мер
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

                // янгдю╗л онрнй времхъ
                FileInputStream fis = new FileInputStream( fileToRead );
                client.upload( wrq, fis );
            }
            else
            {
                // мер рюйни йнлюмдш

                System.out.println( "Unknown command '" + command + "', try using 'help'" );
            }
        }

        System.out.println( "Client application has quit..." );
    }

    /**
     * бшбнд яохяйю днярсомшу онкэгнбюрекч йнлюмд
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
     * бшбнд дерюкэмни яопюбйх он яхмрюйяхяс йнлюмдш
     * 
     * @param commandName йнлюмдю
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
     * бшбндхр хмтнплюжхч он гюосяйс
     */
    public static void printUsage()
    {
        System.out.println( "Program usage:" );
        System.out.println( "\tclient SERVER_HOST SERVER_PORT HOME_DIR\n" );
        System.out.println( "Example:" );
        System.out.println( "\tclient localhost 69 d:\\temp\\\n" );
    }

    /**
     * днлюьмъъ дхпейрнпхъ дкъ йкхемрю, лнфер хглемърэяъ бн бпелъ пюанрш
     */
    private File _home = null;

    /**
     * бнгбпюыюер днлюьмчч дхпейрнпхч
     * 
     * @return днлюьмъъ дхпейрнпхъ
     */
    public File getHome()
    {
        return _home;
    }

    /**
     * сярюмюбкхбюер днлюьмчч дхпейрнпхч я опнбепйни е╗ ясыеярбнбюмхъ
     * 
     * @param homeDir днлюьмъъ дхпейрнпхъ
     * @return TRUE еякх сдюкняэ, FALSE хмюве
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
