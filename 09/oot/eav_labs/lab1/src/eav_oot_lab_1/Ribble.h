// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLE_471BB47802AF_INCLUDED
#define _INC_RIBBLE_471BB47802AF_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Graph.h"
//////////////////////////////////////////////////////////////////////////
//ребро графа
//##ModelId=471BB47802AF
template<class T>
class Ribble 
{
public:
    //##ModelId=472D97EC0167
    virtual ~Ribble()
    {
        // вершины по указателям не удаляем, т.к. возможны ещё
        //  указатели на эти вершины, которые (указатели) нам
        //  не доступны
        cout<<"[ribble] ribble destroyed\n";
    }

    //##ModelId=471E60A600FA
    bool operator==(const Ribble<T>& rhs) const
    {
        return rhs.equals(this);
    }

    //сравннение по указателю
	//##ModelId=472DDAF4033C
    bool equals(const Ribble<T>* ribble)
    {
        return (*_vertex1 == *(ribble->_vertex1)) && 
               (*_vertex2 == *(ribble->_vertex2));
    }

    //принадлежит ли вершина ребру
    //##ModelId=471E5B6D032C
    bool contains(const T* vertex) const
    {
        return (_vertex1 == vertex) || (_vertex2 == vertex);
    }

    //##ModelId=471E3CE002EE
    const T* get__vertex2() const
    {
        return _vertex2;
    };

    //##ModelId=471BB59F0222
    const T* get__vertex1() const
    {
        return _vertex1;
    };

    //##ModelId=471E4BB5034B
    Ribble(T* vertex1, T* vertex2): _vertex1(vertex1), _vertex2(vertex2)
    {
        cout<<"[ribble] ribble created\n";
    };

private:
    //##ModelId=471BB58F01E4
    const T* _vertex1;

    //##ModelId=471BB5A20157
    const T* _vertex2;
};

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLE_471BB47802AF_INCLUDED */
