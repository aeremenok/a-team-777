// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_RIBBLE_471BB47802AF_INCLUDED
#define _INC_RIBBLE_471BB47802AF_INCLUDED
//////////////////////////////////////////////////////////////////////////
//ребро графа
//##ModelId=471BB47802AF
template<class T>
class Ribble 
{
public:
 //##ModelId=471E4BB5034B
 Ribble(T vertex1, T vertex2)
 {
     _vertex1 = vertex1;
     _vertex2 = vertex2;
 };

 //##ModelId=471E3CE002EE
 const T& get__vertex2() const
 {
     return _vertex2;
 };

 //##ModelId=471E3CE10215
 void set__vertex2(T& value)
 {
     _vertex2 = value;
 };

 //##ModelId=471BB59F0222
 const T& get__vertex1() const
 {
     return _vertex1;
 };

 //##ModelId=471BB5A000DA
 void set__vertex1(T& value)
 {
     _vertex1 = value;
 };

private:
 //##ModelId=471BB58F01E4
 T _vertex1;

 //##ModelId=471BB5A20157
 T _vertex2;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLE_471BB47802AF_INCLUDED */
