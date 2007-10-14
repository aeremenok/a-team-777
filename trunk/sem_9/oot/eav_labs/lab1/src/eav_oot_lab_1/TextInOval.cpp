// Copyright (C) 1991 - 1999 Rational Software Corporation

#include "TextInOval.h"
#include <ostream.h>


//##ModelId=46F677F2002E
TextInOval::~TextInOval()
{
	// ToDo: Add your specialized code here and/or call the base class
}

//##ModelId=471219010148
ostream& TextInOval::speak(ostream& os) const
{
    return Oval::speak(os)<<"текст:\n\t"<<_content<<endl;
}

