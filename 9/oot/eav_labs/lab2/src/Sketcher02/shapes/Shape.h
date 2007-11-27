// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_SHAPE_46F50BDC00BB_INCLUDED
#define _INC_SHAPE_46F50BDC00BB_INCLUDED
//////////////////////////////////////////////////////////////////////////
class ostream;
#include "..\Elements.h"

//////////////////////////////////////////////////////////////////////////
//обобщенна€ фигура
//##ModelId=473EDDF4032C
class Shape
: public CElement
{
private:
    //счетчик объектов
    //##ModelId=473EDDF4032D
    static int _counter;

    //идентификатор объекта
    //##ModelId=473EDDF4033C
    int _id;
protected:
    //абсцисса фигуры. все фигуры по умолчанию по€вл€ютс€ в начале координат
    //##ModelId=473EDDF4033D
    float _x;
    
    //ордината фигуры. все фигуры по умолчанию по€вл€ютс€ в начале координат
    //##ModelId=473EDDF4033E
	float _y;

    //##ModelId=473EDDF4033F
    Shape(float x, float y);
    
    //вывести состо€ние фигуры в поток
    //##ModelId=473EDDF4034C
    virtual ostream& speak(ostream& os) const;
public:
	//##ModelId=474C8E6F02BF
	virtual void Move(CSize& aSize);

    //##ModelId=473EDDF4034F
    virtual ~Shape();

    //получить ординату фигуры
    //##ModelId=473EDDF40351
    const float get__y() const;
    
    //задать ординату фигуры
    //##ModelId=473EDDF40353
    void set__y(float value);
    
    //получить абсциссу фигуры
    //##ModelId=473EDDF40355
    const float get__x() const;
    
    //задать абциссу фигуры
    //##ModelId=473EDDF40357
	void set__x(float value);

    //оператор вывода в поток
    //##ModelId=473EDDF40363
	friend ostream& operator<<(ostream& o, const Shape& rhs);

    //##ModelId=473EDDF4035B
    virtual bool operator==(const Shape& rhs) const;

	//перенести фигуру в заданную точку
	//##ModelId=473EDDF4035E
	void moveToPoint(float x_pos, float y_pos);

    //вычисл€ет площадь фигуры
    //##ModelId=473EDDF40361
    virtual float Area() const = 0;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_SHAPE_46F50BDC00BB_INCLUDED */
