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

//void Controller::createNetwork (int newNumberOfHoles, int newNumberOfCell)
//{
//}
//
//bool Controller::restoreNetwork ()
//{
//	return 0;
//}
//
//bool Controller::restorePlayers ()
//{
//	return 0;
//}
//
//bool Controller::restoreGame (std::string destanation)
//{
//	return 0;
//}
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
   netStruc->clean();
}

void Controller::Save(std::string strName) const
{
   //! ������� ����� ��� ������ ������
   ser::Archive arch;
   int idPlayers = playersList->PutIntoArchive(arch);
   int idStruct  = netStruc->PutIntoArchive(arch);


   std::ofstream os(strName.c_str());

   os << idPlayers << separ << idStruct << separ << arch;
   os.close();
}

bool Controller::Open(std::string strName)
{
   //! ������� ������ ����������
   delete playersList;
   playersList = new ListOfPlayers;

   //! ������� ����� ��� ���������� ������
   ser::Archive arch;
   /************************************************************************/
   /*                  TODO: ���������� �� ����� � �����                   */
   /************************************************************************/

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
