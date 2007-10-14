// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXT_46F50C7401C5_INCLUDED
#define _INC_TEXT_46F50C7401C5_INCLUDED

#include "Shape.h"

#include <cstring>

class CString;
class ostream;

//////////////////////////////////////////////////////////////////////////
//текст
//##ModelId=46F50C7401C5
class Text 
: public Shape
{
protected:
    //содержимое текста
    //##ModelId=46F510A50251
    CString* _content;

    //вывести состояние текста в поток
    //##ModelId=471218CE00DA
    virtual ostream& speak(ostream& os) const;
public:
 //вычислить площадь фигуры
 //##ModelId=47125F23009C
 virtual int Area();


	//##ModelId=471220F702FD
    Text();

	//##ModelId=46F676990213
	virtual ~Text();

	//##ModelId=46F510F20232
    CString getText() const;

	//##ModelId=46F511550280
    void setText(CString* text);

	//добавить новый текст к старому
	//##ModelId=46F5119901A5
    void appendText(CString* text);
};

#endif /* _INC_TEXT_46F50C7401C5_INCLUDED */
