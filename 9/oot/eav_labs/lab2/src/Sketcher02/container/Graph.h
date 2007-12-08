// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_GRAPH_46F8FA7D014A_INCLUDED
#define _INC_GRAPH_46F8FA7D014A_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include <ostream>
#include <string>
#include <list>

#include "container/Ribble.h"
#include "container/Iterator.h"
#include "container/ExternalGraphIterator.h"

#include "exceptions/GraphException.h"
#include "exceptions/RibbleExistsException.h"
#include "exceptions/RibbleNotFoundException.h"
#include "exceptions/VertexNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
template<class T> class Ribble;
template<class T> class Iterator;
//////////////////////////////////////////////////////////////////////////
//граф на базе списка ребер
//##ModelId=4741F10E0399
template<class T> class Graph 
{
private:
    //список ребер
    //##ModelId=4741F10E03AC
    list< Ribble<T> *>* _ribbleList;

    //внутрениий итератор
    //##ModelId=4741F10E03C8
    template<class T>
        class GraphIterator : public Iterator<T>
    {
    private:
        // локальная копия указателя на список
		//##ModelId=4741F10F006E
        list< Ribble <T>* >* _innerList;

        // итератор обхода списка рёбер
		//##ModelId=4741F10F0073
        list< Ribble <T>* >::iterator _iter;
    public:
        //перейти к первому эл-ту
        //##ModelId=4741F10F0077
        virtual Ribble<T>* first()
        {
            if (_innerList->size() == 0)
            {
                return NULL;
            } 
            else
            {
                _iter = _innerList->begin();
                return *_iter;
            }
        }

        //перейти к последнему эл-ту
        //##ModelId=475326640280
        virtual Ribble<T>* last()
        {
            if (_innerList->size() == 0)
            {
                return NULL;
            } 
            else
            {
                first();
                Ribble<T>* res = NULL;
                while (hasNext())
                {
                    res = next();
                }
                return res;
            }
        }

        //перейти к следующему эл-ту
        //##ModelId=4741F10F0079
        virtual Ribble<T>* next()
        {
            Ribble<T>* res = *_iter;
            _iter++;
            return res;
        }

        //перейти к предыдущему эл-ту
		//##ModelId=475326640282
        virtual Ribble<T>* previous()
        {
            _iter--;
            Ribble<T>* res = *_iter;
            return res;
        }

        //##ModelId=4741F10F007E
        virtual bool hasPrevious()
        {
            return 
                _innerList->size() > 0 &&
                _iter != _innerList->begin();
        }

		//##ModelId=475326640284
        virtual bool hasNext()
        {
            return 
                _innerList->size() > 0 &&
                _iter != _innerList->end();
        }

        //##ModelId=4741F10F0080
        GraphIterator(list< Ribble<T>* >* innerList)
        {
            _innerList = innerList;
            _iter = _innerList->begin();
            cout<<"[graph_iterator] graph iterator created\n";
        }
    };
public:
    // количество ребер в графе
	//##ModelId=47527CD9036B
    int getRibbleCount() const
    {
        return _ribbleList->size(); 
    }

    // связать две вершины графа ребром
	//##ModelId=475A821C0157
    void linkVertices(T* vertex1, T* vertex2)
    {
        Iterator<T>* iter = getIterator();
        // удостоверяемся, что такие вершины есть
        bool v1Present = false;
        bool v2Present = false;
        Ribble<T>* firstRibble = NULL;
        Ribble<T>* secondRibble = NULL;
        while ( iter->hasNext() && !(v1Present && v2Present) )
        {
            Ribble<T>* current = iter->next();
            if (!v1Present && current->contains(vertex1))
            {
                v1Present = true;
                // запоминаем ребро
                firstRibble = current;
            }
            if (!v2Present && current->contains(vertex2))
            {
                v2Present = true;
                // запоминаем ребро
                secondRibble = current;
            }
        }

        if ( !v1Present || !v2Present)
        {
            throw new VertexNotFoundException("no vertex to link");
        }
        else if (v1Present && v2Present)
        { // вершины найдены
            // дуги не храним, избавляемся от них
            if (firstRibble->isLoop())
            {
                removeRibble(firstRibble);
            }
            if (secondRibble->isLoop())
            {
                removeRibble(secondRibble);
            }
            // добавляем ребро
            addRibble(vertex1, vertex2);
        }
    }

    //очистить граф. очистка объектов по указателям не производится
    //##ModelId=4741F10E03B0
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
    //##ModelId=4741F10E03B1
    void addRibble(T* vertex1, T* vertex2)
    {
        if (vertex1 == NULL || vertex2 == NULL)
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }

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

    //добавить вершину в граф, на выходе - вершина, к которой присоединена новая
    //##ModelId=474DE48500EA
    T* addVertex(T* vertex)
    {
        if (vertex == NULL)
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }
        // проверяем, нет ли ребра c одной и той же вершиной
        Iterator<T>* iter = getIterator();

        Ribble<T>* current = NULL;
        while (iter->hasNext())
        {
            current = iter->next();
            
            if ( *(current->get__vertex1()) == *(current->get__vertex2()) )
            { // вершины одинаковы - заменяем одну вершину и выходим
                current->set__vertex2(vertex);
                return current->get__vertex1();
            }
        }
        
        if (current!=NULL)
        { // такого ребра нет - присоединяем к вершине последнего ребра
            _ribbleList->push_back(new Ribble<T>(current->get__vertex2(), vertex));
            return current->get__vertex2();
        } 
        else
        { // ребер еще вообще нет
            _ribbleList->push_back(new Ribble<T>(vertex, vertex));
            return vertex;
        }
    };

    // удалить ребро
	//##ModelId=475A821C0168
    void removeRibble(Ribble<T>* ribble)
    {
        // проверяем, есть ли такое ребро
        // проверка не обязательна, но нужна, чтобы продемонстрировать экспешен =)
        Iterator<T> *iter = getIterator();
        bool isPresent = false;

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

    //удалить ребро по его вершинам
	//##ModelId=4741F10E03B4
    void removeRibble(T* vertex1, T* vertex2)
    {
        cout<<"\n[graph] removing ribble\n";
        Ribble<T>* ribble = new Ribble<T>(vertex1, vertex2);
        removeRibble(ribble);
    }

    // удалить вершину
    //##ModelId=4741F10E03BB
    void removeVertex(T* vertex)
    {
        // проверяем, есть ли такая вершина
        Iterator<T>* iter = getIterator();
        bool isPresent = false;

        while (iter->hasNext())
        {
            Ribble<T>* current = iter->next();

            // запоминаем вершину, ставшую висячей
            T* hangVertex = NULL;
            if (*current->get__vertex1() == *vertex)
            {
                hangVertex = current->get__vertex2();
            } 
            else if (*current->get__vertex2() == *vertex)
            {
                hangVertex = current->get__vertex1();
            }
            
            if (hangVertex != NULL)
            {
                if (!(*current->get__vertex1() == *current->get__vertex2()))
                { // это не та же самая вершина, что удаляем
                    addVertex(hangVertex);
                }
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
	//##ModelId=4741F10E03BD
    GraphIterator<T>* getIterator() const
    {
        return new GraphIterator<T>(_ribbleList);
    }

    //##ModelId=4741F10E03BF
    Graph() 
    {
        _ribbleList = new list< Ribble<T>* >;
        cout<<"[graph] graph created\n";
    };

    //##ModelId=4741F10E03C0
    friend std::ostream& operator<<(std::ostream& o, const Graph<T>& rhs);
};
//////////////////////////////////////////////////////////////////////////
template <class T>
std::ostream& operator<<( std::ostream& o, const Graph<T>& rhs )
{
    Iterator<T>* iter = new ExternalGraphIterator<T>(&rhs);
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
