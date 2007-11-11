// Copyright (C) 1991 - 1999 Rational Software Corporation
#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
#define _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"
#include "Text.h"

#include <list>
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//текст в овале
//##ModelId=46F50C7B01D4
class TextInOval 
: public Oval
, public Text
{
private:
    //##ModelId=472DF1EC00DA
    TextInOval(float rad1, float rad2, std::string content, float x, float y);

    //указатели на созданные тексты в овалах
    //##ModelId=4736C5F102FD
    static list<TextInOval*> _textsInOvals;
public:
    //выдает указатель на уже существующий текст в овале, если он имеет заданные 
    //параметры
    //если такого не существует - создает новый
    //##ModelId=4736C62903A9
    static TextInOval* create(float rad1, float rad2, std::string content, float x, float y);

	//##ModelId=46F677F2002E
	virtual ~TextInOval();
    
    //вычисляет площадь фигуры
    //##ModelId=472DF2A20119
    virtual float Area() const;
protected:
    //вывести состояние текста в овале в поток
    //##ModelId=471219010148
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
