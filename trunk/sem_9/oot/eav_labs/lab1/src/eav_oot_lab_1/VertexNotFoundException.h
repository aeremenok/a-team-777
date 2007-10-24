// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED
#define _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F7FBF03D8
class VertexNotFoundException 
: virtual public GraphException
{
public:
    //получить информацию об исключении
    //##ModelId=471F98F6036B
    virtual string getException();

    //вывести информацию об исключении в консоль
    //##ModelId=471F98F7013A
    virtual void printException();

    //##ModelId=471F98F803CA
    VertexNotFoundException(string message);

    //##ModelId=471F9958037A
    virtual const string& get__description() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED */
