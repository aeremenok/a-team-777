// **************************************************************************
//! \file   Controller.h
//! \brief  ���������� ������ ��� ���������� �����
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
//!	����� ��������� ������� ���������
class Controller 
{
      //! ��������� ����������� �������� �������� Singleton
      //! ����� �������� �� ������� ������ �����
      Controller(CMainDlg* dlg);
      
  public:
     ~Controller();

      //! ������� ��������� ��� ����
      void createNetwork (int newNumberOfHoles, int newNumberOfCell);

      //! ��������������� ���� �� �����
	   bool restoreGame (std::string destanation);
      
      //! �������� ��������� ������������� ��� ������������� �������
      //! ������� ������ ������� � ���������
      void initialize (std::list<std::string>& names, int nStr);

      //! ������������ ���� ������ �����
      void makeStep(int hole);

      //! ���������� true, ���� ���� �������
      bool isActive() const {return active;};

      //! �������� ��������� ����
      void setActive(bool newState) {active = newState;};

      //! �������������� ��������� ��������� ����
      //! ������ ������� �������� ������ � ���������� ���������
      void reset();

      //! ���� ������ ��� ������, ���������� ������, ����� ������� ������
      static Controller* Instance(CMainDlg* dlg = 0);

  private:
    
      //! ��������������� ��������� ���� ��� �������� ���� �� �����
      bool restoreNetwork ();

      //! ��������������� ������ ������� ��� �������� ����
      bool restorePlayers ();
   
  private: 
      ListOfPlayers        *playersList;     //!< ������ �������
      tdata::Data          *netStruc;        //!< ���������� ���������� ����
      CMainDlg             *mainDlg;         //!< ������� ���� ���������
      bool                 active;           //!< ���� true, �� ���� �������

      static Controller* _instance; //!< ������������ ������ � ���������
};
   
} //end of namespace Game
// ==========================================================================
#endif
