// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
#define _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED

#include "Oval.h"
#include "Text.h"

//текст в овале
//##ModelId=46F50C7B01D4
class TextInOval 
: public Oval
, public Text
{
public:
	//##ModelId=46F677F2002E
	virtual ~TextInOval();

	//##ModelId=46F677F30128
	TextInOval();

	//вывести информацию об объекте
	//##ModelId=46F6787D009C
	virtual void speak() const;

};

#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
