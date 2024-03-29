/**
 * ���������� ����������� ����������
 * 
 * @author lysenko
 */
public class BubbleSort
    extends SortMethod
{

    /**
     * @param array - ����������� ������
     */
    public BubbleSort(
        Short[] array )
    {
        super( array );
    }

    /**
     * ��������� ���������� ������� ������� ��������
     */
    protected void sort(Short[] arrayToSort)
        throws InterruptedException
    {

        /* �������� ������� � ������������� �������� �� ������������ �������.
         * �� ������ ������ �������� ��������������� ������������ ������� �,
         * ���� ������� � ���� ��������, ����������� ����� ���������. �������
         * �� ������� ����������� �� ��� ���, ���� �� ��������� �������
         * �� ��������, ��� ������ ������ �� �����, ��� �������� � ������
         * ������������. ��� ������� ���������, �������, ������� �� �� ���� �����,
         * ���������� �� ������ ������� ��� ������ � ����, ������ � ��������
         * ���������.
         */
        for ( int i = _array.length - 1; i > 0; i-- )
        {
            for ( int j = 0; j < i; j++ )
            {
                if ( _array[j] > _array[j + 1] )
                {
                    swap( j, j + 1 );
                }
            }
        }
    }

    /**
     * ���������� ����� ���������
     * 
     * @param i - ������ ������� ��������
     * @param j - ������ ������� ��������
     */
    void swap(
        int i,
        int j )
    {

        short t = _array[i];
        _array[i] = _array[j];
        _array[j] = t;
    }

    /**
     * @return ��� ������
     */
    protected String getMethodName()
    {
        return "Bubble sort";
    }

}
