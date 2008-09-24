package life;

/**
 * �����, ������������ "�����" � ����� �� ������ ����. ������� ���� "�����" �������� ���:
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

                boolean[][] fields = data.getFields();

                // ������� ������� ����� ������
                int neighbours = 0;
                for ( int i = row - 1; i < row + 2; i++ )
                {
                    for ( int j = col - 1; j < col + 2; j++ )
                    {
                        if ( i >= 0 && i < fields.length && j >= 0 && j < fields[i].length )
                        {// ���� ������ ����������� ����
                            if ( i != row || j != col )
                            {// � ��� �� ����������� ������
                                if ( fields[i][j] )
                                {// � ������ - �����
                                    neighbours++;
                                }
                            }
                        }
                    }
                }

                StringBuffer info = new StringBuffer();
                info.append( "cell ( " + row + ", " + col + " )\t" + (fields[row][col] ? "ALIVE" : "DEAD") +
                    "\tneighbours = " + neighbours + "\t" );

                // ������ ����
                if ( !fields[row][col] )
                {// ������ ������
                    if ( neighbours == 3 )
                    {// ������ ������ � ����� 3 ������ - �������
                        info.append( "DEAD CELL REVIVES!" );
                        fields[row][col] = true;
                    }
                }
                else
                {// ������ ����
                    if ( neighbours < 2 || neighbours > 3 )
                    {// ������ ����� �� �����������/�������������
                        info.append( "LIVING CELL DIES!" );
                        fields[row][col] = false;
                    }
                }

                System.out.println( info );

                // try
                // {
                // Thread.sleep( 500 );
                // }
                // catch ( InterruptedException e )
                // {
                // Thread.currentThread().interrupt();
                // }

                data.setChanged( true );
                data.notifyAll();
            }
        }
    }
}
