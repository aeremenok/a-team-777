// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXT_46F50C7401C5_INCLUDED
#define _INC_TEXT_46F50C7401C5_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//�����
//##ModelId=46F50C7401C5
class Text 
: public Shape
{
protected:
    //���������� ������
    //##ModelId=46F510A50251
    char* _content;

    //������� ��������� ������ � �����
    //##ModelId=471218CE00DA
    virtual ostream& speak(ostream& os) const;
public:
    //��������� ������� ������
    //##ModelId=47125F23009C
    virtual int Area();

    //##ModelId=471220F702FD
    Text();

	//##ModelId=46F676990213
	virtual ~Text();

	//##ModelId=46F510F20232
    char* getText() const;

	//##ModelId=46F511550280
    void setText(char* text);

	//�������� ����� ����� � �������
	//##ModelId=46F5119901A5
    void appendText(char* text);
};

#endif /* _INC_TEXT_46F50C7401C5_INCLUDED */
