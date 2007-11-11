// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=4736B8520000
int Shape::_counter = 0;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4736C3EF00AB
Shape::Shape(float x, float y)
{
    _id = _counter++;
    _x = x;
    _y = y;
    cout<<"[shape] shape created"<<endl;
}

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
    cout<<"[shape] default shape created"<<endl;
}

//##ModelId=4708DDC4001F
Shape::~Shape()
{
    cout<<"[shape] shape destroyed"<<endl;
}

//##ModelId=4712170B0271
ostream& Shape::speak(ostream& os) const
{
    return os<<"[shape] shape center coordinates: ("
             <<_x<<", "<<_y<<"), "
             <<"area = "<<Area()<<endl;
}

//##ModelId=472DDB08029F
bool Shape::operator==(const Shape& rhs) const
{
    // учитывая проверку при создании объектов,
    //  достаточно сравнить идентификаторы
    return (_id == rhs._id);
}

//////////////////////////////////////////////////////////////////////////
ostream& operator<<( ostream& o, const Shape& rhs )
{
    return rhs.speak(o);
}
//////////////////////////////////////////////////////////////////////////
