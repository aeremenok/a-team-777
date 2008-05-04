package ru.spb.etu;

import java.io.File;
import java.util.ArrayList;

import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.ClassFile;

/**
 * запускает компиляцию
 * 
 * @author eav
 */
public class Compiler
{
    /**
     * инструкции ПОЛИЗ, сгенерированные СА
     */
    static ArrayList            RPNInstructions = new ArrayList( 0 );

    /**
     * целевые классы с байт-кодом
     */
    static ArrayList<ClassFile> builtClasses    = new ArrayList<ClassFile>( 0 );

    /**
     * классы, полученные по резульатам анализа
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
     * разбор в ПОЛИЗ
     * 
     * @param fileToAnalyse
     */
    private static void analize(
        String fileToAnalyse )
    {
        // todo
    }

    /**
     * для всех найденных в разборе описаний классов создать class-файлы
     */
    private static void prepareClassFiles()
    {
        for ( Object object : parsedClasses )
        {
            ClassFile classFile = new ClassFile();
            // todo как-то заполнить
            builtClasses.add( classFile );
        }
    }

    /**
     * сохранить сгенерированный байткод в файлы
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
     * транслировать инструкции ПОЛИЗ в байткод
     */
    private static void translate()
    {
        // TODO Auto-generated method stub
    }
}
