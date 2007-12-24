// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "VertexNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4741F10E00CC
string VertexNotFoundException::getException()
{
    return GraphException::getException();
}

//##ModelId=4741F10E00CE
void VertexNotFoundException::printException()
{
    GraphException::printException();
}

//##ModelId=4741F10E00DB
VertexNotFoundException::VertexNotFoundException(string message) : GraphException(message)
{
}

//##ModelId=4741F10E00DD
const string& VertexNotFoundException::get__description() const
{
    return _description;
}

//##ModelId=4741F10E00DF
const string VertexNotFoundException::getName() const
{
    return "VertexNotFoundException";
}
//////////////////////////////////////////////////////////////////////////
