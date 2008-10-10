package talkie.server.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import talkie.common.ui.MyFrame;
import talkie.server.Server;

public class ServerUI
    extends MyFrame
    implements
        Runnable,
        ActionListener
{
    private static final int                    EXIT          = 0;
    private static final int                    OPEN          = 1;
    private static final int                    SAVE          = 2;
    private static final int                    SAVE_AS       = 3;

    private Logger                              log           = Logger.getLogger( ServerUI.class );
    private final Server                        server;
    private Properties                          protNames     = new Properties();
    private HashMap<Integer, JCheckBoxMenuItem> protActions   = new HashMap<Integer, JCheckBoxMenuItem>();
    private HashMap<String, Runnable>           protInstances = new HashMap<String, Runnable>();
    private HashMap<String, Thread>             protRunning   = new HashMap<String, Thread>();

    public ServerUI(
        Server server )
    {
        this.server = server;

        try
        {
            protNames.load( new FileInputStream( "protocols.properties" ) );
        }
        catch ( IOException e )
        {
            log.warn( "Unable to load protocols, will continue without them!", e );
        }

        // загрузка доступных протоколов
        for ( String key : protNames.stringPropertyNames() )
        {
            try
            {
                String clazzName = protNames.getProperty( key );
                if ( clazzName.length() == 0 )
                {
                    clazzName = "talkie.server.process." + key + "Server";
                }
                Class clazz = Class.forName( clazzName );
                Object object = clazz.newInstance();
                if ( object instanceof Runnable )
                {
                    protInstances.put( key, (Runnable) object );
                }
                else
                {
                    log.error( "Protocol is not runnable: " + key );
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

    public void actionPerformed(
        ActionEvent e )
    {
        int command = Integer.parseInt( e.getActionCommand() );

        switch ( command )
        {
            case EXIT:
                System.exit( 0 );
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
                    server.setUserFilePath( fileName );
                    server.loadUsers( fileName );
                }
                break;

            case SAVE:
                try
                {
                    server.saveUsers();
                }
                catch ( IOException e1 )
                {
                    // todo вывести сообщение в виде MessageBox
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
                        server.setUserFilePath( toSave.toString() );
                        try
                        {
                            server.saveUsers();
                        }
                        catch ( IOException e1 )
                        {
                            // todo вывести сообщение в виде MessageBox
                        }
                    }
                }
                break;

            default:
                // сюда попадут в том числе все протоколы
                JCheckBoxMenuItem item = protActions.get( command );
                if ( item != null )
                {// если это протокол, а не что-то иное
                    String name = item.getText();
                    if ( item.isSelected() )
                    {// запускаем протокол
                        Thread runner = new Thread( protInstances.get( name ) );
                        protRunning.put( name, runner );
                        runner.start();
                    }
                    else
                    {// останавливаем протокол
                        Thread runner = protRunning.get( name );
                        if ( runner != null && !runner.isInterrupted() )
                        {
                            runner.interrupt();
                        }
                    }
                }
                break;
        }
    }

    public void run()
    {
        setLayout( new BorderLayout() );
        setSize( 500, 300 );
        initMenuBar();

        display();
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
        Set<String> keys = protNames.stringPropertyNames();
        for ( String key : keys )
        {
            i++;
            JCheckBoxMenuItem item = new JCheckBoxMenuItem( key );
            item.setActionCommand( "" + i );
            item.setEnabled( protInstances.get( key ) != null );
            item.addActionListener( this );

            protActions.put( i, item );

            mProtocols.add( item );
        }

        // Помощь
        JMenu mHelp = new JMenu( "Помощь" );

        JMenuItem mHelpAbout = new JMenuItem( "О программе..." );
        mHelp.add( mHelpAbout );

        // Меню
        JMenuBar bar = new JMenuBar();
        setJMenuBar( bar );

        bar.add( mFile );
        bar.add( mProtocols );
        bar.add( mHelp );
    }
}
