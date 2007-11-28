/**
 * @author lysenko
 */
abstract class SortMethod
    implements
        Runnable
{
    /**
     * Копия сортируемого массива
     */
    protected short[] _array;

    /**
     * Объект потока
     */
    Thread            t;

    /**
     * @param array - сортируемый массив
     */
    public SortMethod(
        short[] array )
    {

        // СОЗДАТЬ ПОТОК
        t = new Thread( this, getMethodName() );

        System.out.println( "\nThread created: " + t );

        // СОЗДАТЬ КОПИЮ ПОДЛЕЖАЩЕГО СОРТИРОВКЕ МАССИВА
        _array = new short[array.length];

        for ( int i = 0; i < array.length; i++ )
        {
            _array[i] = array[i];
        }

        printArray( "initial array" );

        // ЗАПУСТИТЬ ПОТОК
        t.start();
    }

    /**
     * Вывести текущее содержимое массива на экран
     * 
     * @param s - Строка, которая будет напечатана перед массивом
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
     * @return Имя метода
     */
    protected String getMethodName()
    {
        return "No method specified.";
    }

    /**
     * Выполнить сортировку
     */
    abstract protected void sort()
        throws InterruptedException;

    /**
     * Точка входа
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
            System.out.println( "Поток " + getMethodName() + " прерван!" );
        }
    }
}
