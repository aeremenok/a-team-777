// *************************************************************
//  main   version:  1.0   ·  date: 09/23/2007
//  -------------------------------------------------------------
//  author: eav, 3351 
//  -------------------------------------------------------------
//  Copyright (C) 2007 - All Rights Reserved
// ***************************************************************
// 
// ***************************************************************
#include <ostream.h>
#include <istream.h>
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
// тестирует операции с фигурами
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
// тестирует операции с последовательностями
void sequenceTest()
{
    cout<<"\t\ttesting sequence\n\t\t";
    int i;
    int containerSize = 7;

    // создаем вектор
    vector<int> numVector(0);
    vector<int>::iterator iter;
    // заполняем его
    for (i = 0; i < containerSize; ++i)
    {
        numVector.push_back(i);
    }
    // проверяем порядок заполнения   
    for (iter = numVector.begin(); iter != numVector.end(); iter++)
    {
        cout<< *iter <<" "; 
    }
    cout<<"\n\t\tsame as\n\t\t";
    // то же самое индексированием
    for (i = 0; i < numVector.size(); ++i)
    {
        cout<<numVector.at(i)<<" ";
    }
    cout<<endl;
}
//////////////////////////////////////////////////////////////////////////
// тестирует операции с адаптерами последовательностей
void adapterTest()
{
    cout<<"\t\ttesting adaptor\n\t\t";
    int i;
    int containerSize = 7;
    // создаем стек
    stack<int> numStack;
    // заполняем его
    for (i = 0; i < containerSize; ++i)
    {
        numStack.push(i);
    }
    // проверяем порядок заполнения   
    while (numStack.size() > 0)
    {
        int res = numStack.top();
        cout<<res<<" ";
        numStack.pop();
    }
    cout<<endl;
}
/////////////////////////////////////////////////////////////////////////
// отношение порядка
//##ModelId=471BB28203B9
struct ltstr
{
	//##ModelId=471BB28203BB
    bool operator()(const char* s1, const char* s2) const
    {
        return strcmp(s1, s2) < 0;
    }
};

