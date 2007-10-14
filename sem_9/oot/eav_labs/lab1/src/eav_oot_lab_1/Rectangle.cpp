// Copyright (C) 1991 - 1999 Rational Software Corporation

#include "Rectangle.h"
#include <ostream.h>



//##ModelId=46F677C6037B
Rectangle::~Rectangle()
{
	cout<<"прямоугольник уничтожен"<<endl;
}

//##ModelId=46F67BE003BB
const int Rectangle::get__length() const
{
	return _length;
}

//##ModelId=46F67BE1035D
void Rectangle::set__length(int value)
{
	_length = value;
	return;
}

//##ModelId=46F67BEC002E
const int Rectangle::get__width() const
{
	return _width;
}

//##ModelId=46F67BED01C5
void Rectangle::set__width(int value)
{
	_width = value;
	return;
}



//##ModelId=4708F05E03D8
int Rectangle::Area()
{
    return _width * _length;
}

//##ModelId=4712182B029F
ostream& Rectangle::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"размеры прямоугольника: ("<<_length<<", "<<_width<<")"<<endl;
}

//##ModelId=47125E3F035B
Rectangle::Rectangle( int l, int w )
{
    Shape::Shape();
    _length = l;
    _width = w;    
}
