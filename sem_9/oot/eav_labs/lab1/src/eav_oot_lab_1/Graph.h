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
        Ribble<T>* ribble = new Ribble<T>(&vertex1, &vertex2);
        addRibble(ribble);
        delete(ribble);
    };

    //�������� ������� �����
    //##ModelId=471BBA680399
    void addRibble(const Ribble<T>* ribble)
    {
        // ���������, ��� �� ��� ������ �����
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
        // ���� ��� - ���������
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

    //������� �����, �� ������ ��� ������
    //##ModelId=471BBA9D038A
    void removeRibble(const Ribble<T>* ribble)
    {
        // ���������, ���� �� ����� �����
        // �������� �� �����������, �� �����, ����� ������������������ �������� =)
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
        // ���� ���� - �������
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

    // ������� �������
    //##ModelId=471BBAE20290
    void removeVertex(T vertex)
    {
//         // ���������, ���� �� ����� �������
//         list< Ribble<T> >::iterator iter;
//         for (iter = _ribbleList->begin(); iter != _ribbleList->end(); iter++)
//         {
//             if (iter->contains(&vertex))
//             {   // ���� ���� - �������
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
