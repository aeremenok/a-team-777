// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "RibbleExistsException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20702C8
RibbleExistsException::RibbleExistsException(string message):GraphException(message)
{
}

//##ModelId=4770E20702D5
string RibbleExistsException::getException()
{
    return GraphException::getException();
}

//##ModelId=4770E20702D7
void RibbleExistsException::printException()
{
    GraphException::printException();
}

//##ModelId=4770E20702D9
const string& RibbleExistsException::get__description() const
{
    return _description;
}

//##ModelId=4770E20702DB
const string RibbleExistsException::getName() const
{
    return "RibbleExistsException";
}
//////////////////////////////////////////////////////////////////////////

