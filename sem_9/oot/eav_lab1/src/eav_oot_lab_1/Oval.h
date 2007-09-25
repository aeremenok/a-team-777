// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_OVAL_46F50C54004E_INCLUDED
#define _INC_OVAL_46F50C54004E_INCLUDED

#include "Shape.h"

//овал
//##ModelId=46F50C54004E
class Oval 
: public Shape
{
public:
	//получить больший радиус
	//##ModelId=46F67B2D004E
	const int get__biggerRad() const;

	//задать больший радиус
	//##ModelId=46F67B2D0244
	void set__biggerRad(int value);

	//получить меньший радиус
	//##ModelId=46F67B2F0263
	const int get__lesserRad() const;

	//задать меньший радиус
	//##ModelId=46F67B30008E
	void set__lesserRad(int value);

	//##ModelId=46F674D6002E
	virtual ~Oval();

	//##ModelId=46F674F4000F
	Oval();

	//вывести информацию об объекте
	//##ModelId=46F67864008C
	virtual void speak() const;

private:
	//больший радиус овала
	//##ModelId=46F5136F00BB
	int _biggerRad;
	//меньший радиус овала
	//##ModelId=46F5139C0138
	int _lesserRad;

};

#endif /* _INC_OVAL_46F50C54004E_INCLUDED */
