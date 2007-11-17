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
//##ModelId=473EDDF403B9
class Oval 
: public virtual Shape
{
private:
    //указатели на созданные овалы
    //##ModelId=473EDDF403BE
    static list<Oval*> _ovals;
protected:
    //##ModelId=473EDDF403C8
    Oval(float rad1, float rad2, float x, float y);

	//больший радиус овала
	//##ModelId=473EDDF403CD
	float _rad1;

    //меньший радиус овала
    //##ModelId=473EDDF403CE
	float _rad2;

    //вывести состояние овала в поток
    //##ModelId=473EDDF403D8
    virtual ostream& speak(ostream& os) const;
public:
	//Virtual draw operation
	//##ModelId=473EF25A00EA
	virtual void Draw(CDC* pDC);

    //выдает указатель на уже существующий овал, если он имеет заданные параметры
    //если такого не существует - создает новый
    //##ModelId=473EDDF403DB
    static Oval* create(float rad1, float rad2, float x, float y);

    static Oval* create(CPoint Start, CPoint End, COLORREF aColor);

    //##ModelId=473EDDF50001
	virtual ~Oval();

	//получить больший радиус
	//##ModelId=473EDDF50003
	const float getRad1() const;

	//задать больший радиус
	//##ModelId=473EDDF50005
	void setRad1(float value);

	//получить меньший радиус
	//##ModelId=473EDDF50007
	const float getRad2() const;

	//задать меньший радиус
	//##ModelId=473EDDF50009
	void setRad2(float value);

    //вычисляет площадь фигуры
    //##ModelId=473EDDF5000F
    virtual float Area() const;
};

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_OVAL_46F50C54004E_INCLUDED */
