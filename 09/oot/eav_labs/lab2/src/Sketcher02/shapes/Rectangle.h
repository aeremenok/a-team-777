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
class Rectangle2 
: public Shape
{
private:
    //��������� �� ��������� ��������������
    //##ModelId=473EDDF40370
    static list<Rectangle2*> _rectangles;

	//�����
	//##ModelId=473EDDF4037C
	float _length;

	//������
	//##ModelId=473EDDF4037D
	float _width;

    //##ModelId=473EDDF4038A
    Rectangle2(float l, float w, float x, float y);
public:
	// Virtual draw operation
	//##ModelId=4754603F0290
	virtual void Draw(CDC* pDC, CElement* pElement = 0, bool isIdVisible = true);

	//������������� ���� �������
	//##ModelId=4751CD2403C8
	virtual int getType() const;

	//##ModelId=4751AC870261
	Rectangle2();

	//##ModelId=4751692C0119
	virtual void Serialize(CArchive& ar);

    //������ ��������� �� ��� ������������ �������������, ���� �� ����� �������� 
    //���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=473EDDF4038F
    static Rectangle2* create(float length = NULL, float width = NULL, float x = NULL, float y = NULL);

    // ������� ������������� �� ������ �����
	//##ModelId=474055EF0280
    static Rectangle2* create(CPoint Start, CPoint End, COLORREF aColor);

    //##ModelId=473EDDF4039B
	virtual ~Rectangle2();

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
    virtual std::ostream& speak(std::ostream& os) const;
};

//##ModelId=4754603F0290

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RECTANGLE_46F50C350000_INCLUDED */
