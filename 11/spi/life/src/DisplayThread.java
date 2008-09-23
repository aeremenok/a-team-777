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
        // todo загружать начальную конфигурацию из файла
        boolean[][] fields =
            new boolean[][] { { false, true, false, false }, { false, true, true, true }, { false, true, true, false },
                            { false, true, false, false } };

        DisplayThread displayThread = new DisplayThread( fields );
        displayThread.start();

        for ( int i = 0; i < 5; i++ )
        {
            Thread t = new Thread( new LifeRunnable( fields ), "Life Thread " + i );
            displayThread.getLifeThreads().add( t );
            t.start();
        }
    }

    public DisplayThread(
        final boolean[][] fields )
    {
        this.setName( "Display Thread" );
        this.fields = fields;
    }

    @Override
    public void run()
    {
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

        // shell.setMinimumSize( fields[0].length * width, fields.length * height );
        shell.setMinimumSize( 80, 150 );
        shell.open();

        synchronized ( fields )
        {
            while ( !shell.isDisposed() )
            {
                System.out.println( "!!! REDRAWING !!!" );

                shell.redraw();

                fields.notifyAll();
                try
                {
                    fields.wait();
                }
                catch ( InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

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
    }

    private ArrayList<Thread> getLifeThreads()
    {
        return lifeThreads;
    };
}
