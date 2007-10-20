// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Rectangle.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////

//##ModelId=46F677C6037B
Rectangle::~Rectangle()
{
	cout<<"rectangle destroyed"<<endl;
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
        <<"rectangle is speaking:\n\t"
        <<"rectangle size: ("<<_length<<", "<<_width<<")"<<endl;
}

//##ModelId=47125E3F035B
Rectangle::Rectangle( float l, float w )
{
    _length = l;
    _width = w;    
    cout<<"rectangle created"<<endl;
}
