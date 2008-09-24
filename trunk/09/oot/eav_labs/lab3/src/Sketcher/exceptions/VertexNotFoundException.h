// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED
#define _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E206000A
class VertexNotFoundException 
: virtual public GraphException
{
public:
    //получить информацию об исключении
    //##ModelId=4770E2060016
    virtual string getException();

    //вывести информацию об исключении в консоль
    //##ModelId=4770E2060018
    virtual void printException();

    //##ModelId=4770E206001A
    VertexNotFoundException(string message);

    //##ModelId=4770E206001C
    virtual const string& get__description() const;

	//##ModelId=4770E206001E
    virtual const string getName() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_VERTEXNOTFOUNDEXCEPTION_471F7FBF03D8_INCLUDED */
