/**
 * ������� ����� ��� ������ ����������
 * 
 * @author lysenko
 */
abstract class SortMethod
    implements
        Runnable
{
    /**
     * ���������� ���������� (��� ��������� �������)
     */
    private static int _LOOP = 1000;

    /**
     * ����� ������������ �������
     */
    protected Short[]  _array;

    /**
     * ������ ������
     */
    Thread             t;

    /**
     * @param array - ����������� ������
     */
    public SortMethod(
        Short[] array )
    {

        // ������� �����
        t = new Thread( this, getMethodName() );

        System.out.println( "\n\t\tThread created: " + t );

        // ������� ����� ����������� ���������� �������
        synchronized ( array )
        {

            _array = new Short[array.length];

            for ( int i = 0; i < array.length; i++ )
            {
                _array[i] = array[i];
            }
        }

        printArray( _array, "initial array" );

        // ��������� �����
        t.start();
    }

    /**
     * ������� ������� ���������� ������� �� �����
     * 
     * @param array todo
     * @param message - ������, ������� ����� ���������� ����� ��������
     */
    private void printArray(
        Short[] array,
        String message )
    {

        synchronized ( System.out )
        {

            System.out.println( "\n\t" + getMethodName() + " - " + message + ":" );

            for ( int i = 0; i < array.length; i++ )
            {
                System.out.print( array[i] + " " );
            }

            System.out.println();
        }
    }

    /**
     * @return ��� ������
     */
    protected String getMethodName()
    {
        return "No method specified.";
    }

    /**
     * ��������� ����������
     * 
     * @param arrayToSort todo
     */
    abstract protected void sort(
        Short[] arrayToSort )
        throws InterruptedException;

    /**
     * ����� �����
     */
    public void run()
    {
        try
        {
            Short[] tempArray = null;

            // ����� ������
            long startTime = System.currentTimeMillis();

            // ��������� � ����� ��� ���������� �������
            for ( int i = 0; i < _LOOP; i++ )
            {
                // �������� ������
                tempArray = new Short[_array.length];

                for ( int j = 0; j < _array.length; j++ )
                {
                    tempArray[j] = _array[j];
                }

                // ��������� ������������� ������
                sort( tempArray );
            }

            // ����� ���������
            long finishTime = System.currentTimeMillis();

            // ������� ��������������� ������
            printArray( tempArray, "sorted array (finished in " + (finishTime - startTime) + " ms)" );

        }
        catch ( InterruptedException e )
        {
            System.out.println( "\n\n\tThread " + getMethodName() + " interrupted!\n\n" );
        }
    }
}
