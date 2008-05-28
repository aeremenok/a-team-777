package ru.spb.etu;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;

/**
 * ������� ���������� ���� (���������� �� ������ ���� ����������� � ����������. ����� ��������� ���-������ ���)
 * 
 * @author eav
 */
class CodeWrapper
{
    InstructionList   il;
    InstructionHandle last;            // ��� ���������
    ClassGen          classGen;
    MethodGen         methodGen;
    boolean           returned = false;

    public CodeWrapper(
        ClassGen classGen,
        InstructionList il,
        MethodGen methodGen )
    {
        this.il = il;
        this.classGen = classGen;
        this.methodGen = methodGen;
    }

    public InstructionHandle append(
        Instruction instr )
    {
        last = il.append( instr );
        return last;
    }
}
