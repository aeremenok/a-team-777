package talkie.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import talkie.server.ui.ServerUI;

public class Server
{
    private static final String USERS_PROPERTIES = "users.properties";

    private static final String LOG4J_PROPERTIES = "log4j.properties";
    private Logger              log              = Logger.getLogger( Server.class );
    private Properties          users            = new Properties();

    /**
     * @param args
     * @throws Exception
     */
    public static void main(
        String[] args )
    {
        // ������������� ������
        PropertyConfigurator.configureAndWatch( LOG4J_PROPERTIES );

        // ��������� ���������� � �������������
        Server server = new Server();

        // ��������� ���������
        new Thread( new ServerUI( server ) ).start();
    }

    public Server()
    {
        loadUsers();
    }

    /**
     * ��������� ������ ������������� �� ����� ������������
     */
    private void loadUsers()
    {
        try
        {
            users.load( new FileInputStream( USERS_PROPERTIES ) );
        }
        catch ( IOException e )
        {
            log.warn( "Missing configuration file '" + USERS_PROPERTIES +
                "', starting without any preconfigured users!" );
        }
    }

    /**
     * ��������� ������ ������������� � ���� ������������
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void saveUsers()
        throws IOException,
            FileNotFoundException
    {
        users.store( new FileOutputStream( USERS_PROPERTIES ), "" );
    }

    @Override
    protected void finalize()
        throws Throwable
    {
        // ����� ������� ������ ��������� ����� �������������
        saveUsers();

        super.finalize();
    }
}
