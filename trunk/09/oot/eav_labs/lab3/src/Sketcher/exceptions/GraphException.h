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
//##ModelId=4770E20800F1
class GraphException 
{
public:
    //##ModelId=4770E20800F2
    virtual const string& get__description() const;

    //##ModelId=4770E20800F4
    GraphException(string message);

    //получить информацию об исключении
    //##ModelId=4770E20800F6
    virtual string getException();

    //вывести информацию об исключении в консоль
    //##ModelId=4770E2080102
    virtual void printException();
   
protected:
    // название исключения
	//##ModelId=4770E2080104
    virtual const string getName() const;

    //описание ошибки
    //##ModelId=4770E2080107
    string _description;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_GRAPHEXCEPTION_471F849B00AB_INCLUDED */
