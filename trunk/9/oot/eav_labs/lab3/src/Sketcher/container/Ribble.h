// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLE_471BB47802AF_INCLUDED
#define _INC_RIBBLE_471BB47802AF_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "container/Graph.h"
//////////////////////////////////////////////////////////////////////////
//ребро графа
//##ModelId=4741F10E0302
template<class T>
class Ribble 
{
public:
	//##ModelId=4751C556028C
	void set__vertex1(T* value)
	{
        // проверка нужна, чтобы избежать неожиданностей 
        //  при сериализации
        if ( value != NULL && _vertex1 != NULL )
        {
            _vertex1 = value;
        } 
        else
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }
	}

	//##ModelId=4751BEE202EA
	void set__vertex2(T* value)
	{
        // проверка нужна, чтобы избежать неожиданностей 
        //  при сериализации
        if ( value != NULL && _vertex2 != NULL )
        {
            _vertex2 = value;
        } 
        else
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }
    }

    //##ModelId=4741F10E0304
    virtual ~Ribble()
    {
        // вершины по указателям не удаляем, т.к. возможны ещё
        //  указатели на эти вершины, которые (указатели) нам
        //  не доступны
        cout<<"[ribble] ribble destroyed\n";
    }

    //##ModelId=4741F10E0306
    bool operator==(const Ribble<T>& rhs) const
    {
        return rhs.equals(this);
    }

    //сравннение по указателю
	//##ModelId=4741F10E030F
    bool equals(const Ribble<T>* ribble)
    {
        return (*_vertex1 == *(ribble->_vertex1)) && 
               (*_vertex2 == *(ribble->_vertex2));
    }

    // является ли ребро петлей
	//##ModelId=475A821C00AB
    bool isLoop()
    {
        return *_vertex1 == *_vertex2;
    }

    //принадлежит ли вершина ребру
    //##ModelId=4741F10E0311
    bool contains(const T* vertex) const
    {
        return (_vertex1 == vertex) || (_vertex2 == vertex);
    }

    //##ModelId=4741F10E0314
    T* get__vertex2() const
    {
        if (_vertex2 != NULL)
        {
            return _vertex2;
        } 
        else
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }
    }

    //##ModelId=4741F10E0316
    T* get__vertex1() const
    {
        if (_vertex1 != NULL)
        {
            return _vertex1;
        } 
        else
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }
    }

    //получить вершину, отличную от заданной
	//##ModelId=475AD653035B
    T* getAnotherVertex(T* vertex)
    {
        if (isLoop())
        {
            return NULL;
        } 
        else
        {
            if (*_vertex1 == *vertex )
            {
                return _vertex2;
            }
            else
            {
                return _vertex1;
            }
        }
    }

    //##ModelId=4741F10E0318
    Ribble(T* vertex1, T* vertex2): _vertex1(vertex1), _vertex2(vertex2)
    {
        if (_vertex1 == NULL || _vertex2 == NULL)
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }
        cout<<"[ribble] ribble created\n";
    }

private:
    //##ModelId=4741F10E031B
    T* _vertex1;

    //##ModelId=4741F10E031C
    T* _vertex2;
};

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLE_471BB47802AF_INCLUDED */
