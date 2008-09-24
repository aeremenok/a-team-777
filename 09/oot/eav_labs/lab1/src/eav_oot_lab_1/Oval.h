// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_OVAL_46F50C54004E_INCLUDED
#define _INC_OVAL_46F50C54004E_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

#include <list>
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//овал
//##ModelId=46F50C54004E
class Oval 
: public virtual Shape
{
private:
    //указатели на созданные овалы
    //##ModelId=4736BF96004E
    static list<Oval*> _ovals;
protected:
    //##ModelId=4713C61F0203
    Oval(float rad1, float rad2, float x, float y);

	//больший радиус овала
	//##ModelId=46F5136F00BB
	float _rad1;

    //меньший радиус овала
    //##ModelId=46F5139C0138
	float _rad2;

    //вывести состояние овала в поток
    //##ModelId=471218B200EA
    virtual ostream& speak(ostream& os) const;
public:
    //выдает указатель на уже существующий овал, если он имеет заданные параметры
    //если такого не существует - создает новый
    //##ModelId=4736C0F90213
    static Oval* create(float rad1, float rad2, float x, float y);

    //##ModelId=46F674D6002E
	virtual ~Oval();

	//получить больший радиус
	//##ModelId=46F67B2D004E
	const float getRad1() const;

	//задать больший радиус
	//##ModelId=46F67B2D0244
	void setRad1(float value);

	//получить меньший радиус
	//##ModelId=46F67B2F0263
	const float getRad2() const;

	//задать меньший радиус
	//##ModelId=46F67B30008E
	void setRad2(float value);

    //вычисляет площадь фигуры
    //##ModelId=472DF28503A9
    virtual float Area() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_OVAL_46F50C54004E_INCLUDED */
