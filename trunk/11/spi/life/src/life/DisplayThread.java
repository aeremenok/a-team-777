package life;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DisplayThread
    extends Thread
{
    private ArrayList<Thread> lifeThreads = new ArrayList<Thread>();
    private Shell             shell;
    private Display           display;
    private LifeData          data;
    private MouseListener     mouseListener;
    private boolean           work        = false;

    public static void main(
        String args[] )
        throws IOException
    {
        // создаём и запускаем поток отображения
        DisplayThread displayThread = new DisplayThread();
        displayThread.start();
    }

    public DisplayThread()
    {
        this.setName( "Display Thread" );

        // формируем начальное состояние
        final int SIZE = 10;
        boolean[][] fields = new boolean[SIZE][SIZE];

        data = new LifeData( fields );
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

                boolean[][] fields = data.getFields();
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

        this.mouseListener = new MouseAdapter()
        {
            @Override
            public void mouseDown(
                MouseEvent e )
            {
                if ( e.button == 1 )
                {
                    int row = e.y / 20;
                    int col = e.x / 20;

                    boolean[][] fields = data.getFields();
                    if ( fields.length > row && fields[row].length > col )
                    {
                        fields[row][col] = !fields[row][col];
                    }

                    shell.redraw();
                    shell.update();
                }
                else
                {
                    go();
                }
                ;
            }
        };
        shell.addMouseListener( mouseListener );

        // todo разобраться с размерами окна
        shell.setMinimumSize( 300, 300 );
        shell.pack();
        shell.open();

        // перерисовка
        while ( !shell.isDisposed() && !currentThread().isInterrupted() )
        {
            synchronized ( data )
            {
                if ( work )
                {
                    while ( !data.isChanged() )
                    {
                        try
                        {
                            data.wait();
                        }
                        catch ( InterruptedException e )
                        {
                            currentThread().interrupt();
                        }

                        System.out.println( "\nDISPLAY THREAD executes!\n" );
                    }
                }

                shell.redraw();
                shell.update();

                data.setChanged( false );
                data.notifyAll();

                if ( !display.readAndDispatch() )
                {
                    display.sleep();
                }
            }
        }
        display.dispose();
    }

    private void go()
    {
        work = !work;

        if ( work )
        {
            // создаём потоки, иммитирующие "жизнь"
            for ( int i = 0; i < data.getFields().length; i++ )
            {
                for ( int j = 0; j < data.getFields()[i].length; j++ )
                {
                    Thread t = new Thread( new LifeRunnable( data, i, j ), "Life Thread ( " + i + ", " + j + " )" );
                    lifeThreads.add( t );
                    t.start();
                }
            }
        }
        else
        {
            for ( Thread t : lifeThreads )
            {
                t.interrupt();
            }
            lifeThreads.clear();
        }
    }
}
