package ru.spb.etu;

import org.apache.bcel.Constants;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.LocalVariableGen;

import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ArrayType;

import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;

import org.apache.bcel.generic.StoreInstruction;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.FSTORE;
import org.apache.bcel.generic.ISTORE;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	static final int _EOF = 0;
	static final int _id = 1;
	static final int _openRoundBracket = 2;
	static final int _closeRoundBracket = 3;
	static final int _openCurlyBracket = 4;
	static final int _closeCurlyBracket = 5;
	static final int _dot = 6;
	static final int _isequal = 7;
	static final int _intLit = 8;
	static final int _floatLit = 9;
	static final int _charLit = 10;
	static final int _stringLit = 11;
	static final int maxT = 57;

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
    // ???? ? ???????????? ???????
    public static String filePath;
	
    // ????????? ??????
    public HashMap<String, ClassGen> classes = new HashMap<String, ClassGen>();
    // ???????????? ??????
    public HashMap<String, ObjectType> objectTypes = new HashMap<String, ObjectType>();
    
	// ???????? ????? ?????????? - ??? ?? ?????????? ?????? ??????????
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
    
    // ??????? ?????????? ??????????
    public class Args
    {
        Type[] argTypes = null;
        String[] argNames = null;
    }
    
    // ??????
    public void log(String msg)
    {
        System.err.println(msg);        
    }
    
    // ??????? ?????????? ???? (?????????? ?? ?????? ???? ??????????? ? ??????????. ????? ????????? ???-?????? ???)
    class CodeWrapper
    {
        InstructionList il;
        ClassGen        classGen;
        MethodGen       methodGen;

        public CodeWrapper(
            ClassGen classGen,
            InstructionList il,
            MethodGen methodGen
        )
        {
            this.il = il;
            this.classGen = classGen;
            this.methodGen = methodGen; 
        }
    }
    
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
		if (value.equals("new")) SemErr("\"new\" is not a valid id");
		
		return value;
	}

	int  staticAccess() {
		int  modifier;
		Expect(12);
		modifier = Constants.ACC_STATIC;
		return modifier;
	}

	int  finalAccess() {
		int  modifier;
		Expect(13);
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
		if (la.kind == 18 || la.kind == 19 || la.kind == 20) {
			specifier = accessSpecifier();
		}
		if (la.kind == 17) {
			interfaceDeclaration(specifier);
		} else if (la.kind == 14) {
			classDeclaration(specifier);
		} else SynErr(58);
	}

	int  accessSpecifier() {
		int  specifier;
		if (la.kind == 18) {
			Get();
			specifier = Constants.ACC_PUBLIC;
		} else if (la.kind == 19) {
			Get();
			specifier = Constants.ACC_PROTECTED;
		} else if (la.kind == 20) {
			Get();
			specifier = Constants.ACC_PRIVATE;
		} else SynErr(59);
		specifier = 0;
		return specifier;
	}

	void interfaceDeclaration(int modifier) {
		Expect(17);
		modifier |= Constants.ACC_INTERFACE | Constants.ACC_ABSTRACT; 
		String interfaceName = identifier();
		String superInterfaceName = null;
		if (la.kind == 15) {
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
		try{
		   classGen.getJavaClass().dump( filePath+"/"+interfaceName+".class" );
		}catch(Exception e){e.printStackTrace();}
		
	}

	void classDeclaration(int modifier) {
		Expect(14);
		String className = identifier();
		String superName = "java.lang.Object";
		if (la.kind == 15) {
			Get();
			superName = identifier();
			if(superName.equals(className)) SemErr("cannot self-inherit");
			objectTypes.put(superName, new ObjectType(superName));
			
		}
		String interfaceName = null;
		if (la.kind == 16) {
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
		try{
		   classGen.getJavaClass().dump( filePath+"/"+className+".class" );
		}catch(Exception e){e.printStackTrace();}
		
	}

	void classBody(ClassGen classGen) {
		Expect(4);
		while (StartOf(2)) {
			int modifier = 0;
			if (la.kind == 18 || la.kind == 19 || la.kind == 20) {
				modifier = accessSpecifier();
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
				                  CodeWrapper cw = new CodeWrapper(classGen, il, methodGen);						  
				   
				Statement(cw);
			} else if (StartOf(4)) {
				if (la.kind == 13) {
					int fMod = finalAccess();
					modifier |= fMod;
				}
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
					    log("added type "+typeName);
					}
					
				} else SynErr(60);
				String member = identifier();
				if (la.kind == 2) {
					Args args = new Args();
					Get();
					if (StartOf(3)) {
						formalParameterList(args);
					}
					Expect(3);
					if (member.equals("main")) // ??? "main"
					{
					    if ( args.argTypes != null ) SemErr("too many args in main");
					    if ( !typeLiteral.equals(Type.VOID) ) SemErr("main cannot return a value. type must be \"void\"");
					                                
					    modifier |= Constants.ACC_STATIC;
					    args.argTypes = new Type[]{ new ArrayType( Type.STRING, 1 ) };
					    args.argNames = new String[] { "argv" };
					    
					} // todo ???????? ?.?. 1 ????????
					
					InstructionList il = new InstructionList();
					MethodGen methodGen = new MethodGen(
					modifier,
					typeLiteral,
					args.argTypes,
					args.argNames,
					member,
					classGen.getClassName(),
					il,
					classGen.getConstantPool()
					                );
					                log("method "+member+" created");
					                CodeWrapper cw = new CodeWrapper(classGen, il, methodGen);
					            
					Statement(cw);
				} else if (la.kind == 30) {
					FieldGen fieldGen = new FieldGen(
					   modifier,
					   typeLiteral,
					   member,
					   classGen.getConstantPool()
					);                            
					
					Get();
				} else SynErr(61);
			} else SynErr(62);
		}
		Expect(5);
	}

	void interfaceBody(ClassGen classGen) {
		Expect(4);
		while (StartOf(6)) {
			int modifier = 0;
			if (la.kind == 18 || la.kind == 19 || la.kind == 20) {
				modifier = accessSpecifier();
				if (modifier == Constants.ACC_PRIVATE || modifier == Constants.ACC_PROTECTED)
				       SemErr("interface memebers ought to be public or default");
				     modifier = Constants.ACC_PUBLIC;
				 
			}
			if (la.kind == 13) {
				int fMod = finalAccess();
				modifier |= fMod;
			}
			if (la.kind == 12) {
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
				{
				    typeLiteral = new ObjectType(typeName);
				    objectTypes.put(typeName, (ObjectType)typeLiteral);
				}
				
			} else SynErr(63);
			log("abstr method");
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
		case 21: {
			Get();
			typeLiteral = Type.BOOLEAN;
			break;
		}
		case 22: {
			Get();
			typeLiteral = Type.BYTE;
			break;
		}
		case 23: {
			Get();
			typeLiteral = Type.SHORT;
			break;
		}
		case 24: {
			Get();
			typeLiteral = Type.INT;
			break;
		}
		case 25: {
			Get();
			typeLiteral = Type.FLOAT;
			break;
		}
		case 26: {
			Get();
			typeLiteral = Type.VOID;
			break;
		}
		case 27: {
			Get();
			typeLiteral = new ObjectType("java.lang.String");
			break;
		}
		case 28: {
			Get();
			typeLiteral = new ObjectType("java.util.Vector");
			break;
		}
		default: SynErr(64); break;
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
			
		} else SynErr(65);
		types.add(typeLiteral);
		String param = identifier();
		names.add(param);
		while (la.kind == 29) {
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
				    
			} else SynErr(66);
			types.add(typeLiteral);
			param = identifier();
			names.add(param);
		}
		args.argTypes = types.toArray(new Type[types.size()]);
		args.argNames = names.toArray(new String[names.size()]);
		
	}

	void Statement(CodeWrapper cw) {
		switch (la.kind) {
		case 4: {
			Block(cw);
			break;
		}
		case 31: {
			Get();
			Type exprType = ParExpression(cw);
			Statement(cw);
			if (la.kind == 32) {
				Get();
				Statement(cw);
			}
			break;
		}
		case 33: {
			Get();
			Type exprType = ParExpression(cw);
			Statement(cw);
			break;
		}
		case 34: {
			Get();
			if (StartOf(7)) {
				Type exprType = Expression(cw);
			}
			Expect(30);
			break;
		}
		case 35: {
			Get();
			break;
		}
		case 36: {
			Get();
			break;
		}
		case 30: {
			Get();
			break;
		}
		case 1: case 2: case 8: case 9: case 11: case 37: case 38: case 39: case 40: case 41: case 42: {
			Type exprType = Expression(cw);
			Expect(30);
			break;
		}
		default: SynErr(67); break;
		}
	}

	void Block(CodeWrapper cw) {
		Expect(4);
		while (StartOf(8)) {
			BlockStatement(cw);
		}
		Expect(5);
	}

	Type  ParExpression(CodeWrapper cw) {
		Type  exprType;
		Expect(2);
		exprType = Expression(cw);
		Expect(3);
		return exprType;
	}

	Type  Expression(CodeWrapper cw) {
		Type  exprType;
		exprType = null;
		if (la.kind == 37) {
			Get();
			exprType = Creator(cw);
		} else if (StartOf(9)) {
			exprType = Literal();
		} else if (la.kind == 2) {
			Get();
			exprType = Expression(cw);
			Infixop();
			exprType = Expression(cw);
			Expect(3);
		} else if (la.kind == 1 || la.kind == 38 || la.kind == 39) {
			if (next(_dot)) {
				if (la.kind == 38) {
					Get();
				} else if (la.kind == 39) {
					Get();
				} else if (la.kind == 1) {
					String objVarName = identifier();
				} else SynErr(68);
				Expect(6);
				exprType = Selector(cw);
			} else {
				exprType = Selector(cw);
			}
		} else SynErr(69);
		return exprType;
	}

	void BlockStatement(CodeWrapper cw) {
		if (StartOf(10)) {
			Statement(cw);
		} else if (StartOf(3)) {
			LocalVariableDeclaration(cw);
			Expect(30);
		} else SynErr(70);
	}

	void LocalVariableDeclaration(CodeWrapper cw) {
		InstructionFactory factory = new InstructionFactory( cw.classGen );
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
			cw.il.append( factory.createNew( typeLiteral.getSignature() ) );
			
		} else SynErr(71);
		String varName = identifier();
		LocalVariableGen lg = cw.methodGen.addLocalVariable(varName, typeLiteral, null, null);
		cw.il.append( InstructionConstants.DUP ); 
		
		if (la.kind == 7) {
			Get();
			log("init");
			Type exprType = Expression(cw);
			StoreInstruction store = null;
			
			int name = lg.getIndex();
			if (typeLiteral.equals(Type.VOID))
			    SemErr("void variables are not allowed");
			else if (
			    typeLiteral.equals(Type.INT) ||
			    typeLiteral.equals(Type.BYTE) ||
			    typeLiteral.equals(Type.SHORT)
			    )
			    store = new ISTORE( name );
			else if ( typeLiteral.equals(Type.FLOAT) )
			    store = new FSTORE( name );
			else if ( typeLiteral instanceof ObjectType )
			    store = new ASTORE( name );
			else
			    SemErr("unknown type "+typeLiteral.getSignature());
			 
			lg.setStart( cw.il.append( store ) );
			
		}
	}

	Type  Creator(CodeWrapper cw) {
		Type  createdType;
		String className = identifier();
		Type[] argTypes = Arguments(cw);
		InstructionFactory factory = new InstructionFactory( cw.classGen );
		cw.il.append( factory.createInvoke(
		    className,
		    "<init>",
		    Type.VOID,
		    argTypes, 
		    Constants.INVOKESPECIAL 
		) );
		// todo ????????
		createdType = new ObjectType(className);
		
		return createdType;
	}

	Type  Literal() {
		Type  exprType;
		exprType = null;
		switch (la.kind) {
		case 8: {
			Get();
			exprType = Type.INT;
			break;
		}
		case 9: {
			Get();
			exprType = Type.FLOAT;
			break;
		}
		case 11: {
			Get();
			exprType = new ObjectType("java.lang.String");
			break;
		}
		case 40: {
			Get();
			exprType = Type.BOOLEAN;
			break;
		}
		case 41: {
			Get();
			exprType = Type.BOOLEAN;
			break;
		}
		case 42: {
			Get();
			exprType = Type.NULL;
			break;
		}
		default: SynErr(72); break;
		}
		log("lit="+t.val);
		return exprType;
	}

	void Infixop() {
		switch (la.kind) {
		case 43: {
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
		default: SynErr(73); break;
		}
	}

	Type  Selector(CodeWrapper cw) {
		Type  selType;
		selType = null;
		if (next(_openRoundBracket)) {
			selType = rval(cw);
		} else if (la.kind == 1) {
			selType = lval(cw);
		} else SynErr(74);
		return selType;
	}

	Type  rval(CodeWrapper cw) {
		Type  selType;
		String method = identifier();
		selType = null;
		Type[] argTypes = Arguments(cw);
		return selType;
	}

	Type  lval(CodeWrapper cw) {
		Type  selType;
		selType = null;
		if (next(_isequal)) {
			String var = identifier();
			selType = null;
			AssignmentOperator(cw);
			Type exprType = Expression(cw);
		} else if (la.kind == 1) {
			String var = identifier();
			selType = null;
		} else SynErr(75);
		return selType;
	}

	Type[]  Arguments(CodeWrapper cw) {
		Type[]  argTypes;
		ArrayList<Type> types = new ArrayList<Type>();
		Expect(2);
		if (StartOf(7)) {
			Type exprType = Expression(cw);
			types.add(exprType);
			while (la.kind == 29) {
				Get();
				exprType = Expression(cw);
				types.add(exprType);
			}
		}
		Expect(3);
		argTypes = types.toArray(new Type[types.size()]);
		return argTypes;
	}

	void AssignmentOperator(CodeWrapper cw) {
		log("assgmnt");
		Expect(7);
		
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		CompilationUnit();

		Expect(0);
	}

	private boolean[][] set = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,T,x,x, x,x,T,T, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,T,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, T,T,x,x, x,x,T,T, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,T,x, x,x,x,x, T,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,T,x, T,x,x,x, T,T,x,T, x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,x,T,T, x,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, T,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,T,x, T,x,x,x, T,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, x,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x}

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
			case 6: s = "dot expected"; break;
			case 7: s = "isequal expected"; break;
			case 8: s = "intLit expected"; break;
			case 9: s = "floatLit expected"; break;
			case 10: s = "charLit expected"; break;
			case 11: s = "stringLit expected"; break;
			case 12: s = "\"static\" expected"; break;
			case 13: s = "\"final\" expected"; break;
			case 14: s = "\"class\" expected"; break;
			case 15: s = "\"extends\" expected"; break;
			case 16: s = "\"implements\" expected"; break;
			case 17: s = "\"interface\" expected"; break;
			case 18: s = "\"public\" expected"; break;
			case 19: s = "\"protected\" expected"; break;
			case 20: s = "\"private\" expected"; break;
			case 21: s = "\"boolean\" expected"; break;
			case 22: s = "\"byte\" expected"; break;
			case 23: s = "\"short\" expected"; break;
			case 24: s = "\"int\" expected"; break;
			case 25: s = "\"float\" expected"; break;
			case 26: s = "\"void\" expected"; break;
			case 27: s = "\"String\" expected"; break;
			case 28: s = "\"Vector\" expected"; break;
			case 29: s = "\",\" expected"; break;
			case 30: s = "\";\" expected"; break;
			case 31: s = "\"if\" expected"; break;
			case 32: s = "\"else\" expected"; break;
			case 33: s = "\"while\" expected"; break;
			case 34: s = "\"return\" expected"; break;
			case 35: s = "\"break;\" expected"; break;
			case 36: s = "\"continue;\" expected"; break;
			case 37: s = "\"new\" expected"; break;
			case 38: s = "\"this\" expected"; break;
			case 39: s = "\"super\" expected"; break;
			case 40: s = "\"true\" expected"; break;
			case 41: s = "\"false\" expected"; break;
			case 42: s = "\"null\" expected"; break;
			case 43: s = "\"||\" expected"; break;
			case 44: s = "\"&&\" expected"; break;
			case 45: s = "\"^\" expected"; break;
			case 46: s = "\"==\" expected"; break;
			case 47: s = "\"!=\" expected"; break;
			case 48: s = "\"<\" expected"; break;
			case 49: s = "\">\" expected"; break;
			case 50: s = "\"<=\" expected"; break;
			case 51: s = "\">=\" expected"; break;
			case 52: s = "\"+\" expected"; break;
			case 53: s = "\"-\" expected"; break;
			case 54: s = "\"*\" expected"; break;
			case 55: s = "\"/\" expected"; break;
			case 56: s = "\"%\" expected"; break;
			case 57: s = "??? expected"; break;
			case 58: s = "invalid typeDeclaration"; break;
			case 59: s = "invalid accessSpecifier"; break;
			case 60: s = "invalid classBody"; break;
			case 61: s = "invalid classBody"; break;
			case 62: s = "invalid classBody"; break;
			case 63: s = "invalid interfaceBody"; break;
			case 64: s = "invalid type"; break;
			case 65: s = "invalid formalParameterList"; break;
			case 66: s = "invalid formalParameterList"; break;
			case 67: s = "invalid Statement"; break;
			case 68: s = "invalid Expression"; break;
			case 69: s = "invalid Expression"; break;
			case 70: s = "invalid BlockStatement"; break;
			case 71: s = "invalid LocalVariableDeclaration"; break;
			case 72: s = "invalid Literal"; break;
			case 73: s = "invalid Infixop"; break;
			case 74: s = "invalid Selector"; break;
			case 75: s = "invalid lval"; break;
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

