// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_OVAL_46F50C54004E_INCLUDED
#define _INC_OVAL_46F50C54004E_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

#include <list>
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//����
//##ModelId=473EDDF403B9
class Oval
: public virtual Shape
{
private:
    //��������� �� ��������� �����
    //##ModelId=473EDDF403BE
    static list<Oval*> _ovals;
protected:
    Oval(float rad1, float rad2, float x, float y);

	//������� ������ �����
	//##ModelId=473EDDF403CD
	float _rad1;

    //������� ������ �����
    //##ModelId=473EDDF403CE
	float _rad2;

    //������� ��������� ����� � �����
    //##ModelId=473EDDF403D8
    virtual std::ostream& speak(std::ostream& os) const;
public:
    //##ModelId=4751AC7A02BF
	Oval();

	// Virtual draw operation
	//##ModelId=4754601601A5
	virtual void Draw(CDC* pDC, CElement* pElement = 0, bool isIdVisible = true);

	//������������� ���� �������
	//##ModelId=4751CD0F0196
	virtual int getType() const;

	//##ModelId=475168E301A5
	virtual void Serialize(CArchive& ar);

    //������ ��������� �� ��� ������������ ����, ���� �� ����� �������� ���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=473EDDF403DB
    //static Oval* create(float rad1 = NULL, float rad2 = NULL, float x = NULL, float y = NULL);

	//##ModelId=474055EF02FD
    static Oval* create(CPoint Start, CPoint End, COLORREF aColor);

    //##ModelId=473EDDF50001
	virtual ~Oval();

	//�������� ������� ������
	//##ModelId=473EDDF50003
	const float getRad1() const;

	//������ ������� ������
	//##ModelId=473EDDF50005
	void setRad1(float value);

	//�������� ������� ������
	//##ModelId=473EDDF50007
	const float getRad2() const;

	//������ ������� ������
	//##ModelId=473EDDF50009
	void setRad2(float value);

    //��������� ������� ������
    //##ModelId=473EDDF5000F
    virtual float Area() const;
};

//##ModelId=4754601601A5


//////////////////////////////////////////////////////////////////////////
#endif /* _INC_OVAL_46F50C54004E_INCLUDED */
