// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RECTANGLE_46F50C350000_INCLUDED
#define _INC_RECTANGLE_46F50C350000_INCLUDED

#include "Shape.h"
#include <ostream.h>

class ostream;


//�������������
//##ModelId=46F50C350000
class Rectangle 
: public Shape
{
private:
 //������� ��������� �������������� � �����
 //##ModelId=4712182B029F
 virtual ostream& speak(ostream& os) const;

	//�����
	//##ModelId=46F51212001F
	int _length;
	//������
	//##ModelId=46F512910128
	int _width;
public:

	//##ModelId=47125E3F035B
    Rectangle(int l, int w);

    //##ModelId=46F677C6037B
	virtual ~Rectangle();

    //��������� ������� ������
    //##ModelId=4708F05E03D8
    virtual int Area();

	//�������� �����
	//##ModelId=46F67BE003BB
	const int get__length() const;

	//������ �����
	//##ModelId=46F67BE1035D
	void set__length(int value);

	//�������� ������
	//##ModelId=46F67BEC002E
	const int get__width() const;

	//������ ������
	//##ModelId=46F67BED01C5
	void set__width(int value);
};

#endif /* _INC_RECTANGLE_46F50C350000_INCLUDED */
