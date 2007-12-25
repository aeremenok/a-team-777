// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_OVAL_46F50C54004E_INCLUDED
#define _INC_OVAL_46F50C54004E_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"
//////////////////////////////////////////////////////////////////////////
//����
//##ModelId=4770E20703C0
class Oval
: public virtual Shape
{
private:
    //��������� �� ��������� �����
    //##ModelId=4770E20703D2
    static list<Oval*> _ovals;
protected:
	//##ModelId=4770E20703DF
    Oval(float firstRad, float secondRad, float x, float y);

	//������� ������ �����
	//##ModelId=4770E2080007
	float _rad1;

    //������� ������ �����
    //##ModelId=4770E2080008
	float _rad2;

    //������� ��������� ����� � �����
    //##ModelId=4770E2080009
    virtual std::ostream& speak(std::ostream& os) const;
public:
    //##ModelId=4770E2080017
	Oval();

	// Virtual draw operation
	//##ModelId=4770E2080018
	virtual void Draw(CDC* pDC, CElement* pElement = 0, bool isIdVisible = true);

	//������������� ���� �������
	//##ModelId=4770E2080026
	virtual int getType() const;

	//##ModelId=4770E2080028
	virtual void Serialize(CArchive& ar);

    //������ ��������� �� ��� ������������ ����, ���� �� ����� �������� ���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=4770E208002B
    static Oval* create(float firstRad = NULL, float secondRad = NULL, float x = NULL, float y = NULL);

	//##ModelId=4770E2080038
    static Oval* create(CPoint Start, CPoint End, COLORREF aColor);

    //##ModelId=4770E208003D
	virtual ~Oval();

	//�������� ������� ������
	//##ModelId=4770E208003F
	const float getRad1() const;

	//������ ������� ������
	//##ModelId=4770E2080046
	void setRad1(float value);

	//�������� ������� ������
	//##ModelId=4770E2080048
	const float getRad2() const;

	//������ ������� ������
	//##ModelId=4770E208004A
	void setRad2(float value);

    //��������� ������� ������
    //##ModelId=4770E2080056
    virtual float Area() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_OVAL_46F50C54004E_INCLUDED */
