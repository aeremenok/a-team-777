package ru.spb.etu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;

class Foo
{
    int a;

    public Foo(

        int c )
    {
        int b = a % c;
    }
}

public class Compiler
    implements
        Constants
{
    private static final String STRIPE = "======================";

    public static void info(
        String info )
    {
        System.out.println( STRIPE + info + STRIPE );
    }

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
            info( "scanning" );
            scanner.Scan();

            Parser parser = new Parser( scanner );

            Parser.filePath = file.getParent();
            System.out.println( Parser.filePath );
            info( "parsing" );

            parser.Parse();

            // createApacheClass( args[0] );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /*
    import java.io.*;

    public class HelloWorld {
    public static void main(String[] argv) {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String name = null;

      try {
    System.out.print("Please enter your name> ");
    name = in.readLine();
      } catch(IOException e) { return; }

      System.out.println("Hello, " + name);
    }
    }
    

     */

    private static void createApacheClass(
        String fileName )
        throws IOException
    {
        System.out.println( fileName );
        String className = fileName.substring( fileName.lastIndexOf( "\\" ) + 1, fileName.indexOf( ".java" ) );
        String classFileName = fileName.replaceAll( "\\.java", "\\.class" );
        System.out.println( className );

        // ----------------------------------- example
        ClassGen classGen = new ClassGen( className, "java.lang.Object", classFileName, ACC_PUBLIC | ACC_SUPER, null );

        ConstantPoolGen cp = classGen.getConstantPool(); // cg creates constant pool
        InstructionList il = new InstructionList();

        MethodGen mg = new MethodGen( ACC_STATIC | ACC_PUBLIC, // access flags
                                      Type.VOID, // return type
                                      new Type[] { // argument types
                                      new ArrayType( Type.STRING, 1 ) }, new String[] { "argv" }, // arg names
                                      "main", className, // method, class
                                      il, cp );

        InstructionFactory factory = new InstructionFactory( classGen );

        ObjectType i_stream = new ObjectType( "java.io.InputStream" );
        ObjectType p_stream = new ObjectType( "java.io.PrintStream" );

        il.append( factory.createNew( "java.io.BufferedReader" ) );
        il.append( InstructionConstants.DUP ); // Use predefined constant
        factory.createConstant( p_stream );
        il.append( factory.createNew( "java.io.InputStreamReader" ) );
        il.append( InstructionConstants.DUP );
        il.append( factory.createFieldAccess( "java.lang.System", "in", i_stream, Constants.GETSTATIC ) );
        il.append( factory.createInvoke( "java.io.InputStreamReader", "<init>", Type.VOID, new Type[] { i_stream },
                                         Constants.INVOKESPECIAL ) );
        il.append( factory.createInvoke( "java.io.BufferedReader", "<init>", Type.VOID,
                                         new Type[] { new ObjectType( "java.io.Reader" ) }, Constants.INVOKESPECIAL ) );

        LocalVariableGen lg = mg.addLocalVariable( "in", new ObjectType( "java.io.BufferedReader" ), null, null );
        int in = lg.getIndex();
        lg.setStart( il.append( new ASTORE( in ) ) ); // "i" valid from here

        lg = mg.addLocalVariable( "name", Type.STRING, null, null );
        int name = lg.getIndex();
        il.append( InstructionConstants.ACONST_NULL );
        lg.setStart( il.append( new ASTORE( name ) ) ); // "name" valid from here

        InstructionHandle try_start =
                                      il.append( factory.createFieldAccess( "java.lang.System", "out", p_stream,
                                                                            Constants.GETSTATIC ) );

        Field f;

        il.append( new PUSH( cp, "Please enter your name> " ) );
        il.append( factory.createInvoke( "java.io.PrintStream", "print", Type.VOID, new Type[] { Type.STRING },
                                         Constants.INVOKEVIRTUAL ) );
        il.append( new ALOAD( in ) );
        il.append( factory.createInvoke( "java.io.BufferedReader", "readLine", Type.STRING, Type.NO_ARGS,
                                         Constants.INVOKEVIRTUAL ) );
        il.append( new ASTORE( name ) );

        GOTO g = new GOTO( null );
        InstructionHandle try_end = il.append( g );

        InstructionHandle handler = il.append( InstructionConstants.RETURN );
        mg.addExceptionHandler( try_start, try_end, handler, new ObjectType( "java.io.IOException" ) );

        InstructionHandle ih =
                               il.append( factory.createFieldAccess( "java.lang.System", "out", p_stream,
                                                                     Constants.GETSTATIC ) );

        g.setTarget( ih );

        il.append( factory.createNew( Type.STRINGBUFFER ) );
        il.append( InstructionConstants.DUP );
        il.append( new PUSH( cp, "Hello, " ) );
        il.append( factory.createInvoke( "java.lang.StringBuffer", "<init>", Type.VOID, new Type[] { Type.STRING },
                                         Constants.INVOKESPECIAL ) );
        il.append( new ALOAD( name ) );
        il.append( factory.createInvoke( "java.lang.StringBuffer", "append", Type.STRINGBUFFER,
                                         new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL ) );
        il.append( factory.createInvoke( "java.lang.StringBuffer", "toString", Type.STRING, Type.NO_ARGS,
                                         Constants.INVOKEVIRTUAL ) );

        il.append( factory.createInvoke( "java.io.PrintStream", "println", Type.VOID, new Type[] { Type.STRING },
                                         Constants.INVOKEVIRTUAL ) );
        il.append( InstructionConstants.RETURN );

        mg.setMaxStack();
        classGen.addMethod( mg.getMethod() );
        il.dispose(); // Allow instruction handles to be reused
        classGen.addEmptyConstructor( ACC_PUBLIC );

        // ----------------------------------- example
        classGen.getJavaClass().dump( classFileName );
    }

    private static void getMethod(
        ClassGen classGen,
        String name )
    {
        Method[] methods = classGen.getMethods();
        for ( Method method : methods )
        {
            if ( method.getName().equals( name ) )
            {
                break;
            }
        }
    }
}
