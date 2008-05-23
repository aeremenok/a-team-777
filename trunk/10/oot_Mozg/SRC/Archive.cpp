// **************************************************************************
//! \file   fsserialize.cpp
//! \brief  ������ ��� ������ � ���������������� ��������� ������ (Serialization).
//! \author Lysenko A.A.
//! \date   02.Sep.2006 - 02.Sep.2006
// **************************************************************************

#include "stdafx.h"
#include "Archive.h"

using namespace ser;

// ==========================================================================
void Archive::clear()
{
   Record.nElem = 0; // ������
   Data.nElem = 0;   // ������ ������
   Index.clear();    // ��������������� �� ������� ������ ��������� ������
}

// ==========================================================================
//                   ������ ������, ������� ������ ������
// \param sz - ������ ������ � ������
// \param data - ����� ������������� �����
// \return ������������� ������, ���� -1 � ������ ������
int Archive::write(size_t sz, const uchar* data)
{
   // �������� ����� ��� ������
   int id = GetVacantRecord(sz);
   // �������� ������
   memcpy(&Data[ Record[id].offset ], data, sz);
   //std::copy(data, data+sz, &Data[ Record[id].offset ]);

   return id;
}

// ==========================================================================
//             ������� ������ � ��������� �� � ������ ����������
// \param prev - ������������� ���������� ������
// \param sz - ������ ������ � ������
// \param data - ����� ������������� �����
// \param next - ������������� ��������� ������
// \return <true>, ���� ��������� �������� ���������
bool Archive::append(int prev, size_t sz, const uchar* data, int* next)
{
   CONFIRM(IdIsValid(prev));
   CONFIRM(next != NULL);
   // �������� ������
   *next = write(sz, data);
   // �������� � ����� ������
   Record[prev].next = *next;
   return true;
}

// ==========================================================================
//                              ��������� ������
// \param id - ������������� ������
// \param sz - ������ ������ � ������
// \param data - ����� ������
// \param next - ������������� ��������� ������
// \return <true>, ���� ��������� �������� ���������
bool Archive::read(int id, size_t sz, uchar* data, int* next)
{
   CONFIRM(IdIsValid(id));
   CONFIRM(next != NULL);
   const RECORD& rec = Record[id];
   CONFIRM( rec.size == sz );
   *next = rec.next;

   const uchar* ptr = &Data[rec.offset];
   memcpy(data, ptr, sz);
   //std::copy(ptr, ptr + sz, data);
   return true;
}

// // ==========================================================================
// //                �������� ���������� ������ � �������� �����
// // \param id - ������������� ������
// // \param sz - ������ ������ � ������
// // \param data - ����� ������
// // \param next - ������������� ��������� ������
// // \return <true>, ���� ��������� �������� ���������
// bool Archive::swap(int id, size_t sz, uchar* data, int* next)
// {
// 
// }

// ==========================================================================
// ������� ������, ������� � ��������� ������
bool Archive::erase(int id)
{
   CONFIRM(IdIsValid(id));
   // ��� ������ ������ � ������
   while (id >= 0)
   {
      RECORD& rec = Record[id];
      int next = rec.next; // ��������� ������ ����. ������

      // �������� ������ ������ ��������� ������
      int& headfree = SizeToIndex(rec.size);

      // �������� ������ � ������ ���������
      rec.next = headfree;
      headfree = id;

      id = next;
   }
   return true;
}

// ==========================================================================
//! �������� ����� ��� ������ ���������� �������
int Archive::GetVacantRecord(size_t sz)
{
   int idfree = NONE;
   // �������� �� ������� ������ ��������� ������ ���������� �������
   int& headfree = SizeToIndex(sz);
   // ���� ��������� ������ ���
   if (headfree < 0)
   {
      idfree = Record.nElem;
      // ������� ����� ����
      ++Record.nElem;
      RECORD& rec = Record.last();
      rec.size = sz;
      rec.offset = Data.nElem;
      rec.next = NONE;

      Data.nElem += static_cast<int>(sz);
   }
   else  // �����
   {
      // ��������� ���� �� ������ ���������
      idfree = headfree;
      
      RECORD& rec = Record[idfree];
      headfree = rec.next;

      // ���������������� ����
      CONFIRM( rec.size == sz );
      CONFIRM( 0 <= rec.offset  && rec.offset <= Data.nElem - int(sz) );
      rec.next = NONE;
   }
   return  idfree;
}

// ==========================================================================
//! �������� ������ ������ ��������� ������ ���������� �������
int& Archive::SizeToIndex(size_t sz)
{
   // ����� ���������� ������ ��������� ������
   INDEX::iterator it = Index.find(sz);
   if (it != Index.end())
      return it->second;
   else
   {
      // �������� � ������ ������ ������
      std::pair<INDEX::iterator, bool > retv = Index.insert( std::make_pair(sz, int(-1)) );
      CONFIRM( retv.second != false );
      return retv.first->second;
   }
}

// ==========================================================================
//! �������� ������������ ��������������
bool Archive::IdIsValid(int id) const
{
   return (0 <= id && id < Record.nElem);
}

// ==========================================================================
