// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "TextInOval.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////

//##ModelId=46F677F2002E
TextInOval::~TextInOval()
{
	cout<<"text in oval destroyed"<<endl;
}

//##ModelId=471219010148
ostream& TextInOval::speak(ostream& os) const
{
    return 
        Shape::speak(os)
        <<"text_in_oval is speaking:\n"
        <<"\toval chords: ("<<_rad1<<", "<<_rad2<<")"<<endl
        <<"\ttext content: "<<_content.c_str()<<endl
        ;
}

//##ModelId=4713C74D038A
TextInOval::TextInOval(float big, float less):
Text(), Oval(big, less)
{
    cout<<"text in oval created"<<endl;
}

