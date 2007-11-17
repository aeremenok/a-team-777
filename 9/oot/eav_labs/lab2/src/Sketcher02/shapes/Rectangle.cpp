// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Rectangle.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40370
list<Rectangle*> Rectangle::_rectangles;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF4038A
Rectangle::Rectangle( float l, float w, float x, float y )
: Shape(x, y)
{
    _length = l;
    _width = w;
    cout<<"[rectangle] rectangle created"<<endl;
}

//##ModelId=473EDDF4038F
Rectangle* Rectangle::create(float length, float width, float x, float y)
{
    // ищем, нет ли уже прямоугольника с такими параметрами
    using namespace std;
    list<Rectangle*>::iterator iter;
    for (iter = _rectangles.begin(); iter != _rectangles.end(); iter++)
    {
        Rectangle* rectangle = *iter;
        if (
            rectangle->_length == length &&
            rectangle->_width == width &&
            rectangle->_x == x &&
            rectangle->_y == y
            )
        {   // такой есть
            return rectangle;
        }
    }
    // не нашли - создаем новый
    Rectangle* rectangle = new Rectangle(length, width, x, y);
    _rectangles.push_back(rectangle);
    return rectangle;
}

//##ModelId=473EDDF4039B
Rectangle::~Rectangle()
{
    _rectangles.remove(this);
	cout<<"[rectangle] rectangle destroyed"<<endl;
}

//##ModelId=473EDDF4039D
const float Rectangle::get__length() const
{
	return _length;
}

//##ModelId=473EDDF4039F
void Rectangle::set__length(float value)
{
	_length = value;
}

//##ModelId=473EDDF403A1
const float Rectangle::get__width() const
{
	return _width;
}

//##ModelId=473EDDF403A3
void Rectangle::set__width(float value)
{
	_width = value;
}

//##ModelId=473EDDF403AC
ostream& Rectangle::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[rectangle] rectangle size: ("
        <<_length<<", "<<_width<<")"<<endl;
}

//##ModelId=473EDDF403AA
float Rectangle::Area() const
{
    return _length * _width;
}

//////////////////////////////////////////////////////////////////////////

