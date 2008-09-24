// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED
#define _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Ribble.h"
//////////////////////////////////////////////////////////////////////////
//внешний интерфейс итератора
//##ModelId=4741F10E036B
template<class T>
class Iterator 
{
public:
    //перейти к следующему эл-ту
    //##ModelId=4741F10E037B
    virtual Ribble<T>* next() = 0;

    //перейти к предыдущему эл-ту
    //##ModelId=475326640232
    virtual Ribble<T>* previous() = 0;

    //перейти к первому эл-ту
    //##ModelId=4741F10E037D
    virtual Ribble<T>* first() = 0;

    //перейти к последнему эл-ту
    //##ModelId=475326640234
    virtual Ribble<T>* last() = 0;

    //есть ли еще эл-ты для обратного обхода
    //##ModelId=475326640236
    virtual bool hasPrevious() = 0;

    //есть ли еще эл-ты для обхода
    //##ModelId=4741F10E037F
    virtual bool hasNext() = 0;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED */
