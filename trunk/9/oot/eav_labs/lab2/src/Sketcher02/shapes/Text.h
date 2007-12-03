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
    virtual std::ostream& speak(std::ostream& os) const;
public:
	// Virtual draw operation
	//##ModelId=4754603600FA
	virtual void Draw(CDC* pDC, CElement* pElement = 0, bool isIdVisible = true);

	//идентификатор типа объекта
	//##ModelId=4751CD1D00EA
	virtual int getType() const;

	//##ModelId=4751AC8201D4
	Text();

	//##ModelId=475168EB032C
	virtual void Serialize(CArchive& ar);

    //##ModelId=473EDDF402B0
    const std::string& get__content() const;

    //##ModelId=473EDDF402B2
    void set__content(std::string& value);

    //выдает указатель на уже существующий текст, если он имеет заданные параметры
    //если такого не существует - создает новый
    //##ModelId=473EDDF402BF
    static Text* create(std::string content = "", float x = NULL, float y = NULL);

	//##ModelId=474055EF0167
    static Text* create(CPoint Start, CPoint End, COLORREF aColor);

	//##ModelId=473EDDF402CF
	virtual ~Text();

    //вычисляет площадь фигуры
    //##ModelId=473EDDF402D1
    virtual float Area() const;
};

//##ModelId=4754603600FA

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXT_46F50C7401C5_INCLUDED */
