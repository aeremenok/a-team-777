// LL-gram.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <string>
#include "Parser.h"
#include "Scanner.h"


int _tmain(int argc, _TCHAR* argv[])
{
	wchar_t* str = L"D:\\SVNLeti\\10\\lpd\\java-reduced\\examples\\Foo1.java";
	Scanner scanPadStack(str);
	Parser parsPadStack(&scanPadStack);
	parsPadStack.Parse();
	printf("Completed\n");
	getchar();
	return 0;
}

