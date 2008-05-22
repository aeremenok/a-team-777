//------- COMMDEF.H ----------- О.Б. ПОЛУБАСОВ ---- 28.11.91 -- 02.11.2000 --
// Zudin S.V. 27.09.2002
//---------------------------------------------------------------------------
//--------------------  ОПРЕДЕЛЕНИЯ  ОБЩЕГО  НАЗНАЧЕНИЯ  --------------------

#ifndef __COMMDEF_H
#define __COMMDEF_H

#include <stdio.h>

#define _USE_MATH_DEFINES
#include <math.h>

//#include "localset.h"
#include "comtempl.h"

#undef small
// **************************************************************************
//             ОБЪЯВЛЕНИЯ ДЛЯ ИМПОРТА/ЭКСПОРТА КЛАССОВ/ФУНКЦИЙ
/* FSGCMMN больше не DLL */
#define FSG_CMMN_CLASS

//#ifdef FSG_CMMN_EXPRT
//  #define FSG_CMMN_CLASS __declspec(dllexport)
//#elif defined( FSG_NOEXPORT ) /* без использования DLL */
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
//                               Строка STL
namespace std
{
   typedef basic_string<TCHAR> tstring;
}
// ==========================================================================

//---------------- сокращения имен часто используемых типов -----------------

typedef   signed char   schar;
typedef unsigned char   uchar;
typedef unsigned short  ushort;
typedef unsigned int    uint;
typedef unsigned long   ulong;
typedef    const char*  lpcstr;
typedef unsigned char   BYTE;
typedef COLORREF        CLR;                           // цвет
typedef int             SCR;                           // экранные координаты

enum { EXIT_OK, EXIT_ERROR, EXIT_DONT_SAVE, EXIT_SAVE };     // КОДЫ ВОЗВРАТА
enum { NONE = -1 };
enum { UNIT_FUFEL  = 2,          // Годится любое значение, отличное от 0 и не совпадающее с валидными.
       UNIT_DBU    = 1,          // Внутренние е.и.
       UNIT_MKM    = 10,         // "Микроны"
       UNIT_CMM    = 100,        // "СантиМиллиМетpы"
       UNIT_MIL    = 254,        // "МиллиДюймы"
       UNIT_RUSMIL = 250,        // "Русские МиллиДюймы"
       UNIT_MM     = 10000,      // "МиллиМетpы"
       UNIT_CM     = 100000,     // "СантиМетpы"
       UNIT_IN     = 254000      // "Дюймы"
     }; 

#define CLR_NONE     0xFFFFFFFFL
#define CLR_DEFAULT  0xFF000000L
#define BITI(i)  (1u<<i)             /*------ ВЫДЕЛИТЬ i-Й БИТ ЦЕЛОГО -----*/

namespace FSG
{
//--------------------- некоторые полезные функции --------------------------

//FILE* TOOLS_fopen     (lpcstr filename, lpcstr mode, int mes = 1);
//FILE* TOOLS_fopen_sys (lpcstr filename, lpcstr mode, int mes = 1);

inline long round (double x) { return long (x < 0. ? x-0.5 : x+0.5); }
inline long round (long   x) { return x; }
inline long round (int    x) { return x; }

//===========================================================================
//                    ВЫЧИСЛЯЕМОЕ ОТНОШЕНИЕ ОДИН К ДВУМ
//---------------------------------------------------------------------------

#define RELATION_1TO2(EDGE, ARC, Edge, Arc, edge, arc)                           \
 static inline ARC  Arc##_##edge (EDGE e) { return static_cast<ARC>(e*2u); }     \
 static inline EDGE Edge##_##arc (ARC a)  { return static_cast<EDGE>(a/2u); }    \
 static inline ARC  Arc##Over    (ARC a)  { return a^1; }        /* обратный */  \
 static inline ARC  Arc##Low     (ARC a)  { return a&~1; }       /* младший  */  \
 static inline ARC  Arc##Hi      (ARC a)  { return a|1; }        /* старший  */  \
 static inline bool Is##Arc##Low (ARC a)  { return !(a&1); }     /* младший? */  \
 static inline bool Is##Arc##Hi  (ARC a)  { return a&1; }        /* старший? */
//---------------------------------------------------------------------------

//===========================================================================
//                    ВЫЧИСЛЯЕМОЕ ОТНОШЕНИЕ ОДИН К N
//---------------------------------------------------------------------------

#define RELATION_1TON(N, EDGE, ARC, Edge, Arc, edge, arc)                          \
 static inline ARC  Arc##_##edge (EDGE e) { return static_cast<ARC>(e*uint(N)); }      \
 static inline EDGE Edge##_##arc (ARC a)  { return static_cast<EDGE>(a/uint(N)); }     \
 static inline ARC  Arc##Offset  (ARC a)  { return a%uint(N); }         /* смещение */ \
 static inline ARC  Arc##Low     (ARC a)  { return a-a%uint(N); }       /* младший  */ \
 static inline ARC  Arc##Hi      (ARC a)  { return a-a%uint(N)+(N)-1; } /* старший  */ \
 static inline bool Is##Arc##Low (ARC a)  { return !(a%uint(N)); }      /* младший? */ \
 static inline bool Is##Arc##Hi  (ARC a)  { return !((a+1)%uint(N)); }  /* старший? */
// static inline ARC  Arc##Over    (ARC a)  { return a+(N)-1-2u*(a%(N)); } /* обратный */
//---------------------------------------------------------------------------

//===========================================================================
//  КЛАСС, КОТОРЫЙ (В ОТЛИЧИЕ ОТ НЕКОТОРЫХ) НЕ ЗАБЫВАЕТ ОТДАТЬ ТО, ЧТО ВЗЯЛ
//     (если, конечно, конструктор не трапнется, а иначе - его иепархия)
//---------------------------------------------------------------------------
template <class T> class ALG_EXCHANGE
 {
 public:                                  // получить обрабатываемые данные
  ALG_EXCHANGE (T *ptr) : pT(ptr) { pT->GetData(); }
 ~ALG_EXCHANGE () { pT->PutData(); }      // отдать назад обработанные данные

 private:                                            //       ЗАПРЕЩЕНЫ
 T *const pT;                                        // смена указателя,
 ALG_EXCHANGE (const ALG_EXCHANGE&);                 // инициатор копии и
 ALG_EXCHANGE& operator= (const ALG_EXCHANGE&);      // оператор присваивания
 };

}; // namespace FSG

// ==========================================================================
//! Сообщить о незаконченной функции
/** Функция присутствует только в незаконченных функциях. Если она попадает в
Релизную сборку, то должна бросать исключение.
Функция возвращает true, если надо перейти в отладку
**/
FSG_CMMN_CLASS bool ShowTodoMessage(bool bExcept, LPCSTR what, LPCSTR responsible, LPCSTR fname, int line);
// ==========================================================================
#ifdef DEBUG

// ==========================================================================
// Функция возвращает <true>, если в данный момент времени выводится
// CONFIRM сообщение. Реализация - в файле commdef.cpp
FSG_CMMN_CLASS bool InConfirm();
// ==========================================================================
FSG_CMMN_CLASS void EnterConfirm();
FSG_CMMN_CLASS void LeaveConfirm();

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

#endif//ndef __COMMDEF_H
