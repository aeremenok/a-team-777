// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_GRAPH_46F8FA7D014A_INCLUDED
#define _INC_GRAPH_46F8FA7D014A_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include <list>

#include "Ribble.h"
#include "stdafx.h"
//////////////////////////////////////////////////////////////////////////
using namespace std;
//////////////////////////////////////////////////////////////////////////
//���� �� ���� ������ �����
//##ModelId=46F8FA7D014A
template<class T> class Graph 
{
private:
 //���������� ��������
 //##ModelId=471C6F3A0222
 template<class T>
 class internalIterator 
 {
 public:
  //##ModelId=471C7927034B
  virtual Ribble<T> first()
 {
  // ToDo: Add your specialized code here
  
  return static_cast<Ribble<T>>(0);
 }


  //##ModelId=471C79280271
  virtual Ribble<T> nextLeft()
 {
  // ToDo: Add your specialized code here
  
  return static_cast<Ribble<T>>(0);
 }


  //##ModelId=471C79320119
  internalIterator()
 {
  // ToDo: Add your specialized code here and/or call the base class
 }

 };


 //������ �����
 //##ModelId=471BB7C90138
 list< Ribble<T> > _ribbleList;

public:
 //�������� �����
 //##ModelId=471BBA1B0177
 void addRibble(T vertex1, T vertex2);


 //�������� ������� �����
 //##ModelId=471BBA680399
 void addRibble(Ribble<T> ribble);


 //������� �����, �� ������ ��� ������
 //##ModelId=471BBA9D038A
 void removeRibble(Ribble<T> ribble);


 //##ModelId=471BBAE20290
 void removeVertex(T vertex);

 //##ModelId=471BB2E30271
 Graph();

 //##ModelId=471BB2E30280
 virtual ~Graph();
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_GRAPH_46F8FA7D014A_INCLUDED */
