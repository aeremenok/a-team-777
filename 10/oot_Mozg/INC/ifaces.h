// **************************************************************************
//! \file   ifaces.h
//! \brief  Объявление используемых интерфейсов
//! \author Lysenko A.A.
//! \date   14.May.2008 - 14.May.2008
// **************************************************************************

#ifndef __IFACES_H
#define __IFACES_H

#include "Archive.h"

// ==========================================================================
namespace iface   //!< Общие интерфейсы, используемы в программе
{
   // =======================================================================
   //! Интерфейс, описывающий возможности отображения данных
   class iDrawable
   {
   public:
      //! Задать контекст рисования
      virtual void SetHdc(HDC hDC) = 0;

      //! Получить текущий контекст рисования
      virtual void GetHdc(HDC hDC) = 0;

      //! Перерисовать
      virtual void Redraw() const = 0;

      virtual ~iDrawable() {};
   };

   // =======================================================================
   //! Интрфейс, описывающий возможности сериализации данных
   class iSerializable
   {
   public:
      //! Записать данные в файл
      //! \return идентификатор цепочки записей в архиве
      virtual int PutIntoArchive(ser::Archive& archive) = 0;

      //! Получить данные из файла
      //! \param id - идентификатор цепочки записей в архиве
      virtual void GetFromArchive(ser::Archive& archive, int id) = 0;

      virtual ~iSerializable() {};

   };
   class iNetworkStruct 
   {
   public:

      //! Выбирается структура на основе ID
      virtual void generate (int newIDofGame) =0;

      //! Генериться новая внутренняя структура на основе заданных параметров
      virtual void generate (int newNumberOfHoles, int newNumberOfCell) =0;

      //! Кидает шарик в дырку. Если больше некуда кидать возвращает false
      //! в противном true
      //! numberOfHole - номер дырки, в которую кидается шарик.
      virtual bool makeStep (int numberOfHole) =0;	

      //! Очистить структуру от фишек.
      virtual void clean () =0;

      virtual ~iNetworkStruct(){}
   };


} // end of namespace iface

// ==========================================================================
#endif // __IFACES_H