// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "VertexNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2060016
string VertexNotFoundException::getException()
{
    return GraphException::getException();
}

//##ModelId=4770E2060018
void VertexNotFoundException::printException()
{
    GraphException::printException();
}

//##ModelId=4770E206001A
VertexNotFoundException::VertexNotFoundException(string message) : GraphException(message)
{
}

//##ModelId=4770E206001C
const string& VertexNotFoundException::get__description() const
{
    return _description;
}

//##ModelId=4770E206001E
const string VertexNotFoundException::getName() const
{
    return "VertexNotFoundException";
}
//////////////////////////////////////////////////////////////////////////
