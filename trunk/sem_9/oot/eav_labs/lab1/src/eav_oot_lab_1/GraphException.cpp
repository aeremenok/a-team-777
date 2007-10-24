// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
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
    return "exception "+getName()+":\n"+_description;
}

//##ModelId=471F878E02DE
void GraphException::printException()
{
    cout<<getException().c_str()<<endl;
}

const string GraphException::getName() const
{
    return "GraphException";
}
//////////////////////////////////////////////////////////////////////////
