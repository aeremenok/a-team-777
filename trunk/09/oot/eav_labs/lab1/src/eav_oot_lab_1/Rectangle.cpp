// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Rectangle.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=4736B9C40109
list<Rectangle*> Rectangle::_rectangles;
//////////////////////////////////////////////////////////////////////////
//##ModelId=47125E3F035B
Rectangle::Rectangle( float l, float w, float x, float y )
: Shape(x, y)
{
    _length = l;
    _width = w;
    cout<<"[rectangle] rectangle created"<<endl;
}

//##ModelId=4736B8E101C5
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

//##ModelId=46F677C6037B
Rectangle::~Rectangle()
{
    _rectangles.remove(this);
	cout<<"[rectangle] rectangle destroyed"<<endl;
}

//##ModelId=46F67BE003BB
const float Rectangle::get__length() const
{
	return _length;
}

//##ModelId=46F67BE1035D
void Rectangle::set__length(float value)
{
	_length = value;
}

//##ModelId=46F67BEC002E
const float Rectangle::get__width() const
{
	return _width;
}

//##ModelId=46F67BED01C5
void Rectangle::set__width(float value)
{
	_width = value;
}

//##ModelId=4712182B029F
ostream& Rectangle::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[rectangle] rectangle size: ("
        <<_length<<", "<<_width<<")"<<endl;
}

//##ModelId=472DF2720138
float Rectangle::Area() const
{
    return _length * _width;
}

//////////////////////////////////////////////////////////////////////////

