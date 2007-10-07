// Copyright (C) 1991 - 1999 Rational Software Corporation

#include "Rectangle.h"



//##ModelId=46F677C502EE
Rectangle::Rectangle()
{
	// ToDo: Add your specialized code here and/or call the base class
}

//##ModelId=46F677C6037B
Rectangle::~Rectangle()
{
	// ToDo: Add your specialized code here and/or call the base class
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
 // ToDo: Add your specialized code here
 
 return static_cast<int>(0);
}

