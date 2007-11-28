/**
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

        System.out.println( "\nThread created: " + t );

        // ������� ����� ����������� ���������� �������
        _array = new short[array.length];

        for ( int i = 0; i < array.length; i++ )
        {
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

            System.out.println( "\n" + getMethodName() + " - " + s + ":" );

            for ( short element : _array )
            {
                System.out.print( element + " " );
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
            sort();

            printArray( "sorted array" );
        }
        catch ( InterruptedException e )
        {
            System.out.println( "����� " + getMethodName() + " �������!" );
        }
    }
}
