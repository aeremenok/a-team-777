package talkie.server.ui;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import talkie.common.ui.MyFrame;
import talkie.server.Server;

public class ServerUI
    extends MyFrame
    implements
        Runnable
{
    private static final String EXIT = "exit";
    private final Server        server;

    public ServerUI(
        Server server )
    {
        this.server = server;
    }

    public void run()
    {
        setLayout( new BorderLayout() );
        initMenuBar();

        display();
    }

    private void initMenuBar()
    {
        // Файл
        JMenu mFile = new JMenu( "Файл" );

        JMenuItem mFileExit = new JMenuItem( "Выход" );
        mFile.add( mFileExit );

        // Протоколы
        JMenu mProtocols = new JMenu( "Протоколы" );
        JMenuItem mProtUdp = new JMenuItem( "UDP" );
        JMenuItem mProtTcp = new JMenuItem( "TCP" );
        JMenuItem mProtRmi = new JMenuItem( "RMI" );
        JMenuItem mProtCorba = new JMenuItem( "CORBA" );
        mProtocols.add( mProtUdp );
        mProtocols.add( mProtTcp );
        mProtocols.add( mProtRmi );
        mProtocols.add( mProtCorba );

        // Помощь
        JMenu mHelp = new JMenu( "Помощь" );
        JMenuItem mHelpAbout = new JMenuItem( "О программе..." );
        mHelp.add( mHelpAbout );

        JMenuBar bar = new JMenuBar();
        setJMenuBar( bar );

        bar.add( mFile );
        bar.add( mProtocols );
        bar.add( mHelp );
    }
}
