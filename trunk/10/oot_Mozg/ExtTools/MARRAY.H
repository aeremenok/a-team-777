//-------- MARRAY.H ----------- О.Б. ПОЛУБАСОВ ---- 06.02.91 -- 28.04.01 ----
//---------------------------------------------------------------------------
//
//            ОПРЕДЕЛЕНИЕ  СЕМЕЙСТВА  КЛАССОВ  МОБИЛЬНЫХ  МАССИВОВ
//
// --------------------------------------------------------------------------
// 08/06/2000 Zudin   - генерация исключений
// 12.10.2000 Zudin   - Экспорт из DLL
// 29.01.2002 Smirnov - модификатор inline (для определённости),
//                      куча процесса и флаги работы с кучей,
//                      изменение принципа работы с выделением,
// 31/01/2002 Smirnov & Zudin - оптимизация кода

#ifndef  __MARRAY_H
#define  __MARRAY_H

#include "commdef.h"
#include "nelem.h"
#include "excptns.h" // базовый класс для исключений

namespace FSG  // FreeStyle Group
{
//! \todo Удалить ИСКЛЮЧЕНИЯ, ГЕНЕРИРУЕМЫЕ МОБИЛЬНЫМИ МАССИВАМИ из Marray.h
/*
// ==========================================================================
//             ИСКЛЮЧЕНИЯ, ГЕНЕРИРУЕМЫЕ МОБИЛЬНЫМИ МАССИВАМИ
enum
   {
   ERR_MA_MEMORY = ERR_GENERIC+1,   // не хватает физ. памяти
   ERR_MA_ALLOC,                    // не хватает адресного пространства
   ERR_MA_2LARGE,                   // слишком большой размер
   ERR_MA_0SIZE,                    // обращение к массиву 0-го размера
   ERR_MA_INDEX,                    // неверный индекс
   };

class FSG_CMMN_CLASS MA_EXCEPTION: public EXCEPTION
 {
public:
 enum
    {
    OUT_OF_MEMORY,    // недостаточно памяти
    TOO_LARGE,        // слишком большой размер (> 32767 для типа short)
    ZERO_SIZE,        // обращение к массиву 0-й длины
    WRONG_INDEX,      // индекс за пределами массива
    };

 MA_EXCEPTION(long erc, int n, int sz, int i, bool autodel = true);
 virtual ~MA_EXCEPTION()   {};
 virtual bool GetErrorMessage(char* text, int len) const;
 virtual int ReportError(lpcstr caption) const;
protected:
 int  nElem;    // кол-во элементов в массиве
 int  szElem;   // размер одного элемента
 int  index;    // индекс элемента
 };
*/

//===========================================================================
//                БАЗОВЫЙ КЛАСС СЕМЕЙСТВА МОБИЛЬНЫХ МАССИВОВ 
//---------------------------------------------------------------------------
class FSG_CMMN_CLASS MARRAY
 {
public:
 void setNElem (uint n);           // установить (изменить) число элементов

 NELEM<MARRAY> nElem;              // число элементов (квазипеременная)

protected:
 int   maxnElem;                   // макс.число элементов
 char* body;                       // адрес тела массива
 short szElem;                     // размер элемента (байт)
 char  szIndex;                    // размер индекса  (байт)
 bool  m_bNote;                    // признак хранения дополнительной страницы

  MARRAY ();
 ~MARRAY ();

 void allocSeg (void);          // выделить область памяти для массива
 void freeSeg  (void);          // ОСВОБОДИТЬ ПАМЯТЬ, ОСТАВИВ nElem ПРЕЖНИМ

 inline void addElem (void)     // увеличить длину массива на один элемент
    { setNElem (nElem.n+1); }

 int  setMaxNElem (uint n);     // установить макс.число элементов

public:
 long getSizePack () const;                  // ВЫЧИСЛИТЬ УПАКОВАННЫЙ РАЗМЕР
 long write (HANDLE hFile) const;            // ЗАПИСАТЬ МАССИВ В ФАЙЛ
 void read  (HANDLE hFile);                  // ПРОЧИТАТЬ МАССИВ ИЗ ФАЙЛА

 static void IndexError (uint n, uint i);    // сообщение об ошибке индекса
 static void ZeroError  (void);              // сообщение о пустом массиве

 inline int maxNElem (void) const            // макс.число элементов
    { return maxnElem; }

 void ins (uint i, uint n = 1); // вставить <n> ячеек, начиная от <i>
 void del (uint i, uint n = 1); // удалить  <n> ячеек, начиная от <i>

 void put  (MARRAY& bank);      // поместить на хранение в <bank>
 void get  (MARRAY& bank);      // получить обратно из <bank>
 void swap (MARRAY& x);         // обменять содержимым с <x>

 void empty();                  // освободить все ресурсы, занятые массивом
 bool isEmpty() const { return nElem.n == 0; } ;              // массив пуст?

 friend class MA_NELEM;
 friend class virt_file;
 friend class VF_KEEP;
 friend class VF_BRK;

// --- Begin ----------------------------------------------
// Smirnov: 29.01.2002 - 31.01.2002
public:
 static void AllocError();               // сообщение об ошибке резервирования памяти
 static void MemoryError();              // сообщение об ошибке выделения памяти

protected:
 enum
    {
    MA_HEAP_LIMIT    = 65536,           // переходный размер региона в куче
    MA_HEAP_FLAGS    = 0,               // никаких флагов для функций HeapXXX
    MA_VIRTUAL_LIMIT = 1000000000,      // предел выделяемой памяти для VirtualXXX
    MA_VIRTUAL_FLAGS = PAGE_READWRITE,  // разрешено чтение и запись страниц
    MA_VIRTUAL_PAGE  = 4096,            // размер страницы виртуальной памяти
    MA_VIRTUAL_GRAN  = 65536,           // размер гранулярности виртуальной памяти
    };

//    const static HANDLE MA_HEAP_HANDLE;     // дескриптор кучи процесса

 void  MarrayFree();
 bool  MarrayVirtualMove(char* pDst, char* pSrc, size_t nMoveSize) const;

 // ++- округлить размер памяти до большей гранулярности
 inline size_t RoundToGranularitySize(const int nSize) const
    { return (( nSize - 1 ) | ( MA_VIRTUAL_GRAN - 1 )) + 1; }

 // ++- округлить размер памяти до большей страницы
 inline size_t RoundToPageSize(const int nSize) const
    { return (( nSize - 1 ) | ( MA_VIRTUAL_PAGE - 1 )) + 1; }

 // ++- округлить адрес памяти до меньшей гранулярности
 inline char* RoundToGranularityAddr(const char* const pAddr) const
    { return (char*)( (int)pAddr & ~( MA_VIRTUAL_GRAN - 1 )); }

 // ++- округлить адрес памяти до большей страницы
 inline char* RoundToPageAddr(const char* const pAddr) const
    { return (char*)( (int)pAddr & ~( MA_VIRTUAL_PAGE - 1 )); }
// --- End ------------------------------------------------

}; //class MARRAY


//===========================================================================
//                    ОПРЕДЕЛЕНИЕ ПРОИЗВОДНЫХ КЛАССОВ 
//---------------------------------------------------------------------------
template <class type, class typeindex = int> class MA: public MARRAY
 {
public:
 typedef type      typeElem;
 typedef typeindex typeIndex;

 MA() { szElem = sizeof(type); szIndex = sizeof(typeindex); }

 inline size_t sizeElem (void) const                // размер элемента (байт)
    { return sizeof(type); }

 inline size_t sizeIndex (void) const               // размер индекса (байт)
    { return sizeof(typeindex); }

 //inline const type& operator() (uint i) const    // значение элемента массива
 inline const type operator() (uint i) const     // значение элемента массива
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  IndexError (nElem.n, i);
    #endif
    return ((const type*)body)[i];
    }

 inline const type & operator[] (uint i) const   // ссылка на элемент массива
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  IndexError (nElem.n, i);
    #endif

    return ((type*)body)[i];
    }

 inline type& operator[] (uint i)                // ссылка на элемент массива
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  IndexError (nElem.n, i);
    #endif

    return ((type*)body)[i];
    }

// Необходимо для VC.NET но не работает в VC 6
template <class type, class typeindex> 
friend const type* constant (MA<type, typeindex>& x); // конст.указ. на первый элемент

 inline const type& last (void) const          // ссылка на последний элемент
    {
    #ifdef  DEBUG
       if (nElem.n <= 0)  ZeroError();
    #endif

    return ((type*)body)[nElem.n - 1];
    }
 
 inline type& last (void)                      // ссылка на последний элемент
    {
    #ifdef  DEBUG
       if (nElem.n <= 0)  ZeroError();
    #endif

    return ((type*)body)[nElem.n-1];
    }

 inline const type& last(uint i) const       // ссылка на i-й элемент с конца
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  ZeroError();
    #endif

    return ((type*)body)[nElem.n - 1 - i];
    }

 inline MA& operator<< (const type source)  // добавить в конец новый элемент
    {
    addElem();
    ((type*)body)[nElem.n-1] = source;
    return *this;
    }

 inline MA& operator>> (type &dest)              // выбрать последний элемент
    {
    #ifdef  DEBUG
       if (nElem.n <= 0)  ZeroError();
    #endif

    dest = ((type*)body)[nElem.n-1];            // сначала присвоить

    setNElem (nElem.n-1);
    return *this;
    }

 inline void swap (MARRAY& x) { MARRAY::swap(x); } // обменять содержимым с x

 inline void swap (uint i, uint j)                // переставить два элемента
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  IndexError(nElem.n, i);
       if (j >= (uint)nElem.n)  IndexError(nElem.n, j);
    #endif

    ::swap (((type*)body)[i], ((type*)body)[j]);
    }

 inline void copy_from(const MA<type, typeindex>& x)
 {
    setNElem( x.nElem() );
    if (!x.isEmpty())
       ::memcpy(body, x.body, sizeof(type) * x.nElem());
 }

 // ДОБАВИТЬ В ХВОСТ СОДЕРЖИМОЕ ЧУЖОГО МАССИВА
 inline void append(const MA<type, typeindex>& src)
 { 
    CONFIRM (&src != this);
    
    if (!src.isEmpty())
    {
       int dst = nElem.n;
       setNElem(nElem.n  + src.nElem());
       ::memcpy(&(this->operator[](dst)), src.body, sizeof(type) * src.nElem());
    }
    
 }

 inline void memmove (uint dest, uint src, uint count)     // сдвиг элементов
    {
    #ifdef  DEBUG
       if (src+count  > (uint)nElem.n) IndexError( nElem.n, src+count-1 );
       if (dest+count > (uint)nElem.n) IndexError( nElem.n, dest+count-1 );
    #endif

    ::memmove (((type*)body) + dest,
              ((type*)body) + src,
              count * sizeof(type));
    }

 inline void memset (uint c)         // заполнить весь массив заданным байтом
    {::memset (body, c, nElem.n * sizeof(type)); }

private:                                             //       ЗАПРЕЩЕНЫ
 MA (MA&);                                           //   инициатор копии и
 void operator= (MA&);                               // оператор присваивания
 };

template <class type, class typeindex> 
inline const type* constant (MA<type, typeindex>& x) // конст.указ. на первый элемент
{
#ifdef  DEBUG
	if (x.nElem.n <= 0)  x.ZeroError();
#endif

return (const type*)(x.body);
}

}  // end of namespace FSG

#endif//ndef __MARRAY_H
