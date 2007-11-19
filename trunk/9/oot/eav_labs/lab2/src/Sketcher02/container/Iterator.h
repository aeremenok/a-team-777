// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED
#define _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Ribble.h"
//////////////////////////////////////////////////////////////////////////
//������� ��������� ���������
//##ModelId=4741F10E036B
template<class T>
class Iterator 
{
public:
 //������� � ���������� ��-��
 //##ModelId=4741F10E037B
 virtual Ribble<T>* next() = 0;

 //������� � ������� ��-��
 //##ModelId=4741F10E037D
 virtual Ribble<T>* first() = 0;

	//##ModelId=4741F10E037F
 virtual bool hasNext() = 0;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED */
