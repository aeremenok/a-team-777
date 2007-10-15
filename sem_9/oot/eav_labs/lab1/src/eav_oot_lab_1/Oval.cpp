// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"
#include <ostream.h>
//////////////////////////////////////////////////////////////////////////

//##ModelId=46F674D6002E
Oval::~Oval()
{
    Shape::~Shape();
	cout<<"oval destroyed"<<endl;
}

//##ModelId=46F67B2D004E
const int Oval::get__biggerRad() const
{
	return _biggerRad;
}

//##ModelId=46F67B2D0244
void Oval::set__biggerRad(int value)
{
	_biggerRad = value;
}

//##ModelId=46F67B2F0263
const int Oval::get__lesserRad() const
{
	return _lesserRad;
}

//##ModelId=46F67B30008E
void Oval::set__lesserRad(int value)
{
	_lesserRad = value;
}

//##ModelId=4708F071008C
int Oval::Area()
{
    return static_cast<int>(0);
}


//##ModelId=471218B200EA
ostream& Oval::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"oval is speaking:\n\t"
        <<"oval chords: ("<<_biggerRad<<", "<<_lesserRad<<")"<<endl;
}



//##ModelId=4713C61F0203
Oval::Oval(int bigger, int less)
{
    _biggerRad = bigger;
    _lesserRad = less;
    cout<<"oval created"<<endl;
}


