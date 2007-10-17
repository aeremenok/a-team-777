// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"
#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
#define M_PI 3.1415926
//////////////////////////////////////////////////////////////////////////
//##ModelId=46F674D6002E
Oval::~Oval()
{
    Shape::~Shape();
	cout<<"oval destroyed"<<endl;
}

//##ModelId=46F67B2D004E
const float Oval::getRad1() const
{
	return _rad1;
}

//##ModelId=46F67B2D0244
void Oval::setRad1(float value)
{
	_rad1 = value;
}

//##ModelId=46F67B2F0263
const float Oval::getRad2() const
{
	return _rad2;
}

//##ModelId=46F67B30008E
void Oval::setRad2(float value)
{
	_rad2 = value;
}

//##ModelId=4708F071008C
float Oval::Area()
{
    return (float)M_PI * _rad1 * _rad2;
}


//##ModelId=471218B200EA
ostream& Oval::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"oval is speaking:\n\t"
        <<"oval chords: ("<<_rad1<<", "<<_rad2<<")"<<endl;
}

//##ModelId=4713C61F0203
Oval::Oval(float rad1, float rad2)
{
    _rad1 = rad1;
    _rad2 = rad2;
    cout<<"oval created"<<endl;
}


