/**
 * Базовый класс для потока сортировки
 * 
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

        System.out.println( "\n\t\tThread created: " + t );

        // СОЗДАТЬ КОПИЮ ПОДЛЕЖАЩЕГО СОРТИРОВКЕ МАССИВА
        synchronized ( array )
        {

            _array = new short[array.length];

            for ( int i = 0; i < array.length; i++ )
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

            System.out.println( "\n\t" + getMethodName() + " - " + s + ":" );

            for ( int i = 0; i < _array.length; i++ )
                System.out.print( _array[i] + " " );

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
