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
//!                       ИНДЕКСЫ КАРТИНОК ДЛЯ ТУЛБАРА
enum    {
           TBCMD_NEW,        //!< Создать игру
           TBCMD_OPEN,       //!< Открыть игру
           TBCMD_SAVE,       //!< Сохранить игру
           TBCMD_RESET,      //!< Начать заново
           TBCMD_ABOUT,      //!< Информация о программе
           TBCMD_CLOSE       //!< Закрыть программу
        };

// ===========================================================================
LPTSTR CMainDlg::tbToolTips[TBAR_BTN_NUM] = 
{
   "Создать игру",
   "Открыть игру",
   "Сохранить игру",
   "Начать заново",
   "Информация о программе",
   "Закрыть программу"
};

// ===========================================================================
TBBUTTON CMainDlg::tbButtons[TBAR_BTN_NUM] = 
{
   // ------------------------------------------------------------------------
   {
      TBCMD_NEW,                    // индекс картинки 
      ID_CMD_NEW,                   // "Создать игру"
      TBSTATE_ENABLED,              // состояние
      BTNS_AUTOSIZE |BTNS_BUTTON,   // стиль
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string     
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_OPEN,                   // индекс картинки 
      ID_CMD_OPEN,                  // "Открыть игру"
      TBSTATE_ENABLED,              // состояние
      BTNS_AUTOSIZE |BTNS_BUTTON,   // стиль
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string    
    },
   // ------------------------------------------------------------------------
   {
      TBCMD_SAVE,                   // индекс картинки 
      ID_CMD_SAVE,                  // "Сохранить игру"
      TBSTATE_ENABLED,              // состояние
      BTNS_AUTOSIZE |BTNS_BUTTON,   // стиль
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string      
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_RESET,                  // индекс картинки 
      ID_CMD_RESET,                 // "Начать заново"
      TBSTATE_ENABLED,              // состояние
      BTNS_AUTOSIZE |BTNS_BUTTON,   // стиль
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_ABOUT,                  // индекс картинки 
      ID_CMD_ABOUT,                 // "Информация о программе"
      TBSTATE_ENABLED,              // состояние
      BTNS_AUTOSIZE |BTNS_BUTTON,   // стиль
      {0, 0},                       // bReserved[2] padding for alignment
      0,                            // Application-defined value.
      NULL                          // index of the button string
   },
   // ------------------------------------------------------------------------
   {
      TBCMD_CLOSE,                  // индекс картинки 
      ID_CMD_CLOSE,                 // "Закрыть программу"
      TBSTATE_ENABLED,              // состояние
      BTNS_AUTOSIZE |BTNS_BUTTON,   // стиль
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
   // СОЗДАТЬ СПИСОК КАРТИНОК ДЛЯ ТУЛБАРА
   m_images.CreateFromImage(IDB_MAINTOOLBAR, 24, 0, RGB(255, 255, 255),
                            IMAGE_BITMAP, LR_CREATEDIBSECTION);

   RECT tbrect;
   ::GetWindowRect(GetDlgItem(IDC_TOOLBAR_MAIN), &tbrect);
   ::DestroyWindow(GetDlgItem(IDC_TOOLBAR_MAIN));

   // СОЗДАТЬ И НАСТРОИТЬ ТУЛБАР
   const DWORD tbarstyle = WS_CHILD | WS_VISIBLE | TBSTYLE_FLAT | 
                           TBSTYLE_TOOLTIPS | CCS_NORESIZE | 
                           CCS_NOPARENTALIGN | TBSTYLE_LIST;

   m_tbar.Create(m_hWnd, tbrect, NULL, tbarstyle, NULL, IDC_TOOLBAR_MAIN);
   m_tbar.SetButtonStructSize();

   m_tbar.SetImageList(m_images);

   m_tbar.AddButtons(TBAR_BTN_NUM, tbButtons);
   m_tbar.AutoSize();

   // ПОДВИНУТЬ ТУЛБАР НА ЕГО МЕСТО
   ScreenToClient(&tbrect);
   m_tbar.MoveWindow(&tbrect);

   //=========================================================================
   
   //! Деактивируем кнопки и элементы меню
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
   ttt.hinst    = NULL; // NULL, так как lpszText - адрес строки тултипа
   ttt.lpszText = tbToolTips[idCtrl];
   
   return 0;
}

LRESULT CMainDlg::OnNew(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   CCreateNewDlg dlg;
   LRESULT res = dlg.DoModal(m_hWnd);
   if (res == IDOK) //! Пользователь выбрал создать новую игру
   {
      //! Активируем кнопки
      ActivateButtons(TRUE);
      ActivateSave(TRUE);
      ActivateReset(TRUE);
      //! Заполняем список имен игроков, полученный из окна новой игры
      std::list<std::string> names;
      for (int i = 0; i<dlg.m_nPlayersNumber; i++)
      {
         names.push_back(dlg.m_strPlayers[i]); 
      }

      //! Считываем структуру игры и передаем ее 
      CComboBox cb = GetDlgItem(IDC_COMBO_STRUCT_NUMBER);
      ATLASSERT(::IsWindow(cb.m_hWnd));
      cb.GetCurSel();
      //! Вызов инициализации
      Game::Controller::Instance()->initialize(names, cb.GetCurSel());
      
   }else
   { //! Произошла отмена, проверяем была ли игра активной ранее
      if(Game::Controller::Instance()->isActive())
      {
         //! Игра была активной, активируем кнопки
         ActivateButtons(TRUE);
         ActivateSave(TRUE);
         ActivateReset(TRUE);
      }else
      {
         //! Игра не была активной
         //! Деактивируем кнопки и элементы меню
         ActivateButtons(FALSE);
         ActivateSave(FALSE);
         ActivateReset(FALSE);
      }  
   }   
   return 0;
}
LRESULT CMainDlg::OnOpen(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   // -------------------- Вызвать диалог открытия файла --------------------
   // НАСТРОИТЬ ФИЛЬТР И ФЛАГИ
   DWORD flags =  OFN_ENABLESIZING | OFN_EXPLORER |
      OFN_HIDEREADONLY | OFN_LONGNAMES;
   //! Задаем фильтр для файлов
   char szFilter[] = "Мозгодолбалки (*.mzg)\0*.mzg\0Все файлы (*.*)\0*.*\0\0";
   CFileDialog fileDlg(TRUE, "mzg", NULL, flags, szFilter);

   if (fileDlg.DoModal() == IDCANCEL)
   {
      return 0;
   }
   //! Восстановление из файла
   Controller::Instance()->Open(fileDlg.m_ofn.lpstrFile);

   return 0;
}
LRESULT CMainDlg::OnSave(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   // -------------------- Вызвать диалог открытия файла --------------------
   // НАСТРОИТЬ ФИЛЬТР И ФЛАГИ
   DWORD flags =  OFN_ENABLESIZING | OFN_EXPLORER |
      OFN_HIDEREADONLY | OFN_LONGNAMES;
   //! Задаем фильтр для файлов
   char szFilter[] = "Мозгодолбалки (*.mzg)\0*.mzg\0Все файлы (*.*)\0*.*\0\0";
   CFileDialog fileDlg(FALSE, "mzg", NULL, flags, szFilter);

   if (fileDlg.DoModal() == IDCANCEL)
   {
      return 0;
   }

   //! Сохраняет в файл
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

//! Функция для деактивации кнопок и некоторых элементов меню
//! act == TRUE активировать 
//! act == FALSE деактивировать
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
