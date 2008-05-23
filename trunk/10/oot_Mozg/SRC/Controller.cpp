// **************************************************************************
//! \file   Controller.cpp
//! \brief  Реализация класса для управления игрой
//! \author Bessonov A.V.
//! \date   17.May.2008 - 22.May.2008
// **************************************************************************

// ==========================================================================
#include "stdafx.h"
#include "MainPlayer.h"
#include "SlavePlayer.h"
#include <fstream>
#include <strsafe.h>

namespace Game
{
// Class Controller 

Controller::Controller(CMainDlg* dlg)
   : mainDlg(dlg), active(false)
{
   playersList = new ListOfPlayers;
   netStruc = new tdata::Data;
}

Controller::~Controller()
{
   delete playersList;
   delete netStruc;
}

// ==========================================================================
void Controller::Redraw() const
{
   mainDlg->GetDrawWnd().Invalidate();
}

// ==========================================================================
void Controller::Draw(CPaintDC& dc)
{
   netStruc->Draw(dc);
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

   try
   {
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
   }
   catch(excptns::PlayerListException& e)
   {
      bool ret = true;
      switch ( e.getCode() )
      {
      case excptns::PlayerListException::ERR_CODE_NONE:
         ::MessageBox(mainDlg->m_hWnd, "Неопознанная ошибка", "Добавление игроков", MB_OK | MB_ICONERROR);
         break;
      case excptns::PlayerListException::ERR_CODE_MAXCOUNT:
         ::MessageBox(mainDlg->m_hWnd, "Произошла попытка добавить больше максимального числа игроков", "Добавление игроков", MB_OK | MB_ICONWARNING);
         ret = false;
         break;
      case excptns::PlayerListException::ERR_CODE_NAMECONFLICT:
         {
            std::string msg = "Игрок с именем ";
            msg += e.getName();
            msg += " уже существует!\nУказанный игрок исключен из списка игроков.\nДля игры ";
            char buf[15] = {'\0'};
            ::StringCchPrintf(buf, sizeof(buf)/sizeof(buf[0]), "%d", names.size());
            msg += &buf[0];
            msg += " игроков создайте игру заново и укажите различные имена.";
            std::list<std::string>::iterator it = names.begin(), itRes = names.begin();
            for ( ; it != names.end(); ++it )
               if ( *it == e.getName() )
                  itRes = it;
            names.erase( itRes );
            netStruc = new tdata::Data;

            if ( names.size() >= 2 )
            {
               ::MessageBox(mainDlg->m_hWnd, msg.c_str(), "Добавление игроков", MB_OK | MB_ICONWARNING);
               initialize(names, nStr);
               return;
            }
            else
               ::MessageBox(mainDlg->m_hWnd, "Имена всех игроков совпадают", "Добавление игроков", MB_OK | MB_ICONERROR);
         }
         break;
      case excptns::PlayerListException::ERR_CODE_NOMEMORY:
         ::MessageBox(mainDlg->m_hWnd, "Недостаточно памяти", "Добавление игроков", MB_OK | MB_ICONERROR);
         break;
      }

      if ( ret )
      {
         active = false;
         delete netStruc;
         delete playersList;
         playersList = new ListOfPlayers;
         netStruc = new tdata::Data;
         return;
      }
   }
   catch(std::bad_alloc&)
   {
      ::MessageBox(mainDlg->m_hWnd, "Недостаточно памяти", "Добавление игроков", MB_OK | MB_ICONERROR);
      active = false;
      delete playersList;
      playersList = new ListOfPlayers;
      netStruc = new tdata::Data;
      return;
   }

   try
   {
      //! Создается новая структура по ID
      netStruc = new tdata::Data;
   }
   catch (std::bad_alloc&)
   {
      ::MessageBox(mainDlg->m_hWnd, "Недостаточно памяти", "Инициализация", MB_OK | MB_ICONERROR);
      active = false;
      delete playersList;
      delete netStruc;
      playersList = new ListOfPlayers;
      netStruc = new tdata::Data;
      return;
   }
   
   netStruc->generate(nStr);

   nStructNum = nStr;

   //! Установить в статусе подсказку, что ходит первый игрок
   mainDlg->setTip("Ход игрока " + 
      playersList->getActivePlayer()->getName());
}

void Controller::makeStep(int hole)
{
   try
   {
      if ( netStruc->makeStep(hole) )
      {
         Redraw();               //!< Перерисовываем сеть Перти
         playersList->goNext();  //!< Перешли к следующему игроку
         deActivateButtons();

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
      }
      else 
      {
         //! Больше некуда кидать, игра закончена
         //! Деактивируем кнопки и элемент меню сохранить
         mainDlg->ActivateButtons(FALSE);
         mainDlg->ActivateSave(FALSE);
         mainDlg->ActivateClose(TRUE);
         mainDlg->ActivateReset(TRUE);
         mainDlg->ActivateOpen(TRUE);
         //! Выводим сообщение, о победителе
         mainDlg->setTip("Поздравляем " + 
                          playersList->getActivePlayer()->getName() +
                         " с победой в игре Мозгодолбалка!");
         Redraw();
      }
   }
   catch (excptns::MaxFiredException& e)
   {
      Redraw();               //!< Перерисовываем сеть Перти
      playersList->goNext();  //!< Перешли к следующему игроку
      deActivateButtons();

      if ( e.isEndOfGame() )
      {
         //! Больше некуда кидать, игра закончена
         //! Деактивируем кнопки и элемент меню сохранить
         mainDlg->ActivateButtons(FALSE);
         mainDlg->ActivateSave(FALSE);
         mainDlg->ActivateClose(TRUE);
         mainDlg->ActivateReset(TRUE);
         mainDlg->ActivateOpen(TRUE);
         //! Выводим сообщение, о победителе
         mainDlg->setTip("Поздравляем " + 
                         playersList->getActivePlayer()->getName() +
                         " с победой в игре Мозгодолбалка!");
      }
      else
         ::MessageBox(mainDlg->m_hWnd, "Обнаружено зацикливание сетевой \
структуры.\nРекомендуется использовать другую структуру для новой игры.",
                      "Игра возможно бесконечна!", MB_OK | MB_ICONINFORMATION);
   }
}

void Controller::reset()
{
   //! Делаем активным главного игрока
   while (!playersList->getActivePlayer()->ifMain())
      playersList->goNext();

   //! Очищаем структуру сети
   netStruc->clear();
   netStruc->generate(nStructNum);
   //! Делаем неактивными кнопки, если нельзя кинуть фишку в их отверстие
   deActivateButtons();
   netStruc->Redraw();
}

void Controller::Save(std::string strName) const
{
   //! Создаем архив для записи данных
   ser::Archive arch;
   int idPlayers = playersList->PutIntoArchive(arch);
   int idStruct  = netStruc->PutIntoArchive(arch);

   std::ofstream os(strName.c_str());

   os << nStructNum << separ << idPlayers << separ << idStruct << separ << arch;
   os.close();
}

bool Controller::Open(std::string strName)
{
   //! Удаляем старую информацию
   delete playersList;
   playersList = new ListOfPlayers;

   //! Создаем архив для считывания данных
   ser::Archive arch;
   
   int idPlayers;
   int idStruct;
   
   std::ifstream is/*(strName.c_str())*/;
   try
   {
      is.open(strName.c_str(),std::ios_base::in);
      if ( is.bad() || !is.is_open() )
      {
         excptns::SerRestoreException e(excptns::SerRestoreException::ERR_CODE_NOFILE);
         throw e;
      }

      is >> nStructNum >> idPlayers >> idStruct >> arch;
      is.peek();
      if ( !is.eof() )
      {
         excptns::SerRestoreException e(excptns::SerRestoreException::ERR_CODE_MOREDATA);
         throw e;
      }
   }
   catch (excptns::SerRestoreException& e)
   {
      bool ret = true;
      switch ( e.getCode() )
      {
      case excptns::SerRestoreException::ERR_CODE_NONE:
         ::MessageBox(mainDlg->m_hWnd, "Неопознанная ошибка", "Чтение файла", MB_OK | MB_ICONERROR);
         ret = false;
         break;
      case excptns::SerRestoreException::ERR_CODE_NOFILE:
         ::MessageBox(mainDlg->m_hWnd, "Указнный файл не найден", "Чтение файла", MB_OK | MB_ICONERROR);
      	break;
      case excptns::SerRestoreException::ERR_CODE_NOTENOUGHDATA:
         ::MessageBox(mainDlg->m_hWnd, "Файл поврежден: недостаточно данных", "Чтение файла", MB_OK | MB_ICONERROR);
         break;
      case excptns::SerRestoreException::ERR_CODE_NOTVALID:
         ::MessageBox(mainDlg->m_hWnd, "Файл поврежден: данные искажены", "Чтение файла", MB_OK | MB_ICONERROR);
         break;
      case excptns::SerRestoreException::ERR_CODE_MOREDATA:
         int res = ::MessageBox(mainDlg->m_hWnd, "В файле обнаружены несанкционированные изменения.\n \
Возможно искажение данных. Продолжить чтение?", "Чтение файла", MB_YESNO | MB_ICONWARNING);
         ret = (res == IDYES) ? false : true;
         break;
      }

      if ( ret )
      {
         is.close();
         return false;      
      }
   }

   playersList->GetFromArchive(arch, idPlayers);
   netStruc->GetFromArchive(arch, idStruct);
   Redraw();
   is.close();

   mainDlg->setTip("Ход игрока " + 
      playersList->getActivePlayer()->getName());
   mainDlg->ActivateNew();
   mainDlg->ActivateSave();
   mainDlg->ActivateOpen();
   mainDlg->ActivateClose();
   mainDlg->ActivateReset();

   //! Делаем неактивными кнопки, если нельзя кинуть фишку в их отверстие
   deActivateButtons();

   return true;
}

void Controller::deActivateButtons()
{
   //! Сменили подсказку
   mainDlg->setTip("Ход игрока " + 
                   playersList->getActivePlayer()->getName());
   mainDlg->ActivateButtons(TRUE);
   for (int i = 0; i < 6; i++)
      if ( netStruc->isPositionBlocked(i) )
      {
         CButton button = mainDlg->GetDlgItem(IDC_BUTTON_HOLE1 + i);
         ATLASSERT(::IsWindow(button.m_hWnd));
         button.EnableWindow(FALSE);
      }
}

//! Инициализация статической переменной
Controller* Controller::_instance = 0;

Controller* Controller::Instance(CMainDlg* dlg)
{
   if (_instance == 0)
      _instance = new Controller(dlg);
   return _instance;
}

} //end of namespace Game
// ==========================================================================
