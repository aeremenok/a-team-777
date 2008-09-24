// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "RibbleNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20702B6
string RibbleNotFoundException::getException()
{
    return GraphException::getException();
}

//##ModelId=4770E20702B8
void RibbleNotFoundException::printException()
{
    GraphException::printException();
}

//##ModelId=4770E20702BA
RibbleNotFoundException::RibbleNotFoundException(string message) : GraphException(message)
{
    //cout<<this->getName().c_str()<<" has been thrown"<<endl;
}

//##ModelId=4770E20702BC
const string& RibbleNotFoundException::get__description() const
{
    return _description;
}

//##ModelId=4770E20702BE
const string RibbleNotFoundException::getName() const
{
    return "RibbleNotFoundException";
}
//////////////////////////////////////////////////////////////////////////

