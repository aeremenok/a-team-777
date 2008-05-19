// **************************************************************************
//! \file   Controller.cpp
//! \brief  ���������� ������ ��� ���������� �����
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
   //! ������ ��������� ���� ��� ����
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

   /************************************************************************/
   /*                     //! TODO: ������� ���������                      */
   /************************************************************************/
   netStruc = new tdata::Data;

   //! ���������� � ������� ���������, ��� ����� ������ �����
   mainDlg->setTip("��� ������ " + playersList->getActivePlayer()->getName());
}
void Controller::makeStep(int hole)
{
   if (netStruc->makeStep(hole))
   {
      netStruc->Redraw();     //!< �������������� ���� �����
      playersList->goNext();  //!< ������� � ���������� ������
      //! ������� ���������
      mainDlg->setTip("��� ������ " + playersList->getActivePlayer()->getName());
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
      //! ������� ���������, � ����������
      mainDlg->setTip("����������� " + playersList->getActivePlayer()->getName() 
                                      + " � ������� � ���� �������������!");
   }
}
} //end of namespace Game
// ==========================================================================
