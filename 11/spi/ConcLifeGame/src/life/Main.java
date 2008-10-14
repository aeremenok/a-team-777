package life;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main
    extends Component
{
    private static final int               FIELD_SIZE    = 10;
    private static boolean[][]             currentCells  = new boolean[FIELD_SIZE][FIELD_SIZE];
    private static boolean[][]             futureCells   = new boolean[FIELD_SIZE][FIELD_SIZE];
    private BufferedImage                  imgAlive      = null;
    private BufferedImage                  imgDead       = null;
    private int                            width;
    private int                            height;
    private static ArrayList<LifeRunnable> lifeRunnables = new ArrayList<LifeRunnable>( FIELD_SIZE * FIELD_SIZE );
    private static ManageRunnable          manager       = null;
    private static Thread                  managerThread = null;

    public static void main(
        String[] args )
        throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            UnsupportedLookAndFeelException
    {
        // готовим окошко для отображения
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        Main field = new Main();
        frame.add( field );
        frame.pack();

        frame.setSize( 300, 300 );
        frame.setVisible( true );

        // готовим потоки пересчёта
        for ( int i = 0; i < FIELD_SIZE; i++ )
        {
            for ( int j = 0; j < FIELD_SIZE; j++ )
            {
                lifeRunnables.add( new LifeRunnable( currentCells, futureCells, i, j ) );
            }
        }

        // готовим поток управления
        manager = new ManageRunnable( FIELD_SIZE, lifeRunnables, currentCells, futureCells, field );
    }

    public Main()
        throws HeadlessException
    {
        super();

        try
        {
            imgAlive = ImageIO.read( new File( "images/alive.png" ) );
            imgDead = ImageIO.read( new File( "images/dead.png" ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        width = imgAlive.getWidth();
        height = imgAlive.getHeight();

        setSize( FIELD_SIZE * width, FIELD_SIZE * height );

        addMouseListener( new MouseAdapter()
        {
            @Override
            public void mousePressed(
                MouseEvent e )
            {
                switch ( e.getButton() )
                {
                    case MouseEvent.BUTTON1:
                        int i = e.getX() / width;
                        int j = e.getY() / height;
                        currentCells[i][j] = !currentCells[i][j];
                        paint( getGraphics() );
                        break;
                    case MouseEvent.BUTTON3:
                        if ( managerThread == null )
                        {// производим запуск
                            managerThread = new Thread( manager );
                            managerThread.start();
                        }
                        else
                        {// прерываем выполнение и удаляем поток
                            managerThread.interrupt();
                            managerThread = null;
                        }
                        break;
                    default:
                        break;
                }
            }
        } );
    }

    @Override
    public void paint(
        Graphics g )
    {
        for ( int i = 0; i < FIELD_SIZE; i++ )
        {
            for ( int j = 0; j < FIELD_SIZE; j++ )
            {
                BufferedImage toDraw = currentCells[i][j] ? imgAlive : imgDead;
                g.drawImage( toDraw, width * i, height * j, null );
            }
        }
    }
}
