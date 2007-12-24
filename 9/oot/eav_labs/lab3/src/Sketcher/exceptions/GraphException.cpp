// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4741F10E0382
const string& GraphException::get__description() const
{
    return _description;
}

//##ModelId=4741F10E038A
GraphException::GraphException(string message)
{
    _description = message;
}

//##ModelId=4741F10E038C
string GraphException::getException()
{
    return "["+getName()+"]\n\t"+_description;
}

//##ModelId=4741F10E038E
void GraphException::printException()
{
    cout<<getException().c_str()<<endl;
}

//##ModelId=4741F10E0390
const string GraphException::getName() const
{
    return "GraphException";
}
//////////////////////////////////////////////////////////////////////////
