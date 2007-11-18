// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F868D0109
const string& GraphException::get__description() const
{
    return _description;
}

//##ModelId=471F872800BB
GraphException::GraphException(string message)
{
    _description = message;
}

//##ModelId=471F8742038A
string GraphException::getException()
{
    return "["+getName()+"]\n\t"+_description;
}

//##ModelId=471F878E02DE
void GraphException::printException()
{
    cout<<getException().c_str()<<endl;
}

//##ModelId=4721A0BA02FD
const string GraphException::getName() const
{
    return "GraphException";
}
//////////////////////////////////////////////////////////////////////////
