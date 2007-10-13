// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXT_46F50C7401C5_INCLUDED
#define _INC_TEXT_46F50C7401C5_INCLUDED

#include "Shape.h"

class ostream;

//текст
//##ModelId=46F50C7401C5
class Text 
: public Shape
{
public:
 //##ModelId=47111D6101B5
 friend ostream& operator<<(ostream& o, const Text &rhs);

	//##ModelId=46F6769602BF
	Text();

	//##ModelId=46F676990213
	virtual ~Text();

	//##ModelId=46F510F20232
	char *  getText() const;

	//##ModelId=46F511550280
	void setText(char * text = "");

	//добавить новый текст к старому
	//##ModelId=46F5119901A5
	void appendText(char * text = "");

private:
	//содержимое текста
	//##ModelId=46F510A50251
	char * _content;

};

#endif /* _INC_TEXT_46F50C7401C5_INCLUDED */
