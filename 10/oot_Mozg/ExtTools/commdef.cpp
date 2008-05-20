// **************************************************************************
//! \file   commdef.cpp
//! \brief  Функции для отладки
// **************************************************************************

#include "stdafx.h"

// ==========================================================================
#ifdef DEBUG
// Устанавливается в <true>, если выводится CONFIRM сообщение
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