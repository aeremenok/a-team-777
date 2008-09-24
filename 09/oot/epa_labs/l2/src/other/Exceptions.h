#ifndef _EXCEPTION_H
#define _EXCEPTION_H
//////////////////////////////////////////////////////////////////////////

#include <string>
#include <ostream.h>
using namespace std;
//////////////////////////////////////////////////////////////////////////
class Exception 
{
public:

    virtual const string& get__description() const;
    
	Exception(string message);

    //получить информацию об исключении
    virtual string getException();

    //вывести информацию об исключении в консоль
    virtual void printException();
   
protected:

    // название исключения
	virtual const string getName() const;

    //описание ошибки
    string _description;
};


//////////////////////////////////////////////////////////////////////////
class StackFullException : virtual public Exception
{
public:
	
	virtual string getException();

	virtual void printException();

	StackFullException(string message);

	virtual const string& get__description() const;

	virtual const string getName() const;

};
//////////////////////////////////////////////////////////////////////////
class StackEmptyException : virtual public Exception
{
public:
	
	virtual string getException();

	virtual void printException();

	StackEmptyException(string message);

	virtual const string& get__description() const;

	virtual const string getName() const;

};
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
#endif 
