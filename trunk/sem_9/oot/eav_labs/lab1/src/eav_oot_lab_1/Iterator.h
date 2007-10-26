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
//##ModelId=471BCA1D009C
template<class T>
class Iterator 
{
public:
 //������� � ���������� ��-��
 //##ModelId=471BCA6300EA
 virtual Ribble<T>  next() = 0;

 //������� � ������� ��-��
 //##ModelId=471BCAB501A5
 virtual Ribble<T>  first() = 0;

	//##ModelId=4721A0BA0290
 virtual bool hasNext() = 0;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED */
