package ru.spb.etu;


public class Parser {
	static final int _EOF = 0;
	static final int _identifier = 1;
	static final int _realNumber = 2;
	static final int _string = 3;
	static final int _interface = 4;
	static final int _final = 5;
	static final int _static = 6;
	static final int _openRoundBracket = 7;
	static final int _closeRoundBracket = 8;
	static final int _openCurlyBracket = 9;
	static final int _closeCurlyBracket = 10;
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
		Token x = scanner.Peek();
		return (x.kind == i);
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
	
	void CompilationUnit() {
		typeDeclaration();
		while (StartOf(1)) {
			typeDeclaration();
		}
	}

	void typeDeclaration() {
		if (la.kind == 15 || la.kind == 16 || la.kind == 17) {
			accessSpecifier();
		}
		if (la.kind == 4) {
			interfaceDeclaration();
		} else if (la.kind == 5 || la.kind == 6 || la.kind == 11) {
			classDeclaration();
		} else SynErr(67);
	}

	void accessSpecifier() {
		if (la.kind == 15) {
			Get();
		} else if (la.kind == 16) {
			Get();
		} else if (la.kind == 17) {
			Get();
		} else SynErr(68);
	}

	void interfaceDeclaration() {
		Expect(4);
		Expect(1);
		if (la.kind == 12) {
			Get();
			Expect(1);
		}
		interfaceBody();
	}

	void classDeclaration() {
		if (la.kind == 5) {
			Get();
		}
		if (la.kind == 6) {
			Get();
		}
		Expect(11);
		Expect(1);
		if (la.kind == 12) {
			Get();
			Expect(1);
		}
		if (la.kind == 13) {
			Get();
			Expect(1);
			while (la.kind == 14) {
				Get();
				Expect(1);
			}
		}
		classBody();
	}

	void classBody() {
		Expect(9);
		while (StartOf(2)) {
			if (la.kind == 15 || la.kind == 16 || la.kind == 17) {
				accessSpecifier();
			}
			if (next(_openRoundBracket)) {
				Expect(1);
				Expect(7);
				if (StartOf(3)) {
					formalParameterList();
				}
				Expect(8);
				Expect(9);
				while (StartOf(4)) {
					statement();
				}
				Expect(10);
			} else if (StartOf(5)) {
				if (la.kind == 5) {
					Get();
				}
				if (la.kind == 6) {
					Get();
				}
				if (StartOf(6)) {
					type();
				} else if (la.kind == 1) {
					Get();
				} else SynErr(69);
				Expect(1);
				if (la.kind == 7) {
					Get();
					if (StartOf(3)) {
						formalParameterList();
					}
					Expect(8);
					Expect(9);
					while (StartOf(4)) {
						statement();
					}
					Expect(10);
				} else if (la.kind == 27 || la.kind == 28) {
					if (la.kind == 27) {
						Get();
						expression();
					}
					Expect(28);
				} else SynErr(70);
			} else SynErr(71);
		}
		Expect(10);
	}

	void interfaceBody() {
		Expect(9);
		while (StartOf(2)) {
			if (la.kind == 15 || la.kind == 16 || la.kind == 17) {
				accessSpecifier();
			}
			if (la.kind == 5) {
				Get();
			}
			if (la.kind == 6) {
				Get();
			}
			if (StartOf(6)) {
				type();
			} else if (la.kind == 1) {
				Get();
			} else SynErr(72);
			Expect(1);
			Expect(7);
			if (StartOf(3)) {
				formalParameterList();
			}
			Expect(8);
			Expect(28);
		}
		Expect(10);
	}

	void type() {
		switch (la.kind) {
		case 18: {
			Get();
			break;
		}
		case 19: {
			Get();
			break;
		}
		case 20: {
			Get();
			break;
		}
		case 21: {
			Get();
			break;
		}
		case 22: {
			Get();
			break;
		}
		case 23: {
			Get();
			break;
		}
		case 24: {
			Get();
			break;
		}
		case 25: {
			Get();
			break;
		}
		case 26: {
			Get();
			break;
		}
		default: SynErr(73); break;
		}
	}

	void formalParameterList() {
		if (StartOf(6)) {
			type();
		} else if (la.kind == 1) {
			Get();
		} else SynErr(74);
		Expect(1);
		while (la.kind == 14) {
			Get();
			if (StartOf(6)) {
				type();
			} else if (la.kind == 1) {
				Get();
			} else SynErr(75);
			Expect(1);
		}
	}

