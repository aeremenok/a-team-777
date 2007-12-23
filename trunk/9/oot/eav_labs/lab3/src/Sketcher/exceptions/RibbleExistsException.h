// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED
#define _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4741F10E02F2
class RibbleExistsException 
: virtual public GraphException
{
public:
 //##ModelId=4741F10E02F4
 RibbleExistsException(string message);

 //получить информацию об исключении
 //##ModelId=4741F10E02F6
 virtual string getException();

 //вывести информацию об исключении в консоль
 //##ModelId=4741F10E02F8
 virtual void printException();

 //##ModelId=4741F10E02FE
 virtual const string& get__description() const;
	//##ModelId=4741F10E0300
    virtual const string getName() const;    
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED */
