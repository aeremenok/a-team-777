package ru.spb.etu;

import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.FLOAD;
import org.apache.bcel.generic.FSTORE;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.bcel.generic.IF_ACMPNE;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPGE;
import org.apache.bcel.generic.IF_ICMPGT;
import org.apache.bcel.generic.IF_ICMPLE;
import org.apache.bcel.generic.IF_ICMPLT;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.ILOAD;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReturnInstruction;
import org.apache.bcel.generic.Type;

public class InstructionProvider
    implements
        InstructionConstants
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
        {
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
        {
            ifInstruction = new IF_ACMPEQ( null );
        }
        return ifInstruction;
    }

    public IfInstruction getIFCMPGT(
        Type exprType )
    {
        IfInstruction ifInstruction = null;
        if ( exprType.equals( Type.INT ) || exprType.equals( Type.BOOLEAN ) )
        {
            ifInstruction = new IF_ICMPGT( null );
        }
        else if ( exprType.equals( Type.FLOAT ) )
        {
            parser.SemErr( "float comparison unsupported" );
        }
        else if ( exprType instanceof ObjectType )
        {
            parser.SemErr( "'>' for object unsupported" );
        }
        return ifInstruction;
    }

    public IfInstruction getIFCMPGE(
        Type exprType )
    {
        IfInstruction ifInstruction = null;
        if ( exprType.equals( Type.INT ) || exprType.equals( Type.BOOLEAN ) )
        {
            ifInstruction = new IF_ICMPGE( null );
        }
        else if ( exprType.equals( Type.FLOAT ) )
        {
            parser.SemErr( "float comparison unsupported" );
        }
        else if ( exprType instanceof ObjectType )
        {
            parser.SemErr( "'>=' for object unsupported" );
        }
        return ifInstruction;
    }

    public IfInstruction getIFCMPLT(
        Type exprType )
    {
        IfInstruction ifInstruction = null;
        if ( exprType.equals( Type.INT ) || exprType.equals( Type.BOOLEAN ) )
        {
            ifInstruction = new IF_ICMPLT( null );
        }
        else if ( exprType.equals( Type.FLOAT ) )
        {
            parser.SemErr( "float comparison unsupported" );
        }
        else if ( exprType instanceof ObjectType )
        {
            parser.SemErr( "'<' for object unsupported" );
        }
        return ifInstruction;
    }

    public IfInstruction getIFCMPLE(
        Type exprType )
    {
        IfInstruction ifInstruction = null;
        if ( exprType.equals( Type.INT ) || exprType.equals( Type.BOOLEAN ) )
        {
            ifInstruction = new IF_ICMPLE( null );
        }
        else if ( exprType.equals( Type.FLOAT ) )
        {
            parser.SemErr( "float comparison unsupported" );
        }
        else if ( exprType instanceof ObjectType )
        {
            parser.SemErr( "'<=' for object unsupported" );
        }
        return ifInstruction;
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

    public ReturnInstruction getReturnInstruction(
        Type type )
    {
        ReturnInstruction instr = null;
        if ( type.equals( Type.VOID ) )
        {
            instr = RETURN;
        }
        else if ( type.equals( Type.INT ) || type.equals( Type.BOOLEAN ) )
        {
            instr = IRETURN;
        }
        else if ( type.equals( Type.FLOAT ) )
        {
            instr = FRETURN;
        }
        else if ( type instanceof ObjectType || type.equals( Type.NULL ) )
        {
            instr = ARETURN;
        }
        else
        {
            parser.SemErr( "unexpected variable type" + type );
        }
        return instr;
    }
}
