//------- COMMDEF.H ----------- �.�. ��������� ---- 28.11.91 -- 02.11.2000 --
// Zudin S.V. 27.09.2002
//---------------------------------------------------------------------------
//--------------------  �����������  ������  ����������  --------------------

#ifndef __COMMDEF_H
#define __COMMDEF_H

#include <stdio.h>

#define _USE_MATH_DEFINES
#include <math.h>

//#include "localset.h"
#include "comtempl.h"

#undef small
// **************************************************************************
//             ���������� ��� �������/�������� �������/�������
/* FSGCMMN ������ �� DLL */
#define FSG_CMMN_CLASS

//#ifdef FSG_CMMN_EXPRT
//  #define FSG_CMMN_CLASS __declspec(dllexport)
//#elif defined( FSG_NOEXPORT ) /* ��� ������������� DLL */
//   #define FSG_CMMN_CLASS 
//#else
//  #define FSG_CMMN_CLASS __declspec(dllimport)
//#endif

#define FSG_FASTCALL  __fastcall

// **************************************************************************
//#define HIBITL (-32768 * 2048)
//#define HIBITI (-32768 * 2048)
#define HIBITL ((long)0x80000000)
#define HIBITI ((int)0x80000000)
//#define HIBITS ((short)0x8000)
#define HIBITS (-32768)
//#define HIBITI (-32768)
//#define HIBITL ((long)0x80000000)
//#define HIBITI ((int)0x80000000)
//#define HIBITS ((short)0x8000)
#define HIBITC ((signed char)0x80)

// ==========================================================================
//                               ������ STL
namespace std
{
   typedef basic_string<TCHAR> tstring;
}
// ==========================================================================

//---------------- ���������� ���� ����� ������������ ����� -----------------

typedef   signed char   schar;
typedef unsigned char   uchar;
typedef unsigned short  ushort;
typedef unsigned int    uint;
typedef unsigned long   ulong;
typedef    const char*  lpcstr;
typedef unsigned char   BYTE;
typedef COLORREF        CLR;                           // ����
typedef int             SCR;                           // �������� ����������

enum { EXIT_OK, EXIT_ERROR, EXIT_DONT_SAVE, EXIT_SAVE };     // ���� ��������
enum { NONE = -1 };
enum { UNIT_FUFEL  = 2,          // ������� ����� ��������, �������� �� 0 � �� ����������� � ���������.
       UNIT_DBU    = 1,          // ���������� �.�.
       UNIT_MKM    = 10,         // "�������"
       UNIT_CMM    = 100,        // "�������������p�"
       UNIT_MIL    = 254,        // "����������"
       UNIT_RUSMIL = 250,        // "������� ����������"
       UNIT_MM     = 10000,      // "��������p�"
       UNIT_CM     = 100000,     // "��������p�"
       UNIT_IN     = 254000      // "�����"
     }; 

#define CLR_NONE     0xFFFFFFFFL
#define CLR_DEFAULT  0xFF000000L
#define BITI(i)  (1u<<i)             /*------ �������� i-� ��� ������ -----*/

