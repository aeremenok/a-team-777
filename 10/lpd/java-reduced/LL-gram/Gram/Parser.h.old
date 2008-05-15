

#if !defined(COCO_PARSER_H__)
#define COCO_PARSER_H__



#include "Scanner.h"



class Errors {
public:
	int count;			// number of errors detected

	Errors();
	void SynErr(int line, int col, int n);
	void Error(int line, int col, const wchar_t *s);
	void Warning(int line, int col, const wchar_t *s);
	void Warning(const wchar_t *s);
	void Exception(const wchar_t *s);

}; // Errors

class Parser {
private:
	enum {
		_EOF=0,
		_identifier=1,
		_realNumber=2,
		_string=3,
		_interface=4,
		_final=5,
		_static=6,
		_openRoundBracket=7,
		_closeRoundBracket=8,
		_openCurlyBracket=9,
		_closeCurlyBracket=10,
	};
	int maxT;

	Token *dummyToken;
	int errDist;
	int minErrDist;

	void SynErr(int n);
	void Get();
	void Expect(int n);
	bool StartOf(int s);
	void ExpectWeak(int n, int follow);
	bool WeakSeparator(int n, int syFol, int repFol);

public:
	Scanner *scanner;
	Errors  *errors;

	Token *t;			// last recognized token
	Token *la;			// lookahead token

bool next(int i)
    {
       scanner->ResetPeek();
       Token* x = scanner->Peek();
       return x->kind == i;
    };
  
/*--------------------------------------------------------------------------*/


	Parser(Scanner *scanner);
	~Parser();
	void SemErr(const wchar_t* msg);

	void CompilationUnit();
	void typeDeclaration();
	void accessSpecifier();
	void interfaceDeclaration();
	void classDeclaration();
	void classBody();
	void interfaceBody();
	void type();
	void formalParameterList();
	void statement();
	void expression();
	void expressionName();
	void assignmentOperator();
	void conditionalOrExpression();
	void conditionalAndExpression();
	void inclusiveOrExpression();
	void exclusiveOrExpression();
	void andExpression();
	void equalityExpression();
	void relationalExpression();
	void shiftExpression();
	void additiveExpression();
	void multiplicativeExpression();
	void unaryExpression();

	void Parse();

}; // end Parser



#endif // !defined(COCO_PARSER_H__)

