// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
#define _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"
#include "Text.h"
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//����� � �����
//##ModelId=46F50C7B01D4
class TextInOval 
: public Oval
, public Text
{
public:
    /************************************************************************/
    /* ������������ � ����������                                            */
    /************************************************************************/
    //##ModelId=4713C74D038A
    TextInOval(float big, float less);

	//##ModelId=472DF1EC00DA
    TextInOval(float big, float less, std::string text);

	//##ModelId=46F677F2002E
	virtual ~TextInOval();
    
    /************************************************************************/
    /* ������ ������                                                        */
    /************************************************************************/    
    //##ModelId=472DDB2C0280
    virtual bool operator==(const TextInOval& rhs) const;

    //��������� ������� ������
    //##ModelId=472DF2A20119
    virtual float Area() const;
protected:
    //��� ������
    //##ModelId=472DFE0402CE
    virtual int getName() const;

    //������� ��������� ������ � ����� � �����
    //##ModelId=471219010148
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
