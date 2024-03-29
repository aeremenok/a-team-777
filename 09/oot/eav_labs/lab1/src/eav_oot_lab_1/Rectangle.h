// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RECTANGLE_46F50C350000_INCLUDED
#define _INC_RECTANGLE_46F50C350000_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

#include <list>
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//�������������
//##ModelId=46F50C350000
class Rectangle 
: public Shape
{
private:
    //��������� �� ��������� ��������������
    //##ModelId=4736B9C40109
    static list<Rectangle*> _rectangles;

	//�����
	//##ModelId=46F51212001F
	float _length;

	//������
	//##ModelId=46F512910128
	float _width;

    //##ModelId=47125E3F035B
    Rectangle(float l, float w, float x, float y);
public:
    //������ ��������� �� ��� ������������ �������������, ���� �� ����� �������� 
    //���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=4736B8E101C5
    static Rectangle* create(float length, float width, float x, float y);

    //##ModelId=46F677C6037B
	virtual ~Rectangle();

	//�������� �����
	//##ModelId=46F67BE003BB
	const float get__length() const;

	//������ �����
	//##ModelId=46F67BE1035D
	void set__length(float value);

	//�������� ������
	//##ModelId=46F67BEC002E
	const float get__width() const;

	//������ ������
	//##ModelId=46F67BED01C5
	void set__width(float value);

    //��������� ������� ������
    //##ModelId=472DF2720138
    virtual float Area() const;
protected:
    //������� ��������� �������������� � �����
    //##ModelId=4712182B029F
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RECTANGLE_46F50C350000_INCLUDED */
