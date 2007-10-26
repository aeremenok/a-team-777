// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "VertexNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471F98F6036B
string VertexNotFoundException::getException()
{
    return GraphException::getException();
}

//##ModelId=471F98F7013A
void VertexNotFoundException::printException()
{
    GraphException::printException();
}

//##ModelId=471F98F803CA
VertexNotFoundException::VertexNotFoundException(string message) : GraphException(message)
{
//     string s = this->getName() + " has been thrown\n";
//     cout<<s.c_str();
}

//##ModelId=471F9958037A
const string& VertexNotFoundException::get__description() const
{
    return _description;
}

//##ModelId=4721A0BA0167
const string VertexNotFoundException::getName() const
{
    return "VertexNotFoundException";
}
//////////////////////////////////////////////////////////////////////////
