// **************************************************************************
//! \file   Controller.cpp
//! \brief  Реализация класса для управления игрой
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************

// ==========================================================================
#include "stdafx.h"
#include "Controller.h"
#include "MainPlayer.h"
#include "SlavePlayer.h"

namespace Game
{
// Class Controller 

Controller::Controller(CMainDlg* dlg)
   :mainDlg(dlg),active(false)
{
   playersList = new ListOfPlayers;
   netStruc = new tdata::Data;
   //! Задаем контоллер игры для окна
   mainDlg->setControl(this);
}

Controller::~Controller()
{
   delete playersList;
   delete netStruc;
}

void Controller::createNetwork (int newNumberOfHoles, int newNumberOfCell)
{
}

bool Controller::restoreNetwork ()
{
	return 0;
}

bool Controller::restorePlayers ()
{
	return 0;
}

bool Controller::restoreGame (std::string destanation)
{
	return 0;
}
void Controller::initialize (std::list<std::string>& names, int nStr)
{
   //! Проверяем была игра активна
   if (isActive())
   {
      //! Игра была активна, удаляем структуру сети и список  игроков
      delete netStruc;
      delete playersList;
   } 
   else
   {
      //! Игра была не активна, делаем игру активной
      active = true;
   }

   //! Создаем список игроков, первого игрока делаем главным
   playersList = new ListOfPlayers;
   Player* pl = new MainPlayer;
   std::list<std::string>::const_iterator itr = names.begin();
   pl->setName(*itr);
   playersList->addPlayer(pl);
   //! Остальных ведомыми
   itr++;
   while (itr != names.end())
   {
      pl = new SlavePlayer;
      pl->setName(*itr);
      playersList->addPlayer(pl);
      itr++;
   }

   /************************************************************************/
   /*                     //! TODO: Создаем структуру                      */
   /************************************************************************/
   netStruc = new tdata::Data;

   //! Установить в статусе подсказку, что ходит первый игрок
   mainDlg->setTip("Ход игрока " + playersList->getActivePlayer()->getName());
}
void Controller::makeStep(int hole)
{
   if (netStruc->makeStep(hole))
   {
      netStruc->Redraw();     //!< Перерисовываем сеть Перти
      playersList->goNext();  //!< Перешли к следующему игроку
      //! Сменили подсказку
      mainDlg->setTip("Ход игрока " + playersList->getActivePlayer()->getName());
      if (playersList->getActivePlayer()->ifMain())
      {
         mainDlg->ActivateOpen(TRUE);
         mainDlg->ActivateSave(TRUE);
         mainDlg->ActivateReset(TRUE);
         mainDlg->ActivateClose(TRUE);
         mainDlg->ActivateNew(TRUE);  
      } 
      else
      {
         mainDlg->ActivateOpen(FALSE);
         mainDlg->ActivateSave(FALSE);
         mainDlg->ActivateReset(FALSE);
         mainDlg->ActivateClose(FALSE);
         mainDlg->ActivateNew(FALSE);
      }
   }else 
   {  //! Больше некуда кидать, игра закончина
      //! Деактивируем кнопки и элемент меню сохранить
      mainDlg->ActivateButtons(FALSE);
      mainDlg->ActivateSave(FALSE);
      //! Выводим сообщение, о победители
      mainDlg->setTip("Поздравляем " + playersList->getActivePlayer()->getName() 
                                      + " с победой в игре Мозгодолбалка!");
   }
}
} //end of namespace Game
// ==========================================================================
