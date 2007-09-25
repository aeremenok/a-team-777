// Copyright (C) 1991 - 1999 Rational Software Corporation

#include "Oval.h"



//##ModelId=46F674D6002E
Oval::~Oval()
{
	// ToDo: Add your specialized code here and/or call the base class
}

//##ModelId=46F674F4000F
Oval::Oval()
{
	// ToDo: Add your specialized code here and/or call the base class
}

//##ModelId=46F67864008C
void Oval::speak() const
{
	// ToDo: Add your specialized code here
	
	static_cast<void>(0);
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
	return;
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
	return;
}


