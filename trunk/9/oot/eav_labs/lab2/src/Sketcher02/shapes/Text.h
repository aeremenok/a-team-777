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
//##ModelId=473EDDF40261
class Text 
: public virtual Shape
{
private:
    //указатели на созданные тексты
    //##ModelId=473EDDF40281
    static list<Text*> _texts;
protected:
    //##ModelId=473EDDF40285
    Text(std::string content, float x, float y);

    //содержимое текста
    //##ModelId=473EDDF402A0
    std::string _content;

    //вывести состояние прямоугольника в поток
    //##ModelId=473EDDF402A4
    virtual ostream& speak(ostream& os) const;
public:
	//Virtual draw operation
	//##ModelId=473EF26003D8
	virtual void Draw(CDC* pDC);

    //##ModelId=473EDDF402B0
    const std::string& get__content() const;

    //##ModelId=473EDDF402B2
    void set__content(std::string& value);

    //выдает указатель на уже существующий текст, если он имеет заданные параметры
    //если такого не существует - создает новый
    //##ModelId=473EDDF402BF
    static Text* create(std::string content, float x, float y);

    static Text* create(CPoint Start, CPoint End, COLORREF aColor);

	//##ModelId=473EDDF402CF
	virtual ~Text();

    //вычисляет площадь фигуры
    //##ModelId=473EDDF402D1
    virtual float Area() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXT_46F50C7401C5_INCLUDED */
