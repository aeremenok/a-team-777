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
#include <cstring>

#include <vector>
#include <list>
#include <deque>

#include <stack>
#include <queue>

#include <map>
#include <algorithm>
//////////////////////////////////////////////////////////////////////////
// class vector;
// class list;
// class string;
//////////////////////////////////////////////////////////////////////////
#include "Rectangle.h"
#include "TextInOval.h"
//////////////////////////////////////////////////////////////////////////
using namespace std;

//////////////////////////////////////////////////////////////////////////
// тестирует операции с фигурами
void shapeTest()
{
    cout<<"\ttesting shapes"<<endl;

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
    cout<<"\t\ttesting sequences"<<endl<<"\t\t";
    int i;
    int containerSize = 7;

    // создаем вектор
    vector<int> numVector(0);
    // заполняем его
    for (i = 0; i < containerSize; ++i)
    {
        numVector.push_back(i);
    }
    // проверяем порядок заполнения   
    for (i = 0; i < numVector.size(); ++i)
    {
        cout<<numVector[i]<<" ";
    }
    cout<<endl;

//     // создаем список
//     list<int> numList(0);
//     // заполняем его
//     for (i=0; i < containerSize; i++)
//     {
//         numList.push_back(i);
//     }
//     // тестируем порядок заполнения
//     int res;
//     for (i = 0; i < numList.size(); i++)
//     {
//         res = numList.front();
//         cout<<res<<" ";
//         numList.pop_front();
//         numList.push_back(res);
//     }
//     cout<<endl;
// 
//     // создаем двунаправленную очередь
//     deque<int> numDeque(0);
//     // заполняем ее
//     for (i = 0; i < containerSize; ++i)
//     {
// 
//     }
}
//////////////////////////////////////////////////////////////////////////
// тестирует операции с адаптерами последовательностей
void adapterTest()
{
    cout<<"\t\ttesting adapters"<<endl<<"\t\t";
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
struct ltstr
{
  bool operator()(const char* s1, const char* s2) const
  {
    return strcmp(s1, s2) < 0;
  }
};

// тестирует операции с ассоциативными контейнерами
void assocTest()
{
    cout<<"\t\ttesting associative containers"<<endl<<"\t\t";

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
// тестирует операции с контейнерами и итераторами
void containerTest()
{
    cout<<"\ttesting containers"<<endl;

    sequenceTest();
    adapterTest();
    assocTest();    
}
//////////////////////////////////////////////////////////////////////////
int main()
{
    shapeTest();

    containerTest();

    return 0;
}
//////////////////////////////////////////////////////////////////////////
