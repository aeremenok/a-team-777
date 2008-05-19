// **************************************************************************
//! \file   Controller.h
//! \brief  Объявление класса для управления игрой
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
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
class Controller 
{
  public:
      Controller(CMainDlg* dlg);
      ~Controller();

      //! Создает структуру для игры
      void createNetwork (int newNumberOfHoles, int newNumberOfCell);

      //! Восстанавливает игру из файла
	   bool restoreGame (std::wstring destanation);
      
      //! Запускае начальную инициализацию или перазапускает текущую
      //! Создает список игроков и структуру
      void initialize (std::list<std::wstring>& names, int nStr);

      //! Обрабатывает один бросок фишки
      void makeStep(int hole);

      //! Возвращает true, если игра активна
      bool isActive() const {return active;};

      //! Изменяет состояние игры
      void setActive(bool newState) {active = newState;};

  private:
    
      //! Восстанавливает структуру сети при загрузке игры из файла
      bool restoreNetwork ();

      //! Восстанавливает список игроков при загрузке игры
      bool restorePlayers ();
   
  private: 
      ListOfPlayers        *playersList;  //!< Список игроков
      tdata::Data          *netStruc;     //!< Внутренняя структутра игры
      CMainDlg             *mainDlg;      //!< Главное окно программы
      bool                 active;      //!< Если true, то игра активна
};
   
} //end of namespace Game
// ==========================================================================
#endif
