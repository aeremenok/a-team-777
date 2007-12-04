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

    //�������� ���������� �� ����������
    virtual string getException();

    //������� ���������� �� ���������� � �������
    virtual void printException();
   
protected:

    // �������� ����������
	virtual const string getName() const;

    //�������� ������
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
