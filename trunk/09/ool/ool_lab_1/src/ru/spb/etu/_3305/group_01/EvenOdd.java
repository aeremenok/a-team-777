package ru.spb.etu._3305.group_01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Класс вводит или получает n целых чисел, выводит все чётные и нечётные
 */
public class EvenOdd
{
    public static void main(
        String[] args )
    {
        // числа для работы
        int[] numbers = null;

        // входной поток
        BufferedReader in = null;

        // выходной поток
        BufferedWriter out = null;

        try
        {
            // настраиваем кодировку вывода на Dos для работы из Windows-консоли
            out = new BufferedWriter( new OutputStreamWriter( System.out, "cp866" ) );

            // помощь в работе с программой
            if ( args.length == 1 && args[0].indexOf( "help" ) != -1 )
            {
                // вывод справки
                out.write( "\n" );
                out.write( "Использование: EvenOdd\n" );
                out.write( "               или\n" );
                out.write( "               EvenOdd [список_чисел]\n" );
                out.write( "\n" );
                out.write( "В первом случае вы можете указать вы должны будете "
                           + "указать программе параметры в ходе короткого диалога, "
                           + "во втором будут использоваться числа, заданные в качестве "
                           + "аргументов командной строки.\n" );
                out.flush();
            }
            else
            {
                // работа
                // открываем входной поток
                in = new BufferedReader( new InputStreamReader( System.in, "cp866" ) );

                // узнаём количество чисел для работы
                int n = 0;

                if ( args.length == 0 )
                {
                    // если аргументов командной строки нет - поболтаем с
                    // пользователем
                    out.write( "Пожалуйста, введите количество чисел для работы ( 1 <= n <= 10 ): " );
                    out.flush();
                    String str = in.readLine();
                    n = Integer.parseInt( str );
                }
                else
                {
                    // если есть аргументы командной строки - используем их
                    n = args.length;
                }

                // проверяем количество чисел для обработки
                if ( n > 10 || n < 1 )
                {
                    // если чисел много/мало
                    out.write( "Число вне границ указанного диапазона!" );
                    out.flush();
                }
                else
                {
                    // если количество чисел в норме
                    numbers = new int[n];

                    // узнаём числа
                    if ( args.length == 0 )
                    {
                        // пользователь вводит числа
                        out.write( "\n" );

                        for ( int i = 0; i < n; i++ )
                        {
                            out.write( "" + (i + 1) + ". Введите целое число: " );
                            out.flush();
                            String str = in.readLine();
                            numbers[i] = Integer.parseInt( str );
                        }
                    }
                    else
                    {
                        // берём числа из командной строки
                        for ( int i = 0; i < args.length; i++ )
                        {
                            numbers[i] = Integer.parseInt( args[i] );
                        }
                    }

                    // выводим все числа
                    prettyArrayPrint( numbers, out, "Все введённые числа:", new BogusIntFilter() );

                    // выводим чётные числа
                    prettyArrayPrint( numbers, out, "Нечётные числа:", new OddIntFilter() );

                    // выводим нечётные числа
                    prettyArrayPrint( numbers, out, "Чётные числа:", new EvenIntFilter() );

                    // до свидания, пользователь
                    out.write( "\n" );
                    out.write( "Спасибо за использование программы!\n" );
                    out.write( "(C) 2007, Свириденко С.В., Лысенко А.А., Бессонов А.В.\n" );
                    out.flush();
                }
            }
        }
        catch ( UnsupportedEncodingException e )
        {
            System.out.println( "Current encoding is not supported! Try running under Windows-compatible OS ^_^" );
        }
        catch ( IOException e )
        {
            try
            {
                out.write( "Ошибки работы с потоками, попробуйте перезапустить программу!\n" );
            }
            catch ( IOException e1 )
            {
            }
        }
        catch ( NumberFormatException e )
        {
            try
            {
                out.write( "Введено не число!\n" );
            }
            catch ( IOException e1 )
            {
            }
        }
        finally
        {
            // пробуем закрыть входной поток
            if ( in != null )
            {
                try
                {
                    in.close();
                }
                catch ( IOException e )
                {
                }
            }

            // пробуем закрыть выходной поток
            if ( out != null )
            {
                try
                {
                    out.close();
                }
                catch ( IOException e )
                {
                }
            }
        }
    }

    /**
     * Аккуратный вывод подходящих под фильтр элементов массива с заголовком
     * 
     * @param values массив чисел
     * @param writer поток для записи
     * @param header заголовок
     * @param filter фильтр элементов
     * @throws IOException если не удаётся запись в поток
     */
    private static void prettyArrayPrint(
        int[] values,
        Writer writer,
        String header,
        IntFilter filter )
        throws IOException
    {
        // выводим заголовок
        writer.write( "\n" );
        writer.write( "\n" + header + "\n" );
        StringBuffer s = new StringBuffer( header );
        String stub = s.toString().replaceAll( ".", "=" );
        writer.write( stub + "\n" );
        writer.write( "    " );

        // печатаем числа, попадающие под условие
        for ( int value : values )
        {
            if ( filter.accepts( value ) )
            {
                writer.write( value + "\t" );
            }
        }
        writer.write( "\n" );
        writer.flush();
    }
}