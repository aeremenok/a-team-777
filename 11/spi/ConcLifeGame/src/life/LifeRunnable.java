package life;

import java.util.concurrent.CountDownLatch;

/**
 * поток пересчёта состояния клетки на поле
 * 
 * @author ssv
 */
public class LifeRunnable
    implements
        Runnable
{
    /**
     * примитив синхронизации из java.util.concurrent
     */
    private CountDownLatch    latch;
    /**
     * текущее состояние клеток
     */
    private final boolean[][] currentCells;
    /**
     * состояние клеток на следующем шаге
     */
    private final boolean[][] futureCells;
    /**
     * строка для анализа
     */
    private final int         row;
    /**
     * столбец для анализа
     */
    private final int         col;

    public LifeRunnable(
        boolean[][] currentCells,
        boolean[][] futureCells,
        int row,
        int col )
    {
        this.currentCells = currentCells;
        this.futureCells = futureCells;
        this.row = row;
        this.col = col;
    }

    public void run()
    {
        // считаем количество соседей
        int neighbours = 0;
        for ( int i = row - 1; i < row + 2; i++ )
        {
            for ( int j = col - 1; j < col + 2; j++ )
            {
                if ( i >= 0 && i < currentCells.length && j >= 0 && j < currentCells[i].length )
                {// клетка принадлежит полю
                    if ( i != row || j != col )
                    {// и это не сама клетка
                        if ( currentCells[i][j] )
                        {// это живой сосед
                            neighbours++;
                        }
                    }
                }
            }
        }

        if ( !currentCells[row][col] )
        {// клетка дохлая
            if ( neighbours == 3 )
            {// и у неё 3 соседа
                // она оживает
                futureCells[row][col] = true;
            }
            else
            {// всё остаётся как в текущем поколении
                futureCells[row][col] = currentCells[row][col];
            }
        }
        else
        {// клетка живая
            if ( neighbours < 2 || neighbours > 3 )
            {// и соседей мало/много
                // клетке абзац
                futureCells[row][col] = false;
            }
            else
            {// всё остаётся как в текущем поколении
                futureCells[row][col] = currentCells[row][col];
            }
        }

        // синхронизация
        latch.countDown();
    }

    public void setLatch(
        CountDownLatch latch )
    {
        this.latch = latch;
    }
}
