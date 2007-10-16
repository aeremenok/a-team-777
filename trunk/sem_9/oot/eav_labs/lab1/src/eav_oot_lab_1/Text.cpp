// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Text.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//##ModelId=46F510F20232
char* Text::getText() const
{
    return _content;
}

//##ModelId=46F511550280
void Text::setText( char* text )
{
    _content = text;
}

//##ModelId=46F5119901A5
void Text::appendText( char* text )
{
    // todo
}

//##ModelId=46F676990213
Text::~Text()
{
    delete(_content);
	cout<<"text destroyed"<<endl;
}

//##ModelId=471218CE00DA
ostream& Text::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"text is speaking:\n\t"
        <<_content<<endl;
}

//##ModelId=471220F702FD
Text::Text()
{
    _content = "lorem ipsum dolor";
    cout<<"default text created"<<endl;
}

//##ModelId=47125F23009C
float Text::Area()
{
    return -1.0f;
}

