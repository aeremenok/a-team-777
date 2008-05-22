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
      void PutIntoStream(std::basic_ostream<charT, traits>& strm) const
      {
         strm << (int)Record.nElem << separ;
         for ( int i = 0; i < Record.nElem; i++ )
            strm << Record[i].next << separ << Record[i].offset << separ << (int)Record[i].size << separ;

         strm << (int)Data.nElem << separ;
         noskipws(strm);
         strm.write((const char*)&(Data[0]), Data.nElem);
         std::vector<char> buf(Data.nElem + 1, '\0');
         ::memcpy((void*)&(Data[0]), &(buf[0]), Data.nElem);

         /*strm << Record.nElem << separ;
         strm.write((const char*)FSG::constant(Record), Record.nElem * Record.sizeElem());
         strm << separ << Data.nElem << separ;
         strm.write((const char*)FSG::constant(Data), Data.nElem * Data.sizeElem());
         strm << separ << Index.size() << separ;
         for ( INDEX::const_iterator it = Index.begin(); it != Index.end(); ++it )
            strm << it->first << separ << it->second << separ;*/


         /*strm << Record.nElem << separ;
         for ( int i = 0; i < Record.nElem; i++ )
            strm << Record[i].next << separ << Record[i].offset << separ << Record[i].size << separ;

         strm << Data.nElem << std::endl;
         for ( int i = 0; i < Data.nElem; i++ )
            //strm << Data[i] << separ;
            strm << Data[i];
         
         strm << std::endl << Index.size() << separ;
         for ( INDEX::const_iterator it = Index.begin(); it != Index.end(); ++it )
            strm << it->first << separ << it->second << separ;*/
      }

      //! ��������� ����� �� ������
      template<class charT, class traits>
      void GetFromStream(std::basic_istream<charT, traits>& strm)
      {
         int nElem = 0;
         strm >> nElem;
         for ( int i = 0; i < nElem; i++ )
         {
            int next, offset, size;
            strm >> next >> offset >> size;
            RECORD tmp;
            tmp.next = next; tmp.offset = offset; tmp.size = size;
            Record << tmp;
         }
         CONFIRM ( Record.nElem == nElem );

         strm >> nElem;
         Data.setNElem(nElem);
         noskipws(strm);
         char tmp;
         strm.read(&tmp, 1);         
         strm.read((char*)&(Data[0]), Data.nElem);
         //strm._Read_s((char*)&(Data[0]), Data.nElem, Data.nElem);

         /*int nElem = 0;
         strm >> nElem;
         Record.nElem = nElem;
         strm.read((char*)FSG::constant(Record), Record.nElem * Record.sizeElem());
         //strm._Read_s((char*)FSG::constant(Record), Record.nElem * Record.sizeElem() / 4, Record.nElem * Record.sizeElem() / 4);
         if ( strm.eof() )
            ::MessageBox(NULL, "", "", MB_OK);
         strm >> nElem;
         Data.nElem = nElem;
         strm.read((char*)FSG::constant(Data), Data.nElem * Data.sizeElem());
         size_t mapSize = 0;
         strm >> mapSize;
         for ( size_t i = 0; i < mapSize; i++ )
         {
            size_t key = 0;
            int val = 0;
            strm >> key >> val;
            Index.insert( std::make_pair(key, val) );
         }*/


         /*int nElem = 0;
         strm >> nElem;
         for ( int i = 0; i < nElem; i++ )
         {
            RECORD tmp;
            strm >> tmp.next >> tmp.offset >> tmp.size;
            Record << tmp;
         }
         CONFIRM ( Record.nElem == nElem );

         strm >> nElem;
         std::noskipws(strm);
         for ( int i = 0; i < nElem; i++ )
         {
            uchar tmp;
            strm >> tmp;
            Data << tmp;
         }
         CONFIRM ( Data.nElem == nElem );
         
         std::skipws(strm);         
         size_t mapSize = 0;
         strm >> mapSize;
         for ( size_t i = 0; i < mapSize; i++ )
         {
            size_t key = 0;
            int val = 0;
            strm >> key >> val;
            Index.insert( std::make_pair(key, val) );
         }*/
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