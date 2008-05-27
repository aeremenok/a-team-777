package ru.spb.etu;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;

/**
 * генератор классов с некоторыми общими свойствами
 * 
 * @author eav
 */
public class MyClassGen
    extends ClassGen
    implements
        Constants
{
    public MyClassGen(
        String class_name,
        String super_class_name )
    {
        super( class_name, super_class_name, null, ACC_PUBLIC | ACC_SUPER, null );
    }

    public MethodGen createMethod(
        String methodName,
        Type retType,
        Type[] argTypes,
        String[] argNames )
    {
        InstructionList il = new InstructionList();
        MethodGen methodGen =
                              new MethodGen( ACC_PUBLIC, retType, argTypes, argNames, methodName, getClassName(), il,
                                             getConstantPool() );
        il.append( InstructionConstants.RETURN );
        methodGen.setMaxStack();
        addMethod( methodGen.getMethod() );
        return methodGen;
    }
}
