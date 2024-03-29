//------- COMMDEF.H ----------- �.�. ��������� ---- 28.11.91 -- 02.11.2000 --
// Zudin S.V. 27.09.2002
//---------------------------------------------------------------------------
//--------------------  �����������  ������  ����������  --------------------

#ifndef __COMMDEF_H
#define __COMMDEF_H

#include "comtempl.h"

//---------------- ���������� ���� ����� ������������ ����� -----------------
typedef unsigned char   uchar;
typedef unsigned int    uint;;
typedef    const char*  lpcstr;

enum { NONE = -1 };

// ==========================================================================
#ifdef DEBUG

// ==========================================================================
// ������� ���������� <true>, ���� � ������ ������ ������� ���������
// CONFIRM ���������. ���������� - � ����� commdef.cpp
bool InConfirm();

// ==========================================================================
void EnterConfirm();
void LeaveConfirm();

inline bool DisplayAssertMsg(LPCTSTR statement, int line, LPCTSTR fname)
{
   EnterConfirm();
   static lpcstr fmtstr = "Assertion \"%s\" failed!\nLine %d,\nFile: %s";
   char v[1024];// std::vector<TCHAR> v(1024);  // �����
   int retv = _snprintf(v, 1023, fmtstr, statement, line, fname);
   v[retv] = '\0';

   switch( ::MessageBox(/*::GetDesktopWindow()*/NULL, v, "Error", 
                        MB_ICONERROR | MB_TASKMODAL | MB_ABORTRETRYIGNORE | MB_DEFBUTTON3) )
   {
   case IDABORT:
      LeaveConfirm();
      exit (1);      // �������� ��������� ���������
   case IDRETRY:
      LeaveConfirm();
      return true;   // ���������� ������ ��� ����������
   default: // case IDIGNORE:    // ������������ ������
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
