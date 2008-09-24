#include "stdafx.h"
#include "Exceptions.h"
//////////////////////////////////////////////////////////////////////////
const string& Exception::get__description() const
{
    return _description;
}

Exception::Exception(string message)
{
    _description = message;
}

string Exception::getException()
{
    return "["+getName()+"]\n\t"+_description;
}

void Exception::printException()
{
    cout<<getException().c_str()<<endl;
}

const string Exception::getName() const
{
    return "Exception";
}

//////////////////////////////////////////////////////////////////////////
string StackFullException::getException()
{
    return  Exception::getException();
}

void StackFullException::printException()
{
     Exception::printException();
}

StackFullException::StackFullException(string message) : Exception(message)
{
    
}

const string& StackFullException::get__description() const
{
    return _description;
}

const string StackFullException::getName() const
{
    return "StackFullException";
}

//////////////////////////////////////////////////////////////////////////
string StackEmptyException::getException()
{
    return Exception::getException();
}

void StackEmptyException::printException()
{
    Exception::printException();
}

StackEmptyException::StackEmptyException(string message) : Exception(message)
{
    
}

const string& StackEmptyException::get__description() const
{
    return _description;
}

const string StackEmptyException::getName() const
{
    return "StackEmptyException";
}

//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////

