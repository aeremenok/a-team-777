package ru.spb.etu;

import java.util.HashMap;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.FLOAD;
import org.apache.bcel.generic.FSTORE;
import org.apache.bcel.generic.ILOAD;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

public class ParserImpl
    implements
        Constants
{
    /**
     * только классы
     */
    public HashMap<String, ClassGen>       classes    = new HashMap<String, ClassGen>();
    /**
     * только интерфейсы
     */
    public HashMap<String, ClassGen>       interfaces = new HashMap<String, ClassGen>();
    /**
     * типы значений, находяихся в переменных
     */
    public HashMap<LocalVariableGen, Type> realTypes  = new HashMap<LocalVariableGen, Type>();

    /**
     * все созданные типы
     */
    public HashMap<String, ClassGen>       types      = new HashMap<String, ClassGen>();

    /**
     * кому принадлежим
     */
    private Parser                         parser;

    public ParserImpl(
        Parser parser )
    {
        this.parser = parser;
        MyClassGen vector = new MyClassGen( "java.util.Vector", "java.lang.Object" );
        classes.put( "java.util.Vector", vector );
        types.put( "java.util.Vector", vector );
        vector.createMethod( "<init>", Type.VOID, null, null );
        vector.createMethod( "add", Type.INT, new Type[] { Type.OBJECT }, new String[] { "e" } );
        vector.createMethod( "get", Type.OBJECT, new Type[] { Type.INT }, new String[] { "index" } );
        vector.createMethod( "size", Type.INT, new Type[] {}, new String[] {} );
        vector.createMethod( "indexOf", Type.INT, new Type[] { Type.OBJECT }, new String[] { "o" } );

        MyClassGen string = new MyClassGen( "java.lang.String", "java.lang.Object" );
        classes.put( "java.lang.String", string );
        types.put( "java.lang.String", string );
        // todo методов пока не надо

        MyClassGen object = new MyClassGen( "java.lang.Object", "<null>" );
        classes.put( "java.lang.Object", object );
        types.put( "java.lang.Object", object );
        object.createMethod( "<init>", Type.VOID, new Type[] {}, new String[] {} );
        object.createMethod( "equals", Type.BOOLEAN, new Type[] { Type.OBJECT }, new String[] { "obj" } );
        MethodGen m = object.createMethod( "hashCode", Type.INT, new Type[] {}, new String[] {} );
        m.setModifiers( m.getModifiers() | ACC_NATIVE | ACC_FINAL );
    }

    /**
     * @param name добавить класс, если такого нет
     * @param classGen класс
     */
    public void addClass(
        String name,
        ClassGen classGen )
    {
        if ( !isDuplicate( name ) )
        {
            classes.put( name, classGen );
            types.put( name, classGen );
        }
    }

    /**
     * @param name добавить интерфейс, если такого нет
     * @param classGen интерфейс
     */
    public void addInterface(
        String name,
        ClassGen classGen )
    {
        if ( !isDuplicate( name ) )
        {
            interfaces.put( name, classGen );
            types.put( name, classGen );
        }
    }

    /**
     * вызывает методы PrintStream
     * 
     * @param cw код
     * @param method имя метода
     * @param argTypes что печатать
     */
    public void callPrintMethod(
        CodeWrapper cw,
        String method,
        Type[] argTypes )
    {
        InstructionFactory factory = new InstructionFactory( cw.classGen );
        if ( argTypes.length == 1 )
            cw.append( factory.createInvoke( "java.io.PrintStream", method, Type.VOID, argTypes, INVOKEVIRTUAL ) );
        else
            parser.SemErr( "sysout requires 1 parameter" );
    }

    public boolean checkInterfaces(
        ClassGen classGen )
    {
        boolean res = true;

        String[] interfaceNames = classGen.getInterfaceNames();
        while ( interfaceNames != null && interfaceNames.length > 0 )
        {
            ClassGen iGen = interfaces.get( interfaceNames[0] );
            if ( !isPresent( interfaceNames[0] ) )
                res = checkMethods( classGen, res, iGen );
            interfaceNames = iGen.getInterfaceNames();
        }

        return res;
    }

    /**
     * статический instance_of для массивов
     * 
     * @param types1 одни типы
     * @param types2 другие типы
     * @return родственны ли поголовно
     */
    public boolean deepInstanceOf(
        Type[] types1,
        Type[] types2 )
    {
        if ( types1 == null || types2 == null )
            return false;
        if ( types1 == types2 )
            return true;
        if ( types1.length != types2.length )
            return false;

        boolean res = true;
        int i = 0;
        while ( res && i < types1.length )
        {
            res &= instance_of( types1[i], types2[i] );
            i++;
        }
        return res;
    }

    /**
     * @param name имя поля
     * @param classGen класс
     * @return поле
     */
    public Field getFieldGen(
        String name,
        ClassGen classGen )
    {
        for ( Field var : classGen.getFields() )
            if ( var.getName().equals( name ) )
                return var;
        return null;
    }

    /**
     * @param type тип
     * @param index координата переменной
     * @return инструкция загрузки значения из переменной
     */
    public Instruction getLoadInstruction(
        Type type,
        int index )
    {
        Instruction instr = null;
        if ( type.equals( Type.VOID ) )
            parser.SemErr( "invalid variable type void" );
        else if ( type.equals( Type.INT ) || type.equals( Type.BOOLEAN ) )
            instr = new ILOAD( index );
        else if ( type.equals( Type.FLOAT ) )
            instr = new FLOAD( index );
        else if ( type instanceof ObjectType )
            instr = new ALOAD( index );
        else
            parser.SemErr( "unexpected variable type" + type );
        return instr;
    }

    /**
     * @param name имя метода
     * @param argTypes типы аргументов
     * @param classGen генератор класса для поиска
     * @return метод и имя класса, его реализующего
     */
    public MethodParams getMethod(
        String name,
        Type[] argTypes,
        ClassGen classGen )
    {
        for ( Method method : classGen.getMethods() )
            if ( method.getName().equals( name ) && deepInstanceOf( method.getArgumentTypes(), argTypes ) )
                return new MethodParams( method, classGen.getClassName() );

        String superclassName = classGen.getSuperclassName();
        if ( !superclassName.equals( "<null>" ) )
        {
            ClassGen superClass = classes.get( superclassName );
            MethodParams mp = getMethod( name, argTypes, superClass );
            if ( mp != null )
                return mp;
        }
        return null;
    }

    /**
     * @param type тип
     * @param index координата переменной
     * @return инструкция сохранения значения в переменную
     */
    public Instruction getStoreInstruction(
        Type type,
        int index )
    {
        Instruction instr = null;
        if ( type.equals( Type.VOID ) )
            parser.SemErr( "invalid variable type void" );
        else if ( type.equals( Type.INT ) || type.equals( Type.BOOLEAN ) )
            instr = new ISTORE( index );
        else if ( type.equals( Type.FLOAT ) )
            instr = new FSTORE( index );
        else if ( type instanceof ObjectType )
            instr = new ASTORE( index );
        else
            parser.SemErr( "unexpected variable type" + type );
        return instr;
    }

    /**
     * @param name имя типа
     * @return тип
     */
    public ClassGen getType(
        String name )
    {
        ClassGen classGen = types.get( name );
        if ( classGen == null )
            parser.SemErr( "no such type " + name );
        return classGen;
    }

    /**
     * @param name имя переменной
     * @param methodGen метод
     * @return генератор переменной
     */
    public LocalVariableGen getVarGen(
        String name,
        MethodGen methodGen )
    {
        for ( LocalVariableGen var : methodGen.getLocalVariables() )
            if ( var.getName().equals( name ) )
                return var;
        return null;
    }

    /**
     * статический instance_of
     * 
     * @param ancestor потенциальный тип-предок
     * @param child потенциальный тип-потомок
     * @return состоят ли в родстве
     */
    public boolean instance_of(
        Type ancestor,
        Type child )
    {
        // todo if ( ancestor.toString().equals( "java.lang.String" ) )
        // return false;
        if ( ancestor.equals( Type.OBJECT ) )
            return true;

        if ( ancestor.equals( child ) )
            return true;

        if ( ancestor instanceof ObjectType )
        {
            ObjectType objAncestor = (ObjectType) ancestor;
            if ( child instanceof ObjectType )
            {
                ObjectType objChild = (ObjectType) child;
                if ( objAncestor.getClassName().equals( objChild.getClassName() ) )
                    return true;

                return instanceOfClass( objAncestor, objChild ) || instanceOfInterface( objAncestor, objChild );
            }
            else
                return false;
        }
        else
            return false;
    }

    private boolean instanceOfInterface(
        ObjectType objAncestor,
        ObjectType objChild )
    {
        ClassGen iGen = interfaces.get( objAncestor.toString() );
        if ( iGen != null )
        { // предок - интерфейс
            // подымаемся на интерфейс от потомка
            String[] interfaceNames = types.get( objChild.toString() ).getInterfaceNames();
            while ( interfaceNames != null && interfaceNames.length > 0 )
            {
                ClassGen childInterface = interfaces.get( interfaceNames[0] );
                if ( childInterface.getClassName().equals( iGen.getClassName() ) )
                    return true;

                // подымаемся еще на интерфейс от потомка
                interfaceNames = childInterface.getInterfaceNames();
            }
        }
        return false;
    }

    private boolean instanceOfClass(
        ObjectType objAncestor,
        ObjectType objChild )
    {
        ClassGen iGen = classes.get( objAncestor.toString() );
        if ( iGen != null )
        { // предок - класс
            // подымаемся на класс от потомка
            String superclassName = types.get( objChild.toString() ).getSuperclassName();
            while ( !superclassName.equals( Type.OBJECT.toString() ) )
            {
                ClassGen childSuper = classes.get( superclassName );
                if ( childSuper.getClassName().equals( iGen.getClassName() ) )
                    return true;

                // подымаемся еще на класс от потомка
                superclassName = childSuper.getSuperclassName();
            }
        }
        return false;
    }

    /**
     * @param typeName тип
     * @return нет ли уже такого =)
     */
    public boolean isDuplicate(
        String typeName )
    {
        if ( types.get( typeName ) != null )
        {
            parser.SemErr( "duplicate type " + typeName );
            return true;
        }
        return false;
    }

    /**
     * @param typeName тип
     * @return есть ли уже такой
     */
    public boolean isPresent(
        // todo это название - обман пользователя. зарефакторить
        String typeName )
    {
        if ( types.get( typeName ) == null )
        {
            parser.SemErr( "no such type " + typeName );
            return true;
        }
        return false;
    }

    /**
     * загрузить системный поток вывода
     * 
     * @param cw код
     */
    public void preparePrintStream(
        CodeWrapper cw )
    {
        InstructionFactory factory = new InstructionFactory( cw.classGen );
        ObjectType pStream = new ObjectType( "java.io.PrintStream" );
        cw.append( factory.createFieldAccess( "java.lang.System", "out", pStream, GETSTATIC ) );
    }

    public Type realType(
        LocalVariableGen localVariableGen )
    {
        return realTypes.get( localVariableGen );
    }

    /**
     * @param type тип
     * @return его предок
     */
    public Type superType(
        Type type )
    {
        ClassGen classGen = types.get( type.toString() );
        return new ObjectType( classGen.getSuperclassName() );
    }

    public void update(
        LocalVariableGen localVariableGen,
        Type type )
    {
        realTypes.remove( localVariableGen );
        realTypes.put( localVariableGen, type );
    }

    private boolean checkMethods(
        ClassGen classGen,
        boolean res,
        ClassGen iGen )
    {
        Method[] methods = iGen.getMethods();
        for ( int j = 0; j < methods.length; j++ )
        {
            Method method = methods[j];
            MethodParams methodParams = getMethod( method.getName(), method.getArgumentTypes(), classGen );
            if ( methodParams == null )
            {
                System.out.println( classGen.getClassName() + " must implement " + iGen.getClassName() + "#" +
                                    method.getName() );
                res = false;
            }
        }
        return res;
    }

}
