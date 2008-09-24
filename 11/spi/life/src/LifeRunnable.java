/**
 * Поток, иммитирующий "жизнь" в одной из клеток поля. Правила игры "Жизнь" смотреть тут:
 * http://ru.wikipedia.org/wiki/%D0%98%D0%B3%D1%80%D0%B0_%D0%B6%D0%B8%D0%B7%D0%BD%D1%8C
 * 
 * @author ssv
 */
public class LifeRunnable
    implements
        Runnable
{
    private boolean[][] fields;
    private int         row;
    private int         col;

    LifeRunnable(
        boolean[][] fields,
        int i,
        int j )
    {
        this.fields = fields;
        this.row = i;
        this.col = j;
    }

    public void run()
    {
        synchronized ( fields )
        {
            while ( !Thread.currentThread().isInterrupted() )
            {
                System.out.println( Thread.currentThread().getName() + " executes!" );

                // считаем соседей нашей клетки
                int neighbours = 0;
                for ( int i = row - 1; i < row + 2; i++ )
                {
                    for ( int j = col - 1; j < col + 2; j++ )
                    {
                        // если клетка принадлежит рассматриваемому полю и не является текущей
                        if ( i != row && j != col && i > 0 && i < fields.length && j > 0 && j < fields[i].length )
                        {
                            if ( fields[i][j] )
                            {
                                // эта соседняя клетка - жилая
                                neighbours++;
                            }
                        }
                    }
                }

                // логика игры
                if ( !fields[row][col] )
                {// клетка мертва
                    if ( neighbours == 3 )
                    {// клетка мертва и имеет 3 соседа - оживает
                        fields[row][col] = true;
                    }
                }
                else
                {// клетка жива
                    if ( neighbours < 2 || neighbours > 3 )
                    {// клетка помрёт от одиночества/перенаселения
                        fields[row][col] = false;
                    }
                }

                fields.notifyAll();

                try
                {
                    Thread.sleep( 200 );
                    fields.wait();
                }
                catch ( InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
