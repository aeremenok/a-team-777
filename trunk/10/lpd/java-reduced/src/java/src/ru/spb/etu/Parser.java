package ru.spb.etu;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.InstructionList;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	static final int _EOF = 0;
	static final int _id = 1;
	static final int _openRoundBracket = 2;
	static final int _closeRoundBracket = 3;
	static final int _openCurlyBracket = 4;
	static final int _closeCurlyBracket = 5;
	static final int _intLit = 6;
	static final int _floatLit = 7;
	static final int _charLit = 8;
	static final int _stringLit = 9;
	static final int maxT = 66;

	static final boolean T = true;
	static final boolean x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	boolean next(int i)
	{
		scanner.ResetPeek();
		Token peek = scanner.Peek();
		return (peek.kind == i);
	}
	
	void checkTypes()
    {
        for ( String typeName : objectTypes.keySet() )
        {
            if (
                !typeName.equals("String") && 
                !typeName.equals("Vector") &&
                !classes.keySet().contains( typeName ) 
                )
            {
                SemErr( "type " + typeName + " not found" );
            }
        }
    }
    
    public class Args
    {
        Type[] argTypes = null;
        String[] argNames = null;
    }
    
    public void log(String msg)
    {
        System.out.println(msg);        
    }

    public HashMap<String, ClassGen> classes = new HashMap<String, ClassGen>();
    public HashMap<String, ObjectType> objectTypes = new HashMap<String, ObjectType>();
