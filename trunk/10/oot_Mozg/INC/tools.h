// **************************************************************************
//! \file   tools.h
//! \brief  Полезные функции для работы с GUI
//! \author Lysenko A.A.
//! \date   19.May.2008 - 19.May.2008
// **************************************************************************

#ifndef __TOOLS_H
#define __TOOLS_H

#include <vector>
#include "Archive.h"

// ==========================================================================
inline std::string GetControlText(HWND control)
{
   int slen = ::GetWindowTextLength(control) + 1;   
   std::vector<TCHAR> buf(slen, 0);
   ::GetWindowText(control, &buf[0], slen);
   return &buf[0];
}

// ==========================================================================
//! Манипулятор
template<class charT, class traits>
   inline std::basic_ostream<charT, traits>&
   separ (std::basic_ostream<charT, traits>& strm)
   {
      strm.put(' ');
      return strm;
   }

// ==========================================================================
//! Переопределенный оператор для ser::Archive
template<class charT, class traits>
   inline std::basic_ostream<charT, traits>&
      operator << (std::basic_ostream<charT, traits>& strm, 
                  const ser::Archive& arch)
   {
      arch.PutIntoStream<charT, traits>(strm);
      return strm;
   }

// ==========================================================================
//! Переопределенный оператор для ser::Archive
   template<class charT, class traits>
   inline std::basic_istream<charT, traits>&
      operator >> (std::basic_istream<charT, traits>& strm, 
      ser::Archive& arch)
   {
      arch.GetFromStream<charT, traits>(strm);
      return strm;
   }

// ==========================================================================
#endif // __TOOLS_H