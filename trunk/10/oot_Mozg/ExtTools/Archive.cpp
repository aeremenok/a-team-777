// **************************************************************************
//! \file   fsserialize.cpp
//! \brief  Классы для работы с последовательным хранением данных (Serialization).
//! \author Lysenko A.A.
//! \date   02.Sep.2006 - 02.Sep.2006
// **************************************************************************

#include "stdafx.h"
#include "Archive.h"

using namespace ser;

// ==========================================================================
void Archive::clear()
{
   Record.nElem = 0; // Записи
   Data.nElem = 0;   // Массив данных
   Index.clear();    // Индексированные по размеру списки свободных блоков
}

// ==========================================================================
//                   НАЧАТЬ СЕССИЮ, СДЕЛАТЬ ПЕРВУЮ ЗАПИСЬ
// \param sz - размер записи в байтах
// \param data - адрес записываемого блока
// \return Идентификатор записи, либо -1 в случае ошибки
int Archive::write(size_t sz, const uchar* data)
{
   // ВЫДЕЛИТЬ МЕСТО ПОД ЗАПИСЬ
   int id = GetVacantRecord(sz);
   // ЗАПИСАТЬ ДАННЫЕ
   memcpy(&Data[ Record[id].offset ], data, sz);
   //std::copy(data, data+sz, &Data[ Record[id].offset ]);

   return id;
}

// ==========================================================================
//             СОЗДАТЬ ЗАПИСЬ И ПРИЛЕПИТЬ ЕЕ К ХВОСТУ ПРЕДЫДУЩЕЙ
// \param prev - идентификатор предыдущей записи
// \param sz - размер записи в байтах
// \param data - адрес записываемого блока
// \param next - Идентификатор созданной записи
// \return <true>, если параметры переданы правильно
bool Archive::append(int prev, size_t sz, const uchar* data, int* next)
{
   CONFIRM(IdIsValid(prev));
   CONFIRM(next != NULL);
   // ЗАПИСАТЬ ДАННЫЕ
   *next = write(sz, data);
   // ВКЛЮЧИТЬ В ХВОСТ СЕССИИ
   Record[prev].next = *next;
   return true;
}

// ==========================================================================
//                              ПРОЧИТАТЬ ЗАПИСЬ
// \param id - идентификатор записи
// \param sz - размер записи в байтах
// \param data - адрес буфера
// \param next - Идентификатор следующей записи
// \return <true>, если параметры переданы правильно
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
// //                ОБМЕНЯТЬ СОДЕРЖИМОЕ ЗАПИСИ И ВНЕШНЕГО БЛОКА
// // \param id - идентификатор записи
// // \param sz - размер записи в байтах
// // \param data - адрес буфера
// // \param next - Идентификатор следующей записи
// // \return <true>, если параметры переданы правильно
// bool Archive::swap(int id, size_t sz, uchar* data, int* next)
// {
// 
// }

// ==========================================================================
// УДАЛИТЬ СЕССИЮ, НАЧИНАЯ С УКАЗАННОЙ ЗАПИСИ
bool Archive::erase(int id)
{
   CONFIRM(IdIsValid(id));
   // ДЛЯ КАЖДОЙ ЗАПИСИ В СЕССИИ
   while (id >= 0)
   {
      RECORD& rec = Record[id];
      int next = rec.next; // СОХРАНИТЬ ИНДЕКС СЛЕД. ЗАПИСИ

      // ПОЛУЧИТЬ ГОЛОВУ СПИСКА СВОБОДНЫХ БЛОКОВ
      int& headfree = SizeToIndex(rec.size);

      // ВКЛЮЧИТЬ ЗАПИСЬ В СПИСОК СВОБОДНЫХ
      rec.next = headfree;
      headfree = id;

      id = next;
   }
   return true;
}

// ==========================================================================
//! Выделить место под запись указанного размера
int Archive::GetVacantRecord(size_t sz)
{
   int idfree = NONE;
   // ПОЛУЧИТЬ ИЗ ИНДЕКСА СПИСОК СВОБОДНЫХ БЛОКОВ УКАЗАННОГО РАЗМЕРА
   int& headfree = SizeToIndex(sz);
   // ЕСЛИ СВОБОДНЫХ БЛОКОВ НЕТ
   if (headfree < 0)
   {
      idfree = Record.nElem;
      // СОЗДАТЬ НОВЫЙ БЛОК
      ++Record.nElem;
      RECORD& rec = Record.last();
      rec.size = sz;
      rec.offset = Data.nElem;
      rec.next = NONE;

      Data.nElem += static_cast<int>(sz);
   }
   else  // ИНАЧЕ
   {
      // ИСКЛЮЧИТЬ БЛОК ИЗ СПИСКА СВОБОДНЫХ
      idfree = headfree;
      
      RECORD& rec = Record[idfree];
      headfree = rec.next;

      // ИНИЦИАЛИЗИРОВАТЬ ПОЛЯ
      CONFIRM( rec.size == sz );
      CONFIRM( 0 <= rec.offset  && rec.offset <= Data.nElem - int(sz) );
      rec.next = NONE;
   }
   return  idfree;
}

// ==========================================================================
//! Получить индекс первой свободной записи указанного размера
int& Archive::SizeToIndex(size_t sz)
{
   // НАЙТИ ПОДХОДЯЩИЙ СПИСОК СВОБОДНЫХ БЛОКОВ
   INDEX::iterator it = Index.find(sz);
   if (it != Index.end())
      return it->second;
   else
   {
      // ВКЛЮЧИТЬ В ИНДЕКС ПУСТОЙ СПИСОК
      std::pair<INDEX::iterator, bool > retv = Index.insert( std::make_pair(sz, int(-1)) );
      CONFIRM( retv.second != false );
      return retv.first->second;
   }
}

// ==========================================================================
//! Проверка правильности идентификатора
bool Archive::IdIsValid(int id) const
{
   return (0 <= id && id < Record.nElem);
}

// ==========================================================================
