// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED
#define _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4741F10E00BB
class VertexNotFoundException 
: virtual public GraphException
{
public:
    //получить информацию об исключении
    //##ModelId=4741F10E00CC
    virtual string getException();

    //вывести информацию об исключении в консоль
    //##ModelId=4741F10E00CE
    virtual void printException();

    //##ModelId=4741F10E00DB
    VertexNotFoundException(string message);

    //##ModelId=4741F10E00DD
    virtual const string& get__description() const;

	//##ModelId=4741F10E00DF
    virtual const string getName() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED */
