// **************************************************************************
//! \file   MainDlg.cpp
//! \brief  implementation of the CMainDlg class
//! \author Bessonov A.V.
//! \date   14.May.2008 - 14.May.2008
// **************************************************************************

#include "stdafx.h"
#include "MainDlg.h"
#include "aboutdlg.h"
#include "CreateNewDlg.h"
namespace Game
{
// ===========================================================================
//!                       ������� �������� ��� �������
enum    {
           TBCMD_NEW,        //!< ������� ����
           TBCMD_OPEN,       //!< ������� ����
           TBCMD_SAVE,       //!< ��������� ����
           TBCMD_RESET,      //!< ������ ������
           TBCMD_ABOUT,      //!< ���������� � ���������
           TBCMD_CLOSE       //!< ������� ���������
        };

// ===========================================================================
LPTSTR CMainDlg::tbToolTips[TBAR_BTN_NUM] = 
{
   "������� ����",
   "������� ����",
   "��������� ����",
   "������ ������",
   "���������� � ���������",
   "������� ���������"
};

// ===========================================================================
TBBUTTON CMainDlg::tbButtons[TBAR_BTN_NUM] = 
{
   // ------------------------------------------------------------------------
   {
      TBCMD_NEW,                    // ������ �������� 
      ID_CMD_NEW,                   // "������� ����"
      TBSTATE_ENABLED,              // ���������
      BTNS_AUTOSIZE |BTNS_BUTTON,   // �����
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string     
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_OPEN,                   // ������ �������� 
      ID_CMD_OPEN,                  // "������� ����"
      TBSTATE_ENABLED,              // ���������
      BTNS_AUTOSIZE |BTNS_BUTTON,   // �����
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string    
    },
   // ------------------------------------------------------------------------
   {
      TBCMD_SAVE,                   // ������ �������� 
      ID_CMD_SAVE,                  // "��������� ����"
      TBSTATE_ENABLED,              // ���������
      BTNS_AUTOSIZE |BTNS_BUTTON,   // �����
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string      
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_RESET,                  // ������ �������� 
      ID_CMD_RESET,                 // "������ ������"
      TBSTATE_ENABLED,              // ���������
      BTNS_AUTOSIZE |BTNS_BUTTON,   // �����
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_ABOUT,                  // ������ �������� 
      ID_CMD_ABOUT,                 // "���������� � ���������"
      TBSTATE_ENABLED,              // ���������
      BTNS_AUTOSIZE |BTNS_BUTTON,   // �����
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_CLOSE,                  // ������ �������� 
      ID_CMD_CLOSE,                 // "������� ���������"
      TBSTATE_ENABLED,              // ���������
      BTNS_AUTOSIZE |BTNS_BUTTON,   // �����
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string
   }
};

// ==========================================================================
LRESULT CMainDlg::OnInitDialog(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
{
   // center the dialog on the screen
   CenterWindow();

   // set icons
   HICON hIcon = (HICON)::LoadImage(_Module.GetResourceInstance(), MAKEINTRESOURCE(IDR_MAINFRAME), 
      IMAGE_ICON, ::GetSystemMetrics(SM_CXICON), ::GetSystemMetrics(SM_CYICON), LR_DEFAULTCOLOR);
   SetIcon(hIcon, TRUE);
   HICON hIconSmall = (HICON)::LoadImage(_Module.GetResourceInstance(), MAKEINTRESOURCE(IDR_MAINFRAME), 
      IMAGE_ICON, ::GetSystemMetrics(SM_CXSMICON), ::GetSystemMetrics(SM_CYSMICON), LR_DEFAULTCOLOR);
   SetIcon(hIconSmall, FALSE);

   // register object for message filtering and idle updates
   CMessageLoop* pLoop = _Module.GetMessageLoop();
   ATLASSERT(pLoop != NULL);
   pLoop->AddMessageFilter(this);
   pLoop->AddIdleHandler(this);

   UIAddChildWindowContainer(m_hWnd);

   //=========================================================================
   // ������� ������ �������� ��� �������
   m_images.CreateFromImage(IDB_MAINTOOLBAR, 24, 0, RGB(255, 255, 255),
                            IMAGE_BITMAP, LR_CREATEDIBSECTION);

   RECT tbrect;
   ::GetWindowRect(GetDlgItem(IDC_TOOLBAR_MAIN), &tbrect);
   ::DestroyWindow(GetDlgItem(IDC_TOOLBAR_MAIN));

   // ������� � ��������� ������
   const DWORD tbarstyle = WS_CHILD | WS_VISIBLE | TBSTYLE_FLAT | 
                           TBSTYLE_TOOLTIPS | CCS_NORESIZE | 
                           CCS_NOPARENTALIGN | TBSTYLE_LIST;

   m_tbar.Create(m_hWnd, tbrect, NULL, tbarstyle, NULL, IDC_TOOLBAR_MAIN);
   m_tbar.SetButtonStructSize();

   m_tbar.SetImageList(m_images);

   m_tbar.AddButtons(TBAR_BTN_NUM, tbButtons);
   m_tbar.AutoSize();

   // ��������� ������ �� ��� �����
   ScreenToClient(&tbrect);
   m_tbar.MoveWindow(&tbrect);

   //=========================================================================
   
   //! ������������ ������ � �������� ����
   ActivateButtons(FALSE);
   ActivateSave(FALSE);
   ActivateReset(FALSE);

   return TRUE;
}

LRESULT CMainDlg::OnDestroy(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
{
   // unregister message filtering and idle updates
   CMessageLoop* pLoop = _Module.GetMessageLoop();
   ATLASSERT(pLoop != NULL);
   pLoop->RemoveMessageFilter(this);
   pLoop->RemoveIdleHandler(this);

   return 0;
}

// =============================================================================
LRESULT CMainDlg::OnToolTipInfo(int idCtrl, LPNMHDR pnmh, BOOL& /*bHandled*/)
{   
   TOOLTIPTEXT& ttt = *reinterpret_cast<LPTOOLTIPTEXT>(pnmh);
   ttt.hinst    = NULL; // NULL, ��� ��� lpszText - ����� ������ �������
   ttt.lpszText = tbToolTips[idCtrl];
   
   return 0;
}

LRESULT CMainDlg::OnNew(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   CCreateNewDlg dlg;
   LRESULT res = dlg.DoModal(m_hWnd);
   if (res == IDOK) //! ������������ ������ ������� ����� ����
   {
      //! ���������� ������
      ActivateButtons(TRUE);
      ActivateSave(TRUE);
      ActivateReset(TRUE);
      //! ��������� ������ ���� �������, ���������� �� ���� ����� ����
      std::list<std::string> names;
      for (int i = 0; i<dlg.m_nPlayersNumber; i++)
      {
         names.push_back(dlg.m_strPlayers[i]); 
      }

      //! ��������� ��������� ���� � �������� �� 
      CComboBox cb = GetDlgItem(IDC_COMBO_STRUCT_NUMBER);
      ATLASSERT(::IsWindow(cb.m_hWnd));
      cb.GetCurSel();
      //! ����� �������������
      Game::Controller::Instance()->initialize(names, cb.GetCurSel());
      
   }else
   { //! ��������� ������, ��������� ���� �� ���� �������� �����
      if(Game::Controller::Instance()->isActive())
      {
         //! ���� ���� ��������, ���������� ������
         ActivateButtons(TRUE);
         ActivateSave(TRUE);
         ActivateReset(TRUE);
      }else
      {
         //! ���� �� ���� ��������
         //! ������������ ������ � �������� ����
         ActivateButtons(FALSE);
         ActivateSave(FALSE);
         ActivateReset(FALSE);
      }  
   }   
   return 0;
}
LRESULT CMainDlg::OnOpen(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   // -------------------- ������� ������ �������� ����� --------------------
   // ��������� ������ � �����
   DWORD flags =  OFN_ENABLESIZING | OFN_EXPLORER |
      OFN_HIDEREADONLY | OFN_LONGNAMES;
   //! ������ ������ ��� ������
   char szFilter[] = "������������� (*.mzg)\0*.mzg\0��� ����� (*.*)\0*.*\0\0";
   CFileDialog fileDlg(TRUE, "mzg", NULL, flags, szFilter);

   if (fileDlg.DoModal() == IDCANCEL)
   {
      return 0;
   }
   //! �������������� �� �����
   Controller::Instance()->Open(fileDlg.m_ofn.lpstrFile);

   return 0;
}
LRESULT CMainDlg::OnSave(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   // -------------------- ������� ������ �������� ����� --------------------
   // ��������� ������ � �����
   DWORD flags =  OFN_ENABLESIZING | OFN_EXPLORER |
      OFN_HIDEREADONLY | OFN_LONGNAMES;
   //! ������ ������ ��� ������
   char szFilter[] = "������������� (*.mzg)\0*.mzg\0��� ����� (*.*)\0*.*\0\0";
   CFileDialog fileDlg(FALSE, "mzg", NULL, flags, szFilter);

   if (fileDlg.DoModal() == IDCANCEL)
   {
      return 0;
   }

   //! ��������� � ����
   Controller::Instance()->Save(fileDlg.m_ofn.lpstrFile);

   return 0;
}
LRESULT CMainDlg::OnReset(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   Controller::Instance()->reset();
   return 0;
}
LRESULT CMainDlg::OnAppAbout(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   CAboutDlg dlg;
   dlg.DoModal();
   return 0;
}

LRESULT CMainDlg::OnCancel(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   CloseDialog(wID);
   return 0;
}

void CMainDlg::CloseDialog(int nVal)
{
   DestroyWindow();
   ::PostQuitMessage(nVal);
}

LRESULT CMainDlg::OnBnClickedButtonHole1(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   Game::Controller::Instance()->makeStep(1);
   return 0;
}

LRESULT CMainDlg::OnBnClickedButtonHole2(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   Game::Controller::Instance()->makeStep(2);
   return 0;
}

LRESULT CMainDlg::OnBnClickedButtonHole3(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   Game::Controller::Instance()->makeStep(3);
   return 0;
}

LRESULT CMainDlg::OnBnClickedButtonHole4(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   Game::Controller::Instance()->makeStep(4);
   return 0;
}

LRESULT CMainDlg::OnBnClickedButtonHole5(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   Game::Controller::Instance()->makeStep(5);
   return 0;
}

LRESULT CMainDlg::OnBnClickedButtonHole6(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   Game::Controller::Instance()->makeStep(6);
   return 0;
}

//! ������� ��� ����������� ������ � ��������� ��������� ����
//! act == TRUE ������������ 
//! act == FALSE ��������������
void CMainDlg::ActivateButtons(bool act)
{
   CButton button = GetDlgItem(IDC_BUTTON_HOLE1);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(act);

   button = GetDlgItem(IDC_BUTTON_HOLE2);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(act);

   button = GetDlgItem(IDC_BUTTON_HOLE3);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(act);

   button = GetDlgItem(IDC_BUTTON_HOLE4);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(act);

   button = GetDlgItem(IDC_BUTTON_HOLE5);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(act);

   button = GetDlgItem(IDC_BUTTON_HOLE6);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(act);
}
void CMainDlg::ActivateSave(bool act)
{
   m_tbar.EnableButton(TBCMD_SAVE, act);
}
void CMainDlg::ActivateOpen(bool act)
{
   m_tbar.EnableButton(TBCMD_OPEN, act);
}
void CMainDlg::ActivateReset(bool act)
{
   m_tbar.EnableButton(TBCMD_RESET, act);
}
void CMainDlg::ActivateClose(bool act)
{
   m_tbar.EnableButton(ID_CMD_CLOSE, act);
}
void CMainDlg::ActivateNew(bool act)
{
   m_tbar.EnableButton(ID_CMD_NEW, act);
}
void CMainDlg::setTip(std::string tip)
{
   GetDlgItem(IDC_STATIC_STATUS).SetWindowText(tip.c_str());
}
}
