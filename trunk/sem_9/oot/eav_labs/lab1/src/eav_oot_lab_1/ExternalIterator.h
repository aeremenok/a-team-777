// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED
#define _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED

#include "Ribble.h"
#include "stdafx.h"

//##ModelId=471BCA1D009C
template<class T>
class ExternalIterator 
{
public:
 //##ModelId=471BCA6300EA
 virtual Ribble<T> nextLeft() = 0;

 //##ModelId=471BCAB501A5
 virtual Ribble<T> first() = 0;

};

#endif /* _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED */
