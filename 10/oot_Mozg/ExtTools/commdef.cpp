// **************************************************************************
//! \file   commdef.cpp
//! \brief  ������� ��� �������
// **************************************************************************

#include "stdafx.h"

// ==========================================================================
#ifdef DEBUG
// ��������������� � <true>, ���� ��������� CONFIRM ���������
static bool bInConfirm = false;

void EnterConfirm()
{
   bInConfirm = true;
}

void LeaveConfirm()
{
   bInConfirm = false;
}

bool InConfirm()
{
   return bInConfirm;
}
#endif