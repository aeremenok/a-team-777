// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////

//##ModelId=46F50D80038A
void Shape::moveToPoint(float x_pos, float y_pos)
{
    _x = x_pos;
    _y = y_pos;
}

//##ModelId=46F675DE02AF
const float Shape::get__y() const
{
	return _y;
}

//##ModelId=46F675DF010B
void Shape::set__y(float value)
{
	_y = value;
	return;
}

//##ModelId=46F675E401C7
const float Shape::get__x() const
{
	return _x;
}

//##ModelId=46F675E403AB
void Shape::set__x(float value)
{
	_x = value;
	return;
}


//##ModelId=4708DDC303C8
Shape::Shape()
{
    _x = 0;
    _y = 0;
    cout<<"default figure created"<<endl;
}

//##ModelId=4708DDC4001F
Shape::~Shape()
{
    cout<<"figure destroyed"<<endl;
}

//##ModelId=4712170B0271
ostream& Shape::speak(ostream& os) const
{
    return os<<"shape is speaking:\n\t"<<"shape center coordinates: ("<<_x<<", "<<_y<<")"<<endl;
}

ostream& operator<<( ostream& o, const Shape& rhs )
{
    return rhs.speak(o);
}
