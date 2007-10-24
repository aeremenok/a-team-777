// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED
#define _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F7FD6004E
class RibbleNotFoundException 
: virtual public GraphException
{
public:
 //получить информацию об исключении
 //##ModelId=471F98E9009C
 virtual string getException();

 //вывести информацию об исключении в консоль
 //##ModelId=471F98E902C1
 virtual void printException();

 //##ModelId=471F98ED03BB
 RibbleNotFoundException(string message);

 //##ModelId=471F993F000F
 virtual const string& get__description() const;

};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLENOTFOUNDEXCEPTION_471F7FD6004E_INCLUDED */
