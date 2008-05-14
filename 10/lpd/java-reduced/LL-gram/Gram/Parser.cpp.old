
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
		if (la->kind == 11 || la->kind == 12 || la->kind == 13) {
			accessSpecifier();
		}
		if (la->kind == 4) {
			interfaceDeclaration();
		} else if (la->kind == 5 || la->kind == 6 || la->kind == 9) {
			classDeclaration();
		} else SynErr(48);
}

void Parser::accessSpecifier() {
		if (la->kind == 11) {
			Get();
		} else if (la->kind == 12) {
			Get();
		} else if (la->kind == 13) {
			Get();
		} else SynErr(49);
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
		Expect(9);
		Expect(1);
		Expect(10);
		Expect(1);
		classBody();
}

void Parser::classBody() {
		Expect(24);
		while (StartOf(2)) {
			if (la->kind == 11 || la->kind == 12 || la->kind == 13) {
				accessSpecifier();
			}
			if (next(_openRoundBracket)) {
				Expect(1);
				Expect(7);
				if (StartOf(3)) {
					formalParameterList();
				}
				Expect(8);
			} else if (StartOf(4)) {
				if (la->kind == 5) {
					Get();
				}
				if (la->kind == 6) {
					Get();
				}
				if (StartOf(5)) {
					type();
				} else if (la->kind == 1) {
					Get();
				} else SynErr(50);
				Expect(1);
				if (la->kind == 7) {
					Get();
					if (StartOf(3)) {
						formalParameterList();
					}
					Expect(8);
				} else if (la->kind == 23 || la->kind == 25) {
					if (la->kind == 23) {
						Get();
						expression();
					}
					Expect(25);
				} else SynErr(51);
			} else SynErr(52);
		}
		Expect(26);
}

void Parser::type() {
		switch (la->kind) {
		case 14: {
			Get();
			break;
		}
		case 15: {
			Get();
			break;
		}
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
		default: SynErr(53); break;
		}
}

void Parser::formalParameterList() {
		if (StartOf(5)) {
			type();
		} else if (la->kind == 1) {
			Get();
		} else SynErr(54);
		Expect(1);
		if (la->kind == 23) {
			Get();
			expression();
		}
}

void Parser::expression() {
		conditionalExpression();
}

void Parser::conditionalExpression() {
		conditionalOrExpression();
}

void Parser::conditionalOrExpression() {
		conditionalAndExpression();
		while (la->kind == 27) {
			Get();
			conditionalOrExpression();
		}
}

void Parser::conditionalAndExpression() {
		inclusiveOrExpression();
		while (la->kind == 28) {
			Get();
			conditionalAndExpression();
		}
}

void Parser::inclusiveOrExpression() {
		exclusiveOrExpression();
		while (la->kind == 29) {
			Get();
			inclusiveOrExpression();
		}
}

void Parser::exclusiveOrExpression() {
		andExpression();
		while (la->kind == 30) {
			Get();
			exclusiveOrExpression();
		}
}

void Parser::andExpression() {
		equalityExpression();
		while (la->kind == 31) {
			Get();
			andExpression();
		}
}

void Parser::equalityExpression() {
		relationalExpression();
		while (la->kind == 32 || la->kind == 33) {
			if (la->kind == 32) {
				Get();
			} else {
				Get();
			}
			equalityExpression();
		}
}

void Parser::relationalExpression() {
		shiftExpression();
		while (StartOf(6)) {
			if (la->kind == 34) {
				Get();
			} else if (la->kind == 35) {
				Get();
			} else if (la->kind == 36) {
				Get();
			} else {
				Get();
			}
			relationalExpression();
		}
}

void Parser::shiftExpression() {
		additiveExpression();
		while (la->kind == 38 || la->kind == 39 || la->kind == 40) {
			if (la->kind == 38) {
				Get();
			} else if (la->kind == 39) {
				Get();
			} else {
				Get();
			}
			shiftExpression();
		}
}

void Parser::additiveExpression() {
		multiplicativeExpression();
		while (la->kind == 41 || la->kind == 42) {
			if (la->kind == 41) {
				Get();
			} else {
				Get();
			}
			additiveExpression();
		}
}

void Parser::multiplicativeExpression() {
		unaryExpression();
		while (la->kind == 43 || la->kind == 44 || la->kind == 45) {
			if (la->kind == 43) {
				Get();
			} else if (la->kind == 44) {
				Get();
			} else {
				Get();
			}
			multiplicativeExpression();
		}
}

void Parser::unaryExpression() {
		expressionName();
		while (la->kind == 43 || la->kind == 44 || la->kind == 45) {
			if (la->kind == 43) {
				Get();
			} else if (la->kind == 44) {
				Get();
			} else {
				Get();
			}
			unaryExpression();
		}
}

void Parser::expressionName() {
		if (la->kind == 1) {
			Get();
			while (la->kind == 46) {
				Get();
				Expect(1);
			}
		} else if (la->kind == 2) {
			Get();
		} else SynErr(55);
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
	maxT = 47;

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

	static bool set[7][49] = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, T,T,T,x, x,T,x,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,x,x, x,T,T,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,x,x, x,T,T,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x}
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
			case 9: s = coco_string_create(L"\"class\" expected"); break;
			case 10: s = coco_string_create(L"\"extends\" expected"); break;
			case 11: s = coco_string_create(L"\"public\" expected"); break;
			case 12: s = coco_string_create(L"\"protected\" expected"); break;
			case 13: s = coco_string_create(L"\"private\" expected"); break;
			case 14: s = coco_string_create(L"\"byte\" expected"); break;
			case 15: s = coco_string_create(L"\"short\" expected"); break;
			case 16: s = coco_string_create(L"\"int\" expected"); break;
			case 17: s = coco_string_create(L"\"long\" expected"); break;
			case 18: s = coco_string_create(L"\"char\" expected"); break;
			case 19: s = coco_string_create(L"\"float\" expected"); break;
			case 20: s = coco_string_create(L"\"double\" expected"); break;
			case 21: s = coco_string_create(L"\"bool\" expected"); break;
			case 22: s = coco_string_create(L"\"void\" expected"); break;
			case 23: s = coco_string_create(L"\"=\" expected"); break;
			case 24: s = coco_string_create(L"\"{\" expected"); break;
			case 25: s = coco_string_create(L"\";\" expected"); break;
			case 26: s = coco_string_create(L"\"}\" expected"); break;
			case 27: s = coco_string_create(L"\"||\" expected"); break;
			case 28: s = coco_string_create(L"\"&&\" expected"); break;
			case 29: s = coco_string_create(L"\"|\" expected"); break;
			case 30: s = coco_string_create(L"\"^\" expected"); break;
			case 31: s = coco_string_create(L"\"&\" expected"); break;
			case 32: s = coco_string_create(L"\"==\" expected"); break;
			case 33: s = coco_string_create(L"\"!=\" expected"); break;
			case 34: s = coco_string_create(L"\"<\" expected"); break;
			case 35: s = coco_string_create(L"\">\" expected"); break;
			case 36: s = coco_string_create(L"\"<=\" expected"); break;
			case 37: s = coco_string_create(L"\">=\" expected"); break;
			case 38: s = coco_string_create(L"\"<<\" expected"); break;
			case 39: s = coco_string_create(L"\">>\" expected"); break;
			case 40: s = coco_string_create(L"\"<<<\" expected"); break;
			case 41: s = coco_string_create(L"\"+\" expected"); break;
			case 42: s = coco_string_create(L"\"-\" expected"); break;
			case 43: s = coco_string_create(L"\"*\" expected"); break;
			case 44: s = coco_string_create(L"\"/\" expected"); break;
			case 45: s = coco_string_create(L"\"%\" expected"); break;
			case 46: s = coco_string_create(L"\".\" expected"); break;
			case 47: s = coco_string_create(L"??? expected"); break;
			case 48: s = coco_string_create(L"invalid typeDeclaration"); break;
			case 49: s = coco_string_create(L"invalid accessSpecifier"); break;
			case 50: s = coco_string_create(L"invalid classBody"); break;
			case 51: s = coco_string_create(L"invalid classBody"); break;
			case 52: s = coco_string_create(L"invalid classBody"); break;
			case 53: s = coco_string_create(L"invalid type"); break;
			case 54: s = coco_string_create(L"invalid formalParameterList"); break;
			case 55: s = coco_string_create(L"invalid expressionName"); break;

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



