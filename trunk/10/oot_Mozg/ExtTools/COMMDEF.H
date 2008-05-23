//------- COMMDEF.H ----------- О.Б. ПОЛУБАСОВ ---- 28.11.91 -- 02.11.2000 --
// Zudin S.V. 27.09.2002
//---------------------------------------------------------------------------
//--------------------  ОПРЕДЕЛЕНИЯ  ОБЩЕГО  НАЗНАЧЕНИЯ  --------------------

#ifndef __COMMDEF_H
#define __COMMDEF_H

#include "comtempl.h"

//---------------- сокращения имен часто используемых типов -----------------
typedef unsigned char   uchar;
typedef unsigned int    uint;;
typedef    const char*  lpcstr;

enum { NONE = -1 };

// ==========================================================================
#ifdef DEBUG

// ==========================================================================
// Функция возвращает <true>, если в данный момент времени выводится
// CONFIRM сообщение. Реализация - в файле commdef.cpp
bool InConfirm();

// ==========================================================================
void EnterConfirm();
void LeaveConfirm();

inline bool DisplayAssertMsg(LPCTSTR statement, int line, LPCTSTR fname)
{
   EnterConfirm();
   static lpcstr fmtstr = "Assertion \"%s\" failed!\nLine %d,\nFile: %s";
   char v[1024];// std::vector<TCHAR> v(1024);  // буфер
   int retv = _snprintf(v, 1023, fmtstr, statement, line, fname);
   v[retv] = '\0';

   switch( ::MessageBox(/*::GetDesktopWindow()*/NULL, v, "Error", 
                        MB_ICONERROR | MB_TASKMODAL | MB_ABORTRETRYIGNORE | MB_DEFBUTTON3) )
   {
   case IDABORT:
      LeaveConfirm();
      exit (1);      // Аварийно завершить программу
   case IDRETRY:
      LeaveConfirm();
      return true;   // Продолжить работу под отладчиком
   default: // case IDIGNORE:    // Игнорировать ошибку
      LeaveConfirm();
      return false;
   }
}

// --------------------------------------------------------------------------
#define  CONFIRM(STATEMENT)\
         {\
            if (!(STATEMENT) && DisplayAssertMsg(#STATEMENT, __LINE__, __FILE__) )\
            {\
               _asm { int 3 }\
            }\
         }

// --------------------------------------------------------------------------
#else //ifndef DEBUG

   #define CONFIRM(STATEMENT)
   inline bool InConfirm(){return false;}
// --------------------------------------------------------------------------
#endif//def DEBUG

#endif//ndef __COMMDEF_H
