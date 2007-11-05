// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "Text.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//##ModelId=46F510F20232
std::string Text::getText() const
{
    return _content;
}

//##ModelId=46F511550280
void Text::setText( std::string text )
{
    _content = text;
}

//##ModelId=46F676990213
Text::~Text()
{
	cout<<"[text] text destroyed"<<endl;
}

//##ModelId=471218CE00DA
ostream& Text::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[text] text content: "
        <<_content.c_str()<<endl;
}

//##ModelId=471220F702FD
Text::Text()
{
    _content = "lorem ipsum dolor";
    cout<<"[text] default text created"<<endl;
}

//##ModelId=472DF1EC01B5
Text::Text( std::string text )
{
    _content = text;
    cout<<"[text] text created"<<endl;
}

//##ModelId=472DDB2602DE
bool Text::operator==( const Text& rhs ) const
{
    return 
        Shape::operator ==(rhs) &&
        ( _content == rhs._content);
}

//##ModelId=472DF2970261
float Text::Area() const
{
    return -1;
}

//##ModelId=472DFDFD0128
int Text::getName()
{
    return TEXT;
}
//////////////////////////////////////////////////////////////////////////