// **************************************************************************
//! \file   Controller.h
//! \brief  Объявление класса для управления игрой
//! \author Bessonov A.V.
//! \date   17.May.2008 - 19.May.2008
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
      //! Закрываем конструктор согласно паттерну Singleton
      //! Чтобы случайно не создать вторую копию
      Controller(CMainDlg* dlg);
      
  public:
     ~Controller();

      //! Создает структуру для игры
      void createNetwork (int newNumberOfHoles, int newNumberOfCell);

      //! Восстанавливает игру из файла
	   bool restoreGame (std::string destanation);
      
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

      //! Если объект уже создан, возвращает ссылку, иначе создает объект
      static Controller* Instance(CMainDlg* dlg = 0);

  private:
    
      //! Восстанавливает структуру сети при загрузке игры из файла
      bool restoreNetwork ();

      //! Восстанавливает список игроков при загрузке игры
      bool restorePlayers ();
   
  private: 
      ListOfPlayers        *playersList;     //!< Список игроков
      tdata::Data          *netStruc;        //!< Внутренняя структутра игры
      CMainDlg             *mainDlg;         //!< Главное окно программы
      bool                 active;           //!< Если true, то игра активна

      static Controller* _instance; //!< Единственный объект в программе
};
   
} //end of namespace Game
// ==========================================================================
#endif
