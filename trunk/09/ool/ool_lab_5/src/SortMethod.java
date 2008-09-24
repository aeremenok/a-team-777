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
     * Количество повторений (для измерения времени)
     */
    private static int _LOOP = 1000;

    /**
     * Копия сортируемого массива
     */
    protected Short[]  _array;

    /**
     * Объект потока
     */
    Thread             t;

    /**
     * @param array - сортируемый массив
     */
    public SortMethod(
        Short[] array )
    {

        // СОЗДАТЬ ПОТОК
        t = new Thread( this, getMethodName() );

        System.out.println( "\n\t\tThread created: " + t );

        // СОЗДАТЬ КОПИЮ ПОДЛЕЖАЩЕГО СОРТИРОВКЕ МАССИВА
        synchronized ( array )
        {

            _array = new Short[array.length];

            for ( int i = 0; i < array.length; i++ )
            {
                _array[i] = array[i];
            }
        }

        printArray( _array, "initial array" );

        // ЗАПУСТИТЬ ПОТОК
        t.start();
    }

    /**
     * Вывести текущее содержимое массива на экран
     * 
     * @param array todo
     * @param message - Строка, которая будет напечатана перед массивом
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
     * @return Имя метода
     */
    protected String getMethodName()
    {
        return "No method specified.";
    }

    /**
     * Выполнить сортировку
     * 
     * @param arrayToSort todo
     */
    abstract protected void sort(
        Short[] arrayToSort )
        throws InterruptedException;

    /**
     * Точка входа
     */
    public void run()
    {
        try
        {
            Short[] tempArray = null;

            // ВРЕМЯ НАЧАЛА
            long startTime = System.currentTimeMillis();

            // ВЫПОЛНЯЕМ В ЦИКЛЕ ДЛЯ УВЕЛИЧЕНИЯ ВРЕМЕНИ
            for ( int i = 0; i < _LOOP; i++ )
            {
                // КОПИРУЕМ МАССИВ
                tempArray = new Short[_array.length];

                for ( int j = 0; j < _array.length; j++ )
                {
                    tempArray[j] = _array[j];
                }

                // СОРТИРУЕМ СКОПИРОВАННЫЙ МАССИВ
                sort( tempArray );
            }

            // ВРЕМЯ ОКОНЧАНИЯ
            long finishTime = System.currentTimeMillis();

            // ВЫВОДИМ ОТСОРТИРОВАННЫЙ МАССИВ
            printArray( tempArray, "sorted array (finished in " + (finishTime - startTime) + " ms)" );

        }
        catch ( InterruptedException e )
        {
            System.out.println( "\n\n\tThread " + getMethodName() + " interrupted!\n\n" );
        }
    }
}
