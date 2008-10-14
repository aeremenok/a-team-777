package integral;

/**
 * поток для подсчёта значения интеграла
 * 
 * @author ssv
 */
public class IntegralThread
    implements
        Runnable
{
    private MutableDouble  result;
    private final double   x1;
    private final double   x2;
    private MutableInteger semaphor;
    private int            number;

    public IntegralThread(
        double x1,
        double x2,
        MutableDouble result,
        MutableInteger semaphor )
    {
        this.x1 = x1;
        this.x2 = x2;
        this.result = result;
        this.semaphor = semaphor;

        synchronized ( semaphor )
        {
            semaphor.increment();
            this.number = semaphor.getValue();
            System.out.println( "Thread " + number + " created, from\t" + x1 + " to\t" + x2 + "!" );
        }
    }

    public double f(
        double x )
    {
        return x * x * x * x * x - 4 * x * x * x * x + 5 * x * x * x - 2 * x * x + 8 * x - 15;
    }

    public void run()
    {
        System.out.println( "Thread " + number + " is running!" );
        double diff = x2 - x1;

        // считаем по методу 1
        double res1 = (f( x1 ) + f( x2 )) / 2 * diff;

        // считаем по методу 2
        double res2 = f( (x1 + x2) / 2 ) * diff;

        // возвращаем среднее
        double res = (res1 + res2) / 2;

        // записываем результат
        synchronized ( result )
        {
            result.add( res );
        }

        System.out.println( "Thread " + number + " finishes!" );

        // уменьшаем счётчик потоков, если все этот поток последний - будим главный поток от спячки
        synchronized ( semaphor )
        {
            semaphor.decrement();
            if ( semaphor.getValue() == 0 )
            {
                semaphor.notify();
            }
        }
    }
}
