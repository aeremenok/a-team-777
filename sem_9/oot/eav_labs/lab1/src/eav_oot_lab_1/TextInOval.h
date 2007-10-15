// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
#define _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED

#include "Oval.h"
#include "Text.h"

class ostream;

//текст в овале
//##ModelId=46F50C7B01D4
class TextInOval 
: public Oval
, public Text
{
private:
 //вывести состояние текста в овале в поток
 //##ModelId=471219010148
 virtual ostream& speak(ostream& os) const;

public:
 //##ModelId=4713C74D038A
 TextInOval(int big, int less);

	//##ModelId=46F677F2002E
	virtual ~TextInOval();

};

#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
