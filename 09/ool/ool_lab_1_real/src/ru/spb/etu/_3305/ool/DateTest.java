package ru.spb.etu._3305.ool;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * ������� ������ � ������������, ���� ��������� ������� � ���� ����� �������
 */
public class DateTest
{
    public static void main( String args[] )
    {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new OutputStreamWriter( System.out, "cp866" ) );
            writer.write( "\n������������: ���������� �.�., ������� �.�., �������� �.�.\n" );
            writer.write( "���� ��������� �������: 11.10.2007\t11:46\n" );

            Date date = new Date();

            writer.write(
                    "���� ����� �������:     " + date.getDate() + "." + ( date.getMonth() + 1 ) + "." +
                            ( date.getYear() + 1900 ) + "\t" + date.getHours() + ":" + date.getMinutes() + "\n" );
            writer.flush();
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( writer != null )
            {
                try
                {
                    writer.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
