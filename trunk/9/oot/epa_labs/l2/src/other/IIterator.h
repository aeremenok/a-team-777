#ifndef _IITERATOR_H
#define _IITERATOR_H


//внешний интерфейс итератора
template <class T> class IIterator 
{
public:
 
	virtual T* next() = 0;

	virtual T* first() = 0;

	virtual bool hasNext() = 0;

	virtual int getCount() = 0;
};

#endif 
