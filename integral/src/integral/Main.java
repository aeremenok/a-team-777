package integral;

import java.util.ArrayList;

/**
 * считает интергал функции f(x) = x^5 - 4x^4 + 5x^3 - 2x^2 + 8x - 15 на вход получает 2 параметра типа double
 * 
 * @author ssv
 */
public class Main
{
    private static final int THREAD_NUMBER = 100;

    public static void main(
        String[] args )
        throws InterruptedException
    {
        if ( args.length < 2 )
        {
            System.err.println( "Необходимо 2 аргумента типа double!" );
            System.exit( 0 );
        }

        double x1 = Double.parseDouble( args[0] );
        double x2 = Double.parseDouble( args[1] );

        ArrayList<IntegralThread> threadList = new ArrayList<IntegralThread>( THREAD_NUMBER );

        MutableDouble result = new MutableDouble( 0 );
        MutableInteger semaphor = new MutableInteger( 0 );

        // формируем потоки
        for ( int i = 0; i < THREAD_NUMBER; i++ )
        {
            double diff = (x2 - x1) / THREAD_NUMBER;
            threadList.add( new IntegralThread( i * diff, (i + 1) * diff, result, semaphor ) );
        }

        // запускаем потоки
        for ( IntegralThread runnable : threadList )
        {
            new Thread( runnable ).start();
        }

        // ждём, пока все рассчётные потоки завершатся
        synchronized ( semaphor )
        {
            semaphor.wait();
        }

        System.out.flush();
        System.out.println( "Result is: " + result );
    }
}
