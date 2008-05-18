package ru.spb.etu;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.gjt.jclasslib.structures.AccessFlags;

public class Compiler
{
    /**
     * @param args
     */
    public static void main(
        String[] args )
    {
        try
        {
            File file = new File( args[0] );
            FileInputStream fileInputStream = new FileInputStream( file );
            Scanner scanner = new Scanner( fileInputStream );
            System.out.println( "scanning" );
            scanner.Scan();

            Parser parser = new MyParser( scanner );
            System.out.println( "parsing" );
            parser.Parse();

            createClass( args[0] );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    private static void createClass(
        String fileName )
        throws IOException
    {
        ClassType classType = new ClassType( "hello" );
        classType.setClassfileVersion( 0, 50 );
        classType.setModifiers( AccessFlags.ACC_PUBLIC );
        classType.addField( "field", Type.int_type );

        int modifiers = AccessFlags.ACC_PUBLIC | AccessFlags.ACC_STATIC;
        Method method =
                        classType.addMethod( "main", modifiers, new Type[] { ArrayType.make( Type.string_type ) },
                                             Type.void_type );

        CodeAttr code = new CodeAttr( method );
        code.pushScope();
        Variable a = code.addLocal( Type.int_type, "a" );
        Variable b = code.addLocal( Type.int_type, "b" );
        code.pushType( a.getType() );
        code.pushType( b.getType() );
        code.emitAdd();

        File file = new File( fileName.replaceAll( "\\.java", "\\.class" ) );
        classType.writeToStream( new FileOutputStream( file ) );
    }
}
