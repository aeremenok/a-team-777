package life;

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
    private LifeData data;
    private int      row;
    private int      col;

    LifeRunnable(
        LifeData data,
        int i,
        int j )
    {
        this.data = data;
        this.row = i;
        this.col = j;
    }

    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() )
        {
            synchronized ( data )
            {
                while ( data.isChanged() )
                {
                    try
                    {
                        data.wait();
                    }
                    catch ( InterruptedException e )
                    {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println( Thread.currentThread().getName() + " executes!" );

                boolean[][] fields = data.getFields();

                // считаем соседей нашей клетки
                int neighbours = 0;
                for ( int i = row - 1; i < row + 2; i++ )
                {
                    for ( int j = col - 1; j < col + 2; j++ )
                    {
                        if ( i != row || j != col )
                        {// если это не центральная клетка
                            if ( i >= 0 && i < fields.length && j >= 0 && j < fields[i].length )
                            {// и клетка принадлежит рассматриваемому полю
                                if ( fields[i][j] )
                                {// и клетка - жилая
                                    neighbours++;
                                }
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

                data.setChanged( true );
                data.notifyAll();

                try
                {
                    Thread.sleep( 300 );
                }
                catch ( InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
