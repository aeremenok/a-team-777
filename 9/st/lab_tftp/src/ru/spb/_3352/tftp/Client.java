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
 * �������� ��� ������� �������
 */
public class Client
{
    /**
     * ����� �����
     * 
     * @param args ��������� ��������� ������
     * @throws IOException
     * @throws InstantiationException
     */
    public static void main(
        String[] args )
        throws Exception
    {
        // ������������� � ����������� �� ��������� ������ �����������
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

        // ������ �������
        TFTPClient client = new TFTPClient( hostName );

        System.out.println( "TFTP client started" );
        System.out.println( "host=" + hostName );
        System.out.println( "port=" + port );
        System.out.println( "file dir is '" + _this.getHome().getPath() + "'" );
        System.out.println( "\nUse 'help' to list available commands or 'quit' to quit." );

        // ������� ����������� ����
        for ( ;; )
        {
            System.out.print( ">" );
            String command = in.readLine();

            // ���������� ���Ĩ���� �������
            if ( command.equals( "exit" ) )
            {
                // �����

                break;
            }
            else if ( command.equals( "help" ) )
            {
                // ������ ������

                printCommands();
            }
            else if ( command.startsWith( "help" ) )
            {
                // ������� �� ��������

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() != 2 )
                {
                    System.out.println( "Wrong syntax, see 'help'" );
                    continue;
                }

                st.nextToken(); // ���������� ������� help

                printDetailedCommandInfo( st.nextToken() );
            }
            else if ( command.startsWith( "home" ) )
            {
                // ����� �������� ����������

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() != 2 )
                {
                    System.out.println( "Wrong syntax, see 'help home'" );
                    continue;
                }

                st.nextToken(); // ���������� ������� home

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
                // ��������

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() < 2 )
                {
                    System.out.println( "Wrong path" );
                    continue;
                }

                st.nextToken(); // ���������� ����� get

                // ��� ����� �� �������
                String srcFileName = st.nextToken();

                // ��� ����� ��� ����������
                String dstFileName = srcFileName;

                if ( st.hasMoreTokens() )
                {
                    dstFileName = st.nextToken();
                }

                // ������ ������ �� ������ � �������
                RRQ rrq = client.initializeDownload( srcFileName, 0, 0, 0 );
                rrq.setPort( port );

                // ������� ���� ��� ������
                File fileToWrite = new File( _this.getHome().getPath() + "\\" + dstFileName );

                // ������� ���� ���� ������ ���
                if ( !fileToWrite.exists() )
                {
                    fileToWrite.createNewFile();
                }
                else
                {
                    System.out.println( "File already exists, try another name" );
                    continue;
                }

                // ������� ����� ������
                FileOutputStream fos = new FileOutputStream( fileToWrite );

                // ��������� ����
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
                // �������

                StringTokenizer st = new StringTokenizer( command, " " );

                if ( st.countTokens() < 2 )
                {
                    System.out.println( "Wrong path" );
                    continue;
                }

                st.nextToken(); // ���������� ����� put

                // ��� ����� �� �������
                String srcFileName = st.nextToken();

                // ��� ����� �� �������
                String dstFileName = srcFileName;

                if ( st.hasMoreTokens() )
                {
                    dstFileName = st.nextToken();
                }

                // ������ ������ �� ������
                WRQ wrq = client.initializeUpload( dstFileName, 0, 0, 0 );
                wrq.setPort( port );

                // ������� ���� ��� ������
                File fileToRead = new File( _this.getHome().getPath() + "\\" + srcFileName );

                // ���� ������ ����� ���
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

                // ������� ����� ������
                FileInputStream fis = new FileInputStream( fileToRead );
                client.upload( wrq, fis );
            }
            else
            {
                // ��� ����� �������

                System.out.println( "Unknown command '" + command + "', try using 'help'" );
            }
        }

        System.out.println( "Client application has quit..." );
    }

    /**
     * ����� ������ ��������� ������������ ������
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
     * ����� ��������� ������� �� ���������� �������
     * 
     * @param commandName �������
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
     * ������� ���������� �� �������
     */
    public static void printUsage()
    {
        System.out.println( "Program usage:" );
        System.out.println( "\tclient SERVER_HOST SERVER_PORT HOME_DIR\n" );
        System.out.println( "Example:" );
        System.out.println( "\tclient localhost 69 d:\\temp\\\n" );
    }

    /**
     * �������� ���������� ��� �������, ����� ���������� �� ����� ������
     */
    private File _home = null;

    /**
     * ���������� �������� ����������
     * 
     * @return �������� ����������
     */
    public File getHome()
    {
        return _home;
    }

    /**
     * ������������� �������� ���������� � ��������� Ũ �������������
     * 
     * @param homeDir �������� ����������
     * @return TRUE ���� �������, FALSE �����
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
