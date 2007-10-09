package ru.spb.etu._3305.group_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: ssv
 * Date: 09.10.2007
 * Time: 21:47:37
 * To change this template use File | Settings | File Templates.
 */
public class EvenOdd
{
    public static void main( String[] args )
    {
        int[] numbers = null;
        BufferedReader in = null;

        // вывод справки
        if ( args.length == 1 &&
                ( args[0].equalsIgnoreCase( "/?" ) || args[0].equalsIgnoreCase( "-help" ) ) )
        {
            System.out.println();
            System.out.println( "Использование: EvenOdd" );
            System.out.println( "               или" );
            System.out.println( "               EvenOdd [список_чисел]" );
            System.out.println();
            System.out.print( "В первом случае вы можете указать вы должны будете " +
                    "указать программе параметры в ходе короткого диалога, " +
                    "во втором будут использоваться числа, заданные в качестве " +
                    "аргументов командной строки." );
        }
        else
        {
            try
            {
                // TODO здесь будет настройка System.out на кодировку Windows-1251

                // открываем входной поток
                in = new BufferedReader( new InputStreamReader( System.in, "Windows-1251" ) );

                // узнаём количество чисел для работы
                int n = 0;

                if ( args.length == 0 )
                {// если аргументов командной строки нет - поболтаем с пользователем
                    System.out.print( "Пожалуйста, введите количество чисел для работы ( 1 <= n <= 10 ): " );
                    String str = in.readLine();
                    n = Integer.parseInt( str );
                }
                else
                {// если есть аргументы командной строки - используем их
                    n = args.length;
                }

                // если чисел много/мало
                if ( n > 10 || n < 1 )
                {
                    throw new Exception( "Число вне границ указанного диапазона!" );
                }

                // если количество чисел в норме
                numbers = new int[n];

                // узнаём числа
                if ( args.length == 0 )
                {// пользователь вводит числа
                    System.out.println();

                    for ( int i = 0; i < n; i++ )
                    {
                        System.out.print( "" + ( i + 1 ) + ". Введите целое число: " );
                        String str = in.readLine();
                        numbers[i] = Integer.parseInt( str );
                    }
                }
                else
                {// берём числа из командной строки
                    for ( int i = 0; i < args.length; i++ )
                    {
                        numbers[i] = Integer.parseInt( args[i] );
                    }
                }

                // выводим числа
                System.out.println();
                System.out.println( "Введены числа:" );
                System.out.println( "==============" );
                System.out.print( "    " );

                for ( int number : numbers )
                {
                    System.out.print( number + "\t" );
                }
                System.out.println();

                // выводим чётные числа
                System.out.println();
                System.out.println( "Чётные числа:" );
                System.out.println( "=============" );
                System.out.print( "    " );

                for ( int number1 : numbers )
                {
                    if ( number1 % 2 == 0 )
                    {
                        System.out.print( number1 + "\t" );
                    }
                }
                System.out.println();

                // выводим нечётные числа
                System.out.println();
                System.out.println( "Нечётные числа:" );
                System.out.println( "===============" );
                System.out.print( "    " );

                for ( int number2 : numbers )
                {
                    if ( number2 % 2 != 0 )
                    {
                        System.out.print( number2 + "\t" );
                    }
                }
                System.out.println();

                // до свидания, пользователь
                System.out.println();
                System.out.println( "Спасибо за использование программы, всего доброго!" );
                System.out.println( "(C) Свириденко С.В., 2007" );
            }
            catch ( UnsupportedEncodingException e )
            {
                System.out.println( "Кодировка Windows-1251 не поддерживается системой!" );
            }
            catch ( IOException e )
            {
                System.out.println( "Ошибки при работе с потоком" );
            }
            catch ( NumberFormatException e )
            {
                System.out.println( "Введённая строка не является числом!" );
            }
            catch ( Exception e )
            {
                System.out.println( e.getMessage() );
            }
            finally
            {
                try
                {
                    in.close();
                }
                catch ( IOException ex )
                {
                    System.out.println( "Поток сопротивляется закрытию!" );
                }
            }
        }
    }
}