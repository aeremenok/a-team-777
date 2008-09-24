// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_SHAPE_46F50BDC00BB_INCLUDED
#define _INC_SHAPE_46F50BDC00BB_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include <ostream>
#include <list>

#include "../Elements.h"
//////////////////////////////////////////////////////////////////////////
using std::list;
//////////////////////////////////////////////////////////////////////////
//���������� ������
//##ModelId=4770E207021A
class Shape
: public CElement
{
private:
    //������� ��������
    //##ModelId=4770E207021C
    static int _counter;

    //������������� �������
    //##ModelId=4770E207022A
    int _id;
protected:
    //�������� ������. ��� ������ �� ��������� ���������� � ������ ���������
    //##ModelId=4770E207022B
    float _x;
    
    //�������� ������. ��� ������ �� ��������� ���������� � ������ ���������
    //##ModelId=4770E207022C
	float _y;

    //##ModelId=4770E2070239
    Shape(float x, float y);
    
    //������� ��������� ������ � �����
    //##ModelId=4770E207023C
    virtual std::ostream& speak(std::ostream& os) const;
public:
	//������������� ���� �������
	//##ModelId=4770E207023F
    virtual int getType() const =0;

    //������������� ������ ������������ ��� ������������ ���������
	//##ModelId=4770E2070249
	const int get__id() const;

	//##ModelId=4770E207024B
	Shape();

	//##ModelId=4770E207024C
	virtual void Serialize(CArchive& ar);

    //������ ������������� ������
    //##ModelId=4770E207024F
    void drawID(CDC* pDC, bool isIdVisible = true) const;

	//�������� ������ ������
	//##ModelId=4770E2070258
	virtual void resize(CPoint Start, CPoint End);

	//##ModelId=4770E207025C
	virtual void Move(CSize& aSize);

    //##ModelId=4770E207025F
    virtual ~Shape();

    //�������� �������� ������
    //##ModelId=4770E2070261
    const float get__y() const;
    
    //������ �������� ������
    //##ModelId=4770E2070269
    void set__y(float value);
    
    //�������� �������� ������
    //##ModelId=4770E207026B
    const float get__x() const;
    
    //������ ������� ������
    //##ModelId=4770E207026D
	void set__x(float value);

    //�������� ������ � �����
    //##ModelId=4770E207027E
    friend std::ostream& operator<<(std::ostream& o, const Shape& rhs);

    //##ModelId=4770E207026F
    virtual bool operator==(const Shape& rhs) const;

	//��������� ������ � �������� �����
	//##ModelId=4770E2070279
	void moveToPoint(float x_pos, float y_pos);

    //��������� ������� ������
    //##ModelId=4770E207027C
    virtual float Area() const = 0;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_SHAPE_46F50BDC00BB_INCLUDED */
