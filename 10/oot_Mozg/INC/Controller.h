// **************************************************************************
//! \file   Controller.h
//! \brief  ���������� ������ ��� ���������� �����
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
//!	����� ��������� ������� ���������
class Controller 
{
  public:
      Controller(CMainDlg* dlg);
      ~Controller();

      //! ������� ��������� ��� ����
      void createNetwork (int newNumberOfHoles, int newNumberOfCell);

      //! ��������������� ���� �� �����
	   bool restoreGame (std::wstring destanation);
      
      //! �������� ��������� ������������� ��� ������������� �������
      //! ������� ������ ������� � ���������
      void initialize (std::list<std::wstring>& names, int nStr);

      //! ������������ ���� ������ �����
      void makeStep(int hole);

      //! ���������� true, ���� ���� �������
      bool isActive() const {return active;};

      //! �������� ��������� ����
      void setActive(bool newState) {active = newState;};

  private:
    
      //! ��������������� ��������� ���� ��� �������� ���� �� �����
      bool restoreNetwork ();

      //! ��������������� ������ ������� ��� �������� ����
      bool restorePlayers ();
   
  private: 
      ListOfPlayers        *playersList;  //!< ������ �������
      tdata::Data          *netStruc;     //!< ���������� ���������� ����
      CMainDlg             *mainDlg;      //!< ������� ���� ���������
      bool                 active;      //!< ���� true, �� ���� �������
};
   
} //end of namespace Game
// ==========================================================================
#endif
