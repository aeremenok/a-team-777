#ifndef _TITERATOR_H
#define _TITERATOR_H

#include "IIterator.h"

template<class T> class TStack;

//внешний итератор
template<class T> class TIterator : public IIterator<T>
{
public:
   
	TIterator(const TStack<T>* stack)
    {
        //_stack = stack;
        _iterator = stack->getIterator();
        cout<<"[TIterator] External iterator created!\n";
    }
	
    virtual T* first()
    {
        return _iterator->first();
    }

    virtual T* next()
    {
        return _iterator->next();
    }

    virtual bool hasNext()
    {
        return _iterator->hasNext();
    }

    
private:
    //указатель на обходимый стек
    const TStack * _stack;

    //указатель на используемый итератор
    IIterator<T>* _iterator;
};
#endif 
