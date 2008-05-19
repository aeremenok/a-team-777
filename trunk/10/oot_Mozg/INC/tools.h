// **************************************************************************
//! \file   tools.h
//! \brief  Полезные функции для работы с GUI
//! \author Lysenko A.A.
//! \date   19.May.2008 - 19.May.2008
// **************************************************************************

#ifndef __TOOLS_H
#define __TOOLS_H

#include <vector>

// ==========================================================================
inline std::string GetControlText(HWND control)
{
   int slen = ::GetWindowTextLength(control) + 1;   
   std::vector<TCHAR> buf(slen, 0);
   ::GetWindowText(control, &buf[0], slen);
   return &buf[0];
}

// ==========================================================================
#endif // __TOOLS_H