namespace FSG
{
//--------------------- ��������� �������� ������� --------------------------

//FILE* TOOLS_fopen     (lpcstr filename, lpcstr mode, int mes = 1);
//FILE* TOOLS_fopen_sys (lpcstr filename, lpcstr mode, int mes = 1);

inline long round (double x) { return long (x < 0. ? x-0.5 : x+0.5); }
inline long round (long   x) { return x; }
inline long round (int    x) { return x; }

//===========================================================================
//                    ����������� ��������� ���� � ����
//---------------------------------------------------------------------------

#define RELATION_1TO2(EDGE, ARC, Edge, Arc, edge, arc)                           \
 static inline ARC  Arc##_##edge (EDGE e) { return static_cast<ARC>(e*2u); }     \
 static inline EDGE Edge##_##arc (ARC a)  { return static_cast<EDGE>(a/2u); }    \
 static inline ARC  Arc##Over    (ARC a)  { return a^1; }        /* �������� */  \
 static inline ARC  Arc##Low     (ARC a)  { return a&~1; }       /* �������  */  \
 static inline ARC  Arc##Hi      (ARC a)  { return a|1; }        /* �������  */  \
 static inline bool Is##Arc##Low (ARC a)  { return !(a&1); }     /* �������? */  \
 static inline bool Is##Arc##Hi  (ARC a)  { return a&1; }        /* �������? */
//---------------------------------------------------------------------------

//===========================================================================
//                    ����������� ��������� ���� � N
//---------------------------------------------------------------------------

#define RELATION_1TON(N, EDGE, ARC, Edge, Arc, edge, arc)                          \
 static inline ARC  Arc##_##edge (EDGE e) { return static_cast<ARC>(e*uint(N)); }      \
 static inline EDGE Edge##_##arc (ARC a)  { return static_cast<EDGE>(a/uint(N)); }     \
 static inline ARC  Arc##Offset  (ARC a)  { return a%uint(N); }         /* �������� */ \
 static inline ARC  Arc##Low     (ARC a)  { return a-a%uint(N); }       /* �������  */ \
 static inline ARC  Arc##Hi      (ARC a)  { return a-a%uint(N)+(N)-1; } /* �������  */ \
 static inline bool Is##Arc##Low (ARC a)  { return !(a%uint(N)); }      /* �������? */ \
 static inline bool Is##Arc##Hi  (ARC a)  { return !((a+1)%uint(N)); }  /* �������? */
// static inline ARC  Arc##Over    (ARC a)  { return a+(N)-1-2u*(a%(N)); } /* �������� */
//---------------------------------------------------------------------------

//===========================================================================
//  �����, ������� (� ������� �� ���������) �� �������� ������ ��, ��� ����
//     (����, �������, ����������� �� ���������, � ����� - ��� ��������)
//---------------------------------------------------------------------------
template <class T> class ALG_EXCHANGE
 {
 public:                                  // �������� �������������� ������
  ALG_EXCHANGE (T *ptr) : pT(ptr) { pT->GetData(); }
 ~ALG_EXCHANGE () { pT->PutData(); }      // ������ ����� ������������ ������

 private:                                            //       ���������
 T *const pT;                                        // ����� ���������,
 ALG_EXCHANGE (const ALG_EXCHANGE&);                 // ��������� ����� �
 ALG_EXCHANGE& operator= (const ALG_EXCHANGE&);      // �������� ������������
 };

}; // namespace FSG

// ==========================================================================
//! �������� � ������������� �������
/** ������� ������������ ������ � ������������� ��������. ���� ��� �������� �
�������� ������, �� ������ ������� ����������.
������� ���������� true, ���� ���� ������� � �������
**/
FSG_CMMN_CLASS bool ShowTodoMessage(bool bExcept, LPCSTR what, LPCSTR responsible, LPCSTR fname, int line);
// ==========================================================================
#ifdef DEBUG

// ==========================================================================
// ������� ���������� <true>, ���� � ������ ������ ������� ���������
// CONFIRM ���������. ���������� - � ����� commdef.cpp
FSG_CMMN_CLASS bool InConfirm();
// ==========================================================================
FSG_CMMN_CLASS void EnterConfirm();
FSG_CMMN_CLASS void LeaveConfirm();

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
#define CHECK(STATEMENT)\
         {\
            if (!(STATEMENT) && DisplayAssertMsg(#STATEMENT, __LINE__, __FILE__) )\
            {\
               _asm { int 3 }\
            }\
         }

#define DLINE(x) x
// --------------------------------------------------------------------------
#else //ifndef DEBUG
   #define CONFIRM(STATEMENT)
   #define CHECK(STATEMENT) STATEMENT;
   #define DLINE(x)
   inline bool InConfirm(){return false;}
// --------------------------------------------------------------------------
#endif//def DEBUG

#ifdef BUILD_RELEASE_VERSION
#define  TODO(WHO, WHAT)\
   { \
   Not_implemented_method; \
   }
#else
#define  TODO(WHO, WHAT)\
   {\
   static bool skip = false;\
   if (!skip)\
      if (ShowTodoMessage(false, #WHAT, #WHO, __FILE__, __LINE__) )\
      {\
         _asm { int 3 }\
      }\
      else\
         skip = true;\
   }
#endif // BUILD_RELEASE_VERSION
// ==========================================================================

#if defined(PROFILE_ON) && !defined(PROFILE_OFF)
   #include "profile.h"
#else
   #define PROFILE_STOP
   #define PROFILE(X)
   #define PROFILE_TIME_OUT(X)
   #define PROFILE_FAST_FUNC(X)
   #define PROFILE_INLINE_FUNC(X)
   #define EDIT_PROFILE(X)
#endif

#endif//ndef __COMMDEF_H