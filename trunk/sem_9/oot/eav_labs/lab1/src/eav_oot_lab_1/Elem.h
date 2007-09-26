// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_ELEM_46F8FABB02B2_INCLUDED
#define _INC_ELEM_46F8FABB02B2_INCLUDED

class Shape;

//##ModelId=46F8FABB02B2
class Elem 
{
public:

	//##ModelId=46F90EFF00B7
	virtual ~Elem();

	//##ModelId=46F90EFF028E
	Elem();


private:
	//##ModelId=46F917550018
	Shape* vertex1;
	//##ModelId=46F8FB7101C7
	Shape* vertex2;

	//##ModelId=46F913BE0325
	Elem* _next;

};

#endif /* _INC_ELEM_46F8FABB02B2_INCLUDED */
