// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RECTANGLE_46F50C350000_INCLUDED
#define _INC_RECTANGLE_46F50C350000_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"
#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//прямоугольник
//##ModelId=46F50C350000
class Rectangle 
: public Shape
{
private:
    /************************************************************************/
    /* атрибуты                                                             */
    /************************************************************************/
	//длина
	//##ModelId=46F51212001F
	float _length;

	//ширина
	//##ModelId=46F512910128
	float _width;
public:
    /************************************************************************/
    /* конструкторы и деструктор                                            */
    /************************************************************************/
	//##ModelId=47125E3F035B
    Rectangle(float l, float w);

    //##ModelId=46F677C6037B
	virtual ~Rectangle();

    /************************************************************************/
    /* геттеры/сеттеры                                                      */
    /************************************************************************/
	//получить длину
	//##ModelId=46F67BE003BB
	const float get__length() const;

	//задать длину
	//##ModelId=46F67BE1035D
	void set__length(float value);

	//получить ширину
	//##ModelId=46F67BEC002E
	const float get__width() const;

	//задать ширину
	//##ModelId=46F67BED01C5
	void set__width(float value);

    /************************************************************************/
    /* прочие методы                                                        */
    /************************************************************************/    
    //##ModelId=472DDB180213
    virtual bool operator==(const Rectangle& rhs) const;

    //вычисляет площадь фигуры
    //##ModelId=472DF2720138
    virtual float Area() const;
protected:
    //имя класса
    //##ModelId=472DFDF0005D
    virtual int getName() const;

    //вывести состояние прямоугольника в поток
    //##ModelId=4712182B029F
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RECTANGLE_46F50C350000_INCLUDED */
