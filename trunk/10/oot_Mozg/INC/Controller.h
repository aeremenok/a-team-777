// **************************************************************************
//! \file   Controller.h
//! \brief  Объявление класса для управления игрой
//! \author Bessonov A.V.
//! \date   17.May.2008 - 20.May.2008
// **************************************************************************

#ifndef __CONTROLLER_H
#define __CONTROLLER_H

#include "ifaces.h"
#include "MainDlg.h"
#include "ListOfPlayers.h"
#include "Data.h"

// ==========================================================================
namespace Game
{
class CMainDlg;
//!	Класс управляет работой программы
class Controller : public iface::iDrawable
{
      //! Закрываем конструктор согласно паттерну Singleton
      //! Чтобы случайно не создать вторую копию
      Controller(CMainDlg* dlg);
      
  public:
     ~Controller();

     //! Перерисовать
     virtual void Redraw() const;

     //! Вывести изображение
     virtual void Draw(CPaintDC& dc);
      
      //! Запускае начальную инициализацию или перазапускает текущую
      //! Создает список игроков и структуру
      void initialize (std::list<std::string>& names, int nStr);

      //! Обрабатывает один бросок фишки
      void makeStep(int hole);

      //! Возвращает true, если игра активна
      bool isActive() const {return active;};

      //! Изменяет состояние игры
      void setActive(bool newState) {active = newState;};

      //! Востанавливает начальное состояние игры
      //! Делает текущим главного игрока и опустошает структуру
      void reset();

      //! Сохраняет список игроков и структуру в файл strName
      void Save(std::string strName) const;

      //! Восстанавливает список игроков и структуру из файла strName
      //! \return true, если архив корректно прочитался
      bool Open(std::string strName);

      //! Деактивирует кнопки для дырок, в которые нельзя кинуть фишки
      void deActivateButtons();

      //! Если объект уже создан, возвращает ссылку, иначе создает объект
      static Controller* Instance(CMainDlg* dlg = 0);
   
  private: 
      ListOfPlayers        *playersList;     //!< Список игроков
      tdata::Data          *netStruc;        //!< Внутренняя структутра игры
      CMainDlg             *mainDlg;         //!< Главное окно программы
      bool                 active;           //!< Если true, то игра активна

      static Controller* _instance; //!< Единственный объект в программе

      int nStructNum; //!< Номер выбранной структуры
};
   
} //end of namespace Game
// ==========================================================================
#endif
