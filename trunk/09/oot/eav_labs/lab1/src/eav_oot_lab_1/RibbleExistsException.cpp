// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "RibbleExistsException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F991501E4
RibbleExistsException::RibbleExistsException(string message):GraphException(message)
{
//     string s = this->getName() + " has been thrown\n";
//     cout<<s;
}

//##ModelId=471F99170139
string RibbleExistsException::getException()
{
    return GraphException::getException();
}

//##ModelId=471F9917031E
void RibbleExistsException::printException()
{
    GraphException::printException();
}

//##ModelId=471F995102CE
const string& RibbleExistsException::get__description() const
{
    return _description;
}

//##ModelId=4721A0BA01F4
const string RibbleExistsException::getName() const
{
    return "RibbleExistsException";
}
//////////////////////////////////////////////////////////////////////////

