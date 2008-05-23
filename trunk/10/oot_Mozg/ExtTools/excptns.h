// **************************************************************************
// * excptns.h                   Zudin S. V.        05/06/2000 - 19/01/2003 *
// **************************************************************************

#ifndef __EXCPTNS_H
#define __EXCPTNS_H

#include "commdef.h"

namespace FSG  //! FreeStyle Group
{
   inline void FSG_THROW(std::exception& e)
   {
#ifdef _DEBUG
      LPCSTR txt = e.what();
      bool x = false;
      if (x)   // можно под отладчиком изменить значение переменной и вернуться
      {        // в вызвавший функцию код
         return;
      }
      else
#endif
         throw e;
   }
}  // end of namespace FSG

#endif // __EXCPTNS_H