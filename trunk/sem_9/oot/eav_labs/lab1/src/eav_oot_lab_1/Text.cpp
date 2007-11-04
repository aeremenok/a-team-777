// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
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

Text::Text( std::string text )
{
    _content = text;
    cout<<"[text] text created"<<endl;
}

bool Text::operator==( const Text& rhs ) const
{
    return 
        Shape::operator ==(rhs) &&
        ( _content == rhs._content);
}
//////////////////////////////////////////////////////////////////////////