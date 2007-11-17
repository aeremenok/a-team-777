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
//##ModelId=473EDDF4036B
class Rectangle 
: public Shape
{
private:
    //��������� �� ��������� ��������������
    //##ModelId=473EDDF40370
    static list<Rectangle*> _rectangles;

	//�����
	//##ModelId=473EDDF4037C
	float _length;

	//������
	//##ModelId=473EDDF4037D
	float _width;

    //##ModelId=473EDDF4038A
    Rectangle(float l, float w, float x, float y);
public:
    //������ ��������� �� ��� ������������ �������������, ���� �� ����� �������� 
    //���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=473EDDF4038F
    static Rectangle* create(float length, float width, float x, float y);

    //##ModelId=473EDDF4039B
	virtual ~Rectangle();

	//�������� �����
	//##ModelId=473EDDF4039D
	const float get__length() const;

	//������ �����
	//##ModelId=473EDDF4039F
	void set__length(float value);

	//�������� ������
	//##ModelId=473EDDF403A1
	const float get__width() const;

	//������ ������
	//##ModelId=473EDDF403A3
	void set__width(float value);

    //��������� ������� ������
    //##ModelId=473EDDF403AA
    virtual float Area() const;
protected:
    //������� ��������� �������������� � �����
    //##ModelId=473EDDF403AC
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RECTANGLE_46F50C350000_INCLUDED */
