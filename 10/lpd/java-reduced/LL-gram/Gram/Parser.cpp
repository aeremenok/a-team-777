
#include "stdafx.h"
#include <wchar.h>
#include "Parser.h"
#include "Scanner.h"




void Parser::SynErr(int n) {
	if (errDist >= minErrDist) errors->SynErr(la->line, la->col, n);
	errDist = 0;
}

void Parser::SemErr(const wchar_t* msg) {
	if (errDist >= minErrDist) errors->Error(t->line, t->col, msg);
	errDist = 0;
}

void Parser::Get() {
	for (;;) {
		t = la;
		la = scanner->Scan();
		if (la->kind <= maxT) { ++errDist; break; }

		if (dummyToken != t) {
			dummyToken->kind = t->kind;
			dummyToken->pos = t->pos;
			dummyToken->col = t->col;
			dummyToken->line = t->line;
			dummyToken->next = NULL;
			coco_string_delete(dummyToken->val);
			dummyToken->val = coco_string_create(t->val);
			t = dummyToken;
		}
		la = t;
	}
}

void Parser::Expect(int n) {
	if (la->kind==n) Get(); else { SynErr(n); }
}

void Parser::ExpectWeak(int n, int follow) {
	if (la->kind == n) Get();
	else {
		SynErr(n);
		while (!StartOf(follow)) Get();
	}
}

bool Parser::WeakSeparator(int n, int syFol, int repFol) {
	if (la->kind == n) {Get(); return true;}
	else if (StartOf(repFol)) {return false;}
	else {
		SynErr(n);
		while (!(StartOf(syFol) || StartOf(repFol) || StartOf(0))) {
			Get();
		}
		return StartOf(syFol);
	}
}

void Parser::CompilationUnit() {
		typeDeclaration();
		while (StartOf(1)) {
			typeDeclaration();
		}
}

void Parser::typeDeclaration() {
		if (la->kind == 13 || la->kind == 14 || la->kind == 15) {
			accessSpecifier();
		}
		if (la->kind == 4) {
			interfaceDeclaration();
		} else if (la->kind == 5 || la->kind == 6 || la->kind == 11) {
			classDeclaration();
		} else SynErr(66);
}

void Parser::accessSpecifier() {
		if (la->kind == 13) {
			Get();
		} else if (la->kind == 14) {
			Get();
		} else if (la->kind == 15) {
			Get();
		} else SynErr(67);
}

void Parser::interfaceDeclaration() {
		Expect(4);
		Expect(1);
}

void Parser::classDeclaration() {
		if (la->kind == 5) {
			Get();
		}
		if (la->kind == 6) {
			Get();
		}
		Expect(11);
		Expect(1);
		Expect(12);
		Expect(1);
		classBody();
}

void Parser::classBody() {
		Expect(9);
		while (StartOf(2)) {
			if (la->kind == 13 || la->kind == 14 || la->kind == 15) {
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
				if (la->kind == 5) {
					Get();
				}
				if (la->kind == 6) {
					Get();
				}
				if (StartOf(6)) {
					type();
				} else if (la->kind == 1) {
					Get();
				} else SynErr(68);
				Expect(1);
				if (la->kind == 7) {
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
				} else if (la->kind == 26 || la->kind == 27) {
					if (la->kind == 26) {
						Get();
						expression();
					}
					Expect(27);
				} else SynErr(69);
			} else SynErr(70);
		}
		Expect(10);
}

void Parser::type() {
		switch (la->kind) {
		case 16: {
			Get();
			break;
		}
		case 17: {
			Get();
			break;
		}
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
		default: SynErr(71); break;
		}
}

void Parser::formalParameterList() {
		if (StartOf(6)) {
			type();
		} else if (la->kind == 1) {
			Get();
		} else SynErr(72);
		Expect(1);
		while (la->kind == 25) {
			Get();
			if (StartOf(6)) {
				type();
			} else if (la->kind == 1) {
				Get();
			} else SynErr(73);
			Expect(1);
		}
}

