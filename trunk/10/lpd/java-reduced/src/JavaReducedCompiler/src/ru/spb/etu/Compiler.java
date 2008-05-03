package ru.spb.etu;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ru.spb.etu.rpn.templates.ClassTemplate;

/**
 * ��������� ����������
 * 
 * @author eav
 */
public class Compiler
{
    /**
     * ���������� �����, ��������������� ��
     */
    static ArrayList                RPNInstructions = new ArrayList( 0 );

    /**
     * ������� ������ � ����-�����
     */
    static ArrayList<ClassTemplate> builtClasses    = new ArrayList<ClassTemplate>( 0 );

    /**
     * ������, ���������� �� ���������� �������
     */
    static ArrayList                parsedClasses   = new ArrayList( 0 );

    /**
     * @param args
     */
    public static void main(
        String[] args )
    {
        String fileToAnalize = args[0];
        analize( fileToAnalize );

        prepareClassFiles();
        translate();

        saveClasses();
    }

    /**
     * ������ � �����
     * 
     * @param fileToAnalyse
     */
    private static void analize(
        String fileToAnalyse )
    {
        // todo
    }

    /**
     * ��� ���� ��������� � ������� �������� ������� ������� class-�����
     */
    private static void prepareClassFiles()
    {
        for ( Object object : parsedClasses )
        {
            ClassTemplate classTemplate = new ClassTemplate( object );
            builtClasses.add( classTemplate );
        }
    }

    /**
     * ��������� ��������������� ������� � �����
     */
    private static void saveClasses()
    {
        try
        {
            for ( ClassTemplate classTemplate : builtClasses )
            {
                File file = new File( classTemplate.getFileName() );
                FileOutputStream fileOutputStream;
                fileOutputStream = new FileOutputStream( file );
                fileOutputStream.write( classTemplate.toString().getBytes( "utf8" ) );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * ������������� ���������� ����� � �������
     */
    private static void translate()
    {
        // TODO Auto-generated method stub
    }
}
