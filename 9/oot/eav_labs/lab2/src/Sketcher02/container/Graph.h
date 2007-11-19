// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_GRAPH_46F8FA7D014A_INCLUDED
#define _INC_GRAPH_46F8FA7D014A_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include <ostream.h>
#include <string>
#include <list>

#include "container/Ribble.h"
#include "container/Iterator.h"

#include "exceptions/GraphException.h"
#include "exceptions/RibbleExistsException.h"
#include "exceptions/RibbleNotFoundException.h"
#include "exceptions/VertexNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
using namespace std;

class ostream;
template<class T> class Ribble;
template<class T> class Iterator;
//////////////////////////////////////////////////////////////////////////
//граф на базе списка ребер
//##ModelId=46F8FA7D014A
template<class T> class Graph 
{
private:
    //список ребер
    //##ModelId=471BB7C90138
    list< Ribble<T> *>* _ribbleList;

    //внутрениий итератор
    //##ModelId=471C6F3A0222
    template<class T>
        class GraphIterator : public Iterator<T>
    {
    private:
        // локальная копия указателя на список
		//##ModelId=4721A0BB0010
        list< Ribble <T>* >* _innerList;

        // итератор обхода списка рёбер
		//##ModelId=4721A0BB0020
        list< Ribble <T>* >::iterator _iter;
    public:
        //перейти к первому эл-ту
        //##ModelId=471E5F210177
        virtual Ribble<T>* first()
        {
            _iter = _innerList->begin();
            return *_iter;
        }

        //перейти к следующему эл-ту
        //##ModelId=471E5F21031C
        virtual Ribble<T>* next()
        {
            Ribble<T>* res = *_iter;
            _iter++;
            return res;
        }

		//##ModelId=4721A0BB002E
        virtual bool hasNext()
        {
            return _iter != _innerList->end();
        }

        //##ModelId=471E5A1F0213
        GraphIterator(list< Ribble<T>* >* innerList)
        {
            _innerList = innerList;
            _iter = _innerList->begin();
            cout<<"[graph_iterator] graph iterator created\n";
        }
    };
public:
    //очистить граф. очистка объектов по указателям не производится, 
    // т.к. для этого нужно знать тип удаляемого объекта. 
    // это пользователь должен сделать сам
    //##ModelId=472D959B029F
    void clear()
    {
        cout<<"\n[graph] clearing graph\n";
        Iterator<T>* iter = getIterator();
        while (iter->hasNext())
        {
            Ribble<T>* current = iter->next();
            delete current;
        }
        _ribbleList->clear();
        cout<<"[graph] graph cleared\n";
    }

    //добавить ребро
    //##ModelId=471BBA1B0177
    void addRibble(T* vertex1, T* vertex2)
    {
        cout<<"\n[graph] adding ribble, checking if it already exists\n";
       
        // проверяем, нет ли уже такого ребра
        Iterator<T>* iter = getIterator();
        bool isPresent = false;
        Ribble<T>* ribble = new Ribble<T>(vertex1, vertex2);

        while (iter->hasNext() && !isPresent)
        {
            Ribble<T>* current = iter->next();

            if ( current->equals(ribble) )
            {
                isPresent = true;
                throw new RibbleExistsException("cannot add: ribble already exists in graph");
            }
        }

        // если нет - добавляем
        if (!isPresent)
        {
            _ribbleList->push_back(ribble);
            cout<<"[graph] ribble added successfully\n";
        }
    };

    //удалить ребро
	//##ModelId=4721A0BA034B
    void removeRibble(T* vertex1, T* vertex2)
    {
        cout<<"\n[graph] removing ribble\n";

        // проверяем, есть ли такое ребро
        // проверка не обязательна, но нужна, чтобы продемонстрировать экспешен =)
        Iterator<T> *iter = getIterator();
        bool isPresent = false;
        Ribble<T>* ribble = new Ribble<T>(vertex1, vertex2);

        while (iter->hasNext() && !isPresent)
        {
            Ribble<T>* current = iter->next();
            if ( current->equals(ribble) )
            { // если есть - удаляем
                isPresent = true;
                _ribbleList->remove(current);
                current->~Ribble();
                cout<<"[graph] ribble removed successfully\n";
            }
        }
        // если нет - бросаем эксепшен
        if (!isPresent)
        {   
            throw new RibbleNotFoundException("cannot remove: ribble not found");
        }
    }

    // удалить вершину
    //##ModelId=471BBAE20290
    void removeVertex(T* vertex)
    {
        cout<<"\n[graph] removoing all ribbles, containing\n===vertex===\n"
            <<*vertex<<endl;

        // проверяем, есть ли такая вершина
        Iterator<T>* iter = getIterator();
        bool isPresent = false;

        while (iter->hasNext())
        {
            Ribble<T>* current = iter->next();
            if (current->contains(vertex))
            {
                _ribbleList->remove(current);
                current->~Ribble();
                isPresent = true;
                cout<<"\t[graph] ribble containig vertex removed\n";
            }
        }

        if (!isPresent)
        {
            throw new VertexNotFoundException("cannot remove: vertex not found");
        }
        else
        {
            cout<<"[graph] vertex removed successfully\n";
        }
    };

    // получить итератор для обхода графа
	//##ModelId=4721A0BA03A9
    GraphIterator<T>* getIterator() const
    {
        return new GraphIterator<T>(_ribbleList);
    }

    //##ModelId=471BB2E30271
    Graph() 
    {
        _ribbleList = new list< Ribble<T>* >;
        cout<<"[graph] graph created\n";
    };

    //##ModelId=472D958301A5
    friend ostream_withassign& operator<<(ostream_withassign& o, const Graph<T>& rhs);
};
//////////////////////////////////////////////////////////////////////////
template <class T>
ostream_withassign& operator<<( ostream_withassign& o, const Graph<T>& rhs )
{
    Iterator<T>* iter = rhs.getIterator();
    int i = 0;
    while (iter->hasNext())
    {
        o<<"ribble #"<<++i<<endl;
        Ribble<T>* current = iter->next();
        o<<"===vertex 1===\n"<<*(current->get__vertex1())
         <<"===vertex 2===\n"<<*(current->get__vertex2())
         <<endl;
    }
    if (i == 0)
    {
        o<<"[graph] graph is empty"<<endl;
    }
    return o;
}   
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_GRAPH_46F8FA7D014A_INCLUDED */
