package ru.spb.etu;

import java.io.File;
import java.util.ArrayList;

import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.ClassFile;

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
    static ArrayList            RPNInstructions = new ArrayList( 0 );

    /**
     * ������� ������ � ����-�����
     */
    static ArrayList<ClassFile> builtClasses    = new ArrayList<ClassFile>( 0 );

    /**
     * ������, ���������� �� ���������� �������
     */
    static ArrayList            parsedClasses   = new ArrayList( 0 );

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
            ClassFile classFile = new ClassFile();
            // todo ���-�� ���������
            builtClasses.add( classFile );
        }
    }

    /**
     * ��������� ��������������� ������� � �����
     */
    private static void saveClasses()
    {
        try
        {
            for ( ClassFile classFile : builtClasses )
            {
                File file = new File( classFile.getThisClassName() );
                ClassFileWriter.writeToFile( file, classFile );
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
