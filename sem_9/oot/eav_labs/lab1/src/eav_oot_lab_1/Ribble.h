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
 //##ModelId=471BB589037A
 Ribble();

 //##ModelId=471BB58C010A
 virtual ~Ribble();


 //##ModelId=471BB59F0222
 const T& get__vertex1() const;


 //##ModelId=471BB5A000DA
 void set__vertex1(T& value);


 //##ModelId=471BB5B101A6
 const T& get__vertext2() const;


 //##ModelId=471BB5B1032E
 void set__vertext2(T& value);

private:
 //##ModelId=471BB58F01E4
 T _vertex1;

 //##ModelId=471BB5A20157
 T _vertext2;

};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_RIBBLE_471BB47802AF_INCLUDED */
