import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DisplayThread
    extends Thread
{
    private boolean[][]       fields;
    private Shell             shell;
    private Display           display;
    private ArrayList<Thread> lifeThreads = new ArrayList<Thread>();

    public static void main(
        String args[] )
        throws IOException
    {
        // формируем начальное состояние
        // todo загружать начальную конфигурацию из файла
        boolean[][] fields =
            new boolean[][] { { true, true, true, true }, { true, true, true, true }, { true, true, true, true },
                            { true, true, true, true } };

        // запускаем поток отображения
        DisplayThread displayThread = new DisplayThread( fields );
        displayThread.start();

        // создаём потоки, иммитирующие "жизнь"
        for ( int i = 0; i < fields.length; i++ )
        {
            for ( int j = 0; j < fields[i].length; j++ )
            {
                Thread t = new Thread( new LifeRunnable( fields, i, j ), "Life Thread ( " + i + ", " + j + " )" );
                t.setPriority( MIN_PRIORITY );
                displayThread.getLifeThreads().add( t );
            }
        }

        // запускаем созданные потоки
        for ( Thread t : displayThread.getLifeThreads() )
        {
            t.start();
        }
    }

    public DisplayThread(
        final boolean[][] fields )
    {
        this.setName( "Display Thread" );
        this.fields = fields;
        setPriority( MAX_PRIORITY );
    }

    @Override
    public void run()
    {
        // инициализация SWT
        display = new Display();
        shell = new Shell( display );
        shell.setLayout( new RowLayout() );

        final Image alive = new Image( display, "images/alive.png" );
        final Image dead = new Image( display, "images/dead.png" );

        final int width = alive.getBounds().width;
        final int height = alive.getBounds().height;

        shell.addPaintListener( new PaintListener()
        {
            public void paintControl(
                PaintEvent e )
            {
                int wDiff = 0;
                int hDiff = 0;

                for ( int i = 0; i < fields.length; i++ )
                {
                    wDiff = 0;
                    for ( int j = 0; j < fields[i].length; j++ )
                    {
                        Image img = fields[i][j] ? alive : dead;
                        e.gc.drawImage( img, wDiff, hDiff );
                        wDiff += width;
                    }
                    hDiff += height;
                }
            }
        } );

        shell.pack();

        // todo разобраться с размерами окна
        // shell.setMinimumSize( fields[0].length * width, fields.length * height );
        shell.setMinimumSize( 80, 150 );
        shell.open();

        while ( !shell.isDisposed() )
        {
            System.out.println( "!!! REDRAWING !!!" );

            // перерисовка
            shell.redraw();
            shell.update();

            if ( !display.readAndDispatch() )
            {
                display.sleep();
            }
        }
        display.dispose();

        for ( Thread t : lifeThreads )
        {
            t.interrupt();
        }
    }

    private ArrayList<Thread> getLifeThreads()
    {
        return lifeThreads;
    };
}
