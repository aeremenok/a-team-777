// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "RibbleNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4741F10E02E0
string RibbleNotFoundException::getException()
{
    return GraphException::getException();
}

//##ModelId=4741F10E02E2
void RibbleNotFoundException::printException()
{
    GraphException::printException();
}

//##ModelId=4741F10E02E4
RibbleNotFoundException::RibbleNotFoundException(string message) : GraphException(message)
{
    //cout<<this->getName().c_str()<<" has been thrown"<<endl;
}

//##ModelId=4741F10E02EE
const string& RibbleNotFoundException::get__description() const
{
    return _description;
}

//##ModelId=4741F10E02F0
const string RibbleNotFoundException::getName() const
{
    return "RibbleNotFoundException";
}
//////////////////////////////////////////////////////////////////////////

