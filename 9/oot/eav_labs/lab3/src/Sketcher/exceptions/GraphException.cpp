// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "GraphException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20800F2
const string& GraphException::get__description() const
{
    return _description;
}

//##ModelId=4770E20800F4
GraphException::GraphException(string message)
{
    _description = message;
}

//##ModelId=4770E20800F6
string GraphException::getException()
{
    return "["+getName()+"]\n\t"+_description;
}

//##ModelId=4770E2080102
void GraphException::printException()
{
    cout<<getException().c_str()<<endl;
}

//##ModelId=4770E2080104
const string GraphException::getName() const
{
    return "GraphException";
}
//////////////////////////////////////////////////////////////////////////
