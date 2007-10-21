// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_GRAPHITERATOR_471BCB66034B_INCLUDED
#define _INC_GRAPHITERATOR_471BCB66034B_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Ribble.h"
#include "Graph.h"
//////////////////////////////////////////////////////////////////////////
class Graph;
//////////////////////////////////////////////////////////////////////////
//##ModelId=471BCB66034B
template<class T>
class GraphIterator 
{
public:
 //##ModelId=471BCC2A0242
 virtual Ribble<T> first()
 {
  // ToDo: Add your specialized code here
  
  return static_cast<Ribble<T>>(0);
 }


 //##ModelId=471BCC2B008E
 virtual Ribble<T> nextLeft()
 {
  // ToDo: Add your specialized code here
  
  return static_cast<Ribble<T>>(0);
 }


 //##ModelId=471BCCB800FA
 GraphIterator()
 {
  // ToDo: Add your specialized code here and/or call the base class
 }

private:
 //##ModelId=471BCC37037A
 Graph* _graph;

};

#endif /* _INC_GRAPHITERATOR_471BCB66034B_INCLUDED */
