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
//���� �� ���� ������ �����
//##ModelId=46F8FA7D014A
template<class T> class Graph 
{
private:
    //������ �����
    //##ModelId=471BB7C90138
    list< Ribble<T> *>* _ribbleList;

    //���������� ��������
    //##ModelId=471C6F3A0222
    template<class T>
        class GraphIterator : public Iterator<T>
    {
    private:
        // ��������� ����� ��������� �� ������
		//##ModelId=4721A0BB0010
        list< Ribble <T>* >* _innerList;

        // �������� ������ ������ ����
		//##ModelId=4721A0BB0020
        list< Ribble <T>* >::iterator _iter;
    public:
        //������� � ������� ��-��
        //##ModelId=471E5F210177
        virtual Ribble<T>* first()
        {
            _iter = _innerList->begin();
            return *_iter;
        }

        //������� � ���������� ��-��
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
    //�������� ����. ������� �������� �� ���������� �� ������������, 
    // �.�. ��� ����� ����� ����� ��� ���������� �������. 
    // ��� ������������ ������ ������� ���
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

    //�������� �����
    //##ModelId=471BBA1B0177
    void addRibble(T* vertex1, T* vertex2)
    {
        cout<<"\n[graph] adding ribble, checking if it already exists\n";
       
        // ���������, ��� �� ��� ������ �����
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

        // ���� ��� - ���������
        if (!isPresent)
        {
            _ribbleList->push_back(ribble);
            cout<<"[graph] ribble added successfully\n";
        }
    };

    //������� �����
	//##ModelId=4721A0BA034B
    void removeRibble(T* vertex1, T* vertex2)
    {
        cout<<"\n[graph] removing ribble\n";

        // ���������, ���� �� ����� �����
        // �������� �� �����������, �� �����, ����� ������������������ �������� =)
        Iterator<T> *iter = getIterator();
        bool isPresent = false;
        Ribble<T>* ribble = new Ribble<T>(vertex1, vertex2);

        while (iter->hasNext() && !isPresent)
        {
            Ribble<T>* current = iter->next();
            if ( current->equals(ribble) )
            { // ���� ���� - �������
                isPresent = true;
                _ribbleList->remove(current);
                current->~Ribble();
                cout<<"[graph] ribble removed successfully\n";
            }
        }
        // ���� ��� - ������� ��������
        if (!isPresent)
        {   
            throw new RibbleNotFoundException("cannot remove: ribble not found");
        }
    }

    // ������� �������
    //##ModelId=471BBAE20290
    void removeVertex(T* vertex)
    {
        cout<<"\n[graph] removoing all ribbles, containing\n===vertex===\n"
            <<*vertex<<endl;

        // ���������, ���� �� ����� �������
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

    // �������� �������� ��� ������ �����
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
