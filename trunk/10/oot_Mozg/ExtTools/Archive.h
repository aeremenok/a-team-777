// **************************************************************************
//! \file   fsserialize.h
//! \brief  ������ ��� ������ � ���������������� ��������� ������ (Serialization).
//! \author Lysenko A.A.
//! \date   01.Sep.2006 - 30.Oct.2006
// **************************************************************************

#ifndef __FSSERIALIZE_H
#define __FSSERIALIZE_H

#include <map>
#include "marray.h"

// ==========================================================================
namespace ser   // ������������
{
   // FORWARD DECLARATION
   class writer;
   class reader;

   // =======================================================================
   //! ��������� ��������������� ������
   class Archive
   {
   public:
      friend class reader;
      friend class writer;
      // --------------------------------------------------------------------
      /* ������ ������� ��������. ������ ������ ������� �� ������� �������.
         ������ ������� ����� ���� �����.
         ������ � ������� �������������� ����� ����������� ����������
         ��� ������ � ������.
      */

      // --------------------------------------------------------------------
      //! ������� ������, ������� � ��������� ������
      bool erase(int id);

      //! ������� ����� � �����
      template<class charT, class traits>
      void PutIntoStream(std::basic_istream<charT, traits>& strm)
      {
         strm << Index;
      }

      //! ��������� ����� �� ������
      template<class charT, class traits>
      void GetFromStream(std::basic_istream<charT, traits>& strm)
      {
         strm >> Index;
      }

      void clear();
   protected:
      //! ������ ������, ������� ������ ������
      //! \param sz - ������ ������ � ������
      //! \param data - ����� ������������� �����
      //! \return ������������� ������, ���� -1 � ������ ������
      int write(size_t sz, const uchar* data);

      //! ������� ������ � ��������� �� � ������ ����������
      //! \param prev - ������������� ���������� ������
      //! \param sz - ������ ������ � ������
      //! \param data - ����� ������������� �����
      //! \param next - ������������� ��������� ������
      //! \return <true>, ���� ��������� �������� ���������
      bool append(int prev, size_t sz, const uchar* data, int* next);

      //! ��������� ������
      //! \param id - ������������� ������
      //! \param sz - ������ ������ � ������
      //! \param data - ����� ������
      //! \param next - ������������� ��������� ������
      //! \return <true>, ���� ��������� �������� ���������
      bool read(int id, size_t sz, uchar* data, int* next);

      //! �������� ���������� ������ � �������� �����
      //! \param id - ������������� ������
      //! \param sz - ������ ������ � ������
      //! \param data - ����� ������
      //! \param next - ������������� ��������� ������
      //! \return <true>, ���� ��������� �������� ���������
      bool swap(int id, size_t sz, uchar* data, int* next);
      // --------------------------------------------------------------------
   private:
      struct RECORD     //! ������
      {
         int next;      //!< ������ ��������� ������ � ������
         int offset;    //!< �������� ����� � ������� ������
         size_t size;   //!< ������ ����� ������
      };

      FSG::MA<RECORD, int> Record;  //!< ������
      FSG::MA<uchar, int>  Data;    //!< ������ ������
      typedef std::map<size_t, int> INDEX;
      INDEX Index;      //!< ��������������� �� ������� ������ ��������� ������

      // --------------------------------------------------------------------
      //! �������� ����� ��� ������ ���������� �������
      int GetVacantRecord(size_t sz);
      //! �������� ������ ������ ��������� ������ ���������� �������
      int& SizeToIndex(size_t sz);
      //! �������� ������������ ��������������
      bool IdIsValid(int id) const;
   };

   // =======================================================================
   class writer
   {
   public:
      writer(Archive& a) : m_archive(a), m_head(NONE), m_next(NONE)   {};
      int id() const                                  { return m_head; };
      template<typename T> writer& operator<<(const T& obj)
      {
         const uchar* ptr = reinterpret_cast<const uchar*>(&obj);
         if (m_head < 0)
            m_next = m_head = m_archive.write( sizeof(T), ptr );
         else
            CHECK( m_archive.append(m_next, sizeof(T), ptr, &m_next) );

         return *this;
      }

      writer& operator<<(const std::string& s)
      {
         *this << s.size();
         CHECK ( m_archive.append(m_next, s.size()+1, reinterpret_cast<const uchar*>(s.c_str()), &m_next) );
         return *this;
      }

      template<typename T> writer& write(const T& obj)
      {
         const uchar* ptr = reinterpret_cast<const uchar*>(&obj);
         if (m_head < 0)
            m_next = m_head = m_archive.write( sizeof(T), ptr );
         else
            CHECK( m_archive.append(m_next, sizeof(T), ptr, &m_next) );

         return *this;
      }

      template<typename T> writer& writes(T* pobj, size_t num)
      {
         const uchar* ptr = reinterpret_cast<const uchar*>(pobj);
         if (m_head < 0)
            m_next = m_head = m_archive.write( sizeof(T) * num, ptr );
         else
            CHECK( m_archive.append(m_next, sizeof(T) * num, ptr, &m_next) );
         return *this;
      }
   private:
      Archive& m_archive;
      int m_head, m_next;
   };

   // =======================================================================
   class reader
   {
   public:
      reader(Archive& a, int id) : m_archive(a), m_next(id) {};
      template<typename T> reader& operator>>(T& obj)
      {
         CONFIRM(m_next >= 0);
         CHECK( m_archive.read(m_next, sizeof(T), reinterpret_cast<uchar*>(&obj), &m_next ) );
         return *this;
      }

      reader& operator>>(std::string& s)
      {
         int sz;
         *this >> sz;
         CONFIRM(0 <= sz);
         CONFIRM(m_next >= 0);
         std::vector<TCHAR> buf(sz+1, '\0');
         CHECK ( m_archive.read(m_next, sz+1, reinterpret_cast<uchar*>(&buf[0]), &m_next) );
         if ( sz >= 0 )
            s = &buf[0];
         return *this;
      }

      template<typename T> reader& read(T& obj)
      {
         CONFIRM(m_next >= 0);
         CHECK( m_archive.read(m_next, sizeof(T), reinterpret_cast<uchar*>(&obj), &m_next ) );
         return *this;
      }

      template<typename T> reader& reads(T* obj, size_t num)
      {
         CONFIRM(m_next >= 0);
         CHECK( m_archive.read(m_next, sizeof(T)*num, reinterpret_cast<uchar*>(obj), &m_next ) );
         return *this;
      }
   private:
      Archive& m_archive;
      int m_next;
   };

   // =======================================================================

}  // endof namespace ser
// ==========================================================================
#endif // __FSSERIALIZE_H