// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF4032D
int Shape::_counter = 0;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF4033F
Shape::Shape(float x, float y)
{
    _id = _counter++;
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
    return (_id == rhs._id);
}

//////////////////////////////////////////////////////////////////////////
ostream& operator<<( ostream& o, const Shape& rhs )
{
    return rhs.speak(o);
}
//////////////////////////////////////////////////////////////////////////
