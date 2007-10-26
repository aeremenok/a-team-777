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
    //список ребер
    //##ModelId=471BB7C90138
    list< Ribble<T> >* _ribbleList;

    //внутрениий итератор
    //##ModelId=471C6F3A0222
    template<class T>
        class GraphIterator : public Iterator<T>
    {
    private:
        // локальная копия указателя на список
        list< Ribble <T> >* _innerList;
        // итератор обхода списка рёбер
        list< Ribble <T> >::iterator _iter;
    public:
        //перейти к первому эл-ту
        //##ModelId=471E5F210177
        virtual Ribble<T> first()
        {
            _iter = _innerList->begin();
            return *_iter;
        }

        //перейти к следующему эл-ту
        //##ModelId=471E5F21031C
        virtual Ribble<T> next()
        {
            Ribble<T> res = *_iter;
            _iter++;
            return res;
        }

        virtual bool hasNext()
        {
            return _iter != _innerList->end();
        }

        //##ModelId=471E5A1F0213
        GraphIterator(list< Ribble<T> >* innerList)
        {
            _innerList = innerList;
            _iter = _innerList->begin();
            cout<<"graph iterator created\n";
        }
    };
public:
    //добавить ребро
    //##ModelId=471BBA1B0177
    void addRibble(T vertex1, T vertex2)
    {
        cout<<"adding ribble\n";
        // проверяем, нет ли уже такого ребра
        Iterator<T>* iter = getIterator();
        bool isPresent = false;
        while (iter->hasNext() && !isPresent)
        {
            Ribble<T> current = iter->next();
            if (
                current.get__vertex1() == vertex1 &&
                current.get__vertex2() == vertex2
                )
            {
                isPresent = true;
                throw new RibbleExistsException("cannot add: ribble already exists in graph");
            }
        }
        // если нет - добавляем
        if (!isPresent)
        {
            Ribble<T> ribble(vertex1, vertex2);
            _ribbleList->push_back(ribble);
            cout<<"ribble added successfully\n";
        }
    };

    //удалить ребро
    void removeRibble(T vertex1, T vertex2)
    {
        cout<<"removing ribble\n";
        // проверяем, есть ли такое ребро
        // проверка не обязательна, но нужна, чтобы продемонстрировать экспешен =)
        Iterator<T> *iter = getIterator();
        bool isPresent = false;
        while (iter->hasNext() && !isPresent)
        {
            Ribble<T> current = iter->next();
            if (
                current.get__vertex1() == vertex1 &&
                current.get__vertex2() == vertex2
                )
            { // если есть - удаляем
                isPresent = true;
                _ribbleList->remove(current);
                cout<<"ribble removed successfully\n";
            }
        }
        if (!isPresent)
        {   // нет - бросаем эксепшен
            throw new RibbleNotFoundException("cannot remove: ribble not found");
        }
    }

    // удалить вершину
    //##ModelId=471BBAE20290
    void removeVertex(T vertex)
    {
        cout<<"removoing all ribbles, containing vertex "<<vertex<<endl;
        // проверяем, есть ли такая вершина
        Iterator<T>* iter = getIterator();
        bool isPresent = false;
        while (iter->hasNext())
        {
            Ribble<T> current = iter->next();
            if (current.contains(vertex))
            {
                _ribbleList->remove(current);
                isPresent = true;
                cout<<"\tribble containig vertex removed\n";
            }
        }
        if (!isPresent)
        {
            throw new VertexNotFoundException("cannot remove: vertex not found");
        }
        else
        {
            cout<<"vertex removed successfully\n";
        }
    };

    // получить итератор для обхода графа
    GraphIterator<T>* getIterator()
    {
        return new GraphIterator<T>(_ribbleList);
    }

    //##ModelId=471BB2E30271
    Graph() 
    {
        _ribbleList = new list< Ribble<T> >;
        cout<<"graph created\n";
    };
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_GRAPH_46F8FA7D014A_INCLUDED */
