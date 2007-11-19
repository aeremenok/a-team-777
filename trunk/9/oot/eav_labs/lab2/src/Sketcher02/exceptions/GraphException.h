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
//##ModelId=4741F10E0381
class GraphException 
{
public:
    //##ModelId=4741F10E0382
    virtual const string& get__description() const;

    //##ModelId=4741F10E038A
    GraphException(string message);

    //получить информацию об исключении
    //##ModelId=4741F10E038C
    virtual string getException();

    //вывести информацию об исключении в консоль
    //##ModelId=4741F10E038E
    virtual void printException();
   
protected:
    // название исключения
	//##ModelId=4741F10E0390
    virtual const string getName() const;

    //описание ошибки
    //##ModelId=4741F10E0393
    string _description;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_GRAPHEXCEPTION_471F849B00AB_INCLUDED */