/*--------------------------------------------------------------------------*/


	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) { ++errDist; break; }

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	String  identifier() {
		String  value;
		Expect(1);
		value = t.val;
		log("id="+t.val);
		
		return value;
	}

	int  staticAccess() {
		int  modifier;
		Expect(10);
		modifier = Constants.ACC_STATIC;
		return modifier;
	}

	int  finalAccess() {
		int  modifier;
		Expect(11);
		modifier = Constants.ACC_FINAL;
		return modifier;
	}

	void CompilationUnit() {
		typeDeclaration();
		while (StartOf(1)) {
			typeDeclaration();
		}
		checkTypes();
	}

	void typeDeclaration() {
		int specifier = 0;
		if (la.kind == 16 || la.kind == 17 || la.kind == 18) {
			specifier = accessSpecifier();
		}
		if (la.kind == 15) {
			interfaceDeclaration(specifier);
		} else if (la.kind == 10 || la.kind == 11 || la.kind == 12) {
			classDeclaration(specifier);
		} else SynErr(67);
	}

	int  accessSpecifier() {
		int  specifier;
		if (la.kind == 16) {
			Get();
			specifier = Constants.ACC_PUBLIC;
		} else if (la.kind == 17) {
			Get();
			specifier = Constants.ACC_PROTECTED;
		} else if (la.kind == 18) {
			Get();
			specifier = Constants.ACC_PRIVATE;
		} else SynErr(68);
		specifier = 0;
		return specifier;
	}

	void interfaceDeclaration(int modifier) {
		Expect(15);
		modifier |= Constants.ACC_INTERFACE | Constants.ACC_ABSTRACT; 
		String interfaceName = identifier();
		String superInterfaceName = null;
		if (la.kind == 13) {
			Get();
			superInterfaceName = identifier();
			if(superInterfaceName.equals(interfaceName)) SemErr("cannot self-inherit");
			objectTypes.put(superInterfaceName, new ObjectType(superInterfaceName));
			
		}
		ClassGen classGen = new ClassGen(
		   interfaceName, 
		   "java.lang.Object", 
		   interfaceName+".class", 
		   modifier,
		   superInterfaceName==null ? null : new String[]{superInterfaceName} );
		classes.put(interfaceName, classGen);
		log("interface "+interfaceName+" created");
		
		interfaceBody(classGen);
	}

	void classDeclaration(int modifier) {
		if (la.kind == 11) {
			int fMod = finalAccess();
			modifier |= fMod;
		}
		if (la.kind == 10) {
			int sMod = staticAccess();
			modifier |= sMod;
		}
		Expect(12);
		String className = identifier();
		String superName = "java.lang.Object";
		if (la.kind == 13) {
			Get();
			superName = identifier();
			if(superName.equals(className)) SemErr("cannot self-inherit");
			objectTypes.put(superName, new ObjectType(superName));
			
		}
		String interfaceName = null;
		if (la.kind == 14) {
			Get();
			interfaceName = identifier();
			if(interfaceName.equals(className)) SemErr("cannot self-inherit");
			objectTypes.put(superName, new ObjectType(superName));
			
		}
		ClassGen classGen = new ClassGen(
		   className, 
		   superName, 
		   className+".class", 
		   modifier,
		   interfaceName==null ? null : new String[]{interfaceName} );
		classes.put(className, classGen);
		log("class "+className+" created");
		
		classBody(classGen);
	}

	void classBody(ClassGen classGen) {
		Expect(4);
		while (StartOf(2)) {
			int modifier = 0;
			if (la.kind == 16 || la.kind == 17 || la.kind == 18) {
				modifier = accessSpecifier();
			}
			if (la.kind == 11) {
				int fMod = finalAccess();
				modifier |= fMod;
			}
			if (la.kind == 10) {
				int sMod = staticAccess();
				modifier |= sMod;
			}
			if (next(_openRoundBracket)) {
				String methodName = identifier();
				Args args = new Args();
				Expect(2);
				if (StartOf(3)) {
					formalParameterList(args);
				}
				Expect(3);
				InstructionList il = new InstructionList();
				MethodGen methodGen = new MethodGen(
				    modifier,
				    new ObjectType(classGen.getClassName()), // todo ?????????
				    args.argTypes,
				    args.argNames,
				    "<init>",
				    classGen.getClassName(),
				    il,
				    classGen.getConstantPool()
				);
				                  log("method "+methodName+" created");						  
				   
				Expect(4);
				while (StartOf(4)) {
					Statement();
				}
				Expect(5);
			} else if (StartOf(3)) {
				Type typeLiteral = null;
				if (StartOf(5)) {
					typeLiteral = type();
				} else {
					String typeName = identifier();
					typeLiteral = objectTypes.get(typeName);
					if(typeLiteral==null)
					{
					    typeLiteral = new ObjectType(typeName);
					    objectTypes.put(typeName, (ObjectType)typeLiteral);
					    log("added type "+typeName);
					}
					
				}
				String someThing = identifier();
				if (la.kind == 2) {
					Args args = new Args();
					Get();
					if (StartOf(3)) {
						formalParameterList(args);
					}
					Expect(3);
					Statement();
				} else if (la.kind == 29 || la.kind == 30) {
					if (la.kind == 29) {
						Get();
						Expression();
					}
					Expect(30);
				} else SynErr(69);
			} else SynErr(70);
		}
		Expect(5);
	}

	void interfaceBody(ClassGen classGen) {
		Expect(4);
		while (StartOf(2)) {
			int modifier = 0;
			if (la.kind == 16 || la.kind == 17 || la.kind == 18) {
				modifier = accessSpecifier();
				if (modifier == Constants.ACC_PRIVATE || modifier == Constants.ACC_PROTECTED)
				       SemErr("interface memebers ought to be public or default");
				     modifier = Constants.ACC_PUBLIC;
				 
			}
			if (la.kind == 11) {
				int fMod = finalAccess();
				modifier |= fMod;
			}
			if (la.kind == 10) {
				int sMod = staticAccess();
				modifier |= sMod;
			}
			Type typeLiteral = null;
			if (StartOf(5)) {
				typeLiteral = type();
			} else if (la.kind == 1) {
				String typeName = identifier();
				typeLiteral = objectTypes.get(typeName);
				if(typeLiteral==null)
				{   // todo ???-?? ?????? ? ?????????
				    typeLiteral = new ObjectType(typeName);
				    objectTypes.put(typeName, (ObjectType)typeLiteral);
				}
				
			} else SynErr(71);
			String methodName = identifier();
			Args args = new Args();
			Expect(2);
			if (StartOf(3)) {
				formalParameterList(args);
			}
			Expect(3);
			Expect(30);
			MethodGen methodGen = new MethodGen(
			   modifier,
			         typeLiteral,
			         args.argTypes,
			         args.argNames,
			         methodName,
			         classGen.getClassName(),
			         null, // instructions list
			         classGen.getConstantPool()
			);
			
		}
		Expect(5);
	}

	Type  type() {
		Type  typeLiteral;
		typeLiteral = null;
		switch (la.kind) {
		case 19: {
			Get();
			typeLiteral = Type.BYTE;
			break;
		}
		case 20: {
			Get();
			typeLiteral = Type.SHORT;
			break;
		}
		case 21: {
			Get();
			typeLiteral = Type.INT;
			break;
		}
		case 22: {
			Get();
			typeLiteral = Type.CHAR;
			break;
		}
		case 23: {
			Get();
			typeLiteral = Type.FLOAT;
			break;
		}
		case 24: {
			Get();
			typeLiteral = Type.BOOLEAN;
			break;
		}
		case 25: {
			Get();
			typeLiteral = Type.VOID;
			break;
		}
		case 26: {
			Get();
			typeLiteral = new ObjectType("java.lang.String");
			break;
		}
		case 27: {
			Get();
			typeLiteral = new ObjectType("java.util.Vector");
			break;
		}
		default: SynErr(72); break;
		}
		return typeLiteral;
	}

	void formalParameterList(Args args) {
		ArrayList<Type> types = new ArrayList<Type>();
		ArrayList<String> names = new ArrayList<String>();
		Type typeLiteral = null;
		if (StartOf(5)) {
			typeLiteral = type();
		} else if (la.kind == 1) {
			String typeName = identifier();
			typeLiteral = objectTypes.get(typeName);
			if(typeLiteral==null)
			{   
			    typeLiteral = new ObjectType(typeName);
			    objectTypes.put(typeName, (ObjectType)typeLiteral);
			}
			
		} else SynErr(73);
		types.add(typeLiteral);
		String param = identifier();
		names.add(param);
		while (la.kind == 28) {
			Get();
			if (StartOf(5)) {
				typeLiteral = type();
			} else if (la.kind == 1) {
				String typeName = identifier();
				typeLiteral = objectTypes.get(typeName);
				if(typeLiteral==null)
				{ 
				    typeLiteral = new ObjectType(typeName);
				    objectTypes.put(typeName, (ObjectType)typeLiteral);
				}
				    
			} else SynErr(74);
			types.add(typeLiteral);
			param = identifier();
			names.add(param);
		}
		args.argTypes = types.toArray(new Type[types.size()]);
		args.argNames = names.toArray(new String[names.size()]);
		
	}

	void Statement() {
		if (la.kind == 4) {
			Block();
			log("block");
		} else if (la.kind == 31) {
			Get();
			ParExpression();
			Statement();
			if (la.kind == 32) {
				Get();
				Statement();
			}
		} else if (la.kind == 33) {
			Get();
			ParExpression();
			Statement();
		} else if (la.kind == 34) {
			Get();
			if (StartOf(6)) {
				Expression();
			}
			Expect(30);
		} else if (la.kind == 35) {
			Get();
		} else if (la.kind == 36) {
			Get();
		} else if (la.kind == 30) {
			Get();
		} else if (StartOf(6)) {
			Expression();
			Expect(30);
		} else SynErr(75);
	}

	void Expression() {
		Expression1();
		while (StartOf(7)) {
			AssignmentOperator();
			Expression1();
		}
	}

	void Block() {
		Expect(4);
		while (StartOf(8)) {
			BlockStatement();
		}
		Expect(5);
	}

	void ParExpression() {
		Expect(2);
		Expression();
		Expect(3);
	}

	void BlockStatement() {
		if (StartOf(9)) {
			LocalVariableDeclaration();
			Expect(30);
		} else if (StartOf(4)) {
			Statement();
		} else SynErr(76);
	}

	void LocalVariableDeclaration() {
		if (la.kind == 11) {
			Get();
		}
		Type typeLiteral = type();
		String varName = identifier();
		if (la.kind == 29) {
			Get();
			Expression();
		}
	}

	void Expression1() {
		log("expr");
		Expression2();
		if (StartOf(10)) {
			Expression1Rest();
		}
	}

	void AssignmentOperator() {
		switch (la.kind) {
		case 29: {
			Get();
			break;
		}
		case 44: {
			Get();
			break;
		}
		case 45: {
			Get();
			break;
		}
		case 46: {
			Get();
			break;
		}
		case 47: {
			Get();
			break;
		}
		case 48: {
			Get();
			break;
		}
		case 49: {
			Get();
			break;
		}
		case 50: {
			Get();
			break;
		}
		case 51: {
			Get();
			break;
		}
		default: SynErr(77); break;
		}
	}

	void Expression2() {
		if (StartOf(11)) {
			Primary();
		}
		while (la.kind == 40) {
			Selector();
		}
	}

	void Expression1Rest() {
		Infixop();
		Expression2();
		while (StartOf(10)) {
			Infixop();
			Expression2();
		}
	}

	void Infixop() {
		switch (la.kind) {
		case 52: {
			Get();
			break;
		}
		case 53: {
			Get();
			break;
		}
		case 54: {
			Get();
			break;
		}
		case 55: {
			Get();
			break;
		}
		case 56: {
			Get();
			break;
		}
		case 57: {
			Get();
			break;
		}
		case 58: {
			Get();
			break;
		}
		case 59: {
			Get();
			break;
		}
		case 60: {
			Get();
			break;
		}
		case 61: {
			Get();
			break;
		}
		case 62: {
			Get();
			break;
		}
		case 63: {
			Get();
			break;
		}
		case 64: {
			Get();
			break;
		}
		case 65: {
			Get();
			break;
		}
		default: SynErr(78); break;
		}
	}

	void Primary() {
		switch (la.kind) {
		case 2: {
			Get();
			Expression();
			Expect(3);
			break;
		}
		case 37: {
			Get();
			if (la.kind == 2) {
				Arguments();
			}
			break;
		}
		case 38: {
			Get();
			SuperSuffix();
			break;
		}
		case 6: case 7: case 8: case 9: case 41: case 42: case 43: {
			Literal();
			break;
		}
		case 39: {
			Get();
			Creator();
			break;
		}
		case 1: {
			String accessor = identifier();
			while (la.kind == 40) {
				Get();
				accessor = identifier();
			}
			if (la.kind == 2) {
				Arguments();
			}
			break;
		}
		default: SynErr(79); break;
		}
	}

	void Selector() {
		Expect(40);
		String accessor = identifier();
		if (la.kind == 2) {
			Arguments();
		}
	}

	void Arguments() {
		Expect(2);
		if (StartOf(12)) {
			Expression();
			while (la.kind == 28) {
				Get();
				Expression();
			}
		}
		Expect(3);
	}

	void SuperSuffix() {
		if (la.kind == 2) {
			Arguments();
		} else if (la.kind == 40) {
			Get();
			String accessor = identifier();
			if (la.kind == 2) {
				Arguments();
			}
		} else SynErr(80);
	}

	void Literal() {
		switch (la.kind) {
		case 6: {
			Get();
			break;
		}
		case 7: {
			Get();
			break;
		}
		case 8: {
			Get();
			break;
		}
		case 9: {
			Get();
			break;
		}
		case 41: {
			Get();
			break;
		}
		case 42: {
			Get();
			break;
		}
		case 43: {
			Get();
			break;
		}
		default: SynErr(81); break;
		}
		log("lit="+t.val);
	}

	void Creator() {
		Qualident();
		Arguments();
	}

	void Qualident() {
		String accessor = identifier();
		while (la.kind == 40) {
			Get();
			accessor = identifier();
		}
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		CompilationUnit();

		Expect(0);
	}

	private boolean[][] set = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,x, x,x,x,x, x,x,T,T, T,x,x,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,x,x, x,x,x,x, x,x,T,T, x,x,x,x, T,T,T,T, T,T,T,T, T,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,T,x, T,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,T,x, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,T, T,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,T,x, T,x,T,T, T,T,x,T, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,x},
		{x,T,T,x, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, x,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,T,T, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "id expected"; break;
			case 2: s = "openRoundBracket expected"; break;
			case 3: s = "closeRoundBracket expected"; break;
			case 4: s = "openCurlyBracket expected"; break;
			case 5: s = "closeCurlyBracket expected"; break;
			case 6: s = "intLit expected"; break;
			case 7: s = "floatLit expected"; break;
			case 8: s = "charLit expected"; break;
			case 9: s = "stringLit expected"; break;
			case 10: s = "\"static\" expected"; break;
			case 11: s = "\"final\" expected"; break;
			case 12: s = "\"class\" expected"; break;
			case 13: s = "\"extends\" expected"; break;
			case 14: s = "\"implements\" expected"; break;
			case 15: s = "\"interface\" expected"; break;
			case 16: s = "\"public\" expected"; break;
			case 17: s = "\"protected\" expected"; break;
			case 18: s = "\"private\" expected"; break;
			case 19: s = "\"byte\" expected"; break;
			case 20: s = "\"short\" expected"; break;
			case 21: s = "\"int\" expected"; break;
			case 22: s = "\"char\" expected"; break;
			case 23: s = "\"float\" expected"; break;
			case 24: s = "\"boolean\" expected"; break;
			case 25: s = "\"void\" expected"; break;
			case 26: s = "\"String\" expected"; break;
			case 27: s = "\"Vector\" expected"; break;
			case 28: s = "\",\" expected"; break;
			case 29: s = "\"=\" expected"; break;
			case 30: s = "\";\" expected"; break;
			case 31: s = "\"if\" expected"; break;
			case 32: s = "\"else\" expected"; break;
			case 33: s = "\"while\" expected"; break;
			case 34: s = "\"return\" expected"; break;
			case 35: s = "\"break;\" expected"; break;
			case 36: s = "\"continue;\" expected"; break;
			case 37: s = "\"this\" expected"; break;
			case 38: s = "\"super\" expected"; break;
			case 39: s = "\"new\" expected"; break;
			case 40: s = "\".\" expected"; break;
			case 41: s = "\"true\" expected"; break;
			case 42: s = "\"false\" expected"; break;
			case 43: s = "\"null\" expected"; break;
			case 44: s = "\"+=\" expected"; break;
			case 45: s = "\"-=\" expected"; break;
			case 46: s = "\"*=\" expected"; break;
			case 47: s = "\"/=\" expected"; break;
			case 48: s = "\"&=\" expected"; break;
			case 49: s = "\"|=\" expected"; break;
			case 50: s = "\"^=\" expected"; break;
			case 51: s = "\"%=\" expected"; break;
			case 52: s = "\"||\" expected"; break;
			case 53: s = "\"&&\" expected"; break;
			case 54: s = "\"^\" expected"; break;
			case 55: s = "\"==\" expected"; break;
			case 56: s = "\"!=\" expected"; break;
			case 57: s = "\"<\" expected"; break;
			case 58: s = "\">\" expected"; break;
			case 59: s = "\"<=\" expected"; break;
			case 60: s = "\">=\" expected"; break;
			case 61: s = "\"+\" expected"; break;
			case 62: s = "\"-\" expected"; break;
			case 63: s = "\"*\" expected"; break;
			case 64: s = "\"/\" expected"; break;
			case 65: s = "\"%\" expected"; break;
			case 66: s = "??? expected"; break;
			case 67: s = "invalid typeDeclaration"; break;
			case 68: s = "invalid accessSpecifier"; break;
			case 69: s = "invalid classBody"; break;
			case 70: s = "invalid classBody"; break;
			case 71: s = "invalid interfaceBody"; break;
			case 72: s = "invalid type"; break;
			case 73: s = "invalid formalParameterList"; break;
			case 74: s = "invalid formalParameterList"; break;
			case 75: s = "invalid Statement"; break;
			case 76: s = "invalid BlockStatement"; break;
			case 77: s = "invalid AssignmentOperator"; break;
			case 78: s = "invalid Infixop"; break;
			case 79: s = "invalid Primary"; break;
			case 80: s = "invalid SuperSuffix"; break;
			case 81: s = "invalid Literal"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}

