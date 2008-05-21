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
	static final int _intLit = 6;
	static final int _floatLit = 7;
	static final int _charLit = 8;
	static final int _stringLit = 9;
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
		} else if (la.kind == 12) {
			classDeclaration(specifier);
		} else SynErr(58);
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
		} else SynErr(59);
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
		try{
		   classGen.getJavaClass().dump( filePath+"/"+interfaceName+".class" );
		}catch(Exception e){e.printStackTrace();}
		
	}

	void classDeclaration(int modifier) {
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
		try{
		   classGen.getJavaClass().dump( filePath+"/"+className+".class" );
		}catch(Exception e){e.printStackTrace();}
		
	}

	void classBody(ClassGen classGen) {
		Expect(4);
		while (StartOf(2)) {
			int modifier = 0;
			if (la.kind == 16 || la.kind == 17 || la.kind == 18) {
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
				if (la.kind == 11) {
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
				} else if (la.kind == 28) {
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
				{
				    typeLiteral = new ObjectType(typeName);
				    objectTypes.put(typeName, (ObjectType)typeLiteral);
				}
				
			} else SynErr(63);
			String methodName = identifier();
			Args args = new Args();
			Expect(2);
			if (StartOf(3)) {
				formalParameterList(args);
			}
			Expect(3);
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
		Expect(5);
	}

	Type  type() {
		Type  typeLiteral;
		typeLiteral = null;
		switch (la.kind) {
		case 19: {
			Get();
			typeLiteral = Type.BOOLEAN;
			break;
		}
		case 20: {
			Get();
			typeLiteral = Type.BYTE;
			break;
		}
		case 21: {
			Get();
			typeLiteral = Type.SHORT;
			break;
		}
		case 22: {
			Get();
			typeLiteral = Type.INT;
			break;
		}
		case 23: {
			Get();
			typeLiteral = Type.FLOAT;
			break;
		}
		case 24: {
			Get();
			typeLiteral = Type.VOID;
			break;
		}
		case 25: {
			Get();
			typeLiteral = new ObjectType("java.lang.String");
			break;
		}
		case 26: {
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
		while (la.kind == 27) {
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
		if (la.kind == 4) {
			Block(cw);
		} else if (la.kind == 29) {
			Get();
			ParExpression(cw);
			Statement(cw);
			if (la.kind == 30) {
				Get();
				Statement(cw);
			}
		} else if (la.kind == 31) {
			Get();
			ParExpression(cw);
			Statement(cw);
		} else if (la.kind == 32) {
			Get();
			if (StartOf(7)) {
				Expression(cw);
			}
			Expect(28);
		} else if (la.kind == 33) {
			Get();
		} else if (la.kind == 34) {
			Get();
		} else if (la.kind == 28) {
			Get();
		} else if (StartOf(7)) {
			Expression(cw);
			Expect(28);
		} else SynErr(67);
	}

	void Block(CodeWrapper cw) {
		Expect(4);
		while (StartOf(8)) {
			BlockStatement(cw);
		}
		Expect(5);
	}

	void ParExpression(CodeWrapper cw) {
		Expect(2);
		Expression(cw);
		Expect(3);
	}

	void Expression(CodeWrapper cw) {
		Expression2(cw);
		if (la.kind == 35) {
			AssignmentOperator();
			Expression1(cw);
		}
	}

	void BlockStatement(CodeWrapper cw) {
		if (StartOf(9)) {
			Statement(cw);
		} else if (StartOf(3)) {
			LocalVariableDeclaration(cw);
			Expect(28);
		} else SynErr(68);
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
			
		} else SynErr(69);
		String varName = identifier();
		LocalVariableGen lg = cw.methodGen.addLocalVariable(varName, typeLiteral, null, null);
		cw.il.append( InstructionConstants.DUP ); 
		
		if (la.kind == 35) {
			Get();
			log("init");
			Expression(cw);
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

	void Expression2(CodeWrapper cw) {
		if (StartOf(10)) {
			Primary(cw);
		}
		while (la.kind == 39) {
			Selector(cw);
		}
	}

	void AssignmentOperator() {
		log("assgmnt");
		Expect(35);
	}

	void Expression1(CodeWrapper cw) {
		Expression2(cw);
		if (StartOf(11)) {
			Expression1Rest(cw);
		}
	}

	void Expression1Rest(CodeWrapper cw) {
		Infixop();
		Expression2(cw);
		while (StartOf(11)) {
			Infixop();
			Expression2(cw);
		}
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
		default: SynErr(70); break;
		}
	}

	void Primary(CodeWrapper cw) {
		switch (la.kind) {
		case 2: {
			Get();
			Expression(cw);
			Expect(3);
			break;
		}
		case 36: {
			Get();
			if (la.kind == 2) {
				Arguments(cw);
			}
			break;
		}
		case 37: {
			Get();
			SuperSuffix(cw);
			break;
		}
		case 6: case 7: case 8: case 9: case 40: case 41: case 42: {
			Literal();
			break;
		}
		case 38: {
			Get();
			Creator(cw);
			break;
		}
		case 1: {
			String accessor = identifier();
			while (la.kind == 39) {
				Get();
				accessor = identifier();
			}
			if (la.kind == 2) {
				Arguments(cw);
			}
			break;
		}
		default: SynErr(71); break;
		}
	}

	void Selector(CodeWrapper cw) {
		Expect(39);
		String accessor = identifier();
		if (la.kind == 2) {
			Arguments(cw);
		}
	}

	void Arguments(CodeWrapper cw) {
		Expect(2);
		if (StartOf(12)) {
			Expression(cw);
			while (la.kind == 27) {
				Get();
				Expression(cw);
			}
		}
		Expect(3);
	}

	void SuperSuffix(CodeWrapper cw) {
		if (la.kind == 2) {
			Arguments(cw);
		} else if (la.kind == 39) {
			Get();
			String accessor = identifier();
			if (la.kind == 2) {
				Arguments(cw);
			}
		} else SynErr(72);
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
		case 40: {
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
		default: SynErr(73); break;
		}
		log("lit="+t.val);
	}

	void Creator(CodeWrapper cw) {
		Qualident();
		Arguments(cw);
	}

	void Qualident() {
		String accessor = identifier();
		while (la.kind == 39) {
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
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,T, x,x,x,x, T,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,T,T, x,x,x,x, T,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,T,x, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,x,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,T,x, T,x,T,T, T,T,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,x, T,T,x,T, T,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,T,x, T,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,x,T, T,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,T,x, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,x, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x},
		{x,T,T,T, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x}

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
			case 19: s = "\"boolean\" expected"; break;
			case 20: s = "\"byte\" expected"; break;
			case 21: s = "\"short\" expected"; break;
			case 22: s = "\"int\" expected"; break;
			case 23: s = "\"float\" expected"; break;
			case 24: s = "\"void\" expected"; break;
			case 25: s = "\"String\" expected"; break;
			case 26: s = "\"Vector\" expected"; break;
			case 27: s = "\",\" expected"; break;
			case 28: s = "\";\" expected"; break;
			case 29: s = "\"if\" expected"; break;
			case 30: s = "\"else\" expected"; break;
			case 31: s = "\"while\" expected"; break;
			case 32: s = "\"return\" expected"; break;
			case 33: s = "\"break;\" expected"; break;
			case 34: s = "\"continue;\" expected"; break;
			case 35: s = "\"=\" expected"; break;
			case 36: s = "\"this\" expected"; break;
			case 37: s = "\"super\" expected"; break;
			case 38: s = "\"new\" expected"; break;
			case 39: s = "\".\" expected"; break;
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
			case 68: s = "invalid BlockStatement"; break;
			case 69: s = "invalid LocalVariableDeclaration"; break;
			case 70: s = "invalid Infixop"; break;
			case 71: s = "invalid Primary"; break;
			case 72: s = "invalid SuperSuffix"; break;
			case 73: s = "invalid Literal"; break;
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

