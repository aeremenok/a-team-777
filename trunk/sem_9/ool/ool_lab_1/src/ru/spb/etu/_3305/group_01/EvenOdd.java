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

        // ����� �������
        if ( args.length == 1 &&
                ( args[0].equalsIgnoreCase( "/?" ) || args[0].equalsIgnoreCase( "-help" ) ) )
        {
            System.out.println();
            System.out.println( "�������������: EvenOdd" );
            System.out.println( "               ���" );
            System.out.println( "               EvenOdd [������_�����]" );
            System.out.println();
            System.out.print( "� ������ ������ �� ������ ������� �� ������ ������ " +
                    "������� ��������� ��������� � ���� ��������� �������, " +
                    "�� ������ ����� �������������� �����, �������� � �������� " +
                    "���������� ��������� ������." );
        }
        else
        {
            try
            {
                // TODO ����� ����� ��������� System.out �� ��������� Windows-1251

                // ��������� ������� �����
                in = new BufferedReader( new InputStreamReader( System.in, "Windows-1251" ) );

                // ����� ���������� ����� ��� ������
                int n = 0;

                if ( args.length == 0 )
                {// ���� ���������� ��������� ������ ��� - ��������� � �������������
                    System.out.print( "����������, ������� ���������� ����� ��� ������ ( 1 <= n <= 10 ): " );
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
                    System.out.println();

                    for ( int i = 0; i < n; i++ )
                    {
                        System.out.print( "" + ( i + 1 ) + ". ������� ����� �����: " );
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
                System.out.println();
                System.out.println( "������� �����:" );
                System.out.println( "==============" );
                System.out.print( "    " );

                for ( int number : numbers )
                {
                    System.out.print( number + "\t" );
                }
                System.out.println();

                // ������� ������ �����
                System.out.println();
                System.out.println( "׸���� �����:" );
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

                // ������� �������� �����
                System.out.println();
                System.out.println( "�������� �����:" );
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

                // �� ��������, ������������
                System.out.println();
                System.out.println( "������� �� ������������� ���������, ����� �������!" );
                System.out.println( "(C) ���������� �.�., 2007" );
            }
            catch ( UnsupportedEncodingException e )
            {
                System.out.println( "��������� Windows-1251 �� �������������� ��������!" );
            }
            catch ( IOException e )
            {
                System.out.println( "������ ��� ������ � �������" );
            }
            catch ( NumberFormatException e )
            {
                System.out.println( "�������� ������ �� �������� ������!" );
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
                    System.out.println( "����� �������������� ��������!" );
                }
            }
        }
    }
}