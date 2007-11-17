// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "Rectangle.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40370
list<Rectangle2*> Rectangle2::_rectangles;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF4038A
Rectangle2::Rectangle2( float l, float w, float x, float y )
: Shape(x, y)
{
    _length = l;
    _width = w;
    cout<<"[rectangle] rectangle created"<<endl;
}

//##ModelId=473EDDF4038F
Rectangle2* Rectangle2::create(float length, float width, float x, float y)
{
    // ищем, нет ли уже прямоугольника с такими параметрами
    using namespace std;
    list<Rectangle2*>::iterator iter;
    for (iter = _rectangles.begin(); iter != _rectangles.end(); iter++)
    {
        Rectangle2* rectangle = *iter;
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
    Rectangle2* rectangle = new Rectangle2(length, width, x, y);
    _rectangles.push_back(rectangle);
    return rectangle;
}

Rectangle2* Rectangle2::create( CPoint Start, CPoint End, COLORREF aColor )
{
    float x, y, l, w;
    if ( Start.x > End.x )
    {
        x = End.x;
        w = Start.x - x;
    }
    else
    {
        x = Start.x;
        w = End.x - x;
    }
    if ( Start.y > End.y )
    {
        y = End.y;
        l = Start.y - y;
    }
    else
    {
        y = Start.y;
        l = End.y - y;
    }

    Rectangle2* rect = create(l, w, x, y);

    rect->m_Pen = 1;
    rect->m_EnclosingRect = CRect(Start, End);
    rect->m_EnclosingRect.NormalizeRect();

    return rect;
}

//##ModelId=473EDDF4039B
Rectangle2::~Rectangle2()
{
    _rectangles.remove(this);
	cout<<"[rectangle] rectangle destroyed"<<endl;
}

//##ModelId=473EDDF4039D
const float Rectangle2::get__length() const
{
	return _length;
}

//##ModelId=473EDDF4039F
void Rectangle2::set__length(float value)
{
	_length = value;
}

//##ModelId=473EDDF403A1
const float Rectangle2::get__width() const
{
	return _width;
}

//##ModelId=473EDDF403A3
void Rectangle2::set__width(float value)
{
	_width = value;
}

//##ModelId=473EDDF403AC
ostream& Rectangle2::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[rectangle] rectangle size: ("
        <<_length<<", "<<_width<<")"<<endl;
}

//##ModelId=473EDDF403AA
float Rectangle2::Area() const
{
    return _length * _width;
}

//////////////////////////////////////////////////////////////////////////

