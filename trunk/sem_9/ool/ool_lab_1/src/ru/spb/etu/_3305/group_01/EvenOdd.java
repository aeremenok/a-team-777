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
            // ����������� ��������� ������ �� Windows
            out = new BufferedWriter( new OutputStreamWriter( System.out, "cp866" ) );

            // ����� �������
            if ( args.length == 1 &&
                    ( args[0].equalsIgnoreCase( "/?" ) || args[0].equalsIgnoreCase( "-help" ) ) )
            {
                out.write("");
                out.write( "�������������: EvenOdd\n" );
                out.write( "               ���\n" );
                out.write( "               EvenOdd [������_�����]\n" );
                out.write("\n");
                out.write( "� ������ ������ �� ������ ������� �� ������ ������ " +
                        "������� ��������� ��������� � ���� ��������� �������, " +
                        "�� ������ ����� �������������� �����, �������� � �������� " +
                        "���������� ��������� ������.\n" );
                out.flush();
            }
            else
            {
                // ��������� ������� �����
                in = new BufferedReader( new InputStreamReader( System.in, "cp866" ) );

                // ����� ���������� ����� ��� ������
                int n = 0;

                if ( args.length == 0 )
                {// ���� ���������� ��������� ������ ��� - ��������� � �������������
                    out.write( "����������, ������� ���������� ����� ��� ������ ( 1 <= n <= 10 ): " );
                    out.flush();
                    String str = in.readLine();
                    n = Integer.parseInt( str );
                }
                else
                {// ���� ���� ��������� ��������� ������ - ���������� ��
                    n = args.length;
                }

                // ���� ����� �����/����
                if ( n > 10 || n < 1 )
                {
                    throw new Exception( "����� ��� ������ ���������� ���������!" );
                }

                // ���� ���������� ����� � �����
                numbers = new int[n];

                // ����� �����
                if ( args.length == 0 )
                {// ������������ ������ �����
                    out.write("\n");

                    for ( int i = 0; i < n; i++ )
                    {
                        out.write( "" + ( i + 1 ) + ". ������� ����� �����: " );
                        out.flush();
                        String str = in.readLine();
                        numbers[i] = Integer.parseInt( str );
                    }
                }
                else
                {// ���� ����� �� ��������� ������
                    for ( int i = 0; i < args.length; i++ )
                    {
                        numbers[i] = Integer.parseInt( args[i] );
                    }
                }

                // ������� �����
                out.write("\n");
                out.write( "������� �����:\n" );
                out.write( "==============\n" );
                out.write( "    " );

                for ( int number : numbers )
                {
                    out.write( number + "\t" );
                }
                out.write("\n");
                out.flush();

                // ������� ������ �����
                out.write("\n");
                out.write( "׸���� �����:\n" );
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

                // ������� �������� �����
                out.write("\n");
                out.write( "�������� �����:\n" );
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

                // �� ��������, ������������
                out.write("\n");
                out.write( "������� �� ������������� ���������, ����� �������!\n" );
                out.write( "(C) ���������� �.�., 2007\n" );
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