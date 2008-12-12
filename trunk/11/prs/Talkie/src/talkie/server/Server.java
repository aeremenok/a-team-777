package talkie.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import talkie.common.constants.Status;
import talkie.common.ui.MyFrame;
import talkie.server.data.User;
import talkie.server.process.dispatchers.DispatchProtocol;
import talkie.server.ui.UserTableModel;

public class Server
    extends MyFrame
    implements
        Runnable,
        ActionListener
{
    private static Logger                       log                  = Logger.getLogger( Server.class );
    private static final String                 USERS_PROPERTIES     = "..\users.properties";
    private static final String                 LOG4J_PROPERTIES     = "..\log4j.properties";
    private static final String                 PROTOCOLS_PROPERTIES = "..\protocols.properties";
    private static final int                    EXIT                 = 0;
    private static final int                    OPEN                 = 1;
    private static final int                    SAVE                 = 2;
    private static final int                    SAVE_AS              = 3;
    private static final int                    ABOUT                = 4;
    private String                              userFileName         = USERS_PROPERTIES;
    private HashMap<String, User>               users                = null;
    private HashMap<Integer, JCheckBoxMenuItem> protActions          = new HashMap<Integer, JCheckBoxMenuItem>();
    private HashMap<String, DispatchProtocol>   dispatchInstances    = new HashMap<String, DispatchProtocol>();
    private HashMap<String, DispatchProtocol>   dispatchActive       = new HashMap<String, DispatchProtocol>();
    private JTable                              usersTable           = null;

    public static void main(
        String[] args )
    {
        // конфигурируем логгер
        PropertyConfigurator.configureAndWatch( LOG4J_PROPERTIES );

        log.fatal( "\n\nTALKIE SERVER STARTED:" + new Date().toString() );

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
        new Thread( new Server() ).start();
    }

    public Server()
    {
        // настройки окна
        setLayout( new BorderLayout() );
        setSize( 500, 300 );

        // загружаем пользователей
        loadUsers();

        // загружаем доступные протоколы
        loadProtocols();

        // инициализируем меню
        initMenuBar();

        // инициализируем таблицу просмотра пользователей
        UserTableModel utm = new UserTableModel( mapToProps( users ) );
        usersTable = new JTable( utm );
        JScrollPane scrollPane = new JScrollPane( usersTable );
        usersTable.setFillsViewportHeight( true );

        add( "Center", scrollPane );
    }

    public void actionPerformed(
        ActionEvent e )
    {
        int command = Integer.parseInt( e.getActionCommand() );

        switch ( command )
        {
            case EXIT:
                onExit();
                break;

            case OPEN:
                String userDir = System.getProperty( "user.dir" );
                JFileChooser openDialog = new JFileChooser( new File( userDir ) );
                FileNameExtensionFilter filter = new FileNameExtensionFilter( "Property Files", "properties" );
                openDialog.setFileFilter( filter );
                int returnVal = openDialog.showOpenDialog( getContentPane() );
                if ( returnVal == JFileChooser.APPROVE_OPTION )
                {
                    String fileName = openDialog.getSelectedFile().getName();
                    setUserFilePath( fileName );
                    loadUsers( fileName );
                }
                break;

            case SAVE:
                try
                {
                    saveUsers();
                }
                catch ( IOException e1 )
                {
                    JOptionPane.showMessageDialog( this, "Error during save:\n" + e1.getMessage(), "Ошибка",
                        JOptionPane.ERROR_MESSAGE );
                }
                break;

            case SAVE_AS:
                String userDir1 = System.getProperty( "user.dir" );
                JFileChooser saveDialog = new JFileChooser( userDir1 );
                FileNameExtensionFilter filter1 = new FileNameExtensionFilter( "Property Files", "properties" );
                saveDialog.setFileFilter( filter1 );
                int returnVal1 = saveDialog.showSaveDialog( getContentPane() );
                if ( returnVal1 == JFileChooser.APPROVE_OPTION )
                {
                    String absolutePath = saveDialog.getSelectedFile().getAbsolutePath();
                    StringBuffer toSave = new StringBuffer( absolutePath );
                    if ( !absolutePath.endsWith( ".properties" ) )
                    {
                        toSave.append( ".properties" );
                        setUserFilePath( toSave.toString() );
                        try
                        {
                            saveUsers();
                        }
                        catch ( IOException e1 )
                        {
                            JOptionPane.showMessageDialog( this, "Error during save:\n" + e1.getMessage(), "Ошибка",
                                JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
                break;

            case ABOUT:
                JOptionPane
                    .showMessageDialog(
                        this,
                        "Программа TalkieServer (c), версия 0.1 от 10.10.2008.\nВсе права защищены.\n\nАвтор: Свириденко С.В.\nstas.sviridenko@gmail.com",
                        "О программе TalkieServer...", JOptionPane.INFORMATION_MESSAGE );

            default:
                // сюда попадут в том числе все протоколы
                JCheckBoxMenuItem item = protActions.get( command );
                if ( item != null )
                {// если это протокол, а не что-то иное
                    String name = item.getText();
                    if ( item.isSelected() )
                    {// запускаем протокол
                        dispatchActive.put( name, dispatchInstances.get( name ) );
                        Thread runner = new Thread( dispatchInstances.get( name ) );
                        runner.start();
                    }
                    else
                    {// останавливаем протокол
                        DispatchProtocol protocol = dispatchActive.get( name );
                        if ( protocol != null )
                        {
                            protocol.stop();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void dispose()
    {
        onExit();
        super.dispose();
    }

    public HashMap<String, User> getUsers()
    {
        return users;
    }

    public void loadUsers(
        String fileName )
    {
        try
        {
            FileInputStream inStream = new FileInputStream( fileName );
            Properties props = new Properties();
            props.load( inStream );
            inStream.close();

            users = propsToMap( props );
        }
        catch ( IOException e )
        {
            log.warn( "Error loading users from file: " + fileName );
        }
    }

    public void run()
    {
        display();
    }

    /**
     * сохранить список пользователей в файл конфигурации
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void saveUsers()
        throws IOException,
            FileNotFoundException
    {
        FileOutputStream out = new FileOutputStream( userFileName );
        mapToProps( users ).store( out, "" );
        out.close();
    }

    public void setUserFilePath(
        String fileName )
    {
        this.userFileName = fileName;
    }

    private void initMenuBar()
    {
        // Файл
        JMenu mFile = new JMenu( "Файл" );

        JMenuItem mFileOpen = new JMenuItem( "Открыть..." );
        mFileOpen.setActionCommand( "" + OPEN );
        mFileOpen.addActionListener( this );
        mFile.add( mFileOpen );

        JMenuItem mFileSave = new JMenuItem( "Сохранить" );
        mFileSave.setActionCommand( "" + SAVE );
        mFileSave.addActionListener( this );
        mFile.add( mFileSave );

        JMenuItem mFileSaveAs = new JMenuItem( "Сохранить как..." );
        mFileSaveAs.setActionCommand( "" + SAVE_AS );
        mFileSaveAs.addActionListener( this );
        mFile.add( mFileSaveAs );

        mFile.addSeparator();

        JMenuItem mFileExit = new JMenuItem( "Выход" );
        mFileExit.setActionCommand( "" + EXIT );
        mFileExit.addActionListener( this );
        mFile.add( mFileExit );

        // Протоколы
        JMenu mProtocols = new JMenu( "Протоколы" );

        int i = 1000;
        Set<String> keys = dispatchInstances.keySet();
        for ( String key : keys )
        {
            i++;
            JCheckBoxMenuItem item = new JCheckBoxMenuItem( key );
            item.setActionCommand( "" + i );
            item.setEnabled( dispatchInstances.get( key ) != null );
            item.addActionListener( this );

            protActions.put( i, item );

            mProtocols.add( item );
        }

        // Помощь
        JMenu mHelp = new JMenu( "Помощь" );

        JMenuItem mHelpAbout = new JMenuItem( "О программе..." );
        mHelpAbout.setActionCommand( "" + ABOUT );
        mHelpAbout.addActionListener( this );
        mHelp.add( mHelpAbout );

        // Меню
        JMenuBar bar = new JMenuBar();
        setJMenuBar( bar );

        bar.add( mFile );
        bar.add( mProtocols );
        bar.add( mHelp );
    }

    /**
     * Загрузить имена протоколов и пути к классам реализации
     */
    private void loadProtocols()
    {
        Properties protNames = new Properties();
        try
        {
            FileInputStream inStream = new FileInputStream( PROTOCOLS_PROPERTIES );
            protNames.load( inStream );
            inStream.close();
        }
        catch ( IOException e )
        {
            log.warn( "Unable to load protocols, will continue without them!", e );
        }

        for ( String key : protNames.stringPropertyNames() )
        {
            try
            {
                String clazzName = protNames.getProperty( key );
                if ( clazzName.length() == 0 )
                {
                    clazzName = "talkie.server.process.dispatchers." + key + "Dispatcher";
                }
                Class clazz = Class.forName( clazzName );
                Object object = clazz.newInstance();
                if ( object instanceof DispatchProtocol )
                {
                    DispatchProtocol server = (DispatchProtocol) object;
                    server.setServer( this );
                    dispatchInstances.put( key, server );
                }
                else
                {
                    log.error( "Protocol is not a Talkie protocol: " + key );
                }
            }
            catch ( ClassNotFoundException e )
            {
                log.error( "Protocol is not available on server: " + key );
            }
            catch ( InstantiationException e )
            {
                log.error( "Protocol cannot be instantiated: " + key );
            }
            catch ( IllegalAccessException e )
            {
                log.error( "Protocol cannot be instantiated: " + key );
            }
        }
    }

    /**
     * загрузить список пользователей из файла конфигурации
     */
    private void loadUsers()
    {
        loadUsers( USERS_PROPERTIES );
    }

    private Properties mapToProps(
        HashMap<String, User> map )
    {
        Properties props = new Properties();

        for ( String login : map.keySet() )
        {
            String pass = map.get( login ).getPass();
            props.setProperty( login, pass );
        }

        return props;
    }

    private void onExit()
    {
        log.fatal( "\n\nTALKIE SERVER STOPPING:" + new Date().toString() + "\n======================================" );

        // отключаем диспетчеры соединений
        for ( DispatchProtocol p : dispatchActive.values() )
        {
            p.stop();
        }

        // отключаем существующие соединения
        for ( User u : users.values() )
        {
            if ( u.getStatus() == Status.ONLINE && u.getHandler() != null )
            {
                u.getHandler().stop();
            }
        }

        // пытаемся сохранить информацию о пользователях
        try
        {
            saveUsers();
        }
        catch ( IOException e )
        {
        }

        System.exit( 0 );
    }

    private HashMap<String, User> propsToMap(
        Properties props )
    {
        HashMap<String, User> result = new HashMap<String, User>();

        for ( String login : props.stringPropertyNames() )
        {
            String pass = props.getProperty( login );

            if ( login.length() != 0 )
            {
                String validLogin = login;
                if ( login.length() > 10 )
                {
                    validLogin = login.substring( 0, 10 );
                }

                String validPass = pass;
                if ( pass.length() > 10 )
                {
                    validPass = pass.substring( 0, 10 );
                }

                result.put( validLogin, new User( validLogin, validPass ) );
            }
        }

        return result;
    }

}
