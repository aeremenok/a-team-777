// *************************************************************
//  main   version:  1.0   �  date: 09/23/2007
//  -------------------------------------------------------------
//  author: eav, 3351 
//  -------------------------------------------------------------
//  Copyright (C) 2007 - All Rights Reserved
// ***************************************************************
// 
// ***************************************************************
#include <ostream.h>
#include <cstring>

#include <vector>
#include <stack>
#include <map>
//////////////////////////////////////////////////////////////////////////
#include "Rectangle.h"
#include "TextInOval.h"

#include "Graph.h"
#include "Ribble.h"
#include "Iterator.h"

#include "GraphException.h"
#include "RibbleExistsException.h"
#include "RibbleNotFoundException.h"
#include "VertexNotFoundException.h"
//////////////////////////////////////////////////////////////////////////
using namespace std;
//////////////////////////////////////////////////////////////////////////
// ��������� �������� � ��������
void shapeTest()
{
    cout<<"\t\ttesting shapes\n"<<endl;

    Shape* rect = new Rectangle(1,2);
    cout<<*rect<<endl;
    Shape* txt = new Text();
    cout<<*txt<<endl;
    Shape* ovl = new Oval(3.0f, 4.0f);
    cout<<*ovl<<endl;
    Shape* txt_in_oval = new TextInOval(5.0f, 6.0f);
    cout<<*txt_in_oval<<endl;
}
//////////////////////////////////////////////////////////////////////////
// ��������� �������� � ��������������������
void sequenceTest()
{
    cout<<"\t\ttesting sequence\n\t\t";
    int i;
    int containerSize = 7;

    // ������� ������
    vector<int> numVector(0);
    vector<int>::iterator iter;
    // ��������� ���
    for (i = 0; i < containerSize; ++i)
    {
        numVector.push_back(i);
    }
    // ��������� ������� ����������   
    for (iter = numVector.begin(); iter != numVector.end(); iter++)
    {
        cout<< *iter <<" "; 
    }
    cout<<"\n\t\tsame as\n\t\t";
    // �� �� ����� ���������������
    for (i = 0; i < numVector.size(); ++i)
    {
        cout<<numVector.at(i)<<" ";
    }
    cout<<endl;
}
//////////////////////////////////////////////////////////////////////////
// ��������� �������� � ���������� �������������������
void adapterTest()
{
    cout<<"\t\ttesting adaptor\n\t\t";
    int i;
    int containerSize = 7;
    // ������� ����
    stack<int> numStack;
    // ��������� ���
    for (i = 0; i < containerSize; ++i)
    {
        numStack.push(i);
    }
    // ��������� ������� ����������   
    while (numStack.size() > 0)
    {
        int res = numStack.top();
        cout<<res<<" ";
        numStack.pop();
    }
    cout<<endl;
}
/////////////////////////////////////////////////////////////////////////
// ��������� �������
//##ModelId=471BB28203B9
struct ltstr
{
	//##ModelId=471BB28203BB
    bool operator()(const char* s1, const char* s2) const
    {
        return strcmp(s1, s2) < 0;
    }
};

// ��������� �������� � �������������� ������������
void assocTest()
{
    cout<<"\t\ttesting associative containers\n\t\t";

    map<const char*, int, ltstr> monthMap;
    map<const char*, int, ltstr>::iterator iter;
    monthMap["january"] = 31;
    monthMap["february"] = 28;
    monthMap["march"] = 31;
    monthMap["april"] = 30;
    monthMap["may"] = 31;
    monthMap["june"] = 30;
    monthMap["july"] = 31;
    monthMap["august"] = 31;
    monthMap["september"] = 30;
    monthMap["october"] = 31;
    monthMap["november"] = 30;
    monthMap["december"] = 31;

    for (iter = monthMap.begin(); iter != monthMap.end(); iter++)
    {
        cout<<iter->second<<" days in "<<iter->first<<endl<<"\t\t";
    }
    cout<<endl;
}
//////////////////////////////////////////////////////////////////////////
// ��������� �������� � �������������������� � ��������
void shapeSequenceTest()
{
    cout<<"\t\ttesting shape sequence\n";
    
    // ������� ������
    vector<Shape*> shapeVector(0);
    vector<Shape*>::iterator iter;
    // ��������� ���
    shapeVector.push_back(new Rectangle(1, 2));
    shapeVector.push_back(new Oval(3, 4));
    shapeVector.push_back(new Text());
    shapeVector.push_back(new TextInOval(5,6));
    // ��������� ������� ����������   
    for (iter = shapeVector.begin(); iter != shapeVector.end(); iter++)
    {
        cout<<*((Shape*)*iter);
    }
    cout<<endl;
}
//////////////////////////////////////////////////////////////////////////
// ��������� ������ ��������� ��� int
void graphTest()
{
    cout<<"\t\ttesting graph\n";
    Graph<int>* myGraph = new Graph<int>();
    cout<<endl;

    myGraph->addRibble(1, 2);
    cout<<endl;
    myGraph->addRibble(12, 13);
    cout<<endl;
    myGraph->addRibble(24, 25);
    cout<<endl;
    myGraph->addRibble(36, 37);
    cout<<endl;

    try
    {   // �������� �������� ��� ������������ �����
        myGraph->addRibble(1, 2);
    }
    catch (GraphException* e)
    {
    	e->printException();
        cout<<endl;
    }

    try
    {   // �������� ������� �������������� �����
        myGraph->removeRibble(10, 11);
    }
    catch (GraphException* e)
    {
    	e->printException();
        cout<<endl;
    }

    try
    {   // �������� ������� �������������� �������
        myGraph->removeVertex(77);
    }
    catch (GraphException* e)
    {
    	e->printException();
        cout<<endl;
    }

    // ��������� ����� ������� ����������
    cout<<"iterating graph ribbles\n";
    Iterator<int>* iter = myGraph->getIterator();
    while (iter->hasNext())
    {
        Ribble<int> ribble = iter->next();
        cout<<"ribble vertices: "<<ribble.get__vertex1()<<"-"<<ribble.get__vertex2()<<endl;
    }
}
//////////////////////////////////////////////////////////////////////////
// ��������� ������ ��������� ��� ����������� ��������
void shapeGraphTest()
{
    cout<<"\n\t\ttesting shape graph\n";

    Graph<Shape*>* shapeGraph = new Graph<Shape*>();
    cout<<endl;
    shapeGraph->addRibble(
        new Rectangle(1, 2),
        new Text()
        );
    cout<<endl;
    shapeGraph->addRibble(
        new Oval(3, 4),
        new TextInOval(5, 6)
        );
    cout<<endl;
    Iterator<Shape*> *iter = shapeGraph->getIterator();
    while (iter->hasNext())
    {
        Ribble<Shape*> ribble = iter->next();
        cout<<"ribble vertices:\n"<<*ribble.get__vertex1()<<"-\n"<<*ribble.get__vertex2()<<endl;
    }
}
//////////////////////////////////////////////////////////////////////////
int main()
{
    shapeTest();

    sequenceTest();
    adapterTest();
    assocTest();
    
    shapeSequenceTest();

    graphTest();
    shapeGraphTest();

    return 0;
}
//////////////////////////////////////////////////////////////////////////
