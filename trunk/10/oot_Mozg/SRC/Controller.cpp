// **************************************************************************
//! \file   Controller.cpp
//! \brief  ���������� ������ ��� ���������� �����
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
   //! ��������� ���� ���� �������
   if (isActive())
   {
      //! ���� ���� �������, ������� ��������� ���� � ������  �������
      delete netStruc;
      delete playersList;
   } 
   else
   {
      //! ���� ���� �� �������, ������ ���� ��������
      active = true;
   }

   try
   {
      //! ������� ������ �������, ������� ������ ������ �������
      playersList = new ListOfPlayers;
      Player* pl = new MainPlayer;
      std::list<std::string>::const_iterator itr = names.begin();
      pl->setName(*itr);
      playersList->addPlayer(pl);
      //! ��������� ��������
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
         ::MessageBox(mainDlg->m_hWnd, "������������ ������", "���������� �������", MB_OK | MB_ICONERROR);
         break;
      case excptns::PlayerListException::ERR_CODE_MAXCOUNT:
         ::MessageBox(mainDlg->m_hWnd, "��������� ������� �������� ������ ������������� ����� �������", "���������� �������", MB_OK | MB_ICONWARNING);
         ret = false;
         break;
      case excptns::PlayerListException::ERR_CODE_NAMECONFLICT:
         {
            std::string msg = "����� � ������ ";
            msg += e.getName();
            msg += " ��� ����������!\n��������� ����� �������� �� ������ �������.\n��� ���� ";
            char buf[15] = {'\0'};
            ::StringCchPrintf(buf, sizeof(buf)/sizeof(buf[0]), "%d", names.size());
            msg += &buf[0];
            msg += " ������� �������� ���� ������ � ������� ��������� �����.";
            std::list<std::string>::iterator it = names.begin(), itRes = names.begin();
            for ( ; it != names.end(); ++it )
               if ( *it == e.getName() )
                  itRes = it;
            names.erase( itRes );
            netStruc = new tdata::Data;

            if ( names.size() >= 2 )
            {
               ::MessageBox(mainDlg->m_hWnd, msg.c_str(), "���������� �������", MB_OK | MB_ICONWARNING);
               initialize(names, nStr);
               return;
            }
            else
               ::MessageBox(mainDlg->m_hWnd, "����� ���� ������� ���������", "���������� �������", MB_OK | MB_ICONERROR);
         }
         break;
      case excptns::PlayerListException::ERR_CODE_NOMEMORY:
         ::MessageBox(mainDlg->m_hWnd, "������������ ������", "���������� �������", MB_OK | MB_ICONERROR);
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
      ::MessageBox(mainDlg->m_hWnd, "������������ ������", "���������� �������", MB_OK | MB_ICONERROR);
      active = false;
      delete playersList;
      playersList = new ListOfPlayers;
      netStruc = new tdata::Data;
      return;
   }

   try
   {
      //! ��������� ����� ��������� �� ID
      netStruc = new tdata::Data;
   }
   catch (std::bad_alloc&)
   {
      ::MessageBox(mainDlg->m_hWnd, "������������ ������", "�������������", MB_OK | MB_ICONERROR);
      active = false;
      delete playersList;
      delete netStruc;
      playersList = new ListOfPlayers;
      netStruc = new tdata::Data;
      return;
   }
   
   netStruc->generate(nStr);

   nStructNum = nStr;

   //! ���������� � ������� ���������, ��� ����� ������ �����
   mainDlg->setTip("��� ������ " + 
      playersList->getActivePlayer()->getName());
}

