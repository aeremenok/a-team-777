// Copyright (C) 1991 - 1999 Rational Software Corporation

#include "Text.h"

#include <ostream.h>
#include <cstring>
#include "stdafx.h"

//##ModelId=46F510F20232
CString Text::getText() const
{
    return _content;
}

//##ModelId=46F511550280
void Text::setText( CString* text )
{
    _content = text;
}

//##ModelId=46F5119901A5
void Text::appendText( CString* text )
{
}

//##ModelId=46F676990213
Text::~Text()
{
	cout<<"����� ���������"<<endl;
}

//##ModelId=471218CE00DA
ostream& Text::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"�����:\n\t"<<_content<<endl;
}

//##ModelId=471220F702FD
Text::Text()
{
    _content->Insert(0,
        "�������� �������������� ����� �������� ���� ������ ������ �������� ��������� ���������"
        );
}



//##ModelId=47125F23009C
int Text::Area()
{
 // ToDo: Add your specialized code here
 
 return static_cast<int>(0);
}

