// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED
#define _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "container/Graph.h"
#include "container/Iterator.h"
#include "container/Ribble.h"
//////////////////////////////////////////////////////////////////////////
template<class T> class Graph;
template<class T> class Iterator;
template<class T> class Ribble;
//////////////////////////////////////////////////////////////////////////
//внешний итератор
//##ModelId=474C8E0401F4
template<class T>
class ExternalGraphIterator : public Iterator<T>
{
public:
    //##ModelId=474C8E040205
    virtual Ribble<T>* first()
    {
        return _iterator->first();
    }

    //##ModelId=474C8E040207
    virtual Ribble<T>* next()
    {
        return _iterator->next();
    }

    //##ModelId=474C8E040209
    ExternalGraphIterator(const Graph<T>* graph)
    {
        attach(graph);
        cout<<"[ExternalGraphIterator] external iterator created\n";
    }

    //##ModelId=474C8E04020B
    virtual bool hasNext()
    {
        return _iterator->hasNext();
    }

    //присоединить итератор к графу
    //##ModelId=474C8E04020D
    void attach(const Graph<T>* graph)
    {
        _graph = graph;
        _iterator = _graph->getIterator();
    }
private:
    //указатель на обходимый граф
    //##ModelId=474C8E04031D
    const Graph<T>* _graph;

    //указатель на используемый итератор
    //##ModelId=474C8E04032D
    Iterator<T>* _iterator;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED */
