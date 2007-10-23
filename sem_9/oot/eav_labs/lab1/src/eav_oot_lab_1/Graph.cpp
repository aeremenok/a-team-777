// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Graph.h"
#include "Ribble.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=471BB2E30271
template<class T> Graph<T>::Graph()
{
    cout<<"!"<<endl;
}

//##ModelId=471BB2E30280
template<class T> Graph<T>::~Graph ()
{
    cout<<"?"<<endl;
}

//##ModelId=471BBA1B0177
template<class T>
void Graph<T>::addRibble( T vertex1, T vertex2 )
{
    cout<<"???"<<endl;
}

//##ModelId=471BBA680399
template<class T>
void Graph<T>::addRibble( Ribble<T> ribble )
{
    // TODO: Add your specialized code here.
}

//##ModelId=471BBA9D038A
template<class T>
void Graph<T>::removeRibble( Ribble<T> ribble )
{
    // TODO: Add your specialized code here.
}

//##ModelId=471BBAE20290
template<class T>
void Graph<T>::removeVertex( T vertex )
{
    // TODO: Add your specialized code here.
}
//////////////////////////////////////////////////////////////////////////
template<class T>
Ribble<T> Graph<T>::GraphIterator<T>::first()
{
    // ToDo: Add your specialized code here
    
    return static_cast<Ribble<T>>(0);
}
template<class T>
Ribble<T> Graph<T>::GraphIterator<T>::nextLeft()
{
    // ToDo: Add your specialized code here
    
    return static_cast<Ribble<T>>(0);
}
//////////////////////////////////////////////////////////////////////////