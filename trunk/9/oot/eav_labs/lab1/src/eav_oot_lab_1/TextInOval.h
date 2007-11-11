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
//##ModelId=46F50C7B01D4
class TextInOval 
: public Oval
, public Text
{
private:
    //##ModelId=472DF1EC00DA
    TextInOval(float rad1, float rad2, std::string content, float x, float y);

    //��������� �� ��������� ������ � ������
    //##ModelId=4736C5F102FD
    static list<TextInOval*> _textsInOvals;
public:
    //������ ��������� �� ��� ������������ ����� � �����, ���� �� ����� �������� 
    //���������
    //���� ������ �� ���������� - ������� �����
    //##ModelId=4736C62903A9
    static TextInOval* create(float rad1, float rad2, std::string content, float x, float y);

	//##ModelId=46F677F2002E
	virtual ~TextInOval();
    
    //��������� ������� ������
    //##ModelId=472DF2A20119
    virtual float Area() const;
protected:
    //������� ��������� ������ � ����� � �����
    //##ModelId=471219010148
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
