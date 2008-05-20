package ru.spb.etu;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.ObjectType;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	static final int _EOF = 0;
	static final int _realNumber = 1;
	static final int _string = 2;
	static final int _id = 3;
	static final int _openRoundBracket = 4;
	static final int _closeRoundBracket = 5;
	static final int _openCurlyBracket = 6;
	static final int _closeCurlyBracket = 7;
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
		Expect(3);
		value = t.val;
		System.out.println(t.val);
		
		return value;
	}

	int  staticAccess() {
		int  modifier;
		Expect(8);
		modifier = Constants.ACC_STATIC;
		return modifier;
	}

	int  finalAccess() {
		int  modifier;
		Expect(9);
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
		if (la.kind == 14 || la.kind == 15 || la.kind == 16) {
			specifier = accessSpecifier();
		}
		if (la.kind == 13) {
			interfaceDeclaration(specifier);
		} else if (la.kind == 8 || la.kind == 9 || la.kind == 10) {
			classDeclaration(specifier);
		} else SynErr(67);
	}

	int  accessSpecifier() {
		int  specifier;
		if (la.kind == 14) {
			Get();
			specifier = Constants.ACC_PUBLIC;
		} else if (la.kind == 15) {
			Get();
			specifier = Constants.ACC_PROTECTED;
		} else if (la.kind == 16) {
			Get();
			specifier = Constants.ACC_PRIVATE;
		} else SynErr(68);
		specifier = 0;
		return specifier;
	}

	void interfaceDeclaration(int modifier) {
		Expect(13);
		modifier |= Constants.ACC_INTERFACE | Constants.ACC_ABSTRACT; 
		String interfaceName = identifier();
		String superInterfaceName = null;
		if (la.kind == 11) {
			Get();
			superInterfaceName = identifier();
		}
		ClassGen classGen = new ClassGen(
		   interfaceName, 
		   "java.lang.Object", 
		   interfaceName+".class", 
		   modifier,
		   superInterfaceName==null ? null : new String[]{superInterfaceName} );
		classes.put(interfaceName, classGen);
		
		interfaceBody(classGen);
	}

	void classDeclaration(int modifier) {
		if (la.kind == 9) {
			int fMod = finalAccess();
			modifier |= fMod;
		}
		if (la.kind == 8) {
			int sMod = staticAccess();
			modifier |= sMod;
		}
		Expect(10);
		String className = identifier();
		String superName = "java.lang.Object";
		if (la.kind == 11) {
			Get();
			superName = identifier();
		}
		String interfaceName = null;
		if (la.kind == 12) {
			Get();
			interfaceName = identifier();
			ClassGen classGen = new ClassGen(
			   className, 
			   superName, 
			   className+".class", 
			   modifier,
			   interfaceName==null ? null : new String[]{interfaceName} );
			classes.put(interfaceName, classGen);
			
		}
		classBody();
	}

	void classBody() {
		Expect(6);
		while (StartOf(2)) {
			if (la.kind == 14 || la.kind == 15 || la.kind == 16) {
				int specifier = accessSpecifier();
			}
			if (next(_openRoundBracket)) {
				String someThing = identifier();
				Args args = new Args();
				Expect(4);
				if (StartOf(3)) {
					formalParameterList(args);
				}
				Expect(5);
				Expect(6);
				while (StartOf(4)) {
					statement();
				}
				Expect(7);
			} else if (StartOf(5)) {
				if (la.kind == 9) {
					int fMod = finalAccess();
				}
				if (la.kind == 8) {
					int sMod = staticAccess();
				}
				String typeName = "void";
				if (StartOf(6)) {
					Type typeLiteral = type();
				} else if (la.kind == 3) {
					typeName = identifier();
				} else SynErr(69);
				String someThing = identifier();
				if (la.kind == 4) {
					Args args = new Args();
					Get();
					if (StartOf(3)) {
						formalParameterList(args);
					}
					Expect(5);
					Expect(6);
					while (StartOf(4)) {
						statement();
					}
					Expect(7);
				} else if (la.kind == 27 || la.kind == 28) {
					if (la.kind == 27) {
						Get();
						expression();
					}
					Expect(28);
				} else SynErr(70);
			} else SynErr(71);
		}
		Expect(7);
	}

	void interfaceBody(ClassGen classGen) {
		Expect(6);
		while (StartOf(2)) {
			int modifier = 0;
			if (la.kind == 14 || la.kind == 15 || la.kind == 16) {
				modifier = accessSpecifier();
				if (modifier == Constants.ACC_PRIVATE || modifier == Constants.ACC_PROTECTED)
				       SemErr("interface memebers ought to be public or default");
				     modifier = Constants.ACC_PUBLIC;
				 
			}
			if (la.kind == 9) {
				int fMod = finalAccess();
				modifier |= fMod;
			}
			if (la.kind == 8) {
				int sMod = staticAccess();
				modifier |= sMod;
			}
			Type typeLiteral = null;
			if (StartOf(6)) {
				typeLiteral = type();
			} else if (la.kind == 3) {
				String typeName = identifier();
				typeLiteral = objectTypes.get(typeName);
				if(typeLiteral==null)
				{   // todo ???-?? ?????? ? ?????????
				    typeLiteral = new ObjectType(typeName);
				    objectTypes.put(typeName, (ObjectType)typeLiteral);
				}
				
			} else SynErr(72);
			String methodName = identifier();
			Args args = new Args();
			Expect(4);
			if (StartOf(3)) {
				formalParameterList(args);
			}
			Expect(5);
			Expect(28);
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
		Expect(7);
	}

	Type  type() {
		Type  typeLiteral;
		typeLiteral = null;
		switch (la.kind) {
		case 17: {
			Get();
			typeLiteral = Type.BYTE;
			break;
		}
		case 18: {
			Get();
			typeLiteral = Type.SHORT;
			break;
		}
		case 19: {
			Get();
			typeLiteral = Type.INT;
			break;
		}
		case 20: {
			Get();
			typeLiteral = Type.CHAR;
			break;
		}
		case 21: {
			Get();
			typeLiteral = Type.FLOAT;
			break;
		}
		case 22: {
			Get();
			typeLiteral = Type.BOOLEAN;
			break;
		}
		case 23: {
			Get();
			typeLiteral = Type.VOID;
			break;
		}
		case 24: {
			Get();
			typeLiteral = new ObjectType("java.lang.String");
			break;
		}
		case 25: {
			Get();
			typeLiteral = new ObjectType("java.util.Vector");
			break;
		}
		default: SynErr(73); break;
		}
		return typeLiteral;
	}

	void formalParameterList(Args args) {
		ArrayList<Type> types = new ArrayList<Type>();
		ArrayList<String> names = new ArrayList<String>();
		Type typeLiteral = null;
		if (StartOf(6)) {
			typeLiteral = type();
		} else if (la.kind == 3) {
			String typeName = identifier();
			typeLiteral = objectTypes.get(typeName);
			if(typeLiteral==null)
			{   
			    typeLiteral = new ObjectType(typeName);
			    objectTypes.put(typeName, (ObjectType)typeLiteral);
			}
			
		} else SynErr(74);
		types.add(typeLiteral);
		String param = identifier();
		names.add(param);
		while (la.kind == 26) {
			Get();
			if (StartOf(6)) {
				typeLiteral = type();
			} else if (la.kind == 3) {
				String typeName = identifier();
				typeLiteral = objectTypes.get(typeName);
				if(typeLiteral==null)
				{ 
				    typeLiteral = new ObjectType(typeName);
				    objectTypes.put(typeName, (ObjectType)typeLiteral);
				}
				
			} else SynErr(75);
			types.add(typeLiteral);
			param = identifier();
			names.add(param);
		}
		args.argTypes = types.toArray(new Type[types.size()]);
		args.argNames = names.toArray(new String[names.size()]);
		
	}

	void statement() {
		switch (la.kind) {
		case 29: {
			Get();
			Expect(4);
			expression();
			Expect(5);
			if (la.kind == 6) {
				Get();
				while (StartOf(4)) {
					statement();
				}
				Expect(7);
			} else if (StartOf(4)) {
				statement();
			} else SynErr(76);
			if (la.kind == 30) {
				Get();
				if (la.kind == 6) {
					Get();
					while (StartOf(4)) {
						statement();
					}
					Expect(7);
				} else if (StartOf(4)) {
					statement();
				} else SynErr(77);
			}
			break;
		}
		case 31: {
			Get();
			Expect(4);
			expression();
			Expect(5);
			if (la.kind == 6) {
				Get();
				while (StartOf(4)) {
					statement();
				}
				Expect(7);
			} else if (StartOf(4)) {
				statement();
			} else SynErr(78);
			break;
		}
		case 32: {
			Get();
			if (StartOf(7)) {
				expression();
			}
			Expect(28);
			break;
		}
		case 33: {
			Get();
			expressionName();
			Expect(4);
			if (StartOf(7)) {
				expression();
			}
			Expect(5);
			break;
		}
		case 6: {
			Get();
			while (StartOf(4)) {
				statement();
			}
			Expect(7);
			break;
		}
		case 17: case 18: case 19: case 20: case 21: case 22: case 23: case 24: case 25: {
			Type typeLiteral = type();
			String someThing = identifier();
			if (la.kind == 27) {
				Get();
				expression();
			}
			while (la.kind == 26) {
				Get();
				someThing = identifier();
				if (la.kind == 27) {
					Get();
					expression();
				}
			}
			Expect(28);
			break;
		}
		case 3: {
			String someThing = identifier();
			if (la.kind == 4) {
				Get();
				if (StartOf(7)) {
					expression();
					while (la.kind == 26) {
						Get();
						expression();
					}
				}
				Expect(5);
			}
			while (la.kind == 34) {
				Get();
				someThing = identifier();
				if (la.kind == 4) {
					Get();
					if (StartOf(7)) {
						expression();
						while (la.kind == 26) {
							Get();
							expression();
						}
					}
					Expect(5);
				}
			}
			if (la.kind == 3) {
				someThing = identifier();
				if (la.kind == 27) {
					Get();
					expression();
				}
				while (la.kind == 26) {
					Get();
					someThing = identifier();
					if (la.kind == 27) {
						Get();
						expression();
					}
				}
				Expect(28);
			} else if (la.kind == 28) {
				Get();
			} else if (StartOf(8)) {
				assignmentOperator();
				expression();
				Expect(28);
			} else SynErr(79);
			break;
		}
		case 35: {
			Get();
			while (la.kind == 34) {
				Get();
				String someThing = identifier();
			}
			Expect(4);
			if (StartOf(7)) {
				expression();
				while (la.kind == 26) {
					Get();
					expression();
				}
			}
			Expect(5);
			Expect(28);
			break;
		}
		default: SynErr(80); break;
		}
	}

	void expression() {
		conditionalOrExpression();
	}

	void expressionName() {
		if (la.kind == 3 || la.kind == 35) {
			if (la.kind == 3) {
				String someThing = identifier();
			} else {
				Get();
			}
			if (la.kind == 4) {
				Get();
				if (StartOf(7)) {
					expression();
					while (la.kind == 26) {
						Get();
						expression();
					}
				}
				Expect(5);
			}
			while (la.kind == 34) {
				Get();
				String someThing = identifier();
				if (la.kind == 4) {
					Get();
					if (StartOf(7)) {
						expression();
						while (la.kind == 26) {
							Get();
							expression();
						}
					}
					Expect(5);
				}
			}
		} else if (la.kind == 1) {
			Get();
		} else if (la.kind == 2) {
			Get();
		} else SynErr(81);
	}

	void assignmentOperator() {
		switch (la.kind) {
		case 27: {
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
		default: SynErr(82); break;
		}
	}

	void conditionalOrExpression() {
		conditionalAndExpression();
		while (la.kind == 36) {
			Get();
			conditionalOrExpression();
		}
	}

	void conditionalAndExpression() {
		inclusiveOrExpression();
		while (la.kind == 37) {
			Get();
			conditionalAndExpression();
		}
	}

	void inclusiveOrExpression() {
		exclusiveOrExpression();
		while (la.kind == 38) {
			Get();
			inclusiveOrExpression();
		}
	}

	void exclusiveOrExpression() {
		andExpression();
		while (la.kind == 39) {
			Get();
			exclusiveOrExpression();
		}
	}

	void andExpression() {
		equalityExpression();
		while (la.kind == 40) {
			Get();
			andExpression();
		}
	}

	void equalityExpression() {
		relationalExpression();
		while (la.kind == 41 || la.kind == 42) {
			if (la.kind == 41) {
				Get();
			} else {
				Get();
			}
			equalityExpression();
		}
	}

	void relationalExpression() {
		shiftExpression();
		while (StartOf(9)) {
			if (la.kind == 43) {
				Get();
			} else if (la.kind == 44) {
				Get();
			} else if (la.kind == 45) {
				Get();
			} else {
				Get();
			}
			relationalExpression();
		}
	}

	void shiftExpression() {
		additiveExpression();
		while (la.kind == 47 || la.kind == 48 || la.kind == 49) {
			if (la.kind == 47) {
				Get();
			} else if (la.kind == 48) {
				Get();
			} else {
				Get();
			}
			shiftExpression();
		}
	}

	void additiveExpression() {
		multiplicativeExpression();
		while (la.kind == 50 || la.kind == 51) {
			if (la.kind == 50) {
				Get();
			} else {
				Get();
			}
			additiveExpression();
		}
	}

	void multiplicativeExpression() {
		unaryExpression();
		while (la.kind == 52 || la.kind == 53 || la.kind == 54) {
			if (la.kind == 52) {
				Get();
			} else if (la.kind == 53) {
				Get();
			} else {
				Get();
			}
			multiplicativeExpression();
		}
	}

	void unaryExpression() {
		expressionName();
		while (la.kind == 52 || la.kind == 53 || la.kind == 54) {
			if (la.kind == 52) {
				Get();
			} else if (la.kind == 53) {
				Get();
			} else {
				Get();
			}
			unaryExpression();
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
		{x,x,x,x, x,x,x,x, T,T,T,x, x,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,T, x,x,x,x, T,T,x,x, x,x,T,T, T,T,T,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,T, x,x,T,x, x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,x,x, x,T,x,T, T,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,T, x,x,x,x, T,T,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x}

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
			case 1: s = "realNumber expected"; break;
			case 2: s = "string expected"; break;
			case 3: s = "id expected"; break;
			case 4: s = "openRoundBracket expected"; break;
			case 5: s = "closeRoundBracket expected"; break;
			case 6: s = "openCurlyBracket expected"; break;
			case 7: s = "closeCurlyBracket expected"; break;
			case 8: s = "\"static\" expected"; break;
			case 9: s = "\"final\" expected"; break;
			case 10: s = "\"class\" expected"; break;
			case 11: s = "\"extends\" expected"; break;
			case 12: s = "\"implements\" expected"; break;
			case 13: s = "\"interface\" expected"; break;
			case 14: s = "\"public\" expected"; break;
			case 15: s = "\"protected\" expected"; break;
			case 16: s = "\"private\" expected"; break;
			case 17: s = "\"byte\" expected"; break;
			case 18: s = "\"short\" expected"; break;
			case 19: s = "\"int\" expected"; break;
			case 20: s = "\"char\" expected"; break;
			case 21: s = "\"float\" expected"; break;
			case 22: s = "\"boolean\" expected"; break;
			case 23: s = "\"void\" expected"; break;
			case 24: s = "\"String\" expected"; break;
			case 25: s = "\"Vector\" expected"; break;
			case 26: s = "\",\" expected"; break;
			case 27: s = "\"=\" expected"; break;
			case 28: s = "\";\" expected"; break;
			case 29: s = "\"if\" expected"; break;
			case 30: s = "\"else\" expected"; break;
			case 31: s = "\"while\" expected"; break;
			case 32: s = "\"return\" expected"; break;
			case 33: s = "\"new\" expected"; break;
			case 34: s = "\".\" expected"; break;
			case 35: s = "\"super\" expected"; break;
			case 36: s = "\"||\" expected"; break;
			case 37: s = "\"&&\" expected"; break;
			case 38: s = "\"|\" expected"; break;
			case 39: s = "\"^\" expected"; break;
			case 40: s = "\"&\" expected"; break;
			case 41: s = "\"==\" expected"; break;
			case 42: s = "\"!=\" expected"; break;
			case 43: s = "\"<\" expected"; break;
			case 44: s = "\">\" expected"; break;
			case 45: s = "\"<=\" expected"; break;
			case 46: s = "\">=\" expected"; break;
			case 47: s = "\"<<\" expected"; break;
			case 48: s = "\">>\" expected"; break;
			case 49: s = "\"<<<\" expected"; break;
			case 50: s = "\"+\" expected"; break;
			case 51: s = "\"-\" expected"; break;
			case 52: s = "\"*\" expected"; break;
			case 53: s = "\"/\" expected"; break;
			case 54: s = "\"%\" expected"; break;
			case 55: s = "\"*=\" expected"; break;
			case 56: s = "\"/=\" expected"; break;
			case 57: s = "\"%=\" expected"; break;
			case 58: s = "\"+=\" expected"; break;
			case 59: s = "\"-=\" expected"; break;
			case 60: s = "\"<<=\" expected"; break;
			case 61: s = "\">>=\" expected"; break;
			case 62: s = "\">>>=\" expected"; break;
			case 63: s = "\"&=\" expected"; break;
			case 64: s = "\"^=\" expected"; break;
			case 65: s = "\"|=\" expected"; break;
			case 66: s = "??? expected"; break;
			case 67: s = "invalid typeDeclaration"; break;
			case 68: s = "invalid accessSpecifier"; break;
			case 69: s = "invalid classBody"; break;
			case 70: s = "invalid classBody"; break;
			case 71: s = "invalid classBody"; break;
			case 72: s = "invalid interfaceBody"; break;
			case 73: s = "invalid type"; break;
			case 74: s = "invalid formalParameterList"; break;
			case 75: s = "invalid formalParameterList"; break;
			case 76: s = "invalid statement"; break;
			case 77: s = "invalid statement"; break;
			case 78: s = "invalid statement"; break;
			case 79: s = "invalid statement"; break;
			case 80: s = "invalid statement"; break;
			case 81: s = "invalid expressionName"; break;
			case 82: s = "invalid assignmentOperator"; break;
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

