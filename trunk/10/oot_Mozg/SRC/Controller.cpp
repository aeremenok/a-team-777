// **************************************************************************
//! \file   Controller.cpp
//! \brief  ���������� ������ ��� ���������� �����
//! \author Bessonov A.V.
//! \date   17.May.2008 - 20.May.2008
// **************************************************************************

// ==========================================================================
#include "stdafx.h"
#include "MainPlayer.h"
#include "SlavePlayer.h"
#include <fstream>
#include "tools.h"

namespace Game
{
// Class Controller 

Controller::Controller(CMainDlg* dlg)
   :mainDlg(dlg),active(false)
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

   
   //! ��������� ����� ��������� �� ID
   netStruc = new tdata::Data;
   netStruc->generate(nStr);

   nStructNum = nStr;

   //! ���������� � ������� ���������, ��� ����� ������ �����
   mainDlg->setTip("��� ������ " + 
                     playersList->getActivePlayer()->getName());
}
void Controller::makeStep(int hole)
{
   if (netStruc->makeStep(hole))
   {
      netStruc->Redraw();     //!< �������������� ���� �����
      playersList->goNext();  //!< ������� � ���������� ������
      //! ������� ���������
      mainDlg->setTip("��� ������ " + 
                        playersList->getActivePlayer()->getName());
      /*for (int i = 0; i < 6; i++)
      {
         if (netStruc->IsPositionAvailable(i))
         {
            CButton button = mainDlg->GetDlgItem(IDC_BUTTON_HOLE1 + i);
            ATLASSERT(::IsWindow(button.m_hWnd));
            button.EnableWindow(FALSE);
         }
      }*/
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
   {  //! ������ ������ ������, ���� ���������
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

}

void Controller::reset()
{
   //! ������ �������� �������� ������
   while (!playersList->getActivePlayer()->ifMain())
      playersList->goNext();

   //! ������� ��������� ����
   //delete netStruc;
   //netStruc = new Data
   netStruc->clean();
   netStruc->generate(nStructNum);
   netStruc->Redraw();
}

void Controller::Save(std::string strName) const
{
   //! ������� ����� ��� ������ ������
   ser::Archive arch;
   int idPlayers = playersList->PutIntoArchive(arch);
   int idStruct  = netStruc->PutIntoArchive(arch);

   std::ofstream os(strName.c_str());
   if ( os.bad() )
   {
      ::MessageBox(NULL, "FILE NOT OPEN", "Error", MB_ICONERROR | MB_OK );
      return;
   }

   os << nStructNum << separ << idPlayers << separ << idStruct << separ << arch;
   os.flush();
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

   std::ifstream is(strName.c_str());
   if ( is.bad() )
   {
      ::MessageBox(NULL, "FILE NOT OPEN", "Error", MB_ICONERROR | MB_OK );
      return false;
   }
   
   is >> nStructNum >> idPlayers >> idStruct >> arch;

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
   return true;
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
