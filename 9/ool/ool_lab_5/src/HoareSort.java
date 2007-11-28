/**
 * @author lysenko
 */
public class HoareSort
    extends SortMethod
{
    /**
     * @param array - ����������� ������
     */
    public HoareSort(
        short[] array )
    {
        super( array );
    }

    /**
     * ��������� ���������� ������� ������� ����� ������� ���������� ����������
     * ��������� ��������� � ��������. ���� ��������� ������: 1) �������� �
     * ������� ��������� �������, ������� ����� �������� ������� ���������. 2)
     * �������� ���������� �������: ������������ ������ ����� �������, ����� ���
     * ��������, ������� ��� ������ �������� ��������, ��������� ����� �� ����,
     * � ��� ��������, ������� �������� � ������ �� ����. 3) ����������
     * ������������� ���������, ������� ����� � ������ �� �������� ��������.
     * ����� �������� �������� ������, ��������� �� ������ ��� ���� ���������,
     * ������� ��� �����������. �������� ������ �����������, ��������� �� ������
     * �������� �� ������ �� ������� ���� ���� ������� �� ��� �������������
     * �����.
     */
    protected void sort()
        throws InterruptedException
    {
        quicksort( 0, _array.length );
    }

    /**
     * ����������� ������� ����������
     * 
     * @param p
     * @param r
     */
    private void quicksort(
        int p,
        int r )
    {
        int q;
        if ( p < r )
        {
            q = partition( p, r );
            quicksort( p, q - 1 );
            quicksort( q + 1, r );
        }
    }

    /**
     * �������� ���������� �������
     * 
     * @param p
     * @param r
     * @return
     */
    private int partition(
        int p,
        int r )
    {
        short x = _array[r];
        int i = p - 1;
        short tmp;
        for ( int j = p; j < r; j++ )
        {
            if ( _array[j] <= x )
            {
                i++;
                tmp = _array[i];
                _array[i] = _array[j];
                _array[j] = tmp;
            }
        }

        tmp = _array[r];
        _array[r] = _array[i + 1];
        _array[i + 1] = tmp;

        return i + 1;
    }

    /**
     * @return ��� ������
     */
    protected String getMethodName()
    {
        return "Hoare Sort (quicksort)";
    }
}