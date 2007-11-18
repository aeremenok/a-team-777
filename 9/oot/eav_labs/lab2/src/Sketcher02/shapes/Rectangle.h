// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RECTANGLE_46F50C350000_INCLUDED
#define _INC_RECTANGLE_46F50C350000_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

#include <list>
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//прямоугольник
//##ModelId=473EDDF4036B
class Rectangle2 
: public Shape
{
private:
    //указатели на созданные прямоугольники
    //##ModelId=473EDDF40370
    static list<Rectangle2*> _rectangles;

	//длина
	//##ModelId=473EDDF4037C
	float _length;

	//ширина
	//##ModelId=473EDDF4037D
	float _width;

    //##ModelId=473EDDF4038A
    Rectangle2(float l, float w, float x, float y);
public:
	//Virtual draw operation
	//##ModelId=473EF26702EE
	virtual void Draw(CDC* pDC);

    //выдает указатель на уже существующий прямоугольник, если он имеет заданные 
    //параметры
    //если такого не существует - создает новый
    //##ModelId=473EDDF4038F
    static Rectangle2* create(float length, float width, float x, float y);

    // создает прямоугольник по точкам углов
	//##ModelId=474055EF0280
    static Rectangle2* create(CPoint Start, CPoint End, COLORREF aColor);

    //##ModelId=473EDDF4039B
	virtual ~Rectangle2();

	//получить длину
	//##ModelId=473EDDF4039D
	const float get__length() const;

	//задать длину
	//##ModelId=473EDDF4039F
	void set__length(float value);

	//получить ширину
	//##ModelId=473EDDF403A1
	const float get__width() const;

	//задать ширину
	//##ModelId=473EDDF403A3
	void set__width(float value);

    //вычисляет площадь фигуры
    //##ModelId=473EDDF403AA
    virtual float Area() const;
protected:
    //вывести состояние прямоугольника в поток
    //##ModelId=473EDDF403AC
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RECTANGLE_46F50C350000_INCLUDED */
