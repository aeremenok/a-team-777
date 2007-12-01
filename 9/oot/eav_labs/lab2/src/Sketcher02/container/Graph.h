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
#include "container/ExternalGraphIterator.h"

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
//##ModelId=4741F10E0399
template<class T> class Graph 
{
private:
    //������ �����
    //##ModelId=4741F10E03AC
    list< Ribble<T> *>* _ribbleList;

    //���������� ��������
    //##ModelId=4741F10E03C8
    template<class T>
        class GraphIterator : public Iterator<T>
    {
    private:
        // ��������� ����� ��������� �� ������
		//##ModelId=4741F10F006E
        list< Ribble <T>* >* _innerList;

        // �������� ������ ������ ����
		//##ModelId=4741F10F0073
        list< Ribble <T>* >::iterator _iter;
    public:
        //������� � ������� ��-��
        //##ModelId=4741F10F0077
        virtual Ribble<T>* first()
        {
            _iter = _innerList->begin();
            return *_iter;
        }

        //������� � ���������� ��-��
        //##ModelId=4741F10F0079
        virtual Ribble<T>* next()
        {
            Ribble<T>* res = *_iter;
            _iter++;
            return res;
        }

		//##ModelId=4741F10F007E
        virtual bool hasNext()
        {
            return _iter != _innerList->end();
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
    //�������� ����. ������� �������� �� ���������� �� ������������, 
    // �.�. ��� ����� ����� ����� ��� ���������� �������. 
    // ��� ������������ ������ ������� ���
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

    //�������� �����
    //##ModelId=4741F10E03B1
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

    //�������� ������� � ����, �� ������ - �������, � ������� ������������ �����
    //##ModelId=474DE48500EA
    T* addVertex(T* vertex1)
    {
        // ���������, ��� �� ����� c ����� � ��� �� ��������
        Iterator<T>* iter = getIterator();

        Ribble<T>* current = NULL;
        while (iter->hasNext())
        {
            current = iter->next();
            
            if ( *(current->get__vertex1()) == *(current->get__vertex2()) )
            { // �������� ���� ������� � �������
                current->set__vertex2(vertex1);
                return current->get__vertex1();
            }
        }
        
        if (current!=NULL)
        { // ������ ����� ��� - ������������ � ������� ���������� �����
            _ribbleList->push_back(new Ribble<T>(current->get__vertex2(), vertex1));
            return current->get__vertex2();
        } 
        else
        { // ����� ��� ������ ���
            _ribbleList->push_back(new Ribble<T>(vertex1, vertex1));
            return vertex1;
        }
    };

    //������� �����
	//##ModelId=4741F10E03B4
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
    //##ModelId=4741F10E03BB
    void removeVertex(T* vertex)
    {
//         cout<<"\n[graph] removoing all ribbles, containing\n===vertex===\n"
//             <<*vertex<<endl;

        // ���������, ���� �� ����� �������
        Iterator<T>* iter = getIterator();
        bool isPresent = false;

        while (iter->hasNext())
        {
            Ribble<T>* current = iter->next();

            // ���������� �������, ������� �������
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
                { // ��� �� �� �� ����� �������, ��� �������
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

    // �������� �������� ��� ������ �����
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
    friend ostream_withassign& operator<<(ostream_withassign& o, const Graph<T>& rhs);
};
//////////////////////////////////////////////////////////////////////////
template <class T>
ostream_withassign& operator<<( ostream_withassign& o, const Graph<T>& rhs )
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
