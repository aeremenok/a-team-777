// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED
#define _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "container/Graph.h"
//////////////////////////////////////////////////////////////////////////
template<class T> class Graph;
template<class T> class Iterator;
template<class T> class Ribble;
//////////////////////////////////////////////////////////////////////////
//внешний итератор
//##ModelId=4770E2080278
template<class T>
class ExternalGraphIterator : public Iterator<T>
{
public:
    //##ModelId=4770E2080289
    virtual Ribble<T>* first()
    {
        return _iterator->first();
    }

	//##ModelId=4770E208028B
    virtual Ribble<T>* last()
    {
        return _iterator->last();
    }

    //##ModelId=4770E2080297
    virtual Ribble<T>* next()
    {
        return _iterator->next();
    }

	//##ModelId=4770E2080299
    virtual Ribble<T>* previous()
    {
        return _iterator->previous();
    }

    //##ModelId=4770E208029B
    ExternalGraphIterator(const Graph<T>* graph)
    {
        attach(graph);
        cout<<"[ExternalGraphIterator] external iterator created\n";
    }

    //##ModelId=4770E208029D
    virtual bool hasNext()
    {
        return _iterator->hasNext();
    }

	//##ModelId=4770E208029F
    virtual bool hasPrevious()
    {
        return _iterator->hasPrevious();
    }

    //присоединить итератор к графу
    //##ModelId=4770E20802A1
    void attach(const Graph<T>* graph)
    {
        _graph = graph;
        _iterator = _graph->getIterator();
    }

    // количество ребер в графе, по которому бегаем
	//##ModelId=4770E20802A8
    int getGraphRibbleCount()
    {
        return _graph->getRibbleCount();
    }
private:
    //указатель на обходимый граф
    //##ModelId=4770E20802B8
    const Graph<T>* _graph;

    //указатель на используемый итератор
    //##ModelId=4770E20802BD
    Iterator<T>* _iterator;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_EXTERNALGRAPHITERATOR_474088CA0242_INCLUDED */
