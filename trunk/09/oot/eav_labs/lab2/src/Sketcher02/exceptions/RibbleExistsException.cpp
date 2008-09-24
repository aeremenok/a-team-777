// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "RibbleExistsException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4741F10E02F4
RibbleExistsException::RibbleExistsException(string message):GraphException(message)
{
}

//##ModelId=4741F10E02F6
string RibbleExistsException::getException()
{
    return GraphException::getException();
}

//##ModelId=4741F10E02F8
void RibbleExistsException::printException()
{
    GraphException::printException();
}

//##ModelId=4741F10E02FE
const string& RibbleExistsException::get__description() const
{
    return _description;
}

//##ModelId=4741F10E0300
const string RibbleExistsException::getName() const
{
    return "RibbleExistsException";
}
//////////////////////////////////////////////////////////////////////////

