// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXT_46F50C7401C5_INCLUDED
#define _INC_TEXT_46F50C7401C5_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include <string>
#include <list>

#include "Shape.h"
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//текст
//##ModelId=46F50C7401C5
class Text 
: public virtual Shape
{
private:
    //указатели на созданные тексты
    //##ModelId=4736C55C01C5
    static list<Text*> _texts;
protected:
    //##ModelId=472DF1EC01B5
    Text(std::string content, float x, float y);

    //содержимое текста
    //##ModelId=46F510A50251
    std::string _content;

    //вывести состояние прямоугольника в поток
    //##ModelId=471218CE00DA
    virtual ostream& speak(ostream& os) const;
public:
    //##ModelId=4736C4B9037A
    const std::string& get__content() const;

    //##ModelId=4736C4BA02D0
    void set__content(std::string& value);

    //выдает указатель на уже существующий текст, если он имеет заданные параметры
    //если такого не существует - создает новый
    //##ModelId=4736C4CA000F
    static Text* create(std::string content, float x, float y);

	//##ModelId=46F676990213
	virtual ~Text();

    //вычисляет площадь фигуры
    //##ModelId=472DF2970261
    virtual float Area() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXT_46F50C7401C5_INCLUDED */
