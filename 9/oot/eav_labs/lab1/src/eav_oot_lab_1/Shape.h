// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_SHAPE_46F50BDC00BB_INCLUDED
#define _INC_SHAPE_46F50BDC00BB_INCLUDED
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//обобщенна€ фигура
//##ModelId=46F50BDC00BB
class Shape 
{
private:
    //счетчик объектов
    //##ModelId=4736B8520000
    static int _counter;

    //идентификатор объекта
    //##ModelId=4736B87302CE
    int _id;
protected:
    //абсцисса фигуры. все фигуры по умолчанию по€вл€ютс€ в начале координат
    //##ModelId=46F50D19006D
    float _x;
    
    //ордината фигуры. все фигуры по умолчанию по€вл€ютс€ в начале координат
    //##ModelId=46F50D1D00AB
	float _y;

    //##ModelId=4708DDC303C8
    Shape();
    //##ModelId=4736C3EF00AB
    Shape(float x, float y);
    
    //вывести состо€ние фигуры в поток
    //##ModelId=4712170B0271
    virtual ostream& speak(ostream& os) const;
public:
    //##ModelId=4708DDC4001F
    virtual ~Shape();

    //получить ординату фигуры
    //##ModelId=46F675DE02AF
    const float get__y() const;
    
    //задать ординату фигуры
    //##ModelId=46F675DF010B
    void set__y(float value);
    
    //получить абсциссу фигуры
    //##ModelId=46F675E401C7
    const float get__x() const;
    
    //задать абциссу фигуры
    //##ModelId=46F675E403AB
	void set__x(float value);

    //оператор вывода в поток
    //##ModelId=46F6755D0025
	friend ostream& operator<<(ostream& o, const Shape& rhs);

    //##ModelId=472DDB08029F
    virtual bool operator==(const Shape& rhs) const;

	//перенести фигуру в заданную точку
	//##ModelId=46F50D80038A
	void moveToPoint(float x_pos, float y_pos);

    //вычисл€ет площадь фигуры
    //##ModelId=472DF22D0000
    virtual float Area() const = 0;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_SHAPE_46F50BDC00BB_INCLUDED */
