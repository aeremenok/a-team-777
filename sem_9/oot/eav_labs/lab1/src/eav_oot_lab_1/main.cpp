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
#include <string>

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
    cout<<"\t\ttesting sequence\n";
    int i;
    int containerSize = 7;

    // создаем вектор
    vector<int> numVector(0);
    vector<int>::iterator iter;
    // заполняем его
    cout<<"filling vector\n";
    for (i = 0; i < containerSize; ++i)
    {
        numVector.push_back(i);
    }
    cout<<"watching result with iterator\n";
    // проверяем порядок заполнения   
    for (iter = numVector.begin(); iter != numVector.end(); iter++)
    {
        cout<< *iter <<" "; 
    }
    cout<<"\nwatching result with index\n";
    // то же самое индексированием
    for (i = 0; i < numVector.size(); ++i)
    {
        cout<<numVector.at(i)<<" ";
    }
    cout<<"\nresults are equal\n";
}
//////////////////////////////////////////////////////////////////////////
// тестирует операции с адаптерами последовательностей
void adapterTest()
{
    cout<<"\t\ttesting adaptor\n";
    int containerSize = 7;
    // создаем стек
    stack<int> numStack;

    // заполняем его
    cout<<"filling stack, pushing values:\n";
    for (int i = 0; i < containerSize; ++i)
    {
        cout<<i<<" ";
        numStack.push(i);
    }

    // проверяем порядок заполнения   
    cout<<"\nwatching result\n";
    while (numStack.size() > 0)
    {
        int res = numStack.top();
        cout<<res<<" ";
        numStack.pop();
    }
    cout<<"\nresult reversed\n";
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
    cout<<"\t\ttesting associative containers\n";

    map<const char*, int, ltstr> monthMap;
    map<const char*, int, ltstr>::iterator iter;

    cout<<"filling map\n";
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

    cout<<"watching result\n";
    for (iter = monthMap.begin(); iter != monthMap.end(); iter++)
    {
        cout<<iter->second<<" days in "<<iter->first<<endl;
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
    cout<<"\tfilling vector with shapes\n";
    shapeVector.push_back(new Rectangle(1, 2));
    shapeVector.push_back(new Oval(3, 4));
    shapeVector.push_back(new Text());
    shapeVector.push_back(new TextInOval(5,6));

    // проверяем порядок заполнения   
    cout<<"\twatching result\n";
    for (iter = shapeVector.begin(); iter != shapeVector.end(); iter++)
    {
        cout<<*((Shape*)*iter);
    }
    cout<<endl;
}
//////////////////////////////////////////////////////////////////////////
Shape* inputVertexMenu()
{
    int res = 0;

    int a, b;
    char c[BUFSIZ];
    std::string s;

    Shape* shape;
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
            s = ""; s.append(c);
            text = new Text();
            text->setText(s);
            shape = text;
            break;
        case 4:
            cout<<"enter rad1 and rad2: "; cin>>a>>b;
            cout<<"enter text content: "; cin>>c;
            textInOval = new TextInOval(a, b);
            s = ""; s.append(c);
            textInOval->setText(s);
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
    Graph<Shape> shapeGraph;
    Iterator<Shape>* iter = NULL;
    for (;;)
    {
        switch(printGraphMenu())
        {
            case 0:
                exit(0);
        	    break;
            case 1:
                cout<<"clearing graph\n";
                iter = shapeGraph.getIterator();
                while (iter->hasNext())
                {
                    Ribble<Shape>* current = iter->next();
                    current->get__vertex1()->~Shape();
                    current->get__vertex2()->~Shape();
                    current->~Ribble();
                }
                shapeGraph.clear();
                cout<<"graph cleared\n";
        	    break;
            case 2:
                cout<<"adding a ribble to graph. input 2 vertices:\n";
                try
                {
                    shapeGraph.addRibble(
                        inputVertexMenu(),
                        inputVertexMenu()
                    );
                }
                catch (GraphException* e)
                {
                	e->printException();
                }

                break;
            case 3:
                cout<<"removing a ribble from graph. input 2 vertices:\n";
                try
                {
                    shapeGraph.removeRibble(
                        inputVertexMenu(),
                        inputVertexMenu()
                    );
                }
                catch (GraphException* e)
                {
                	e->printException();
                }
                break;
            case 4:
                cout<<"removing a vertex from graph. input a vertex:\n";
                try
                {
                    shapeGraph.removeVertex(
                        inputVertexMenu()
                    );
                }
                catch (GraphException* e)
                {
                	e->printException();
                }
        	    break;
            case 5:
                cout << shapeGraph;
        	    break;
            default:
                break;
        }
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
        <<"4 - graph container with shapes\n"
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
                shapeGraphTest();
        	    break;
            default:
                break;
        }
    }
}
//////////////////////////////////////////////////////////////////////////