void Parser::statement() {
		switch (la->kind) {
		case 28: {
			Get();
			Expect(7);
			expression();
			Expect(8);
			if (la->kind == 9) {
				Get();
				while (StartOf(4)) {
					statement();
				}
				Expect(10);
			} else if (StartOf(4)) {
				statement();
			} else SynErr(74);
			if (la->kind == 29) {
				Get();
				if (la->kind == 9) {
					Get();
					while (StartOf(4)) {
						statement();
					}
					Expect(10);
				} else if (StartOf(4)) {
					statement();
				} else SynErr(75);
			}
			break;
		}
		case 30: {
			Get();
			Expect(7);
			expression();
			Expect(8);
			if (la->kind == 9) {
				Get();
				while (StartOf(4)) {
					statement();
				}
				Expect(10);
			} else if (StartOf(4)) {
				statement();
			} else SynErr(76);
			break;
		}
		case 31: {
			Get();
			if (la->kind == 1 || la->kind == 2 || la->kind == 3) {
				expression();
			}
			Expect(27);
			break;
		}
		case 32: {
			Get();
			expressionName();
			Expect(7);
			if (la->kind == 1 || la->kind == 2 || la->kind == 3) {
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
		case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23: case 24: {
			type();
			Expect(1);
			if (la->kind == 26) {
				Get();
				expression();
			}
			while (la->kind == 25) {
				Get();
				Expect(1);
				if (la->kind == 26) {
					Get();
					expression();
				}
			}
			Expect(27);
			break;
		}
		case 1: {
			Get();
			if (la->kind == 1) {
				Get();
				if (la->kind == 26) {
					Get();
					expression();
				}
				while (la->kind == 25) {
					Get();
					Expect(1);
					if (la->kind == 26) {
						Get();
						expression();
					}
				}
				Expect(27);
			} else if (la->kind == 7) {
				Get();
				if (la->kind == 1 || la->kind == 2 || la->kind == 3) {
					expression();
					while (la->kind == 25) {
						Get();
						expression();
					}
				}
				Expect(8);
				Expect(27);
			} else if (StartOf(7)) {
				assignmentOperator();
				expression();
				Expect(27);
			} else SynErr(77);
			break;
		}
		case 33: {
			Get();
			Expect(34);
			Expect(1);
			Expect(7);
			if (la->kind == 1 || la->kind == 2 || la->kind == 3) {
				expression();
				while (la->kind == 25) {
					Get();
					expression();
				}
			}
			Expect(8);
			Expect(27);
			break;
		}
		default: SynErr(78); break;
		}
}

void Parser::expression() {
		conditionalOrExpression();
}

void Parser::expressionName() {
		if (la->kind == 1) {
			Get();
			while (la->kind == 34) {
				Get();
				Expect(1);
			}
			if (la->kind == 7) {
				Get();
				if (la->kind == 1 || la->kind == 2 || la->kind == 3) {
					expression();
					while (la->kind == 25) {
						Get();
						expression();
					}
				}
				Expect(8);
			}
		} else if (la->kind == 2) {
			Get();
		} else if (la->kind == 3) {
			Get();
		} else SynErr(79);
}

void Parser::assignmentOperator() {
		switch (la->kind) {
		case 26: {
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
		default: SynErr(80); break;
		}
}

void Parser::conditionalOrExpression() {
		conditionalAndExpression();
		while (la->kind == 35) {
			Get();
			conditionalOrExpression();
		}
}

void Parser::conditionalAndExpression() {
		inclusiveOrExpression();
		while (la->kind == 36) {
			Get();
			conditionalAndExpression();
		}
}

void Parser::inclusiveOrExpression() {
		exclusiveOrExpression();
		while (la->kind == 37) {
			Get();
			inclusiveOrExpression();
		}
}

void Parser::exclusiveOrExpression() {
		andExpression();
		while (la->kind == 38) {
			Get();
			exclusiveOrExpression();
		}
}

void Parser::andExpression() {
		equalityExpression();
		while (la->kind == 39) {
			Get();
			andExpression();
		}
}

void Parser::equalityExpression() {
		relationalExpression();
		while (la->kind == 40 || la->kind == 41) {
			if (la->kind == 40) {
				Get();
			} else {
				Get();
			}
			equalityExpression();
		}
}

void Parser::relationalExpression() {
		shiftExpression();
		while (StartOf(8)) {
			if (la->kind == 42) {
				Get();
			} else if (la->kind == 43) {
				Get();
			} else if (la->kind == 44) {
				Get();
			} else {
				Get();
			}
			relationalExpression();
		}
}

void Parser::shiftExpression() {
		additiveExpression();
		while (la->kind == 46 || la->kind == 47 || la->kind == 48) {
			if (la->kind == 46) {
				Get();
			} else if (la->kind == 47) {
				Get();
			} else {
				Get();
			}
			shiftExpression();
		}
}

void Parser::additiveExpression() {
		multiplicativeExpression();
		while (la->kind == 49 || la->kind == 50) {
			if (la->kind == 49) {
				Get();
			} else {
				Get();
			}
			additiveExpression();
		}
}

void Parser::multiplicativeExpression() {
		unaryExpression();
		while (la->kind == 51 || la->kind == 52 || la->kind == 53) {
			if (la->kind == 51) {
				Get();
			} else if (la->kind == 52) {
				Get();
			} else {
				Get();
			}
			multiplicativeExpression();
		}
}

void Parser::unaryExpression() {
		expressionName();
		while (la->kind == 51 || la->kind == 52 || la->kind == 53) {
			if (la->kind == 51) {
				Get();
			} else if (la->kind == 52) {
				Get();
			} else {
				Get();
			}
			unaryExpression();
		}
}



void Parser::Parse() {
	t = NULL;
	la = dummyToken = new Token();
	la->val = coco_string_create(L"Dummy Token");
	Get();
	CompilationUnit();

	Expect(0);
}

Parser::Parser(Scanner *scanner) {
	maxT = 65;

	dummyToken = NULL;
	t = la = NULL;
	minErrDist = 2;
	errDist = minErrDist;
	this->scanner = scanner;
	errors = new Errors();
}

bool Parser::StartOf(int s) {
	const bool T = true;
	const bool x = false;

	static bool set[9][67] = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, T,T,T,x, x,x,x,T, x,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,T,T,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,x,x,x, x,T,x,x, x,x,x,x, T,T,T,T, T,T,T,T, T,x,x,x, T,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,T,x,x, x,T,T,x, x,x,x,x, x,x,x,x, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,T,T, T,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,T, T,x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x}
	};



	return set[s][la->kind];
}

Parser::~Parser() {
	delete errors;
	delete dummyToken;
}

Errors::Errors() {
	count = 0;
}

void Errors::SynErr(int line, int col, int n) {
	wchar_t* s;
	switch (n) {
			case 0: s = coco_string_create(L"EOF expected"); break;
			case 1: s = coco_string_create(L"identifier expected"); break;
			case 2: s = coco_string_create(L"realNumber expected"); break;
			case 3: s = coco_string_create(L"string expected"); break;
			case 4: s = coco_string_create(L"interface expected"); break;
			case 5: s = coco_string_create(L"final expected"); break;
			case 6: s = coco_string_create(L"static expected"); break;
			case 7: s = coco_string_create(L"openRoundBracket expected"); break;
			case 8: s = coco_string_create(L"closeRoundBracket expected"); break;
			case 9: s = coco_string_create(L"openCurlyBracket expected"); break;
			case 10: s = coco_string_create(L"closeCurlyBracket expected"); break;
			case 11: s = coco_string_create(L"\"class\" expected"); break;
			case 12: s = coco_string_create(L"\"extends\" expected"); break;
			case 13: s = coco_string_create(L"\"public\" expected"); break;
			case 14: s = coco_string_create(L"\"protected\" expected"); break;
			case 15: s = coco_string_create(L"\"private\" expected"); break;
			case 16: s = coco_string_create(L"\"byte\" expected"); break;
			case 17: s = coco_string_create(L"\"short\" expected"); break;
			case 18: s = coco_string_create(L"\"int\" expected"); break;
			case 19: s = coco_string_create(L"\"long\" expected"); break;
			case 20: s = coco_string_create(L"\"char\" expected"); break;
			case 21: s = coco_string_create(L"\"float\" expected"); break;
			case 22: s = coco_string_create(L"\"double\" expected"); break;
			case 23: s = coco_string_create(L"\"bool\" expected"); break;
			case 24: s = coco_string_create(L"\"void\" expected"); break;
			case 25: s = coco_string_create(L"\",\" expected"); break;
			case 26: s = coco_string_create(L"\"=\" expected"); break;
			case 27: s = coco_string_create(L"\";\" expected"); break;
			case 28: s = coco_string_create(L"\"if\" expected"); break;
			case 29: s = coco_string_create(L"\"else\" expected"); break;
			case 30: s = coco_string_create(L"\"while\" expected"); break;
			case 31: s = coco_string_create(L"\"return\" expected"); break;
			case 32: s = coco_string_create(L"\"new\" expected"); break;
			case 33: s = coco_string_create(L"\"super\" expected"); break;
			case 34: s = coco_string_create(L"\".\" expected"); break;
			case 35: s = coco_string_create(L"\"||\" expected"); break;
			case 36: s = coco_string_create(L"\"&&\" expected"); break;
			case 37: s = coco_string_create(L"\"|\" expected"); break;
			case 38: s = coco_string_create(L"\"^\" expected"); break;
			case 39: s = coco_string_create(L"\"&\" expected"); break;
			case 40: s = coco_string_create(L"\"==\" expected"); break;
			case 41: s = coco_string_create(L"\"!=\" expected"); break;
			case 42: s = coco_string_create(L"\"<\" expected"); break;
			case 43: s = coco_string_create(L"\">\" expected"); break;
			case 44: s = coco_string_create(L"\"<=\" expected"); break;
			case 45: s = coco_string_create(L"\">=\" expected"); break;
			case 46: s = coco_string_create(L"\"<<\" expected"); break;
			case 47: s = coco_string_create(L"\">>\" expected"); break;
			case 48: s = coco_string_create(L"\"<<<\" expected"); break;
			case 49: s = coco_string_create(L"\"+\" expected"); break;
			case 50: s = coco_string_create(L"\"-\" expected"); break;
			case 51: s = coco_string_create(L"\"*\" expected"); break;
			case 52: s = coco_string_create(L"\"/\" expected"); break;
			case 53: s = coco_string_create(L"\"%\" expected"); break;
			case 54: s = coco_string_create(L"\"*=\" expected"); break;
			case 55: s = coco_string_create(L"\"/=\" expected"); break;
			case 56: s = coco_string_create(L"\"%=\" expected"); break;
			case 57: s = coco_string_create(L"\"+=\" expected"); break;
			case 58: s = coco_string_create(L"\"-=\" expected"); break;
			case 59: s = coco_string_create(L"\"<<=\" expected"); break;
			case 60: s = coco_string_create(L"\">>=\" expected"); break;
			case 61: s = coco_string_create(L"\">>>=\" expected"); break;
			case 62: s = coco_string_create(L"\"&=\" expected"); break;
			case 63: s = coco_string_create(L"\"^=\" expected"); break;
			case 64: s = coco_string_create(L"\"|=\" expected"); break;
			case 65: s = coco_string_create(L"??? expected"); break;
			case 66: s = coco_string_create(L"invalid typeDeclaration"); break;
			case 67: s = coco_string_create(L"invalid accessSpecifier"); break;
			case 68: s = coco_string_create(L"invalid classBody"); break;
			case 69: s = coco_string_create(L"invalid classBody"); break;
			case 70: s = coco_string_create(L"invalid classBody"); break;
			case 71: s = coco_string_create(L"invalid type"); break;
			case 72: s = coco_string_create(L"invalid formalParameterList"); break;
			case 73: s = coco_string_create(L"invalid formalParameterList"); break;
			case 74: s = coco_string_create(L"invalid statement"); break;
			case 75: s = coco_string_create(L"invalid statement"); break;
			case 76: s = coco_string_create(L"invalid statement"); break;
			case 77: s = coco_string_create(L"invalid statement"); break;
			case 78: s = coco_string_create(L"invalid statement"); break;
			case 79: s = coco_string_create(L"invalid expressionName"); break;
			case 80: s = coco_string_create(L"invalid assignmentOperator"); break;

		default:
		{
			wchar_t format[20];
			coco_swprintf(format, 20, L"error %d", n);
			s = coco_string_create(format);
		}
		break;
	}
	wprintf(L"-- line %d col %d: %ls\n", line, col, s);
	coco_string_delete(s);
	count++;
}

void Errors::Error(int line, int col, const wchar_t *s) {
	wprintf(L"-- line %d col %d: %ls\n", line, col, s);
	count++;
}

void Errors::Warning(int line, int col, const wchar_t *s) {
	wprintf(L"-- line %d col %d: %ls\n", line, col, s);
}

void Errors::Warning(const wchar_t *s) {
	wprintf(L"%ls\n", s);
}

void Errors::Exception(const wchar_t* s) {
	wprintf(L"%ls", s); 
	exit(1);
}



