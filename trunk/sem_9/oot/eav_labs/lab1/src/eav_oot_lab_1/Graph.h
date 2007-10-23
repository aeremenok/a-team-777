// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_GRAPH_46F8FA7D014A_INCLUDED
#define _INC_GRAPH_46F8FA7D014A_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include <list>

#include "Ribble.h"
#include "Iterator.h"
//////////////////////////////////////////////////////////////////////////
using namespace std;
//////////////////////////////////////////////////////////////////////////
//граф на базе списка ребер
//##ModelId=46F8FA7D014A
template<class T> class Graph 
{
private:
    //внутрениий итератор
    //##ModelId=471C6F3A0222
    template<class T>
        class GraphIterator : public Iterator<T>
    {
    public:
        //перейти к первому эл-ту
        //##ModelId=471E5F210177
        virtual Ribble<T>* first()
        {

        }

        //перейти к следующему эл-ту
        //##ModelId=471E5F21031C
        virtual Ribble<T>* next()
        {

        }

        //##ModelId=471E5A1F0213
        GraphIterator()
        {

        }
    };

    //список ребер
    //##ModelId=471BB7C90138
    list< Ribble<T> >* _ribbleList;
public:
    //добавить ребро
    //##ModelId=471BBA1B0177
    void addRibble(T vertex1, T vertex2)
    {
        Ribble<T> ribble(vertex1, vertex2);
        addRibble(ribble);
    };

    //добавить готовое ребро
    //##ModelId=471BBA680399
    void addRibble(Ribble<T> ribble)
    {
        // проверяем, нет ли уже такого ребра
        list< Ribble<T> >::iterator iter;
        bool isPresent = false;
        for (iter = _ribbleList->begin(); iter != _ribbleList->end(); iter++)
        {
            if (*(iter) == ribble)
            {
                isPresent = true;
                iter = _ribbleList->end();
            }
        }
        // если нет - добавляем
        if (!isPresent)
        {
            _ribbleList->push_back(ribble);
        }
    };

    //удалить ребро, не удаляя его вершин
    //##ModelId=471BBA9D038A
    void removeRibble(Ribble<T> ribble)
    {
        _ribbleList->remove(ribble);
    };

    //удалить вершину
    //##ModelId=471BBAE20290
    void removeVertex(T vertex)
    {
        list< Ribble<T> >::iterator iter;
        for (iter = _ribbleList->begin(); iter != _ribbleList->end(); iter++)
        {
            if ((*iter).contains(T))
            {
                _ribbleList->remove(*iter);
            }
        }
    };

    //##ModelId=471BB2E30271
    Graph() 
    {
        
    };

    //##ModelId=471BB2E30280
    virtual ~Graph()
    {
        _ribbleList->clear();
    };
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_GRAPH_46F8FA7D014A_INCLUDED */
