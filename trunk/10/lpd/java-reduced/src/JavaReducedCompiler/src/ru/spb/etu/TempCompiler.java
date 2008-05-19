package ru.spb.etu;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.AccessFlags;
import org.gjt.jclasslib.structures.CPInfo;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.MethodInfo;
import org.gjt.jclasslib.structures.constants.ConstantUtf8Info;

/**
 * ��������� ����������
 * 
 * @author eav
 */
public class TempCompiler
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

    class MyClassFile
        extends ClassFile
    {

        public MyClassFile()
        {
            super();
            setMinorVersion( 0 );
            setMajorVersion( 50 );
        }

        void addInterface(
            String interfaceName )
        {
            int[] interfaces = getInterfaces();

        }

        void addMethod(
            String method )
        {
            MethodInfo[] methods = getMethods();
            MethodInfo[] newMethods = Arrays.copyOf( methods, methods.length + 1 );
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.setClassFile( this );
            methodInfo.setAccessFlags( AccessFlags.ACC_PUBLIC | AccessFlags.ACC_STATIC );
            ConstantUtf8Info info = new ConstantUtf8Info();
            info.setClassFile( this );
            info.setString( method );
            enlargeConstantPool( new CPInfo[] { info } );
            methodInfo.setNameIndex( getConstantPoolIndex( info ) );
        }
    }

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
