package ru.spb.etu;

import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.FLOAD;
import org.apache.bcel.generic.FSTORE;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.bcel.generic.IF_ACMPNE;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.ILOAD;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

public class InstructionProvider
{
    private Parser parser;

    public InstructionProvider(
        Parser parser )
    {
        this.parser = parser;
    }

    public IfInstruction getIFCMPNE(
        Type exprType )
    {
        IfInstruction ifInstruction = null;
        if ( exprType.equals( Type.INT ) || exprType.equals( Type.BOOLEAN ) )
        {
            ifInstruction = new IF_ICMPNE( null );
        }
        else if ( exprType.equals( Type.FLOAT ) )
        {
            parser.SemErr( "float comparison unsupported" );
        }
        else if ( exprType instanceof ObjectType )
        { // ���������� ������ �� �������
            ifInstruction = new IF_ACMPNE( null );
        }
        return ifInstruction;
    }

    public IfInstruction getIFCMPEQ(
        Type exprType )
    {
        IfInstruction ifInstruction = null;
        if ( exprType.equals( Type.INT ) || exprType.equals( Type.BOOLEAN ) )
        {
            ifInstruction = new IF_ICMPEQ( null );
        }
        else if ( exprType.equals( Type.FLOAT ) )
        {
            parser.SemErr( "float comparison unsupported" );
        }
        else if ( exprType instanceof ObjectType )
        { // ���������� ������ �� �������
            ifInstruction = new IF_ACMPEQ( null );
        }
        return ifInstruction;
    }

    /**
     * @param type ���
     * @param index ���������� ����������
     * @return ���������� ���������� �������� � ����������
     */
    public Instruction getStoreInstruction(
        Type type,
        int index )
    {
        Instruction instr = null;
        if ( type.equals( Type.VOID ) )
        {
            parser.SemErr( "invalid variable type void" );
        }
        else if ( type.equals( Type.INT ) || type.equals( Type.BOOLEAN ) )
        {
            instr = new ISTORE( index );
        }
        else if ( type.equals( Type.FLOAT ) )
        {
            instr = new FSTORE( index );
        }
        else if ( type instanceof ObjectType )
        {
            instr = new ASTORE( index );
        }
        else
        {
            parser.SemErr( "unexpected variable type" + type );
        }
        return instr;
    }

    /**
     * @param type ���
     * @param index ���������� ����������
     * @return ���������� �������� �������� �� ����������
     */
    public Instruction getLoadInstruction(
        Type type,
        int index )
    {
        Instruction instr = null;
        if ( type.equals( Type.VOID ) )
        {
            parser.SemErr( "invalid variable type void" );
        }
        else if ( type.equals( Type.INT ) || type.equals( Type.BOOLEAN ) )
        {
            instr = new ILOAD( index );
        }
        else if ( type.equals( Type.FLOAT ) )
        {
            instr = new FLOAD( index );
        }
        else if ( type instanceof ObjectType )
        {
            instr = new ALOAD( index );
        }
        else
        {
            parser.SemErr( "unexpected variable type" + type );
        }
        return instr;
    }
}
