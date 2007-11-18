// Copyright (C) 1991 - 1999 Rational Software Corporation
#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
#define _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"
#include "Text.h"

#include <list>
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//����� � �����
//##ModelId=473EDDF401E4
class TextInOval 
: public virtual Oval
, public virtual Text
{
private:
    //##ModelId=473EDDF401E7
    TextInOval(float rad1, float rad2, std::string content, float x, float y);

    //��������� �� ��������� ������ � ������
    //##ModelId=473EDDF40223
    static list<TextInOval*> _textsInOvals;
public:
    //������ ��������� �� ��� ������������ ����� � �����, ���� �� ����� �������� 
    //���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=473EDDF40232
    static TextInOval* create(float rad1, float rad2, std::string content, float x, float y);

	//##ModelId=474055EF00AB
    static TextInOval* create(CPoint Start, CPoint End, COLORREF aColor);

	//##ModelId=473EDDF40245
	virtual ~TextInOval();
    
    //��������� ������� ������
    //##ModelId=473EDDF40252
    virtual float Area() const;

    //Virtual draw operation
    //##ModelId=473EF4F500DA
	virtual void Draw(CDC* pDC);
protected:
    //������� ��������� ������ � ����� � �����
    //##ModelId=473EDDF40254
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
