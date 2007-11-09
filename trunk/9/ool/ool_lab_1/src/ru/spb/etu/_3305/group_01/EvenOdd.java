package ru.spb.etu._3305.group_01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * ����� ������ ��� �������� n ����� �����, ������� ��� ������ � ��������
 */
public class EvenOdd
{
    public static void main(
        String[] args )
    {
        int[] numbers = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try
        {
            // ����������� ��������� ������ �� Windows
            out = new BufferedWriter( new OutputStreamWriter( System.out, "cp866" ) );

            // ������ � ������ � ����������
            if ( args.length == 1 && args[0].indexOf( "help" ) != -1 )
            {
                // ����� �������
                out.write( "\n" );
                out.write( "�������������: EvenOdd\n" );
                out.write( "               ���\n" );
                out.write( "               EvenOdd [������_�����]\n" );
                out.write( "\n" );
                out.write( "� ������ ������ �� ������ ������� �� ������ ������ "
                           + "������� ��������� ��������� � ���� ��������� �������, "
                           + "�� ������ ����� �������������� �����, �������� � �������� "
                           + "���������� ��������� ������.\n" );
                out.flush();
            }
            else
            {
                // ������
                // ��������� ������� �����
                in = new BufferedReader( new InputStreamReader( System.in, "cp866" ) );

                // ����� ���������� ����� ��� ������
                int n = 0;

                if ( args.length == 0 )
                {
                    // ���� ���������� ��������� ������ ��� - ��������� �
                    // �������������
                    out.write( "����������, ������� ���������� ����� ��� ������ ( 1 <= n <= 10 ): " );
                    out.flush();
                    String str = in.readLine();
                    n = Integer.parseInt( str );
                }
                else
                {
                    // ���� ���� ��������� ��������� ������ - ���������� ��
                    n = args.length;
                }

                // ��������� ���������� ����� ��� ���������
                if ( n > 10 || n < 1 )
                {
                    // ���� ����� �����/����
                    out.write( "����� ��� ������ ���������� ���������!" );
                    out.flush();
                }
                else
                {
                    // ���� ���������� ����� � �����
                    numbers = new int[n];

                    // ����� �����
                    if ( args.length == 0 )
                    {
                        // ������������ ������ �����
                        out.write( "\n" );

                        for ( int i = 0; i < n; i++ )
                        {
                            out.write( "" + (i + 1) + ". ������� ����� �����: " );
                            out.flush();
                            String str = in.readLine();
                            numbers[i] = Integer.parseInt( str );
                        }
                    }
                    else
                    {
                        // ���� ����� �� ��������� ������
                        for ( int i = 0; i < args.length; i++ )
                        {
                            numbers[i] = Integer.parseInt( args[i] );
                        }
                    }

                    // ������� ��� �����
                    prettyArrayPrint( numbers, out, "��� �������� �����:", new BogusIntFilter() );

                    // ������� ������ �����
                    prettyArrayPrint( numbers, out, "�������� �����:", new OddIntFilter() );

                    // ������� �������� �����
                    prettyArrayPrint( numbers, out, "׸���� �����:", new EvenIntFilter() );

                    // �� ��������, ������������
                    out.write( "\n" );
                    out.write( "������� �� ������������� ���������!\n" );
                    out.write( "(C) 2007, ���������� �.�., ������� �.�., �������� �.�.\n" );
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
                out.write( "������ ������ � ��������, ���������� ������������� ���������!\n" );
            }
            catch ( IOException e1 )
            {
            }
        }
        catch ( NumberFormatException e )
        {
            try
            {
                out.write( "������� �� �����!\n" );
            }
            catch ( IOException e1 )
            {
            }
        }
        finally
        {
            // ������� ������� ������� �����
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

            // ������� ������� �������� �����
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
     * ���������� ����� ���������� ��� ������ ��������� ������� � ����������
     * 
     * @param values ������ �����
     * @param writer ����� ��� ������
     * @param header ���������
     * @param filter ������ ���������
     * @throws IOException ���� �� ������ ������ � �����
     */
    private static void prettyArrayPrint(
        int[] values,
        Writer writer,
        String header,
        IntFilter filter )
        throws IOException
    {
        // ������� ���������
        writer.write( "\n" );
        writer.write( "\n" + header + "\n" );
        StringBuffer s = new StringBuffer( header );
        String stub = s.toString().replaceAll( ".", "=" );
        writer.write( stub + "\n" );
        writer.write( "    " );

        // �������� �����, ���������� ��� �������
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