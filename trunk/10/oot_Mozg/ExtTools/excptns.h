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
      if (x)   // ����� ��� ���������� �������� �������� ���������� � ���������
      {        // � ��������� ������� ���
         return;
      }
      else
#endif
         throw e;
   }
/*
// ==========================================================================
   enum  {  ERR_GENERIC = 1   };                         // ����� ��� ������
// ==========================================================================

class FSG_CMMN_CLASS EXCEPTION
{
public:
   EXCEPTION(long erc = ERR_GENERIC, bool adel = true) : 
      err_code(erc), AutoDelete(adel)                      {};
   virtual ~EXCEPTION()   {};
   // -----------------------------------------------------------------------
   virtual bool GetErrorMessage(char* text, int len) const { return false; };
   virtual int  ReportError(lpcstr caption) const;
   // -----------------------------------------------------------------------
   void         Delete()   {if (AutoDelete)   delete this;   };
   // -----------------------------------------------------------------------
   long err_code;                                              // ��� ������
private:
   bool AutoDelete;
};
*/

// ==========================================================================
//                            ��������� ����������

//! \todo ������� ������ FSG_THROW �� excptns.h
/*
inline void FSG_THROW(EXCEPTION* e)
{
#if (defined(_DEBUG) || defined(DEBUG))
   CONFIRM(e);
   //e->ReportError(NULL);
   bool x = false;
   if (x)   // ����� ��� ���������� �������� �������� ���������� � ���������
   {        // � ��������� ������� ���
      e->Delete();
      return;
   }
   else
#endif
      throw e;
};

inline void FSG_THROW(EXCEPTION& e)
{
   FSG_THROW(&e);
};
*/
}  // end of namespace FSG

#endif // __EXCPTNS_H
