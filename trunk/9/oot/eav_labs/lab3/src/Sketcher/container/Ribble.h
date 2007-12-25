// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLE_471BB47802AF_INCLUDED
#define _INC_RIBBLE_471BB47802AF_INCLUDED
//////////////////////////////////////////////////////////////////////////
//ребро графа
//##ModelId=4770E20702E5
template<class T>
class Ribble 
{
public:
	//##ModelId=4770E20702F6
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

	//##ModelId=4770E20702F8
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

    //##ModelId=4770E20702FA
    virtual ~Ribble()
    {
        // вершины по указателям не удаляем, т.к. возможны ещё
        //  указатели на эти вершины, которые (указатели) нам
        //  не доступны
        cout<<"[ribble] ribble destroyed\n";
    }

    //##ModelId=4770E20702FC
    bool operator==(const Ribble<T>& rhs) const
    {
        return rhs.equals(this);
    }

    //сравннение по указателю
	//##ModelId=4770E2070304
    bool equals(const Ribble<T>* ribble)
    {
        return (*_vertex1 == *(ribble->_vertex1)) && 
               (*_vertex2 == *(ribble->_vertex2));
    }

    // является ли ребро петлей
	//##ModelId=4770E2070306
    bool isLoop()
    {
        return *_vertex1 == *_vertex2;
    }

    //принадлежит ли вершина ребру
    //##ModelId=4770E2070307
    bool contains(const T* vertex) const
    {
        return (_vertex1 == vertex) || (_vertex2 == vertex);
    }

    //##ModelId=4770E207030A
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

    //##ModelId=4770E2070314
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
	//##ModelId=4770E2070316
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

    //##ModelId=4770E2070318
    Ribble(T* vertex1, T* vertex2): _vertex1(vertex1), _vertex2(vertex2)
    {
        if (_vertex1 == NULL || _vertex2 == NULL)
        {
            throw new GraphException("Nillable vertices are not allowed!");
        }
        cout<<"[ribble] ribble created\n";
    }

private:
    //##ModelId=4770E207031B
    T* _vertex1;

    //##ModelId=4770E2070324
    T* _vertex2;
};

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLE_471BB47802AF_INCLUDED */
