// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_OVAL_46F50C54004E_INCLUDED
#define _INC_OVAL_46F50C54004E_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"
//////////////////////////////////////////////////////////////////////////
class ostream;
//����
//##ModelId=46F50C54004E
class Oval 
: public virtual Shape
{
protected:
    //������� ��������� ����� � �����
    //##ModelId=471218B200EA
    virtual ostream& speak(ostream& os) const;

	//������� ������ �����
	//##ModelId=46F5136F00BB
	float _biggerRad;
    //������� ������ �����
    //##ModelId=46F5139C0138
	float _lesserRad;
public:
    //##ModelId=4713C61F0203
    Oval(float bigger, float less);

    //��������� ������� ������
    //##ModelId=4708F071008C
    virtual float Area();

	//�������� ������� ������
	//##ModelId=46F67B2D004E
	const float get__biggerRad() const;

	//������ ������� ������
	//##ModelId=46F67B2D0244
	void set__biggerRad(float value);

	//�������� ������� ������
	//##ModelId=46F67B2F0263
	const float get__lesserRad() const;

	//������ ������� ������
	//##ModelId=46F67B30008E
	void set__lesserRad(float value);

	//##ModelId=46F674D6002E
	virtual ~Oval();
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_OVAL_46F50C54004E_INCLUDED */