	void statement() {
		switch (la.kind) {
		case 29: {
			Get();
			Expect(7);
			expression();
			Expect(8);
			if (la.kind == 9) {
				Get();
				while (StartOf(4)) {
					statement();
				}
				Expect(10);
			} else if (StartOf(4)) {
				statement();
			} else SynErr(76);
			if (la.kind == 30) {
				Get();
				if (la.kind == 9) {
					Get();
					while (StartOf(4)) {
						statement();
					}
					Expect(10);
				} else if (StartOf(4)) {
					statement();
				} else SynErr(77);
			}
			break;
		}
		case 31: {
			Get();
			Expect(7);
			expression();
			Expect(8);
			if (la.kind == 9) {
				Get();
				while (StartOf(4)) {
					statement();
				}
				Expect(10);
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
			Expect(7);
			if (StartOf(7)) {
				expression();
			}
			Expect(8);
			break;
		}
		case 9: {
			Get();
			while (StartOf(4)) {
				statement();
			}
			Expect(10);
			break;
		}
		case 18: case 19: case 20: case 21: case 22: case 23: case 24: case 25: case 26: {
			type();
			Expect(1);
			if (la.kind == 27) {
				Get();
				expression();
			}
			while (la.kind == 14) {
				Get();
				Expect(1);
				if (la.kind == 27) {
					Get();
					expression();
				}
			}
			Expect(28);
			break;
		}
		case 1: {
			Get();
			if (la.kind == 7) {
				Get();
				if (StartOf(7)) {
					expression();
					while (la.kind == 14) {
						Get();
						expression();
					}
				}
				Expect(8);
			}
			while (la.kind == 34) {
				Get();
				Expect(1);
				if (la.kind == 7) {
					Get();
					if (StartOf(7)) {
						expression();
						while (la.kind == 14) {
							Get();
							expression();
						}
					}
					Expect(8);
				}
			}
			if (la.kind == 1) {
				Get();
				if (la.kind == 27) {
					Get();
					expression();
				}
				while (la.kind == 14) {
					Get();
					Expect(1);
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
				Expect(1);
			}
			Expect(7);
			if (StartOf(7)) {
				expression();
				while (la.kind == 14) {
					Get();
					expression();
				}
			}
			Expect(8);
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
		if (la.kind == 1 || la.kind == 35) {
			if (la.kind == 1) {
				Get();
			} else {
				Get();
			}
			if (la.kind == 7) {
				Get();
				if (StartOf(7)) {
					expression();
					while (la.kind == 14) {
						Get();
						expression();
					}
				}
				Expect(8);
			}
			while (la.kind == 34) {
				Get();
				Expect(1);
				if (la.kind == 7) {
					Get();
					if (StartOf(7)) {
						expression();
						while (la.kind == 14) {
							Get();
							expression();
						}
					}
					Expect(8);
				}
			}
		} else if (la.kind == 2) {
			Get();
		} else if (la.kind == 3) {
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
		{x,x,x,x, T,T,T,x, x,x,x,T, x,x,x,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,x,x, x,T,T,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,x,x, x,x,x,x, x,T,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,x, x,T,x,T, T,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,T,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
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
			case 1: s = "identifier expected"; break;
			case 2: s = "realNumber expected"; break;
			case 3: s = "string expected"; break;
			case 4: s = "interface expected"; break;
			case 5: s = "final expected"; break;
			case 6: s = "static expected"; break;
			case 7: s = "openRoundBracket expected"; break;
			case 8: s = "closeRoundBracket expected"; break;
			case 9: s = "openCurlyBracket expected"; break;
			case 10: s = "closeCurlyBracket expected"; break;
			case 11: s = "\"class\" expected"; break;
			case 12: s = "\"extends\" expected"; break;
			case 13: s = "\"implements\" expected"; break;
			case 14: s = "\",\" expected"; break;
			case 15: s = "\"public\" expected"; break;
			case 16: s = "\"protected\" expected"; break;
			case 17: s = "\"private\" expected"; break;
			case 18: s = "\"byte\" expected"; break;
			case 19: s = "\"short\" expected"; break;
			case 20: s = "\"int\" expected"; break;
			case 21: s = "\"long\" expected"; break;
			case 22: s = "\"char\" expected"; break;
			case 23: s = "\"float\" expected"; break;
			case 24: s = "\"double\" expected"; break;
			case 25: s = "\"bool\" expected"; break;
			case 26: s = "\"void\" expected"; break;
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

