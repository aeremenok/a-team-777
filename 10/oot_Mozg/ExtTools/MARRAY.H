//-------- MARRAY.H ----------- �.�. ��������� ---- 06.02.91 -- 28.04.01 ----
//---------------------------------------------------------------------------
//
//            �����������  ���������  �������  ���������  ��������
//
// --------------------------------------------------------------------------
// 08/06/2000 Zudin   - ��������� ����������
// 12.10.2000 Zudin   - ������� �� DLL
// 29.01.2002 Smirnov - ����������� inline (��� �������������),
//                      ���� �������� � ����� ������ � �����,
//                      ��������� �������� ������ � ����������,
// 31/01/2002 Smirnov & Zudin - ����������� ����

#ifndef  __MARRAY_H
#define  __MARRAY_H

#include "commdef.h"
#include "nelem.h"
#include "excptns.h" // ������� ����� ��� ����������

namespace FSG  // FreeStyle Group
{
//===========================================================================
//                ������� ����� ��������� ��������� �������� 
//---------------------------------------------------------------------------
class MARRAY
 {
public:
 void setNElem (uint n);           // ���������� (��������) ����� ���������

 NELEM<MARRAY> nElem;              // ����� ��������� (���������������)

protected:
 int   maxnElem;                   // ����.����� ���������
 char* body;                       // ����� ���� �������
 short szElem;                     // ������ �������� (����)
 char  szIndex;                    // ������ �������  (����)
 bool  m_bNote;                    // ������� �������� �������������� ��������

  MARRAY ();
 ~MARRAY ();

 void allocSeg (void);          // �������� ������� ������ ��� �������
 void freeSeg  (void);          // ���������� ������, ������� nElem �������

 inline void addElem (void)     // ��������� ����� ������� �� ���� �������
    { setNElem (nElem.n+1); }

 int  setMaxNElem (uint n);     // ���������� ����.����� ���������

public:
 long getSizePack () const;                  // ��������� ����������� ������
 long write (HANDLE hFile) const;            // �������� ������ � ����
 void read  (HANDLE hFile);                  // ��������� ������ �� �����

 static void IndexError (uint n, uint i);    // ��������� �� ������ �������
 static void ZeroError  (void);              // ��������� � ������ �������

 inline int maxNElem (void) const            // ����.����� ���������
    { return maxnElem; }

 void ins (uint i, uint n = 1); // �������� <n> �����, ������� �� <i>
 void del (uint i, uint n = 1); // �������  <n> �����, ������� �� <i>

 void put  (MARRAY& bank);      // ��������� �� �������� � <bank>
 void get  (MARRAY& bank);      // �������� ������� �� <bank>
 void swap (MARRAY& x);         // �������� ���������� � <x>

 void empty();                  // ���������� ��� �������, ������� ��������
 bool isEmpty() const { return nElem.n == 0; } ;              // ������ ����?

 friend class MA_NELEM;
 friend class virt_file;
 friend class VF_KEEP;
 friend class VF_BRK;

// --- Begin ----------------------------------------------
// Smirnov: 29.01.2002 - 31.01.2002
public:
 static void AllocError();               // ��������� �� ������ �������������� ������
 static void MemoryError();              // ��������� �� ������ ��������� ������

protected:
 enum
    {
    MA_HEAP_LIMIT    = 65536,           // ���������� ������ ������� � ����
    MA_HEAP_FLAGS    = 0,               // ������� ������ ��� ������� HeapXXX
    MA_VIRTUAL_LIMIT = 1000000000,      // ������ ���������� ������ ��� VirtualXXX
    MA_VIRTUAL_FLAGS = PAGE_READWRITE,  // ��������� ������ � ������ �������
    MA_VIRTUAL_PAGE  = 4096,            // ������ �������� ����������� ������
    MA_VIRTUAL_GRAN  = 65536,           // ������ ������������� ����������� ������
    };

//    const static HANDLE MA_HEAP_HANDLE;     // ���������� ���� ��������

 void  MarrayFree();
 bool  MarrayVirtualMove(char* pDst, char* pSrc, size_t nMoveSize) const;

 // ++- ��������� ������ ������ �� ������� �������������
 inline size_t RoundToGranularitySize(const int nSize) const
    { return (( nSize - 1 ) | ( MA_VIRTUAL_GRAN - 1 )) + 1; }

