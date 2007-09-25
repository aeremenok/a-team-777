// Copyright (C) 1991 - 1999 Rational Software Corporation

#include "Shape.h"
#include <ostream.h>



//##ModelId=46F50D80038A
void Shape::moveToPoint(int x_pos, int y_pos)
{
    _x = x_pos;
    _y = y_pos;
}

//##ModelId=46F50F9F0222
void Shape::speak() const
{
}



//##ModelId=46F675DE02AF
const int Shape::getY() const
{
	return _y;
}

//##ModelId=46F675DF010B
void Shape::setY(int value)
{
	_y = value;
	return;
}

//##ModelId=46F675E401C7
const int Shape::getX() const
{
	return _x;
}

//##ModelId=46F675E403AB
void Shape::setX(int value)
{
	_x = value;
	return;
}

