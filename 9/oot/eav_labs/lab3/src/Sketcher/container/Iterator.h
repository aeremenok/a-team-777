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
//##ModelId=4770E2080093
template<class T>
class Iterator 
{
public:
    //������� � ���������� ��-��
    //##ModelId=4770E2080095
    virtual Ribble<T>* next() = 0;

    //������� � ����������� ��-��
    //##ModelId=4770E2080097
    virtual Ribble<T>* previous() = 0;

    //������� � ������� ��-��
    //##ModelId=4770E2080099
    virtual Ribble<T>* first() = 0;

    //������� � ���������� ��-��
    //##ModelId=4770E20800A4
    virtual Ribble<T>* last() = 0;

    //���� �� ��� ��-�� ��� ��������� ������
    //##ModelId=4770E20800A6
    virtual bool hasPrevious() = 0;

    //���� �� ��� ��-�� ��� ������
    //##ModelId=4770E20800A8
    virtual bool hasNext() = 0;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_EXTERNALITERATOR_471BCA1D009C_INCLUDED */