 // ++- ��������� ������ ������ �� ������� ��������
 inline size_t RoundToPageSize(const int nSize) const
    { return (( nSize - 1 ) | ( MA_VIRTUAL_PAGE - 1 )) + 1; }

 // ++- ��������� ����� ������ �� ������� �������������
 inline char* RoundToGranularityAddr(const char* const pAddr) const
    { return (char*)( (int)pAddr & ~( MA_VIRTUAL_GRAN - 1 )); }

 // ++- ��������� ����� ������ �� ������� ��������
 inline char* RoundToPageAddr(const char* const pAddr) const
    { return (char*)( (int)pAddr & ~( MA_VIRTUAL_PAGE - 1 )); }
// --- End ------------------------------------------------

}; //class MARRAY


//===========================================================================
//                    ����������� ����������� ������� 
//---------------------------------------------------------------------------
template <class type, class typeindex = int> class MA: public MARRAY
 {
public:
 typedef type      typeElem;
 typedef typeindex typeIndex;

 MA() { szElem = sizeof(type); szIndex = sizeof(typeindex); }

 inline size_t sizeElem (void) const                // ������ �������� (����)
    { return sizeof(type); }

 inline size_t sizeIndex (void) const               // ������ ������� (����)
    { return sizeof(typeindex); }

 //inline const type& operator() (uint i) const    // �������� �������� �������
 inline const type operator() (uint i) const     // �������� �������� �������
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  IndexError (nElem.n, i);
    #endif
    return ((const type*)body)[i];
    }

 inline const type & operator[] (uint i) const   // ������ �� ������� �������
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  IndexError (nElem.n, i);
    #endif

    return ((type*)body)[i];
    }

 inline type& operator[] (uint i)                // ������ �� ������� �������
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  IndexError (nElem.n, i);
    #endif

    return ((type*)body)[i];
    }

// ���������� ��� VC.NET �� �� �������� � VC 6
template <class type, class typeindex> 
friend type* constant (const MA<type, typeindex>& x); // �����.����. �� ������ �������

 inline const type& last (void) const          // ������ �� ��������� �������
    {
    #ifdef  DEBUG
       if (nElem.n <= 0)  ZeroError();
    #endif

    return ((type*)body)[nElem.n - 1];
    }
 
 inline type& last (void)                      // ������ �� ��������� �������
    {
    #ifdef  DEBUG
       if (nElem.n <= 0)  ZeroError();
    #endif

    return ((type*)body)[nElem.n-1];
    }

 inline const type& last(uint i) const       // ������ �� i-� ������� � �����
    {
    #ifdef  DEBUG
       if (i >= (uint)nElem.n)  ZeroError();
    #endif

    return ((type*)body)[nElem.n - 1 - i];
    }

 inline MA& operator<< (const type source)  // �������� � ����� ����� �������
    {
    addElem();
    ((type*)body)[nElem.n-1] = source;
    return *this;
    }

 inline MA& operator>> (type &dest)              // ������� ��������� �������
    {
    #ifdef  DEBUG
       if (nElem.n <= 0)  ZeroError();
    #endif

    dest = ((type*)body)[nElem.n-1];            // ������� ���������

    setNElem (nElem.n-1);
    return *this;
    }

 inline void swap (MARRAY& x) { MARRAY::swap(x); } // �������� ���������� � x

 inline void swap (uint i, uint j)                // ����������� ��� ��������
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

 // �������� � ����� ���������� ������ �������
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

 inline void memmove (uint dest, uint src, uint count)     // ����� ���������
    {
    #ifdef  DEBUG
       if (src+count  > (uint)nElem.n) IndexError( nElem.n, src+count-1 );
       if (dest+count > (uint)nElem.n) IndexError( nElem.n, dest+count-1 );
    #endif

    ::memmove (((type*)body) + dest,
              ((type*)body) + src,
              count * sizeof(type));
    }

 inline void memset (uint c)         // ��������� ���� ������ �������� ������
    {::memset (body, c, nElem.n * sizeof(type)); }

private:                                             //       ���������
 MA (MA&);                                           //   ��������� ����� �
 void operator= (MA&);                               // �������� ������������
 };

template <class type, class typeindex> 
inline type* constant (const MA<type, typeindex>& x) // �����.����. �� ������ �������
{
#ifdef  DEBUG
	if (x.nElem.n <= 0)  x.ZeroError();
#endif

return (type*)(x.body);
}

}  // end of namespace FSG

#endif//ndef __MARRAY_H
