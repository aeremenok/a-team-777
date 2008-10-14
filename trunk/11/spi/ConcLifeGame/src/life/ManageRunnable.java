package life;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * поток управления пересчётом состояний клеток
 * 
 * @author ssv
 */
public class ManageRunnable
    implements
        Runnable
{
    private final int                     fieldSize;
    private final ArrayList<LifeRunnable> lifeRunnables;
    private final boolean[][]             currentCells;
    private final boolean[][]             futureCells;
    private final Main                    component;

    public ManageRunnable(
        int fieldSize,
        ArrayList<LifeRunnable> lifeRunnables,
        boolean[][] currentCells,
        boolean[][] futureCells,
        Main main )
    {
        this.fieldSize = fieldSize;
        this.lifeRunnables = lifeRunnables;
        this.currentCells = currentCells;
        this.futureCells = futureCells;
        this.component = main;
    }

    public void run()
    {
        // основной цикл работы
        while ( !Thread.currentThread().isInterrupted() )
        {
            CountDownLatch latch = new CountDownLatch( fieldSize * fieldSize );

            // даём потокам нужный latch и запускаем
            for ( LifeRunnable life : lifeRunnables )
            {
                life.setLatch( latch );
                new Thread( life ).start();
            }

            try
            {
                Thread.sleep( 500 );
            }
            catch ( InterruptedException e1 )
            {
                Thread.currentThread().interrupt();
            }

            // ждём пересчёта
            try
            {
                latch.await();
            }
            catch ( InterruptedException e )
            {
                Thread.currentThread().interrupt();
            }

            // переносим пересчитанные данные
            for ( int i = 0; i < currentCells.length; i++ )
            {
                for ( int j = 0; j < currentCells[i].length; j++ )
                {
                    currentCells[i][j] = futureCells[i][j];
                }
            }

            // отрисовка
            component.paint( component.getGraphics() );
        }
    }
}
