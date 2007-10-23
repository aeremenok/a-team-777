#include "Ribble.h"
//##ModelId=471BB58C010A
template<class T>
Ribble<T>::~Ribble()
{
    // ToDo: Add your specialized code here and/or call the base class
}


template<class T>
Ribble<T>::Ribble( T vertex1, T vertex2 )
{
    _vertex1 = vertex1;
    _vertex2 = vertex2;
}

//##ModelId=471BB59F0222
template<class T>
const T& Ribble<T>::get__vertex1() const
{
    return _vertex1;
}

//##ModelId=471BB5A000DA
template<class T>
void Ribble<T>::set__vertex1( T& value )
{
    _vertex1 = value;
    return;
}

//##ModelId=471E3CE002EE
template<class T>
const T& Ribble<T>::get__vertex2() const
{
    return _vertex2;
}

//##ModelId=471E3CE10215
template<class T>
void Ribble<T>::set__vertex2( T& value )
{
    _vertex2 = value;
    return;
}
