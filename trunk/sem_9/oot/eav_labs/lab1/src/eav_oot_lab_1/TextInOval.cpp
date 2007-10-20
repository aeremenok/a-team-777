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
        os<<
        "text_in_oval is speaking:\n\t"<<
        // todo
        Shape::speak(os)
        <<"\toval chords: ("<<_rad1<<", "<<_rad2<<")"<<endl
        <<"\ttext content: "<<_content<<endl;
}

//##ModelId=4713C74D038A
TextInOval::TextInOval(float big, float less):
Text(), Oval(big, less)
{
    cout<<"text in oval created"<<endl;
}

