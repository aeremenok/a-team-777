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
//���� �� ���� ������ �����
//##ModelId=46F8FA7D014A
template<class T> class Graph 
{
private:
    //������ �����
    //##ModelId=471BB7C90138
    list< Ribble<T> >* _ribbleList;

    //���������� ��������
    //##ModelId=471C6F3A0222
    template<class T>
        class GraphIterator : public Iterator<T>
    {
    private:
        // ��������� ����� ��������� �� ������
        list< Ribble <T> >* _innerList;
        // �������� ������ ������ ����
        list< Ribble <T> >::iterator _iter;
    public:
        //������� � ������� ��-��
        //##ModelId=471E5F210177
        virtual Ribble<T> first()
        {
            _iter = _innerList->begin();
            return *_iter;
        }

        //������� � ���������� ��-��
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
    //�������� �����
    //##ModelId=471BBA1B0177
    void addRibble(T vertex1, T vertex2)
    {
        cout<<"adding ribble\n";
        // ���������, ��� �� ��� ������ �����
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
        // ���� ��� - ���������
        if (!isPresent)
        {
            Ribble<T> ribble(vertex1, vertex2);
            _ribbleList->push_back(ribble);
            cout<<"ribble added successfully\n";
        }
    };

    //������� �����
    void removeRibble(T vertex1, T vertex2)
    {
        cout<<"removing ribble\n";
        // ���������, ���� �� ����� �����
        // �������� �� �����������, �� �����, ����� ������������������ �������� =)
        Iterator<T> *iter = getIterator();
        bool isPresent = false;
        while (iter->hasNext() && !isPresent)
        {
            Ribble<T> current = iter->next();
            if (
                current.get__vertex1() == vertex1 &&
                current.get__vertex2() == vertex2
                )
            { // ���� ���� - �������
                isPresent = true;
                _ribbleList->remove(current);
                cout<<"ribble removed successfully\n";
            }
        }
        if (!isPresent)
        {   // ��� - ������� ��������
            throw new RibbleNotFoundException("cannot remove: ribble not found");
        }
    }

    // ������� �������
    //##ModelId=471BBAE20290
    void removeVertex(T vertex)
    {
        cout<<"removoing all ribbles, containing vertex "<<vertex<<endl;
        // ���������, ���� �� ����� �������
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

    // �������� �������� ��� ������ �����
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
