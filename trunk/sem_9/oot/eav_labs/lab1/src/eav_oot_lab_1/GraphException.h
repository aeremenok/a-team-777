// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_GRAPHEXCEPTION_471F849B00AB_INCLUDED
#define _INC_GRAPHEXCEPTION_471F849B00AB_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include <string>
#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
using namespace std;
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F849B00AB
class GraphException 
{
public:
 //##ModelId=471F868D0109
 virtual const string& get__description() const;

 //##ModelId=471F872800BB
 GraphException(string message);

 //получить информацию об исключении
 //##ModelId=471F8742038A
 virtual string getException();

 //вывести информацию об исключении в консоль
 //##ModelId=471F878E02DE
 virtual void printException();

    
protected:
    virtual const string getName() const;
 //описание ошибки
 //##ModelId=471F866C0000
 string _description;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_GRAPHEXCEPTION_471F849B00AB_INCLUDED */