void Controller::makeStep(int hole)
{
   try
   {
      if ( netStruc->makeStep(hole) )
      {
         Redraw();               //!< �������������� ���� �����
         playersList->goNext();  //!< ������� � ���������� ������
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
         //! ������ ������ ������, ���� ���������
         //! ������������ ������ � ������� ���� ���������
         mainDlg->ActivateButtons(FALSE);
         mainDlg->ActivateSave(FALSE);
         mainDlg->ActivateClose(TRUE);
         mainDlg->ActivateReset(TRUE);
         mainDlg->ActivateOpen(TRUE);
         //! ������� ���������, � ����������
         mainDlg->setTip("����������� " + 
                          playersList->getActivePlayer()->getName() +
                         " � ������� � ���� �������������!");
         Redraw();
      }
   }
   catch (excptns::MaxFiredException& e)
   {
      Redraw();               //!< �������������� ���� �����
      playersList->goNext();  //!< ������� � ���������� ������
      deActivateButtons();

      if ( e.isEndOfGame() )
      {
         //! ������ ������ ������, ���� ���������
         //! ������������ ������ � ������� ���� ���������
         mainDlg->ActivateButtons(FALSE);
         mainDlg->ActivateSave(FALSE);
         mainDlg->ActivateClose(TRUE);
         mainDlg->ActivateReset(TRUE);
         mainDlg->ActivateOpen(TRUE);
         //! ������� ���������, � ����������
         mainDlg->setTip("����������� " + 
                         playersList->getActivePlayer()->getName() +
                         " � ������� � ���� �������������!");
      }
      else
         ::MessageBox(mainDlg->m_hWnd, "���������� ������������ ������� \
���������.\n������������� ������������ ������ ��������� ��� ����� ����.",
                      "���� �������� ����������!", MB_OK | MB_ICONINFORMATION);
   }
}

void Controller::reset()
{
   //! ������ �������� �������� ������
   while (!playersList->getActivePlayer()->ifMain())
      playersList->goNext();

   //! ������� ��������� ����
   netStruc->clear();
   netStruc->generate(nStructNum);
   //! ������ ����������� ������, ���� ������ ������ ����� � �� ���������
   deActivateButtons();
   netStruc->Redraw();
}

void Controller::Save(std::string strName) const
{
   //! ������� ����� ��� ������ ������
   ser::Archive arch;
   int idPlayers = playersList->PutIntoArchive(arch);
   int idStruct  = netStruc->PutIntoArchive(arch);

   std::ofstream os(strName.c_str());

   os << nStructNum << separ << idPlayers << separ << idStruct << separ << arch;
   os.close();
}

bool Controller::Open(std::string strName)
{
   //! ������� ������ ����������
   delete playersList;
   playersList = new ListOfPlayers;

   //! ������� ����� ��� ���������� ������
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
         ::MessageBox(mainDlg->m_hWnd, "������������ ������", "������ �����", MB_OK | MB_ICONERROR);
         ret = false;
         break;
      case excptns::SerRestoreException::ERR_CODE_NOFILE:
         ::MessageBox(mainDlg->m_hWnd, "�������� ���� �� ������", "������ �����", MB_OK | MB_ICONERROR);
      	break;
      case excptns::SerRestoreException::ERR_CODE_NOTENOUGHDATA:
         ::MessageBox(mainDlg->m_hWnd, "���� ���������: ������������ ������", "������ �����", MB_OK | MB_ICONERROR);
         break;
      case excptns::SerRestoreException::ERR_CODE_NOTVALID:
         ::MessageBox(mainDlg->m_hWnd, "���� ���������: ������ ��������", "������ �����", MB_OK | MB_ICONERROR);
         break;
      case excptns::SerRestoreException::ERR_CODE_MOREDATA:
         int res = ::MessageBox(mainDlg->m_hWnd, "� ����� ���������� ������������������� ���������.\n \
�������� ��������� ������. ���������� ������?", "������ �����", MB_YESNO | MB_ICONWARNING);
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

   mainDlg->setTip("��� ������ " + 
      playersList->getActivePlayer()->getName());
   mainDlg->ActivateNew();
   mainDlg->ActivateSave();
   mainDlg->ActivateOpen();
   mainDlg->ActivateClose();
   mainDlg->ActivateReset();

   //! ������ ����������� ������, ���� ������ ������ ����� � �� ���������
   deActivateButtons();

   return true;
}

void Controller::deActivateButtons()
{
   //! ������� ���������
   mainDlg->setTip("��� ������ " + 
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

//! ������������� ����������� ����������
Controller* Controller::_instance = 0;

Controller* Controller::Instance(CMainDlg* dlg)
{
   if (_instance == 0)
      _instance = new Controller(dlg);
   return _instance;
}

} //end of namespace Game
// ==========================================================================
