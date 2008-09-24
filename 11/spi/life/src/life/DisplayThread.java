package life;

import java.io.IOException;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DisplayThread
    extends Thread
{
    private Shell    shell;
    private Display  display;
    private LifeData data;

    public static void main(
        String args[] )
        throws IOException
    {
        // формируем начальное состояние
        // todo загружать начальную конфигурацию из файла
        boolean[][] fields =
            new boolean[][] { { true, true, true, true }, { true, true, true, true }, { true, true, true, true },
                            { true, true, true, true } };

        LifeData data = new LifeData( fields );

        // создаём поток отображения
        DisplayThread displayThread = new DisplayThread( data );
        displayThread.start();

        // создаём потоки, иммитирующие "жизнь"
        for ( int i = 0; i < data.getFields().length; i++ )
        {
            for ( int j = 0; j < data.getFields()[i].length; j++ )
            {
                Thread t = new Thread( new LifeRunnable( data, i, j ), "Life Thread ( " + i + ", " + j + " )" );
                t.setDaemon( true );
                t.start();
            }
        }
    }

    public DisplayThread(
        final LifeData data )
    {
        this.setName( "Display Thread" );
        this.data = data;
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

        shell.pack();

        // todo разобраться с размерами окна
        // shell.setMinimumSize( fields[0].length * width, fields.length * height );
        shell.setMinimumSize( 80, 150 );
        shell.open();

        // перерисовка
        while ( !shell.isDisposed() && !currentThread().isInterrupted() )
        {
            synchronized ( data )
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
                }

                System.out.println( "!!! REDRAWING !!!" );
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
}
