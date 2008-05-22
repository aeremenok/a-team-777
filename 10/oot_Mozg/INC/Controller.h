// **************************************************************************
//! \file   Controller.h
//! \brief  ���������� ������ ��� ���������� �����
//! \author Bessonov A.V.
//! \date   17.May.2008 - 20.May.2008
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
class Controller : public iface::iDrawable
{
      //! ��������� ����������� �������� �������� Singleton
      //! ����� �������� �� ������� ������ �����
      Controller(CMainDlg* dlg);
      
  public:
     ~Controller();

     //! ������������
     virtual void Redraw() const;

     //! ������� �����������
     virtual void Draw(CPaintDC& dc);
      
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

      //! ��������� ������ ������� � ��������� � ���� strName
      void Save(std::string strName) const;

      //! ��������������� ������ ������� � ��������� �� ����� strName
      //! \return true, ���� ����� ��������� ����������
      bool Open(std::string strName);

      //! ������������ ������ ��� �����, � ������� ������ ������ �����
      void deActivateButtons();

      //! ���� ������ ��� ������, ���������� ������, ����� ������� ������
      static Controller* Instance(CMainDlg* dlg = 0);
   
  private: 
      ListOfPlayers        *playersList;     //!< ������ �������
      tdata::Data          *netStruc;        //!< ���������� ���������� ����
      CMainDlg             *mainDlg;         //!< ������� ���� ���������
      bool                 active;           //!< ���� true, �� ���� �������

      static Controller* _instance; //!< ������������ ������ � ���������

      int nStructNum; //!< ����� ��������� ���������
};
   
} //end of namespace Game
// ==========================================================================
#endif