// тестирует операции с ассоциативными контейнерами
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
// тестирует операции с последовательностями и фигурами
void shapeSequenceTest()
{
    cout<<"\t\ttesting shape sequence\n";
    
    // создаем вектор
    vector<Shape*> shapeVector(0);
    vector<Shape*>::iterator iter;
    // заполняем его
    shapeVector.push_back(new Rectangle(1, 2));
    shapeVector.push_back(new Oval(3, 4));
    shapeVector.push_back(new Text());
    shapeVector.push_back(new TextInOval(5,6));
    // проверяем порядок заполнения   
    for (iter = shapeVector.begin(); iter != shapeVector.end(); iter++)
    {
        cout<<*((Shape*)*iter);
    }
    cout<<endl;
}
//////////////////////////////////////////////////////////////////////////
// тестирует шаблон итератора для int
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
    {   // пытаемся добавить уже существующее ребро
        myGraph->addRibble(1, 2);
    }
    catch (GraphException* e)
    {
    	e->printException();
        cout<<endl;
    }

    try
    {   // пытаемся удалить несуществующее ребро
        myGraph->removeRibble(10, 11);
    }
    catch (GraphException* e)
    {
    	e->printException();
        cout<<endl;
    }

    try
    {   // пытаемся удалить несуществующую вершину
        myGraph->removeVertex(77);
    }
    catch (GraphException* e)
    {
    	e->printException();
        cout<<endl;
    }

    // тестируем обход внешним итератором
    cout<<"iterating graph ribbles\n";
    Iterator<int>* iter = myGraph->getIterator();
    while (iter->hasNext())
    {
        Ribble<int> ribble = iter->next();
        cout<<"ribble vertices: "<<ribble.get__vertex1()<<"-"<<ribble.get__vertex2()<<endl;
    }
}
//////////////////////////////////////////////////////////////////////////
Shape* inputVertexMenu()
{
    Shape* shape;
    int res = 0;
    int a, b;
    char* c;
    Text* text = NULL;
    TextInOval* textInOval = NULL;
    cout<<"select a type of vertex to operate\n"
        <<"1 - rectangle\n"
        <<"2 - oval\n"
        <<"3 - text\n"
        <<"4 - text in oval\n"
        <<"0 - exit\n";
    cout<<"your selection: "; cin>>res;
    switch(res)
    {
        case 0:
            exit(0);
            break;
        case 1:
            cout<<"enter length and width: "; cin>>a>>b;
            shape = new Rectangle(a, b);
            break;
        case 2:
            cout<<"enter rad1 and rad2: "; cin>>a>>b;
            shape = new Oval(a, b);
            break;
        case 3:
            cout<<"enter text content: "; cin>>c;
            text = new Text();
            text->setText(c);
            shape = text;
            break;
        case 4:
            cout<<"enter rad1 and rad2: "; cin>>a>>b;
            cout<<"enter text content: "; cin>>c;
            textInOval = new TextInOval(a, b);
            textInOval->setText(c);
            shape = textInOval;
            break;
        default:
            break;
    }
    return shape;
}
//////////////////////////////////////////////////////////////////////////
int printGraphMenu()
{
    int res = 0;
    cout<<"#####################################\n"
        <<"testing graph with shapes\n"
        <<"select what do you want to do\n"
        <<"1 - clear graph\n"
        <<"2 - add ribble to graph\n"
        <<"3 - remove ribble from graph\n"
        <<"4 - remove vertex from graph\n"
        <<"5 - print graph state\n"
        <<"0 - exit\n";
    cout<<"your selection: "; cin>>res;
    cout<<"#####################################\n";
    return res;
}
//////////////////////////////////////////////////////////////////////////
// тестирует шаблон итератора для графических объектов
void shapeGraphTest()
{
    Graph<Shape*>* shapeGraph = new Graph<Shape*>();
    for (;;)
    {
        switch(printGraphMenu())
        {
            case 0:
                return;
        	    break;
            case 1:
                // todo
        	    break;
            case 2:
                cout<<"adding a ribble to graph. input 2 vertices:\n";
                shapeGraph->addRibble(
                    inputVertexMenu(),
                    inputVertexMenu()
                );
                break;
            case 3:
                cout<<"removing a ribble from graph. input 2 vertices:\n";
                shapeGraph->removeRibble(
                    inputVertexMenu(),
                    inputVertexMenu()
                    );
                break;
            case 4:
                cout<<"removing a vertex from graph. input a vertex:\n";
                shapeGraph->removeVertex(
                    inputVertexMenu()
                    );
        	    break;
            case 5:
        	    break;
            default:
                break;
        }
    }

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
int printMainMenu()
{
    int res = 0;
    cout<<"========================================\n"
        <<"OOT lab #1 (c)eav, 3351\n"    
        <<"select, what do yo want to test:\n"
        <<"1 - simple shapes\n"
        <<"2 - STL containers\n"
        <<"3 - STL containers with shapes\n"
        <<"4 - graph container, throwing exceptions\n"
        <<"5 - graph container with shapes\n"
        <<"0 - exit\n";
    cout<<"your selection: "; cin>>res;
    cout<<"========================================\n";
    return res;
}
//////////////////////////////////////////////////////////////////////////
void main()
{
    for (;;)
    {
        switch(printMainMenu())
        {
            case 0:
                return;
        	    break;
            case 1:
                shapeTest();
        	    break;
            case 2:
                sequenceTest();
                adapterTest();
                assocTest();
                break;
            case 3:
                shapeSequenceTest();
                break;
            case 4:
                graphTest();
        	    break;
            case 5:
                shapeGraphTest();
        	    break;
            default:
                break;
        }
    }
}
//////////////////////////////////////////////////////////////////////////
