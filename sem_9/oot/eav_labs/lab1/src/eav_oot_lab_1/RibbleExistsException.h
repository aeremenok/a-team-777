// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED
#define _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F7F900290
class RibbleExistsException 
: virtual public GraphException
{
public:
 //##ModelId=471F991501E4
 RibbleExistsException(string message);

 //получить информацию об исключении
 //##ModelId=471F99170139
 virtual string getException();

 //вывести информацию об исключении в консоль
 //##ModelId=471F9917031E
 virtual void printException();

 //##ModelId=471F995102CE
 virtual const string& get__description() const;
    virtual const string getName() const;    
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED */
