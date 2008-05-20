//------- MARRAY.CPP ---------- ПОЛУБАСОВ О.Б. ----- 17.06.91 - 09.10.98 ----
//---------------------------------------------------------------------------
//
//                      ПОДДЕРЖКА  МОБИЛЬНЫХ  МАССИВОВ
//
//---------------------------------------------------------------------------
// 08/06/2000 Zudin   - генерация исключений
// 13/10/2000 Зудин   - исправлены исключения
// 29/01/2002 Smirnov - изменение принципа работы с выделением
// 30/01/2002 Smirnov - коррекция остальных методов класса
// 31/01/2002 Smirnov & Zudin - оптимизация кода
// 16/10/2005 Lysenko - исключения, наследуемые от std::exception

#include "stdafx.h"
#include <io.h>
#include "marray.h"

//=============================================================
// ЗАПОЛНЕНИЕ СТАТИЧЕСКИХ ЧЛЕНОВ-СВОЙСТВ
//
//const HANDLE  FSG::MARRAY::MA_HEAP_HANDLE = ::GetProcessHeap();
//const HANDLE  MA_HEAP_HANDLE = ::GetProcessHeap();

//=============================================================
// --- МАКРОСЫ ДЛЯ ЛОКАЛИЗАЦИИ ВЫЧИСЛЕНИЙ ---------------------
//
    // определение выхода индекса за границы
#define FAILED_INDEX( nIndex, szIndex ) ( nIndex > (size_t)( 0x01L << ( 8u * szIndex - 1 )))

// =============================================================================
namespace //! классы исключений для мобильных массивов
{
   //! Базовый класс исключений для мобильных массивов, наследуемый от std::exception
   class MAexception : public std::exception
   {
   public:
      MAexception()     //!< конструктор по умолчанию
         :_text("Mobile array error") {};
      MAexception(const MAexception& e) //!< конструктор копирования
         :_text(e._text) {};
      virtual LPCSTR what() const { return _text.c_str(); }; //!< получение текста ошибки
   protected:
      std::string _text; //!< текст ошибки
   };

// =============================================================================
   //! Исключение, возн. при нехватке физ. памяти 
   class MAexception_MEMORY : public MAexception
   {
   public:
      MAexception_MEMORY()  //!< конструктор по умолчанию
      {
         _text = "Out of memory";
      };
   };

// =============================================================================
   //! Исключение, возн. при нехватке адресного пространства
   class MAexception_ALLOC : public MAexception
   {
   public:
      MAexception_ALLOC() //!< конструктор по умолчанию
      {
         _text = "Out of memory";
      };
   };

// =============================================================================
   //! Исключение, возн. при слишком большом размере массива
   class MAexception_2LARGE : public MAexception
   {
   public:
      MAexception_2LARGE() //!< конструктор по умолчанию
      {
         _text = "Internal error";
      };
      MAexception_2LARGE(int n, int sz) //!< конструктор c параметрами    
      {
      	  _text = "MAexception_2LARGE(int n, int sz)";
      }
   };

// =============================================================================
   //! Исключение, возн. при обращении к массиву 0-го размера
   class MAexception_0SIZE : public MAexception
   {
   public:
      MAexception_0SIZE() //!< конструктор по умолчанию
      {
         _text = "Trying to access an array of zero size";
      }
   };

// =============================================================================
   //! Исключение, возн. при использовании неверного индекса
   class MAexception_INDEX : public MAexception
   {
   public:
      MAexception_INDEX() //!< конструктор по умолчанию
      {
         _text = "Internal error";
      }
      MAexception_INDEX(int n, int i) //!< конструктор c параметрами         
      {
      	  _text= "Trying to access element out of bound";
      }
   };
} // end of nameless namespace

//===========================================================================
//             СОЗДАТЬ МАССИВ, ЗАРЕЗЕРВИРОВАТЬ ДИАПАЗОН АДРЕСОВ
//---------------------------------------------------------------------------
FSG::MARRAY::MARRAY ()
 {
 nElem.n  = 0;                           // элементов нет
 body     = NULL;                        // в памяти не pазмещен
 maxnElem = 0;                           // адресное пространство не выделено
 szElem   = 1;                           // pазмеp элемента (байт)
 szIndex  = sizeof(int);                 // pазмеp индекса  (байт)
 m_bNote  = false;                       // признак дополнительной страницы
 }

//===========================================================================
//             ОСВОБОДИТЬ ВСЕ РЕСУРСЫ, ЗАНЯТЫЕ МОБИЛЬНЫМ МАССИВОМ
//---------------------------------------------------------------------------
FSG::MARRAY::~MARRAY()
// Smirnov - изменения в соответствии с новым механизмом выделения и освобождения памяти
{
    // полностью освободить память и обнулить свойтсва
    MarrayFree();
}

//===========================================================================
//                  РАЗМЕСТИТЬ МАССИВ С ЗАДАННЫМ nElem (ДЛЯ VF)
//---------------------------------------------------------------------------
void FSG::MARRAY::allocSeg(void)
 {
 CONFIRM (body == NULL);
 CONFIRM (maxnElem == 0);                // адресное пространство не выделено

 int n = nElem.n;
 nElem.n = 0;                          // УСТАНОВИТЬ  НУЛЕВОЕ ЧИСЛО ЭЛЕМЕНТОВ
 setNElem (n);
 }

//===========================================================================
//                  ОСВОБОДИТЬ МАССИВ, ОСТАВИВ nElem ПРЕЖНИМ (ДЛЯ VF)
//---------------------------------------------------------------------------
void FSG::MARRAY::freeSeg(void)
 {
 CONFIRM (body != NULL);
 CONFIRM (maxnElem != 0);                // адресное пространство выделено

 int n = nElem.n;
 // полностью освободить память и обнулить свойтсва
 MarrayFree();
 nElem.n = n;                          // ВОССТАНОВИТЬ ЧИСЛО ЭЛЕМЕНТОВ
 }

//===========================================================================
//                   ВСТАВИТЬ <n> ЯЧЕЕК, НАЧИНАЯ ОТ <i> 
//---------------------------------------------------------------------------
void FSG::MARRAY::ins (uint i, uint n)
 {
 PROFILE_FAST_FUNC ("MARRAY:ins");
 CONFIRM (int(n) > 0);                            // вставка не пуста

 setNElem (nElem.n + n);                          // УВЕЛИЧИТЬ ДЛИНУ

 if (i + n < (uint)nElem.n)                       // ЕСЛИ ХВОСТ НЕ ПУСТ
    {
    ::memmove (body + szElem * (i+n),             // СДВИНУТЬ ХВОСТ ВПРАВО
              body + szElem * i,
              szElem * (nElem.n - i - n));
    }
 else if (i + n > (uint)nElem.n)  IndexError (nElem.n, i);
 }

//===========================================================================
//                    УДАЛИТЬ <n> ЯЧЕЕК, НАЧИНАЯ ОТ <i>
//---------------------------------------------------------------------------
void FSG::MARRAY::del (uint i, uint n)
 {
 PROFILE_FAST_FUNC ("MARRAY:del");

 if (n == 0)
    return;

 CONFIRM (int(n) > 0);

 if (i + n < (uint)nElem.n)                  // ЕСЛИ ХВОСТ НЕ ПУСТ
    {
    ::memmove (body + szElem * i,            // СДВИНУТЬ ХВОСТ ВЛЕВО
              body + szElem * (i+n),
              szElem * (nElem.n - i - n));
    }
 else if (i + n > (uint)nElem.n)  IndexError (nElem.n, i);

 setNElem (nElem.n - n);                          // УМЕНЬШИТЬ ДЛИНУ
 }

//===========================================================================
//--------------- ОБМЕНЯТЬ СОДЕРЖИМЫМ С МОБИЛЬНЫМ МАССИВОМ <x> --------------

void FSG::MARRAY::swap (MARRAY& x)
 {
 if (&x != this)                                // ЕСЛИ <x> - ДРУГОЙ МАССИВ
    {
    CONFIRM (x.szElem == szElem);               // pазмеp элемента
    CONFIRM (x.szIndex == szIndex);             // pазмеp индекса

    ::swap (x.maxnElem, maxnElem);              // макс.число элементов
    ::swap (x.nElem.n, nElem.n);                // число элементов
    ::swap (x.body, body);                      // адрес
    ::swap (x.m_bNote, m_bNote);                // доп. страничка
    }
 }

//===========================================================================
//-------------------- ПОМЕСТИТЬ НА ХРАНЕНИЕ В <bank> -----------------------

void FSG::MARRAY::put (MARRAY& bank)
// Smirnov - изменения для механизма выделения и освобождения памяти
{
   // ----------------- Zudin S.V. 14.03.2001 -------------------------------
   CONFIRM( bank.nElem.n == 0 );
   bank.MarrayFree();         // освободить всю память, занимаемую массивом
   // -----------------------------------------------------------------------
   bank.szElem   = szElem;                     // pазмеp элемента
   bank.szIndex  = szIndex;                    // pазмеp индекса
   bank.maxnElem = maxnElem; maxnElem = 0;     // макс.число элементов
   bank.nElem.n  = nElem.n;  nElem.n = 0;      // число элементов
   bank.body     = body;     body = NULL;      // адрес
   bank.m_bNote  = m_bNote;  m_bNote = false;  // дополнительная страничка
}

//===========================================================================
//---------------------- ПОЛУЧИТЬ ОБРАТНО ИЗ <bank> -------------------------

void FSG::MARRAY::get (MARRAY& bank)
// Smirnov - изменения для механизма выделения и освобождения памяти
{
   // ----------------- Zudin S.V. 14.03.2001 -------------------------------
   CONFIRM( bank.szElem == szElem );              // pазмеp элемента
   CONFIRM( bank.szIndex == szIndex );            // pазмеp индекса
   //CONFIRM( nElem.n == 0 );                       // ???
   MarrayFree();              // освободить всю память, занимаемую массивом
   // -----------------------------------------------------------------------
   nElem.n  = bank.nElem.n;   bank.nElem.n = 0;     // число элементов
   body     = bank.body;      bank.body = NULL;     // адрес
   maxnElem = bank.maxnElem;  bank.maxnElem = 0;    // макс.число элементов
   m_bNote  = m_bNote;        bank.m_bNote = false; // дополнительная страничка
}

//===========================================================================
//      ВЫДАТЬ СООБЩЕНИЕ О ВЫХОДЕ ИНДЕКСА МОБИЛЬНОГО МАССИВА ЗА ГРАНИЦЫ 
//---------------------------------------------------------------------------

// ошибка индексации
void FSG::MARRAY::IndexError (uint n, uint i)
{
    FSG_THROW( MAexception_INDEX (n,i) );
}


// массив пуст
void FSG::MARRAY::ZeroError()
{
    FSG_THROW( MAexception_0SIZE() );
}


//******************************************************************************
//
// ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ КЛАССА MARRAY
//
//******************************************************************************

// +++ сообщение об ошибке резервирования памяти
void FSG::MARRAY::AllocError()
{
    FSG_THROW( MAexception_ALLOC() );

} //AllocError

// +++ сообщение об ошибке выделения памяти
void FSG::MARRAY::MemoryError()
{
    FSG_THROW( MAexception_MEMORY() );

} //MemoryError






//===========================================================================
// ++- освободить занятую память
void FSG::MARRAY::MarrayFree()
// Smirnov - для нового механизма выделения и освобождения памяти
{
    // если нечего освобождать
    if ( body == NULL )    return;

    // если старый резерв меньше MA_HEAP_LIMIT
    if ( maxnElem * szElem < MA_HEAP_LIMIT )
        //::HeapFree( MA_HEAP_HANDLE, MA_HEAP_FLAGS, body );
        ::free(body);
    else
        ::VirtualFree( body, 0, MEM_RELEASE );

    // обнулить параметры
    body = NULL;
    maxnElem = nElem.n = 0;
    m_bNote = false;

} //MarrayFree





//===========================================================================
// +++ удалить только элементы (резерв оставить)
void FSG::MARRAY::empty()
// Smirnov - изменения для механизма выделения и освобождения памяти
{
    // если нечего освобождать
    if ( body == NULL )    return;

    // если применяли Virtual
    if ( maxnElem * szElem >= MA_HEAP_LIMIT )
        ::VirtualFree( body, 0, MEM_DECOMMIT );

    // обнулить параметры
    m_bNote = false;
    nElem.n = 0;

} //empty

//===========================================================================
// +++ копирование памяти
bool FSG::MARRAY::MarrayVirtualMove(char* pDst, char* pSrc, size_t nMoveSize) const
// Smirnov - для нового механизма выделения и освобождения памяти
// максимальная экономия физической памяти
//  IN:
//      источник должен быть уже выделен
//      приёмник должен быть только зарезервирован
//      ------------------------------------------
//      !!! round Granularity !!!
//      pDst   - адрес зарезервированного региона-приёмика
//      pSrc   - адрес выделенного региона-источника
//      nCount - количество перемещяемых байтов
//      ------------------------------------------
//  OUT:
//      источник освобождён, но адреса зарезервированы
//      приёмник выделен
//      ----------------------------------------------
//      return - корректность выполнения
{
    // проверка
    CONFIRM( nMoveSize > 0 );
    CONFIRM( nMoveSize == RoundToPageSize( nMoveSize ));
    CONFIRM( pDst == RoundToGranularityAddr( pDst ));
    CONFIRM( pSrc == RoundToGranularityAddr( pSrc ));

    // копирование памяти
    while ( nMoveSize > 0 )
    {
        size_t nCopy = min( nMoveSize, static_cast<size_t>( MA_VIRTUAL_GRAN ));
        CONFIRM( nCopy > 0 );

        // выделить очередной регион памяти
        if ( !::VirtualAlloc( pDst, nCopy, MEM_COMMIT, MA_VIRTUAL_FLAGS ))
            return false;

        // скопировать очередной регион
        ::memcpy( pDst, pSrc, nCopy );

        // освободить не нужный регион памяти
        if ( !::VirtualFree( pSrc, nCopy, MEM_DECOMMIT ))
            return false;

        // перейти к следующему региону памяти
        pDst = pDst + nCopy;
        pSrc = pSrc + nCopy;
        nMoveSize -= nCopy;
    }

    return true;

} //MarrayVirtualMove








//===========================================================================
// ++- установка предельного количества элементов (резервирование)
int FSG::MARRAY::setMaxNElem (uint n)
// Smirnov - переработан механизм выделения и освобождения памяти
//  IN:
//      n - размер резервирования массива в элементах
//  OUT:
//      return - количество зарезервированных позиций в элементах
{
    // резерв ограничен элементами
    if ( n <= static_cast<uint>( nElem.n ))
    {
        // элементов не было и резерв == 0, т.к. здесь (nElem >= n)
        if ( nElem.n == 0 )     MarrayFree();

        // т.к. элементы жалко удалять, значит, НИЧЕГО НЕ ДЕЛАТЬ
        // хотя, резерв до количества элементов можно было и уменьшить
        return maxnElem;
    }

    // действия излишни
    if (static_cast<uint>(maxnElem) == n)    return maxnElem;

    // проверить битость памяти
    //CONFIRM (!::IsBadReadPtr (body, szElem * nElem.n));

    // --- если уже применяли Virtual -----------------------------
    // вариант с (n == 0) и (n <= nElem) уже рассмотрен!
    if ( maxnElem * szElem >= MA_HEAP_LIMIT )
    {
        // новый размер резервированния памяти с округлением
        size_t nNewReserveSize = RoundToGranularitySize( n * szElem );
        CONFIRM( nNewReserveSize > 0 );

        // ничего делать не надо
        if ((n = nNewReserveSize / szElem) == static_cast<uint>(maxnElem))
            return maxnElem;

        // резервировать новый регион памяти
        char* pNewBody = static_cast<char*>( ::VirtualAlloc( 
                0, nNewReserveSize, MEM_RESERVE, MA_VIRTUAL_FLAGS ));

        // проверить результат резервирования
        if ( pNewBody == NULL )     AllocError();

        // экономно копировать элементы в новый регион
        if (nElem.n)                             // ЕСЛИ ПАМЯТЬ БЫЛА ВЫДЕЛЕНА
        if ( !MarrayVirtualMove( pNewBody, body, RoundToPageSize( nElem.n * szElem )))
            MemoryError();

        // освободить старый регион памяти
        if (body)                 // ЕСЛИ АДРЕСНОЕ ПРОСТРАНСТВО БЫЛО ВЫДЕЛЕНО
        ::VirtualFree( body, 0, MEM_RELEASE );

        // корректировать параметры
        m_bNote = false;
        body = pNewBody;
    }
    else
    // --- применяли только Heap -------------------------------------
    // или ничего не применяли
    {
        // --- так и остаёмся в Heap ---
        if ( n * szElem < MA_HEAP_LIMIT )
        {
            // ОГРАНИЧНИЕ: не менее 16 байт или 4 элементов
            n = max(( 15U + szElem ) / szElem, max( 4U, n ));

            // делать ничего не надо
            if (static_cast<uint>(maxnElem) == n)  return maxnElem;

            // если ранее была выделена память
            if ( body )
               {
                //CONFIRM (!::IsBadReadPtr (body, szElem * 4));
                body = static_cast<char*>(::realloc(body, n * szElem));
               }
            else
               {
                // выделить новый регион памяти в Heap
                body = static_cast<char*>(::malloc(n * szElem));
               }

            // поймать ошибку перераспределения памяти
            if ( !body )     MemoryError();
        }
        else
        // --- переходим в область Virtual ---
        {
            // новый размер резервированния памяти с округлением
            size_t nNewReserveSize = RoundToGranularitySize( n * szElem );
//            CONFIRM( nNewReserveSize > 0 );
            CONFIRM( nNewReserveSize >= n * szElem );

            // резервировать новый регион памяти
            char* pNewBody = static_cast<char*>( ::VirtualAlloc( 
                    0, nNewReserveSize, MEM_RESERVE, MA_VIRTUAL_FLAGS ));

            // проверить результат резервирования
            if ( !pNewBody )     AllocError();

            // если ранее была выделена память
            if ( body && nElem.n)
            {
                //CONFIRM( RoundToPageSize( nElem.n * szElem ) >= nElem.n * szElem);

                // размер области выделения памяти
                if ( !::VirtualAlloc( pNewBody, RoundToPageSize( nElem.n * szElem ),
                            MEM_COMMIT, MA_VIRTUAL_FLAGS ))
                    MemoryError();

                // копировать элементы в новый регион
                CONFIRM( nElem.n * szElem > 0 );
                memcpy( pNewBody, body, nElem.n * szElem );

                ::free(body);
            }

            // корректировать параметры
            m_bNote = false;
            body = pNewBody;
            //n = nNewReserveSize / szElem;

//        CONFIRM( n != 32768 || szElem != 4 || maxnElem * szElem >= MA_HEAP_LIMIT );

        }
    }

    // проверить битость памяти
    //CONFIRM (!::IsBadWritePtr (body, nElem.n * szElem));

    return maxnElem = n;
} //setMaxNElem

//===========================================================================
// ++- установка нового количества элементов
void FSG::MARRAY::setNElem (uint n)
// Smirnov - переработан механизм выделения и освобождения памяти
//  IN:
//      n - новый размер массива в элементах
//  OUT:
//      устанавливает передаваемый размер элементов
//      или при ошибке генерирует исключение
{
    // ничего не делать
    if (n == static_cast<uint>(nElem.n))     return;

    // все элементы удаляются (резерв НЕ остаётся)
    if ( n == 0 )
    {
        MarrayFree();
        return;                              
    }

    // --- увеличение -----------------------------------------
    // вариант с (n == 0) и (n == nElem) уже рассмотрен
    if ( n > static_cast<uint>( nElem.n ))
    {
       // контроль диапазона индексов
        if ( FAILED_INDEX( n, szIndex ) || 
           ( n > static_cast<uint>( MA_VIRTUAL_LIMIT / szElem )))
            FSG_THROW( MAexception_2LARGE(n, szElem) );

        // если резерва памяти не хватает, то увеличить резерв в 1.5 раза
        // но, только при повторной нехватке резерва
        if ( n > static_cast<uint>( maxnElem ))
            setMaxNElem( maxnElem? n + n/2u: n );

        // --- только если применяем Virtual ---
        if ( maxnElem * szElem >= MA_HEAP_LIMIT )
        {
            // определить размеры выделения
            size_t nNewCommitSize = RoundToPageSize( szElem * n );
            size_t nOldCommitSize = RoundToPageSize( szElem * nElem.n );

            // есть ещё резервная страница
            if ( m_bNote )
            {
               nOldCommitSize += MA_VIRTUAL_PAGE;
               m_bNote = false;
            }

            // резерва памяти не хвататает
            if ( nNewCommitSize >= nOldCommitSize )
            {

                // надо довыделить память
                if ( nNewCommitSize != nOldCommitSize )
                {
                    if ( !::VirtualAlloc( body + nOldCommitSize, 
                            nNewCommitSize - nOldCommitSize, MEM_COMMIT,
                            MA_VIRTUAL_FLAGS ))
                        MemoryError();
                }
            }
        }
    }
    else
    // --- уменьшение -----------------------------------------
    // вариант с (n == 0) и (n == nElem) уже рассмотрен
    {
        // --- только если применяем Virtual ---
        if ( maxnElem * szElem >= MA_HEAP_LIMIT )
        {
            // определить размеры выделения
            size_t nNewCommitSize = RoundToPageSize( n * szElem );
            size_t nEndSize = RoundToPageSize( nElem.n * szElem ) - nNewCommitSize;

            // есть уменьшение страниц
            if ( nEndSize )
            {
                // добавить ещё одну страницу
                if ( m_bNote )  nEndSize += MA_VIRTUAL_PAGE;

                // уменьшение только на одну страницу
                if ( nEndSize < MA_VIRTUAL_PAGE * 2 )
                    m_bNote = true;
                else
                // удалять всё лишнее
                {
                    m_bNote = false;
                    if ( !::VirtualFree( body + nNewCommitSize, nEndSize, MEM_DECOMMIT ))
                        MemoryError();
                }
            }
        }
    }

    nElem.n = n;

} //setNElem
