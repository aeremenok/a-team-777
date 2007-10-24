// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "RibbleExistsException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F991501E4
RibbleExistsException::RibbleExistsException(string message):GraphException(message)
{
    cout<<getName().c_str()<<" has been thrown"<<endl;
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
//////////////////////////////////////////////////////////////////////////





















