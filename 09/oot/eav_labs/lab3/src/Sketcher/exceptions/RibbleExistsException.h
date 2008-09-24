// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED
#define _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20702C6
class RibbleExistsException 
: virtual public GraphException
{
public:
 //##ModelId=4770E20702C8
 RibbleExistsException(string message);

 //получить информацию об исключении
 //##ModelId=4770E20702D5
 virtual string getException();

 //вывести информацию об исключении в консоль
 //##ModelId=4770E20702D7
 virtual void printException();

 //##ModelId=4770E20702D9
 virtual const string& get__description() const;
	//##ModelId=4770E20702DB
    virtual const string getName() const;    
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLEEXISTSEXCEPTION_471F7F900290_INCLUDED */
