// Copyright (C) 1991 - 1999 Rational Software Corporation

#include "Text.h"



//##ModelId=46F510F20232
char *  Text::getText()
{
    return _content;
}

//##ModelId=46F511550280
void Text::setText(char * text)
{

}

//##ModelId=46F5119901A5
void Text::appendText(char * text)
{
}



//##ModelId=46F6769602BF
Text::Text()
{
	// ToDo: Add your specialized code here and/or call the base class
}

//##ModelId=46F676990213
Text::~Text()
{
	// ToDo: Add your specialized code here and/or call the base class
}

//##ModelId=46F67960031E
void Text::speak()
{
	// ToDo: Add your specialized code here or after the call to base class
	
	Shape::speak();
}

