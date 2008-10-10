package talkie.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import talkie.server.ui.ServerUI;

public class Server
{
    private static final String USERS_PROPERTIES = "users.properties";

    private static final String LOG4J_PROPERTIES = "log4j.properties";
    private static Logger       log              = Logger.getLogger( Server.class );
    private Properties          users            = new Properties();

    /**
     * @param args
     * @throws Exception
     */
    public static void main(
        String[] args )
    {
        // конфигурируем логгер
        PropertyConfigurator.configureAndWatch( LOG4J_PROPERTIES );

        log.fatal( "\n\nTALKIE SERVER STARTED:" + new Date().toString() );

        // считываем информацию о пользователях
        Server server = new Server();

        // конфигурируем внешний вид Swing компонентов
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        }
        catch ( Exception e )
        {
            log.warn( "Unable to set System native look and feel, using default JVM look and feel!", e );
        }

        // запускаем интерфейс
        new Thread( new ServerUI( server ) ).start();
    }

    public Server()
    {
        loadUsers();
    }

    /**
     * загрузить список пользователей из файла конфигурации
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
     * сохранить список пользователей в файл конфигурации
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
        // перед смертью сервер сохраняет своих пользователей
        saveUsers();

        super.finalize();
    }
}
