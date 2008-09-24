// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED
#define _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Graph.h"
#include "Iterator.h"
#include "Ribble.h"
//////////////////////////////////////////////////////////////////////////
template<class T> class Graph;
//////////////////////////////////////////////////////////////////////////
//внешний итератор
//##ModelId=474088CA0242
template<class T>
class ExternalGraphIterator : public Iterator<T>
{
public:
    //##ModelId=4740896802EE
    virtual Ribble<T>* first()
    {
        return _iterator->first();
    }

    //##ModelId=474089F10280
    virtual Ribble<T>* next()
    {
        return _iterator->next();
    }

    //##ModelId=47408A050204
    ExternalGraphIterator(const Graph<T>* graph)
    {
        attach(graph);
        cout<<"[ExternalGraphIterator] external iterator created\n";
    }

    //##ModelId=47408A0900AB
    virtual bool hasNext()
    {
        return _iterator->hasNext();
    }

    //присоединить итератор к графу
    //##ModelId=47408A3D0251
    void attach(const Graph<T>* graph)
    {
        _graph = graph;
        _iterator = _graph->getIterator();
    }
private:
    //указатель на обходимый граф
    //##ModelId=47408A9901C5
    const Graph<T>* _graph;

    //указатель на используемый итератор
    //##ModelId=47408AFA01C5
    Iterator<T>* _iterator;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED */
