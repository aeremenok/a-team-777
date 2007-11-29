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
     * ����� ������������ �������
     */
    protected short[] _array;

    /**
     * ������ ������
     */
    Thread            t;

    /**
     * @param array - ����������� ������
     */
    public SortMethod(
        short[] array )
    {

        // ������� �����
        t = new Thread( this, getMethodName() );

        System.out.println( "\n\t\tThread created: " + t );

        // ������� ����� ����������� ���������� �������
        synchronized ( array )
        {

            _array = new short[array.length];

            for ( int i = 0; i < array.length; i++ )
                _array[i] = array[i];
        }

        printArray( "initial array" );

        // ��������� �����
        t.start();
    }

    /**
     * ������� ������� ���������� ������� �� �����
     * 
     * @param s - ������, ������� ����� ���������� ����� ��������
     */
    private void printArray(
        String s )
    {

        synchronized ( System.out )
        {

            System.out.println( "\n\t" + getMethodName() + " - " + s + ":" );

            for ( int i = 0; i < _array.length; i++ )
                System.out.print( _array[i] + " " );

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
     */
    abstract protected void sort()
        throws InterruptedException;

    /**
     * ����� �����
     */
    public void run()
    {
        try
        {

            long start = System.currentTimeMillis();

            sort();

            long finish = System.currentTimeMillis();

            printArray( "sorted array (finished in " + (finish - start) + " ms)" );

        }
        catch ( InterruptedException e )
        {
            System.out.println( "\n\n\tThread " + getMethodName() + " interrupted!\n\n" );
        }
    }
}
