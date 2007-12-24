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

	//##ModelId=47532664029F
    virtual Ribble<T>* last()
    {
        return _iterator->last();
    }

    //##ModelId=474C8E040207
    virtual Ribble<T>* next()
    {
        return _iterator->next();
    }

	//##ModelId=4753266402A1
    virtual Ribble<T>* previous()
    {
        return _iterator->previous();
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

	//##ModelId=4753266402A3
    virtual bool hasPrevious()
    {
        return _iterator->hasPrevious();
    }

    //присоединить итератор к графу
    //##ModelId=474C8E04020D
    void attach(const Graph<T>* graph)
    {
        _graph = graph;
        _iterator = _graph->getIterator();
    }

    // количество ребер в графе, по которому бегаем
	//##ModelId=475AD654006D
    int getGraphRibbleCount()
    {
        return _graph->getRibbleCount();
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
