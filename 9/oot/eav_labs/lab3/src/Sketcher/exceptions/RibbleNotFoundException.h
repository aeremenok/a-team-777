// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED
#define _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20702A8
class RibbleNotFoundException 
: virtual public GraphException
{
public:
 //получить информацию об исключении
 //##ModelId=4770E20702B6
 virtual string getException();

 //вывести информацию об исключении в консоль
 //##ModelId=4770E20702B8
 virtual void printException();

 //##ModelId=4770E20702BA
 RibbleNotFoundException(string message);

 //##ModelId=4770E20702BC
 virtual const string& get__description() const;
	//##ModelId=4770E20702BE
    virtual const string getName() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED */
