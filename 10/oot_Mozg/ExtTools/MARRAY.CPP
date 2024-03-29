//------- MARRAY.CPP ---------- ��������� �.�. ----- 17.06.91 - 09.10.98 ----
//---------------------------------------------------------------------------
//
//                      ���������  ���������  ��������
//
//---------------------------------------------------------------------------
// 08/06/2000 Zudin   - ��������� ����������
// 13/10/2000 �����   - ���������� ����������
// 29/01/2002 Smirnov - ��������� �������� ������ � ����������
// 30/01/2002 Smirnov - ��������� ��������� ������� ������
// 31/01/2002 Smirnov & Zudin - ����������� ����
// 16/10/2005 Lysenko - ����������, ����������� �� std::exception

#include "stdafx.h"
#include <io.h>
#include "marray.h"

//=============================================================
// ���������� ����������� ������-�������
//
//const HANDLE  FSG::MARRAY::MA_HEAP_HANDLE = ::GetProcessHeap();
//const HANDLE  MA_HEAP_HANDLE = ::GetProcessHeap();

//=============================================================
// --- ������� ��� ����������� ���������� ---------------------
//
    // ����������� ������ ������� �� �������
#define FAILED_INDEX( nIndex, szIndex ) ( nIndex > (size_t)( 0x01L << ( 8u * szIndex - 1 )))

// =============================================================================
namespace //! ������ ���������� ��� ��������� ��������
{
   //! ������� ����� ���������� ��� ��������� ��������, ����������� �� std::exception
   class MAexception : public std::exception
   {
   public:
      MAexception()     //!< ����������� �� ���������
         :_text("Mobile array error") {};
      MAexception(const MAexception& e) //!< ����������� �����������
         :_text(e._text) {};
      virtual LPCSTR what() const { return _text.c_str(); }; //!< ��������� ������ ������
   protected:
      std::string _text; //!< ����� ������
   };

// =============================================================================
   //! ����������, ����. ��� �������� ���. ������ 
   class MAexception_MEMORY : public MAexception
   {
   public:
      MAexception_MEMORY()  //!< ����������� �� ���������
      {
         _text = "Out of memory";
      };
   };

// =============================================================================
   //! ����������, ����. ��� �������� ��������� ������������
   class MAexception_ALLOC : public MAexception
   {
   public:
      MAexception_ALLOC() //!< ����������� �� ���������
      {
         _text = "Out of memory";
      };
   };

// =============================================================================
   //! ����������, ����. ��� ������� ������� ������� �������
   class MAexception_2LARGE : public MAexception
   {
   public:
      MAexception_2LARGE() //!< ����������� �� ���������
      {
         _text = "Internal error";
      };
      MAexception_2LARGE(int n, int sz) //!< ����������� c �����������    
      {
      	  _text = "MAexception_2LARGE(int n, int sz)";
      }
   };

// =============================================================================
   //! ����������, ����. ��� ��������� � ������� 0-�� �������
   class MAexception_0SIZE : public MAexception
   {
   public:
      MAexception_0SIZE() //!< ����������� �� ���������
      {
         _text = "Trying to access an array of zero size";
      }
   };

// =============================================================================
   //! ����������, ����. ��� ������������� ��������� �������
   class MAexception_INDEX : public MAexception
   {
   public:
      MAexception_INDEX() //!< ����������� �� ���������
      {
         _text = "Internal error";
      }
      MAexception_INDEX(int n, int i) //!< ����������� c �����������         
      {
      	  _text= "Trying to access element out of bound";
      }
   };
} // end of nameless namespace

//===========================================================================
//             ������� ������, ��������������� �������� �������
//---------------------------------------------------------------------------
FSG::MARRAY::MARRAY ()
 {
 nElem.n  = 0;                           // ��������� ���
 body     = NULL;                        // � ������ �� p�������
 maxnElem = 0;                           // �������� ������������ �� ��������
 szElem   = 1;                           // p����p �������� (����)
 szIndex  = sizeof(int);                 // p����p �������  (����)
 m_bNote  = false;                       // ������� �������������� ��������
 }

//===========================================================================
//             ���������� ��� �������, ������� ��������� ��������
//---------------------------------------------------------------------------
FSG::MARRAY::~MARRAY()
// Smirnov - ��������� � ������������ � ����� ���������� ��������� � ������������ ������
{
    // ��������� ���������� ������ � �������� ��������
    MarrayFree();
}

//===========================================================================
//                  ���������� ������ � �������� nElem (��� VF)
//---------------------------------------------------------------------------
void FSG::MARRAY::allocSeg(void)
 {
 CONFIRM (body == NULL);
 CONFIRM (maxnElem == 0);                // �������� ������������ �� ��������

 int n = nElem.n;
 nElem.n = 0;                          // ����������  ������� ����� ���������
 setNElem (n);
 }

//===========================================================================
//                  ���������� ������, ������� nElem ������� (��� VF)
//---------------------------------------------------------------------------
void FSG::MARRAY::freeSeg(void)
 {
 CONFIRM (body != NULL);
 CONFIRM (maxnElem != 0);                // �������� ������������ ��������

 int n = nElem.n;
 // ��������� ���������� ������ � �������� ��������
 MarrayFree();
 nElem.n = n;                          // ������������ ����� ���������
 }

//===========================================================================
//                   �������� <n> �����, ������� �� <i> 
//---------------------------------------------------------------------------
void FSG::MARRAY::ins (uint i, uint n)
 {
 CONFIRM (int(n) > 0);                            // ������� �� �����

 setNElem (nElem.n + n);                          // ��������� �����

 if (i + n < (uint)nElem.n)                       // ���� ����� �� ����
    {
    ::memmove (body + szElem * (i+n),             // �������� ����� ������
              body + szElem * i,
              szElem * (nElem.n - i - n));
    }
 else if (i + n > (uint)nElem.n)  IndexError (nElem.n, i);
 }

//===========================================================================
//                    ������� <n> �����, ������� �� <i>
//---------------------------------------------------------------------------
void FSG::MARRAY::del (uint i, uint n)
 {
 if (n == 0)
    return;

 CONFIRM (int(n) > 0);

 if (i + n < (uint)nElem.n)                  // ���� ����� �� ����
    {
    ::memmove (body + szElem * i,            // �������� ����� �����
              body + szElem * (i+n),
              szElem * (nElem.n - i - n));
    }
 else if (i + n > (uint)nElem.n)  IndexError (nElem.n, i);

 setNElem (nElem.n - n);                          // ��������� �����
 }

//===========================================================================
//--------------- �������� ���������� � ��������� �������� <x> --------------

void FSG::MARRAY::swap (MARRAY& x)
 {
 if (&x != this)                                // ���� <x> - ������ ������
    {
    CONFIRM (x.szElem == szElem);               // p����p ��������
    CONFIRM (x.szIndex == szIndex);             // p����p �������

    ::swap (x.maxnElem, maxnElem);              // ����.����� ���������
    ::swap (x.nElem.n, nElem.n);                // ����� ���������
    ::swap (x.body, body);                      // �����
    ::swap (x.m_bNote, m_bNote);                // ���. ���������
    }
 }

//===========================================================================
//-------------------- ��������� �� �������� � <bank> -----------------------

void FSG::MARRAY::put (MARRAY& bank)
// Smirnov - ��������� ��� ��������� ��������� � ������������ ������
{
   // ----------------- Zudin S.V. 14.03.2001 -------------------------------
   CONFIRM( bank.nElem.n == 0 );
   bank.MarrayFree();         // ���������� ��� ������, ���������� ��������
   // -----------------------------------------------------------------------
   bank.szElem   = szElem;                     // p����p ��������
   bank.szIndex  = szIndex;                    // p����p �������
   bank.maxnElem = maxnElem; maxnElem = 0;     // ����.����� ���������
   bank.nElem.n  = nElem.n;  nElem.n = 0;      // ����� ���������
   bank.body     = body;     body = NULL;      // �����
   bank.m_bNote  = m_bNote;  m_bNote = false;  // �������������� ���������
}

//===========================================================================
//---------------------- �������� ������� �� <bank> -------------------------

void FSG::MARRAY::get (MARRAY& bank)
// Smirnov - ��������� ��� ��������� ��������� � ������������ ������
{
   // ----------------- Zudin S.V. 14.03.2001 -------------------------------
   CONFIRM( bank.szElem == szElem );              // p����p ��������
   CONFIRM( bank.szIndex == szIndex );            // p����p �������
   //CONFIRM( nElem.n == 0 );                       // ???
   MarrayFree();              // ���������� ��� ������, ���������� ��������
   // -----------------------------------------------------------------------
   nElem.n  = bank.nElem.n;   bank.nElem.n = 0;     // ����� ���������
   body     = bank.body;      bank.body = NULL;     // �����
   maxnElem = bank.maxnElem;  bank.maxnElem = 0;    // ����.����� ���������
   m_bNote  = m_bNote;        bank.m_bNote = false; // �������������� ���������
}

//===========================================================================
//      ������ ��������� � ������ ������� ���������� ������� �� ������� 
//---------------------------------------------------------------------------

// ������ ����������
void FSG::MARRAY::IndexError (uint n, uint i)
{
    FSG_THROW( MAexception_INDEX (n,i) );
}


// ������ ����
void FSG::MARRAY::ZeroError()
{
    FSG_THROW( MAexception_0SIZE() );
}


//******************************************************************************
//
// �������������� ������ ������ MARRAY
//
//******************************************************************************

// +++ ��������� �� ������ �������������� ������
void FSG::MARRAY::AllocError()
{
    FSG_THROW( MAexception_ALLOC() );

} //AllocError

// +++ ��������� �� ������ ��������� ������
void FSG::MARRAY::MemoryError()
{
    FSG_THROW( MAexception_MEMORY() );

} //MemoryError






//===========================================================================
// ++- ���������� ������� ������
void FSG::MARRAY::MarrayFree()
// Smirnov - ��� ������ ��������� ��������� � ������������ ������
{
    // ���� ������ �����������
    if ( body == NULL )    return;

    // ���� ������ ������ ������ MA_HEAP_LIMIT
    if ( maxnElem * szElem < MA_HEAP_LIMIT )
        //::HeapFree( MA_HEAP_HANDLE, MA_HEAP_FLAGS, body );
        ::free(body);
    else
        ::VirtualFree( body, 0, MEM_RELEASE );

    // �������� ���������
    body = NULL;
    maxnElem = nElem.n = 0;
    m_bNote = false;

} //MarrayFree





//===========================================================================
// +++ ������� ������ �������� (������ ��������)
void FSG::MARRAY::empty()
// Smirnov - ��������� ��� ��������� ��������� � ������������ ������
{
    // ���� ������ �����������
    if ( body == NULL )    return;

    // ���� ��������� Virtual
    if ( maxnElem * szElem >= MA_HEAP_LIMIT )
        ::VirtualFree( body, 0, MEM_DECOMMIT );

    // �������� ���������
    m_bNote = false;
    nElem.n = 0;

} //empty

//===========================================================================
// +++ ����������� ������
bool FSG::MARRAY::MarrayVirtualMove(char* pDst, char* pSrc, size_t nMoveSize) const
// Smirnov - ��� ������ ��������� ��������� � ������������ ������
// ������������ �������� ���������� ������
//  IN:
//      �������� ������ ���� ��� �������
//      ������� ������ ���� ������ ��������������
//      ------------------------------------------
//      !!! round Granularity !!!
//      pDst   - ����� ������������������ �������-�������
//      pSrc   - ����� ����������� �������-���������
//      nCount - ���������� ������������ ������
//      ------------------------------------------
//  OUT:
//      �������� ���������, �� ������ ���������������
//      ������� �������
//      ----------------------------------------------
//      return - ������������ ����������
{
    // ��������
    CONFIRM( nMoveSize > 0 );
    CONFIRM( nMoveSize == RoundToPageSize( nMoveSize ));
    CONFIRM( pDst == RoundToGranularityAddr( pDst ));
    CONFIRM( pSrc == RoundToGranularityAddr( pSrc ));

    // ����������� ������
    while ( nMoveSize > 0 )
    {
        size_t nCopy = min( nMoveSize, static_cast<size_t>( MA_VIRTUAL_GRAN ));
        CONFIRM( nCopy > 0 );

        // �������� ��������� ������ ������
        if ( !::VirtualAlloc( pDst, nCopy, MEM_COMMIT, MA_VIRTUAL_FLAGS ))
            return false;

        // ����������� ��������� ������
        ::memcpy( pDst, pSrc, nCopy );

        // ���������� �� ������ ������ ������
        if ( !::VirtualFree( pSrc, nCopy, MEM_DECOMMIT ))
            return false;

        // ������� � ���������� ������� ������
        pDst = pDst + nCopy;
        pSrc = pSrc + nCopy;
        nMoveSize -= nCopy;
    }

    return true;

} //MarrayVirtualMove








//===========================================================================
// ++- ��������� ����������� ���������� ��������� (��������������)
int FSG::MARRAY::setMaxNElem (uint n)
// Smirnov - ����������� �������� ��������� � ������������ ������
//  IN:
//      n - ������ �������������� ������� � ���������
//  OUT:
//      return - ���������� ����������������� ������� � ���������
{
    // ������ ��������� ����������
    if ( n <= static_cast<uint>( nElem.n ))
    {
        // ��������� �� ���� � ������ == 0, �.�. ����� (nElem >= n)
        if ( nElem.n == 0 )     MarrayFree();

        // �.�. �������� ����� �������, ������, ������ �� ������
        // ����, ������ �� ���������� ��������� ����� ���� � ���������
        return maxnElem;
    }

    // �������� �������
    if (static_cast<uint>(maxnElem) == n)    return maxnElem;

    // ��������� ������� ������
    //CONFIRM (!::IsBadReadPtr (body, szElem * nElem.n));

    // --- ���� ��� ��������� Virtual -----------------------------
    // ������� � (n == 0) � (n <= nElem) ��� ����������!
    if ( maxnElem * szElem >= MA_HEAP_LIMIT )
    {
        // ����� ������ ��������������� ������ � �����������
        size_t nNewReserveSize = RoundToGranularitySize( n * szElem );
        CONFIRM( nNewReserveSize > 0 );

        // ������ ������ �� ����
        if ((n = nNewReserveSize / szElem) == static_cast<uint>(maxnElem))
            return maxnElem;

        // ������������� ����� ������ ������
        char* pNewBody = static_cast<char*>( ::VirtualAlloc( 
                0, nNewReserveSize, MEM_RESERVE, MA_VIRTUAL_FLAGS ));

        // ��������� ��������� ��������������
        if ( pNewBody == NULL )     AllocError();

        // �������� ���������� �������� � ����� ������
        if (nElem.n)                             // ���� ������ ���� ��������
        if ( !MarrayVirtualMove( pNewBody, body, RoundToPageSize( nElem.n * szElem )))
            MemoryError();

        // ���������� ������ ������ ������
        if (body)                 // ���� �������� ������������ ���� ��������
        ::VirtualFree( body, 0, MEM_RELEASE );

        // �������������� ���������
        m_bNote = false;
        body = pNewBody;
    }
    else
    // --- ��������� ������ Heap -------------------------------------
    // ��� ������ �� ���������
    {
        // --- ��� � ������� � Heap ---
        if ( n * szElem < MA_HEAP_LIMIT )
        {
            // ����������: �� ����� 16 ���� ��� 4 ���������
            n = max(( 15U + szElem ) / szElem, max( 4U, n ));

            // ������ ������ �� ����
            if (static_cast<uint>(maxnElem) == n)  return maxnElem;

            // ���� ����� ���� �������� ������
            if ( body )
               {
                //CONFIRM (!::IsBadReadPtr (body, szElem * 4));
                body = static_cast<char*>(::realloc(body, n * szElem));
               }
            else
               {
                // �������� ����� ������ ������ � Heap
                body = static_cast<char*>(::malloc(n * szElem));
               }

            // ������� ������ ����������������� ������
            if ( !body )     MemoryError();
        }
        else
        // --- ��������� � ������� Virtual ---
        {
            // ����� ������ ��������������� ������ � �����������
            size_t nNewReserveSize = RoundToGranularitySize( n * szElem );
//            CONFIRM( nNewReserveSize > 0 );
            CONFIRM( nNewReserveSize >= n * szElem );

            // ������������� ����� ������ ������
            char* pNewBody = static_cast<char*>( ::VirtualAlloc( 
                    0, nNewReserveSize, MEM_RESERVE, MA_VIRTUAL_FLAGS ));

            // ��������� ��������� ��������������
            if ( !pNewBody )     AllocError();

            // ���� ����� ���� �������� ������
            if ( body && nElem.n)
            {
                //CONFIRM( RoundToPageSize( nElem.n * szElem ) >= nElem.n * szElem);

                // ������ ������� ��������� ������
                if ( !::VirtualAlloc( pNewBody, RoundToPageSize( nElem.n * szElem ),
                            MEM_COMMIT, MA_VIRTUAL_FLAGS ))
                    MemoryError();

                // ���������� �������� � ����� ������
                CONFIRM( nElem.n * szElem > 0 );
                memcpy( pNewBody, body, nElem.n * szElem );

                ::free(body);
            }

            // �������������� ���������
            m_bNote = false;
            body = pNewBody;
            //n = nNewReserveSize / szElem;

//        CONFIRM( n != 32768 || szElem != 4 || maxnElem * szElem >= MA_HEAP_LIMIT );

        }
    }

    // ��������� ������� ������
    //CONFIRM (!::IsBadWritePtr (body, nElem.n * szElem));

    return maxnElem = n;
} //setMaxNElem

//===========================================================================
// ++- ��������� ������ ���������� ���������
void FSG::MARRAY::setNElem (uint n)
// Smirnov - ����������� �������� ��������� � ������������ ������
//  IN:
//      n - ����� ������ ������� � ���������
//  OUT:
//      ������������� ������������ ������ ���������
//      ��� ��� ������ ���������� ����������
{
    // ������ �� ������
    if (n == static_cast<uint>(nElem.n))     return;

    // ��� �������� ��������� (������ �� �������)
    if ( n == 0 )
    {
        MarrayFree();
        return;                              
    }

    // --- ���������� -----------------------------------------
    // ������� � (n == 0) � (n == nElem) ��� ����������
    if ( n > static_cast<uint>( nElem.n ))
    {
       // �������� ��������� ��������
        if ( FAILED_INDEX( n, szIndex ) || 
           ( n > static_cast<uint>( MA_VIRTUAL_LIMIT / szElem )))
            FSG_THROW( MAexception_2LARGE(n, szElem) );

        // ���� ������� ������ �� �������, �� ��������� ������ � 1.5 ����
        // ��, ������ ��� ��������� �������� �������
        if ( n > static_cast<uint>( maxnElem ))
            setMaxNElem( maxnElem? n + n/2u: n );

        // --- ������ ���� ��������� Virtual ---
        if ( maxnElem * szElem >= MA_HEAP_LIMIT )
        {
            // ���������� ������� ���������
            size_t nNewCommitSize = RoundToPageSize( szElem * n );
            size_t nOldCommitSize = RoundToPageSize( szElem * nElem.n );

            // ���� ��� ��������� ��������
            if ( m_bNote )
            {
               nOldCommitSize += MA_VIRTUAL_PAGE;
               m_bNote = false;
            }

            // ������� ������ �� ���������
            if ( nNewCommitSize >= nOldCommitSize )
            {

                // ���� ���������� ������
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
    // --- ���������� -----------------------------------------
    // ������� � (n == 0) � (n == nElem) ��� ����������
    {
        // --- ������ ���� ��������� Virtual ---
        if ( maxnElem * szElem >= MA_HEAP_LIMIT )
        {
            // ���������� ������� ���������
            size_t nNewCommitSize = RoundToPageSize( n * szElem );
            size_t nEndSize = RoundToPageSize( nElem.n * szElem ) - nNewCommitSize;

            // ���� ���������� �������
            if ( nEndSize )
            {
                // �������� ��� ���� ��������
                if ( m_bNote )  nEndSize += MA_VIRTUAL_PAGE;

                // ���������� ������ �� ���� ��������
                if ( nEndSize < MA_VIRTUAL_PAGE * 2 )
                    m_bNote = true;
                else
                // ������� �� ������
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
