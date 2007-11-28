// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "Shape.h"
#include "../OurConstants.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF4032D
int Shape::_counter = 0;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF4033F
Shape::Shape(float x, float y) : _id(_counter++)
{
    _x = x;
    _y = y;
    cout<<"[shape] shape created"<<endl;
}

//##ModelId=473EDDF4035E
void Shape::moveToPoint(float x_pos, float y_pos)
{
    _x = x_pos;
    _y = y_pos;
}

//##ModelId=473EDDF40351
const float Shape::get__y() const
{
	return _y;
}

//##ModelId=473EDDF40353
void Shape::set__y(float value)
{
	_y = value;
	return;
}

//##ModelId=473EDDF40355
const float Shape::get__x() const
{
	return _x;
}

//##ModelId=473EDDF40357
void Shape::set__x(float value)
{
	_x = value;
	return;
}

//##ModelId=473EDDF4034F
Shape::~Shape()
{
    cout<<"[shape] shape destroyed"<<endl;
}

//##ModelId=473EDDF4034C
ostream& Shape::speak(ostream& os) const
{
    return os<<"[shape] id="<<_id<<", shape center coordinates: ("
             <<_x<<", "<<_y<<"), "
             <<"area = "<<Area()<<endl;
}

//##ModelId=473EDDF4035B
bool Shape::operator==(const Shape& rhs) const
{
    // учитывая проверку при создании объектов,
    //  достаточно сравнить идентификаторы
    //  на CElement не обращаем внимания - мы уже все проверили
    return (_id == rhs._id);
}

//##ModelId=474C8E6F02BF
void Shape::Move( CSize& aSize )
{
    moveToPoint(_x + aSize.cx, _y + aSize.cy);
    CElement::Move(aSize);
}

//##ModelId=474C966702BF
void Shape::drawID( CDC* pDC ) const
{
    char id[3];
    itoa(_id, id, 10);
    pDC->SetTextColor(GREEN);
    pDC->TextOut(m_EnclosingRect.BottomRight().x, m_EnclosingRect.BottomRight().y, id);
}

//##ModelId=474DCD59038A
void Shape::resize(CPoint Start, CPoint End)
{
    // определяем координаты центра
    _x = (End.x + Start.x) / 2;
    _y = (End.y + Start.y) / 2;
    CElement::resize(Start, End);
}
//////////////////////////////////////////////////////////////////////////
ostream& operator<<( ostream& o, const Shape& rhs )
{
    return rhs.speak(o);
}
//////////////////////////////////////////////////////////////////////////
