// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "RibbleNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F98E9009C
string RibbleNotFoundException::getException()
{
    return GraphException::getException();
}

//##ModelId=471F98E902C1
void RibbleNotFoundException::printException()
{
    GraphException::printException();
}

//##ModelId=471F98ED03BB
RibbleNotFoundException::RibbleNotFoundException(string message) : GraphException(message)
{
}

//##ModelId=471F993F000F
const string& RibbleNotFoundException::get__description() const
{
    return _description;
}

//##ModelId=4721A0BA01D4
const string RibbleNotFoundException::getName() const
{
    return "RibbleNotFoundException";
}
//////////////////////////////////////////////////////////////////////////

