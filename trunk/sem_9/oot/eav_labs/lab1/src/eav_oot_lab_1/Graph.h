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
        Ribble<T>* ribble = new Ribble<T>(&vertex1, &vertex2);
        addRibble(ribble);
        delete(ribble);
    };

    //добавить готовое ребро
    //##ModelId=471BBA680399
    void addRibble(const Ribble<T>* ribble)
    {
        // проверяем, нет ли уже такого ребра
        GraphIterator<T>* iter = getIterator();
        bool isPresent = false;
        while (iter->hasNext() && !isPresent)
        {
            Ribble<T> current = iter->next();
            if (
                current.get__vertex1() == ribble->get__vertex1() &&
                current.get__vertex2() == ribble->get__vertex2()
                )
            {
                isPresent = true;
                throw new RibbleExistsException("cannot add: ribble already exists in graph");
            }
        }
        // если нет - добавляем
        if (!isPresent)
        {
            _ribbleList->push_back(*ribble);
            cout<<"ribble added successfully\n";
        }
    };

    void removeRibble(T vertex1, T vertex2)
    {
        const Ribble<T>* ribble = new Ribble<T>(&vertex1, &vertex2);
        removeRibble(ribble);
    }

    //удалить ребро, не удаляя его вершин
    //##ModelId=471BBA9D038A
    void removeRibble(const Ribble<T>* ribble)
    {
        // проверяем, есть ли такое ребро
        // проверка не обязательна, но нужна, чтобы продемонстрировать экспешен =)
        list< Ribble<T> >::iterator iter;
        bool isPresent = false;
        for (iter = _ribbleList->begin(); iter != _ribbleList->end(); iter++)
        {
            if (*(iter) == *ribble)
            {
                isPresent = true;
                iter = _ribbleList->end();
            }
        }
        // если есть - удаляем
        if (isPresent)
        {
            _ribbleList->remove(*ribble);
            cout<<"ribble removed successfully\n";
        }
        else
        {
            throw new RibbleNotFoundException("cannot remove: ribble niot found");
        }
    };

    // удалить вершину
    //##ModelId=471BBAE20290
    void removeVertex(T vertex)
    {
//         // проверяем, есть ли такая вершина
//         list< Ribble<T> >::iterator iter;
//         for (iter = _ribbleList->begin(); iter != _ribbleList->end(); iter++)
//         {
//             if (iter->contains(&vertex))
//             {   // если есть - удаляем
//                 _ribbleList->remove(*iter);
//                 iter = _ribbleList->end();
//                 cout<<"vertex removed successfully\n";
//             }
//             else
//             {
//                 throw new VertexNotFoundException("cannot remove: vertex not found");
//             }
//         }
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
