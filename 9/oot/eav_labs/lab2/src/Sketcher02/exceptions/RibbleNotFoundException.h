// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED
#define _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4741F10E02DE
class RibbleNotFoundException 
: virtual public GraphException
{
public:
 //получить информацию об исключении
 //##ModelId=4741F10E02E0
 virtual string getException();

 //вывести информацию об исключении в консоль
 //##ModelId=4741F10E02E2
 virtual void printException();

 //##ModelId=4741F10E02E4
 RibbleNotFoundException(string message);

 //##ModelId=4741F10E02EE
 virtual const string& get__description() const;
	//##ModelId=4741F10E02F0
    virtual const string getName() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED */
