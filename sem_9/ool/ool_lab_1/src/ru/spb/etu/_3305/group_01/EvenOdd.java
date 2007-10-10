package ru.spb.etu._3305.group_01;

import java.io.*;

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
        BufferedWriter out = null;

        try
        {
            // настраиваем кодировку вывода на Windows
            out = new BufferedWriter( new OutputStreamWriter( System.out, "cp866" ) );

            // вывод справки
            if ( args.length == 1 &&
                    ( args[0].equalsIgnoreCase( "/?" ) || args[0].equalsIgnoreCase( "-help" ) ) )
            {
                out.write("");
                out.write( "Использование: EvenOdd\n" );
                out.write( "               или\n" );
                out.write( "               EvenOdd [список_чисел]\n" );
                out.write("\n");
                out.write( "В первом случае вы можете указать вы должны будете " +
                        "указать программе параметры в ходе короткого диалога, " +
                        "во втором будут использоваться числа, заданные в качестве " +
                        "аргументов командной строки.\n" );
                out.flush();
            }
            else
            {
                // открываем входной поток
                in = new BufferedReader( new InputStreamReader( System.in, "cp866" ) );

                // узнаём количество чисел для работы
                int n = 0;

                if ( args.length == 0 )
                {// если аргументов командной строки нет - поболтаем с пользователем
                    out.write( "Пожалуйста, введите количество чисел для работы ( 1 <= n <= 10 ): " );
                    out.flush();
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
                    out.write("\n");

                    for ( int i = 0; i < n; i++ )
                    {
                        out.write( "" + ( i + 1 ) + ". Введите целое число: " );
                        out.flush();
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
                out.write("\n");
                out.write( "Введены числа:\n" );
                out.write( "==============\n" );
                out.write( "    " );

                for ( int number : numbers )
                {
                    out.write( number + "\t" );
                }
                out.write("\n");
                out.flush();

                // выводим чётные числа
                out.write("\n");
                out.write( "Чётные числа:\n" );
                out.write( "=============\n" );
                out.write( "    " );

                for ( int number1 : numbers )
                {
                    if ( number1 % 2 == 0 )
                    {
                        out.write( number1 + "\t" );
                    }
                }
                out.write("\n");
                out.flush();

                // выводим нечётные числа
                out.write("\n");
                out.write( "Нечётные числа:\n" );
                out.write( "===============\n" );
                out.write( "    " );

                for ( int number2 : numbers )
                {
                    if ( number2 % 2 != 0 )
                    {
                        out.write( number2 + "\t" );
                    }
                }
                out.write("\n");
                out.flush();

                // до свидания, пользователь
                out.write("\n");
                out.write( "Спасибо за использование программы, всего доброго!\n" );
                out.write( "(C) Свириденко С.В., 2007\n" );
                out.flush();
            }
        }
        catch ( UnsupportedEncodingException e )
        {
            System.out.println( "Current encoding is not supported! Try running under Windows-compatible OS ^_^" );
        }
        catch ( IOException e )
        {
            System.out.println( "Stream is corrupted!" );
        }
        catch ( NumberFormatException e )
        {
            System.out.println( "Not a number!" );
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
            }

            try
            {
                out.close();
            }
            catch ( IOException ex )
            {
            }
        }
    }
}