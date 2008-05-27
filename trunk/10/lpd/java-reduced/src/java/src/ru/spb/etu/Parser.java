package ru.spb.etu;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.ACONST_NULL;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ArithmeticInstruction;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.FADD;
import org.apache.bcel.generic.FDIV;
import org.apache.bcel.generic.FMUL;
import org.apache.bcel.generic.FREM;
import org.apache.bcel.generic.FSUB;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.IADD;
import org.apache.bcel.generic.IAND;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.IDIV;
import org.apache.bcel.generic.IFEQ;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.IMUL;
import org.apache.bcel.generic.IOR;
import org.apache.bcel.generic.IREM;
import org.apache.bcel.generic.ISUB;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NOP;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

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
	static final int _stringLit = 10;
	static final int maxT = 44;

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
    public ParserImpl impl = new ParserImpl(this); 
    
    // ??????
    public void log(String msg)
    { System.err.println(msg); }
    
    // ?????
    public MethodParams getMethod( String name, Type[] argTypes, ClassGen classGen )
    { return impl.getMethod(name, argTypes, classGen );}

    public LocalVariableGen getVarGen( String name, MethodGen methodGen )
    { return impl.getVarGen(name,methodGen ); }
    
    public Field getFieldGen( String name, ClassGen classGen )
    { return impl.getFieldGen( name,classGen ); }
    
    public boolean isPresent(String typeName)
    { return impl.isPresent(typeName); }
    
    public boolean isDuplicate(String typeName)
    { return impl.isDuplicate(typeName); }
    
    public Instruction getStoreInstruction(Type type, int index)
    { return impl.getStoreInstruction(type, index); }
    
    public Instruction getLoadInstruction( Type type, int index )
    { return impl.getLoadInstruction( type, index ); }
    
    public void preparePrintStream(CodeWrapper cw)
    { impl.preparePrintStream(cw); }
    
    public void callPrintMethod(CodeWrapper cw, String method, Type[] argTypes)
    { impl.callPrintMethod(cw, method, argTypes); }
    
    public void addClass( String name, ClassGen classGen )
    { impl.addClass( name, classGen ); }
    
    public void addInterface( String name, ClassGen classGen )
    { impl.addInterface( name, classGen ); }
    
    public ClassGen getType( String name )
    { return impl.getType(name); }
    

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

	void CompilationUnit() {
		typeDeclaration();
		while (la.kind == 11 || la.kind == 14 || la.kind == 15) {
			typeDeclaration();
		}
	}

	void typeDeclaration() {
		int specifier = 0;
		if (la.kind == 15) {
			specifier = accessSpecifier();
		}
		if (la.kind == 14) {
			interfaceDeclaration(specifier);
		} else if (la.kind == 11) {
			classDeclaration(specifier);
		} else SynErr(45);
	}

	int  accessSpecifier() {
		int  specifier;
		specifier = 0;
		Expect(15);
		specifier = Constants.ACC_PUBLIC;
		return specifier;
	}

	void interfaceDeclaration(int modifier) {
		Expect(14);
		String interfaceName = identifier();
		String superInterfaceName = null;
		if (la.kind == 12) {
			Get();
			superInterfaceName = identifier();
			if(superInterfaceName.equals(interfaceName)) SemErr("cannot self-inherit");
			isPresent(superInterfaceName);
		}
		ClassGen classGen = new ClassGen(
		   interfaceName, 
		   "java.lang.Object", 
		   interfaceName+".class", 
		   Constants.ACC_INTERFACE | Constants.ACC_ABSTRACT,
		   superInterfaceName==null ? null : new String[]{superInterfaceName} 
		);
		    
		   addInterface(interfaceName, classGen);
		   
		log("interface "+interfaceName+" created");
		interfaceBody(classGen);
		try{
		   classGen.getJavaClass().dump( filePath+"/"+interfaceName+".class" );
		}catch(Exception e){e.printStackTrace();}
	}

	void classDeclaration(int modifier) {
		Expect(11);
		String className = identifier();
		String superName = "java.lang.Object";
		if (la.kind == 12) {
			Get();
			superName = identifier();
			if(superName.equals(className)) SemErr("cannot self-inherit");
			  isPresent(superName);
		}
		String interfaceName = null;
		if (la.kind == 13) {
			Get();
			interfaceName = identifier();
			if(interfaceName.equals(className)) SemErr("cannot self-implement");
			  isPresent(interfaceName);
		}
		ClassGen classGen = new ClassGen(
		   className, 
		   superName, 
		   className+".class", 
		   modifier,
		   interfaceName==null ? null : new String[]{interfaceName} );
		   
		addClass(className, classGen);
		       
		// todo ????? ????? =)
		classGen.addEmptyConstructor( Constants.ACC_PUBLIC );
		log("class "+className+" created");
		classBody(classGen);
		try{
		   classGen.getJavaClass().dump( filePath+"/"+className+".class" );
		}catch(Exception e){e.printStackTrace();}
	}

	void classBody(ClassGen classGen) {
		Expect(4);
		while (StartOf(1)) {
			int modifier = 0;
			if (la.kind == 15) {
				modifier = accessSpecifier();
			}
			if (next(_openRoundBracket)) {
				String methodName = identifier();
				if (!methodName.equals(classGen.getClassName())) SemErr("wrong constructor name "+methodName);
				        else {
				            Args args = new Args(); 
				Expect(2);
				if (StartOf(2)) {
					formalParameterList(args);
				}
				Expect(3);
				if (getMethod(methodName, args.argTypes, classGen)==null) {
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
				if (cw.il.getLength()==0) 
				cw.append( InstructionConstants.RETURN );
				
				cw.methodGen.setMaxStack();
				classGen.addMethod(cw.methodGen.getMethod());
				} else SemErr("duplicate constructor "+methodName);
				                 } 
			} else if (StartOf(2)) {
				Type typeLiteral = null;
				typeLiteral = type();
				String member = identifier();
				if (la.kind == 2) {
					Args args = new Args();
					Get();
					if (StartOf(2)) {
						formalParameterList(args);
					}
					Expect(3);
					if (getMethod(member, args.argTypes, classGen)==null) {
					if (member.equals("main")) { // ??? "main"
					    if ( args.argTypes != null ) SemErr("too many args in main");
					    if ( !typeLiteral.equals(Type.VOID) ) SemErr("main cannot return a value. type must be \"void\"");
					                                
					    modifier = Constants.ACC_STATIC | Constants.ACC_PUBLIC;
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
					if (cw.il.getLength()==0 || typeLiteral.equals(Type.VOID) )                            
					   cw.append( InstructionConstants.RETURN );
					 cw.methodGen.setMaxStack();
					classGen.addMethod(cw.methodGen.getMethod());
					} else SemErr("duplicate method "+member); 
				} else if (la.kind == 23) {
					FieldGen fieldGen = new FieldGen(
					   modifier,
					   typeLiteral,
					   member,
					   classGen.getConstantPool() ); 
					if ( Arrays.asList(classGen.getFields()).contains(fieldGen.getField()) )
					    SemErr("duplicate field "+member);
					else  
					    classGen.addField( fieldGen.getField() ); 
					Get();
				} else SynErr(46);
			} else SynErr(47);
		}
		Expect(5);
	}

	void interfaceBody(ClassGen classGen) {
		Expect(4);
		while (StartOf(1)) {
			int modifier = 0;
			if (la.kind == 15) {
				modifier = accessSpecifier();
				if (modifier == Constants.ACC_PRIVATE || modifier == Constants.ACC_PROTECTED)
				       SemErr("interface memebers ought to be public or default");
				     modifier = Constants.ACC_PUBLIC;
			}
			Type typeLiteral = null;
			typeLiteral = type();
			String methodName = identifier();
			Args args = new Args();
			Expect(2);
			if (StartOf(2)) {
				formalParameterList(args);
			}
			Expect(3);
			Expect(23);
			MethodGen methodGen = new MethodGen(
			   modifier | Constants.ACC_ABSTRACT,
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

	Type  basicType() {
		Type  typeLiteral;
		typeLiteral = null;
		switch (la.kind) {
		case 16: {
			Get();
			typeLiteral = Type.BOOLEAN;
			break;
		}
		case 17: {
			Get();
			typeLiteral = Type.INT;
			break;
		}
		case 18: {
			Get();
			typeLiteral = Type.FLOAT;
			break;
		}
		case 19: {
			Get();
			typeLiteral = Type.VOID;
			break;
		}
		case 20: {
			Get();
			typeLiteral = new ObjectType("java.lang.String");
			break;
		}
		case 21: {
			Get();
			typeLiteral = new ObjectType("java.util.Vector");
			break;
		}
		default: SynErr(48); break;
		}
		return typeLiteral;
	}

	Type  type() {
		Type  typeLiteral;
		typeLiteral=null;
		if (StartOf(3)) {
			typeLiteral = basicType();
		} else if (la.kind == 1) {
			String typeName = identifier();
			if(!isPresent(typeName)) typeLiteral = new ObjectType(typeName);
		} else SynErr(49);
		return typeLiteral;
	}

	void formalParameterList(Args args) {
		ArrayList<Type> types = new ArrayList<Type>();  
		ArrayList<String> names = new ArrayList<String>();
		Type typeLiteral=null;
		typeLiteral = type();
		types.add(typeLiteral);
		String param = identifier();
		names.add(param);
		while (la.kind == 22) {
			Get();
			typeLiteral = type();
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
		case 24: {
			Get();
			Expect(2);
			IfInstruction ifinstr = new IFEQ(null);
			if (la.kind == 25) {
				Get();
				ifinstr = new IFNE(null);
			}
			Type exprType = Expression(cw);
			Expect(3);
			cw.il.append(ifinstr);
			Statement(cw);
			GOTO g = new GOTO(null); cw.il.append(g);
			if (la.kind == 26) {
				Get();
				ifinstr.setTarget( cw.append(new NOP()) );
				Statement(cw);
			}
			InstructionHandle elseEndH = cw.append(new NOP()); 
			g.setTarget(elseEndH);
			if (ifinstr.getTarget()==null) ifinstr.setTarget(elseEndH); 
			break;
		}
		case 27: {
			Get();
			Expect(2);
			IfInstruction ifinstr = new IFEQ(null);
			if (la.kind == 25) {
				Get();
				ifinstr = new IFNE(null);
			}
			Type exprType = Expression(cw);
			Expect(3);
			InstructionHandle begin = cw.last; // ?????????????? ????????? todo ????????? ?? ???????? 
			cw.append(ifinstr); 
			Statement(cw);
			GOTO g = new GOTO(begin); cw.append(g);
			InstructionHandle end = cw.append(new NOP()); ifinstr.setTarget(end); 
			break;
		}
		case 28: {
			Get();
			if (StartOf(4)) {
				Type exprType = Expression(cw);
				cw.append( InstructionConstants.RETURN );
			}
			Expect(23);
			break;
		}
		case 23: {
			Get();
			break;
		}
		case 1: case 2: case 8: case 9: case 10: case 25: case 29: case 30: case 31: case 32: case 33: case 34: case 35: case 36: {
			Type exprType = Expression(cw);
			Expect(23);
			break;
		}
		default: SynErr(50); break;
		}
	}

	void Block(CodeWrapper cw) {
		Expect(4);
		while (StartOf(5)) {
			BlockStatement(cw);
		}
		Expect(5);
	}

	Type  Expression(CodeWrapper cw) {
		Type  exprType;
		exprType = null;
		switch (la.kind) {
		case 29: {
			Get();
			preparePrintStream(cw);
			Type[] argTypes = Arguments(cw);
			callPrintMethod(cw, "println", argTypes); 
			break;
		}
		case 30: {
			Get();
			preparePrintStream(cw);
			Type[] argTypes = Arguments(cw);
			callPrintMethod(cw, "print", argTypes); 
			break;
		}
		case 31: {
			Get();
			exprType = Creator(cw);
			break;
		}
		case 8: case 9: case 10: case 34: case 35: case 36: {
			exprType = Literal(cw);
			break;
		}
		case 25: {
			Get();
			exprType = Expression(cw);
			if (!exprType.equals(Type.BOOLEAN)) SemErr("cannot negate non-boolean expression");
			 IfInstruction ifinstr = new IFNE(null);
			     cw.il.append(ifinstr);
			     cw.il.append(new ICONST(1));
			     GOTO g = new GOTO(null);
			     cw.il.append(g);
			 ifinstr.setTarget(
			     cw.il.append( new ICONST(0) ) 
			     );
			 g.setTarget( cw.il.append(new NOP()) ); 
			break;
		}
		case 2: {
			Get();
			exprType = Expression(cw);
			ArithmeticInstruction instr = Infixop(exprType);
			Type exprTypeRight = Expression(cw);
			Expect(3);
			if (exprType == null || !exprType.equals(exprTypeRight))
			   SemErr("operand types mismatch "+exprType+" != "+exprTypeRight);
			else
			    cw.append(instr);
			break;
		}
		case 1: case 32: case 33: {
			String className= cw.classGen.getClassName();
			int objIndex = -1;
			if (next(_dot)) {
				if (la.kind == 32) {
					Get();
					objIndex = 0;
				} else if (la.kind == 33) {
					Get();
					objIndex = 0; className = cw.classGen.getSuperclassName();
				} else if (la.kind == 1) {
					String objVarName = identifier();
					LocalVariableGen lg = getVarGen(objVarName, cw.methodGen);
					if (lg==null)
					    SemErr("no such variable "+objVarName);
					else
					    className = lg.getType().toString();
					log("className="+className);
					
					objIndex = lg.getIndex();
				} else SynErr(51);
				Expect(6);
				exprType = Selector(cw, className, objIndex);
			} else {
				exprType = Selector(cw, className, 0);
			}
			break;
		}
		default: SynErr(52); break;
		}
		return exprType;
	}

	void BlockStatement(CodeWrapper cw) {
		if (next(_id)) {
			LocalVariableDeclaration(cw);
			Expect(23);
		} else if (StartOf(6)) {
			Statement(cw);
		} else SynErr(53);
	}

	void LocalVariableDeclaration(CodeWrapper cw) {
		InstructionFactory factory = new InstructionFactory( cw.classGen );
		Type typeLiteral = null; 
		typeLiteral = type();
		if (typeLiteral instanceof ObjectType){
		    cw.append( factory.createNew( typeLiteral.toString() ) );
		 cw.append( InstructionConstants.DUP );
		}
		String varName = identifier();
		if (getVarGen(varName, cw.methodGen)!=null)
		   SemErr("duplicate local variable "+varName);
		else if (typeLiteral!=null)
		{
		    LocalVariableGen lg = cw.methodGen.addLocalVariable(varName, typeLiteral, null, null); 
		if (la.kind == 7) {
			Get();
			Type exprType = Expression(cw);
			log("init");
			if ( !typeLiteral.equals(exprType) && !exprType.equals(Type.NULL) ) // todo ???????????
			    SemErr("incompatible types: expected "+typeLiteral+", got "+exprType);
			else {   
			Instruction store = getStoreInstruction(typeLiteral, lg.getIndex());
			lg.setStart( cw.append( store ) );
			}
			  }
		}
	}

	Type[]  Arguments(CodeWrapper cw) {
		Type[]  argTypes;
		ArrayList<Type> types = new ArrayList<Type>();
		Expect(2);
		if (StartOf(4)) {
			Type exprType = Expression(cw);
			types.add(exprType);
			while (la.kind == 22) {
				Get();
				exprType = Expression(cw);
				types.add(exprType);
			}
		}
		Expect(3);
		argTypes = types.toArray(new Type[types.size()]);
		return argTypes;
	}

	Type  Creator(CodeWrapper cw) {
		Type  createdType;
		String className=null;
		if (la.kind == 21) {
			Get();
			className="java.util.Vector";
		} else if (la.kind == 1) {
			className = identifier();
		} else SynErr(54);
		Type[] argTypes = Arguments(cw);
		if (isPresent(className))
		   createdType = null;
		else {
		 InstructionFactory factory = new InstructionFactory( cw.classGen );
		 cw.append( factory.createInvoke(
		     className,
		     "<init>",
		     Type.VOID,
		     argTypes, 
		     Constants.INVOKESPECIAL 
		 ) );
		 createdType = new ObjectType(className);
		} 
		return createdType;
	}

	Type  Literal(CodeWrapper cw) {
		Type  exprType;
		exprType = null;
		if (StartOf(7)) {
			Object value = null;
			InstructionFactory factory = new InstructionFactory( cw.classGen );
			log("lit="+la.val); 
			if (la.kind == 8) {
				Get();
				exprType = Type.INT; value = new Integer(t.val);
			} else if (la.kind == 9) {
				Get();
				exprType = Type.FLOAT; value = new Float(t.val);
			} else if (la.kind == 34) {
				Get();
				exprType = Type.BOOLEAN; value = new Boolean(t.val);
			} else if (la.kind == 35) {
				Get();
				exprType = Type.BOOLEAN; value = new Boolean(t.val);
			} else {
				Get();
				exprType = new ObjectType("java.lang.String"); value = t.val.substring(1, t.val.length()-1);
			}
			cw.append( factory.createConstant( value )); 
		} else if (la.kind == 36) {
			Get();
			exprType = Type.NULL; cw.append( new ACONST_NULL());
		} else SynErr(55);
		return exprType;
	}

	ArithmeticInstruction  Infixop(Type exprType) {
		ArithmeticInstruction  instr;
		instr = null;
		switch (la.kind) {
		case 37: {
			Get();
			if (exprType.equals(Type.BOOLEAN)) instr = new IOR();
			else SemErr("cannot apply || to "+exprType); 
			break;
		}
		case 38: {
			Get();
			if (exprType.equals(Type.BOOLEAN)) instr = new IAND();
			else SemErr("cannot apply && to "+exprType); 
			break;
		}
		case 39: {
			Get();
			if (exprType.equals(Type.INT)) instr = new IADD();
			else if (exprType.equals(Type.FLOAT) ) instr = new FADD();
			else SemErr("cannot apply + to "+exprType); 
			break;
		}
		case 40: {
			Get();
			if (exprType.equals(Type.INT)) instr = new ISUB();
			else if (exprType.equals(Type.FLOAT) ) instr = new FSUB();
			else SemErr("cannot apply - to "+exprType); 
			break;
		}
		case 41: {
			Get();
			if (exprType.equals(Type.INT)) instr = new IMUL();
			else if (exprType.equals(Type.FLOAT) ) instr = new FMUL();
			else SemErr("cannot apply * to "+exprType); 
			break;
		}
		case 42: {
			Get();
			if (exprType.equals(Type.INT)) instr = new IDIV();
			else if (exprType.equals(Type.FLOAT) ) instr = new FDIV();
			else SemErr("cannot apply / to "+exprType); 
			break;
		}
		case 43: {
			Get();
			if (exprType.equals(Type.INT)) instr = new IREM();
			else if (exprType.equals(Type.FLOAT) ) instr = new FREM();
			else SemErr("cannot apply % to "+exprType); 
			break;
		}
		default: SynErr(56); break;
		}
		return instr;
	}

	Type  Selector(CodeWrapper cw, String className, int objIndex) {
		Type  selType;
		selType = null;
		if (next(_openRoundBracket)) {
			cw.append(new ALOAD(objIndex));
			selType = call(cw, className);
		} else if (la.kind == 1) {
			selType = var(cw, className, objIndex);
		} else SynErr(57);
		return selType;
	}

	Type  call(CodeWrapper cw, String className) {
		Type  selType;
		String method = identifier();
		selType = null;
		Type[] argTypes = Arguments(cw);
		InstructionFactory factory = new InstructionFactory( cw.classGen );
		
		MethodParams mp = getMethod(method, argTypes, getType(className) );
		if (mp==null) SemErr("no such method "+method);
		else {
		    selType = mp.method.getReturnType();
		 cw.append( factory.createInvoke(
		     mp.className,
		     mp.method.getName(),
		     selType,
		     argTypes, 
		     (cw.classGen.getClassName().equals(className))?
		                   Constants.INVOKEVIRTUAL:
		                   Constants.INVOKESPECIAL 
		 ) );
		} 
		return selType;
	}

	Type  var(CodeWrapper cw, String className, int objIndex) {
		Type  selType;
		selType = null;
		if (next(_isequal)) {
			String var = identifier();
			Field fg = null;
			  LocalVariableGen lg = getVarGen(var, cw.methodGen);
			if (lg==null) {
			    fg = getFieldGen(var, getType(className));
			    if( fg == null ) SemErr("no such variable or field "+var);
			    else  { // ??? ????
			        selType = fg.getType();
			        cw.append(new ALOAD(objIndex));
			    }
			}
			else selType = lg.getType();  // ??? ??????????
			
			AssignmentOperator(cw);
			Type exprType = Expression(cw);
			InstructionFactory factory = new InstructionFactory( cw.classGen );
			  if (!selType.equals(exprType) && !exprType.equals(Type.NULL))
			     SemErr("incompatible types: expected "+selType+", got "+exprType);
			  else {
			      if (lg!=null) {
			          // todo hashMap
			       Instruction store = getStoreInstruction(selType, lg.getIndex());
			       cw.append( store );
			       if (lg.getStart()==null) // ?????????? ??? ?? ????????????????
			                    lg.setStart( cw.last );
			      } else if (fg!=null)
			                        cw.append( factory.createFieldAccess( 
			                                                className, 
			                                                fg.getName(), 
			                                                selType, 
			                                                Constants.PUTFIELD ) );
			   }
		} else if (la.kind == 1) {
			selType = access(cw, className);
		} else SynErr(58);
		return selType;
	}

	void AssignmentOperator(CodeWrapper cw) {
		log("assgmnt");
		Expect(7);
	}

	Type  access(CodeWrapper cw, String className) {
		Type  selType;
		String var = identifier();
		selType = null;
		LocalVariableGen lg = getVarGen(var, cw.methodGen);
		if (lg==null) {
		    Field fg = getFieldGen(var, cw.classGen);
		    if( fg == null ) SemErr("no such variable or field "+var);
		    else { // ??? ????
		        selType = fg.getType();
		        InstructionFactory factory = new InstructionFactory( cw.classGen );
		        cw.append( factory.createFieldAccess( 
		                     className, 
		                     fg.getName(), 
		                     selType, 
		                     Constants.GETFIELD ) );    
		    }
		} else { // ??? ??????????
		    selType = lg.getType();
		    Instruction instr = getLoadInstruction(selType, lg.getIndex());
		    cw.append(instr);
		} 
		return selType;
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		CompilationUnit();

		Expect(0);
	}

	private boolean[][] set = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x},
		{x,T,T,x, x,x,x,x, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,x,x, x,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x},
		{x,T,T,x, T,x,x,x, T,T,T,x, x,x,x,x, T,T,T,T, T,T,x,T, T,T,x,T, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x},
		{x,T,T,x, T,x,x,x, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,x,T, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x},
		{x,x,x,x, x,x,x,x, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, x,x,x,x, x,x,x,x, x,x}

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
			case 10: s = "stringLit expected"; break;
			case 11: s = "\"class\" expected"; break;
			case 12: s = "\"extends\" expected"; break;
			case 13: s = "\"implements\" expected"; break;
			case 14: s = "\"interface\" expected"; break;
			case 15: s = "\"public\" expected"; break;
			case 16: s = "\"boolean\" expected"; break;
			case 17: s = "\"int\" expected"; break;
			case 18: s = "\"float\" expected"; break;
			case 19: s = "\"void\" expected"; break;
			case 20: s = "\"String\" expected"; break;
			case 21: s = "\"Vector\" expected"; break;
			case 22: s = "\",\" expected"; break;
			case 23: s = "\";\" expected"; break;
			case 24: s = "\"if\" expected"; break;
			case 25: s = "\"!\" expected"; break;
			case 26: s = "\"else\" expected"; break;
			case 27: s = "\"while\" expected"; break;
			case 28: s = "\"return\" expected"; break;
			case 29: s = "\"System.out.println\" expected"; break;
			case 30: s = "\"System.out.print\" expected"; break;
			case 31: s = "\"new\" expected"; break;
			case 32: s = "\"this\" expected"; break;
			case 33: s = "\"super\" expected"; break;
			case 34: s = "\"true\" expected"; break;
			case 35: s = "\"false\" expected"; break;
			case 36: s = "\"null\" expected"; break;
			case 37: s = "\"||\" expected"; break;
			case 38: s = "\"&&\" expected"; break;
			case 39: s = "\"+\" expected"; break;
			case 40: s = "\"-\" expected"; break;
			case 41: s = "\"*\" expected"; break;
			case 42: s = "\"/\" expected"; break;
			case 43: s = "\"%\" expected"; break;
			case 44: s = "??? expected"; break;
			case 45: s = "invalid typeDeclaration"; break;
			case 46: s = "invalid classBody"; break;
			case 47: s = "invalid classBody"; break;
			case 48: s = "invalid basicType"; break;
			case 49: s = "invalid type"; break;
			case 50: s = "invalid Statement"; break;
			case 51: s = "invalid Expression"; break;
			case 52: s = "invalid Expression"; break;
			case 53: s = "invalid BlockStatement"; break;
			case 54: s = "invalid Creator"; break;
			case 55: s = "invalid Literal"; break;
			case 56: s = "invalid Infixop"; break;
			case 57: s = "invalid Selector"; break;
			case 58: s = "invalid var"; break;
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

