// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "TextInOval.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////

//##ModelId=46F677F2002E
TextInOval::~TextInOval()
{
	cout<<"[text in oval] text in oval destroyed"<<endl;
}

//##ModelId=471219010148
ostream& TextInOval::speak(ostream& os) const
{
    return 
        Shape::speak(os)
        <<"[text_in_oval] oval chords: ("
        <<_rad1<<", "<<_rad2<<")"<<endl
        <<"[text_in_oval] text content: "
        <<_content.c_str()<<endl
        ;
}

//##ModelId=4713C74D038A
TextInOval::TextInOval(float big, float less):
Text(), Oval(big, less)
{
    cout<<"[text_in_oval] text in oval created"<<endl;
}

//##ModelId=472DF1EC00DA
TextInOval::TextInOval( float big, float less, std::string text ):
Text(text), Oval(big, less)
{
    cout<<"[text_in_oval] text in oval created"<<endl;
}

//##ModelId=472DDB2C0280
bool TextInOval::operator==(const TextInOval& rhs) const
{
    return Oval::operator ==(rhs) &&
           Text::operator ==(rhs);
}

//##ModelId=472DF2A20119
float TextInOval::Area() const
{
    return Oval::Area();
}

//##ModelId=472DFE0402CE
int TextInOval::getName() const
{
    return TEXT_IN_OVAL;
}
//////////////////////////////////////////////////////////////////////////
