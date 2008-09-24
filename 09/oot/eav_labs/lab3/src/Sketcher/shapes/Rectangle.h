// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RECTANGLE_46F50C350000_INCLUDED
#define _INC_RECTANGLE_46F50C350000_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"
//////////////////////////////////////////////////////////////////////////
//�������������
//##ModelId=4770E2070333
class Rectangle2 
: public Shape
{
private:
    //��������� �� ��������� ��������������
    //##ModelId=4770E2070346
    static list<Rectangle2*> _rectangles;

	//�����
	//##ModelId=4770E2070353
	float _length;

	//������
	//##ModelId=4770E2070354
	float _width;

    //##ModelId=4770E2070362
    Rectangle2(float l, float w, float x, float y);
public:
	// Virtual draw operation
	//##ModelId=4770E2070372
	virtual void Draw(CDC* pDC, CElement* pElement = 0, bool isIdVisible = true);

	//������������� ���� �������
	//##ModelId=4770E2070377
	virtual int getType() const;

	//##ModelId=4770E2070379
	Rectangle2();

	//##ModelId=4770E207037A
	virtual void Serialize(CArchive& ar);

    //������ ��������� �� ��� ������������ �������������, ���� �� ����� �������� 
    //���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=4770E2070383
    static Rectangle2* create(float length = NULL, float width = NULL, float x = NULL, float y = NULL);

    // ������� ������������� �� ������ �����
	//##ModelId=4770E2070391
    static Rectangle2* create(CPoint Start, CPoint End, COLORREF aColor);

    //##ModelId=4770E2070396
	virtual ~Rectangle2();

	//�������� �����
	//##ModelId=4770E2070398
	const float get__length() const;

	//������ �����
	//##ModelId=4770E20703A1
	void set__length(float value);

	//�������� ������
	//##ModelId=4770E20703A3
	const float get__width() const;

	//������ ������
	//##ModelId=4770E20703A5
	void set__width(float value);

    //��������� ������� ������
    //##ModelId=4770E20703A7
    virtual float Area() const;
protected:
    //������� ��������� �������������� � �����
    //##ModelId=4770E20703B0
    virtual std::ostream& speak(std::ostream& os) const;
};

//##ModelId=4754603F0290

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RECTANGLE_46F50C350000_INCLUDED */
