// **************************************************************************
//! \file   CreateNewDlg.cpp
//! \brief  Диалоговое для создания новой игры
//! \author Bessonov A.V.
//! \date   14.May.2008 - 14.May.2008
// **************************************************************************

#include "stdafx.h"
#include "CreateNewDlg.h"

// ==========================================================================
namespace Game
{
LRESULT CCreateNewDlg::OnInitDialog(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
{
   CenterWindow();
   //! Устанавливаем переключатель в режим локальной игры
   CheckRadioButton(IDC_RADIO_LOCAL_GAME,IDC_RADIO_NETWORK_GAME,IDC_RADIO_LOCAL_GAME);
   
   CUpDownCtrl ud = GetDlgItem(IDC_SPIN_PLAYER_NUMBER);
   ATLASSERT(::IsWindow(ud.m_hWnd));
   //! Устанавливаем диапазон для количества игроков
   ud.SetRange32(2,6); 
   //! Задаем значение по умолчанию для количества игроков
   m_nPlayersNumber = 6;
   
   DoDataExchange(DDX_LOAD);

   m_strPlayers.push_back("Player1"); 
   m_strPlayers.push_back("Player2");
   m_strPlayers.push_back("Player3");
   m_strPlayers.push_back("Player4");
   m_strPlayers.push_back("Player5");
   m_strPlayers.push_back("Player6");
   
   GetDlgItem(IDC_EDIT_PLAYER_1).SetWindowText(m_strPlayers[0].c_str());
   GetDlgItem(IDC_EDIT_PLAYER_2).SetWindowText(m_strPlayers[1].c_str());
   GetDlgItem(IDC_EDIT_PLAYER_3).SetWindowText(m_strPlayers[2].c_str());
   GetDlgItem(IDC_EDIT_PLAYER_4).SetWindowText(m_strPlayers[3].c_str());
   GetDlgItem(IDC_EDIT_PLAYER_5).SetWindowText(m_strPlayers[4].c_str());
   GetDlgItem(IDC_EDIT_PLAYER_6).SetWindowText(m_strPlayers[5].c_str());

   DoDataExchange(DDX_LOAD);

   return TRUE;
}
LRESULT CCreateNewDlg::OnClose(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
{
   return 0;
}
LRESULT CCreateNewDlg::OnOK(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   m_strPlayers[0] = GetControlText(GetDlgItem(IDC_EDIT_PLAYER_1));
   m_strPlayers[1] = GetControlText(GetDlgItem(IDC_EDIT_PLAYER_2));
   m_strPlayers[2] = GetControlText(GetDlgItem(IDC_EDIT_PLAYER_3));
   m_strPlayers[3] = GetControlText(GetDlgItem(IDC_EDIT_PLAYER_4));
   m_strPlayers[4] = GetControlText(GetDlgItem(IDC_EDIT_PLAYER_5));
   m_strPlayers[5] = GetControlText(GetDlgItem(IDC_EDIT_PLAYER_6));
   EndDialog(IDOK);
   return 0;
}
LRESULT CCreateNewDlg::OnCancel(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   EndDialog(IDCANCEL);
   return 0;
}
// ==========================================================================
LRESULT CCreateNewDlg::OnBnClickedRadioLocalGame(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   //! Устанавливаем переключатель в режим локальной игры
   CheckRadioButton(IDC_RADIO_LOCAL_GAME,IDC_RADIO_NETWORK_GAME,IDC_RADIO_LOCAL_GAME);

   //! Активируем кнопку НАЧАТЬ
   CButton button = GetDlgItem(IDOK);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(TRUE);

   return 0;
}

LRESULT CCreateNewDlg::OnBnClickedRadioNetworkGame(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   //! Устанавливаем переключатель в режим сетевой игры
   CheckRadioButton(IDC_RADIO_LOCAL_GAME,IDC_RADIO_NETWORK_GAME,IDC_RADIO_NETWORK_GAME);
   
   //! Деактивируем кнопку НАЧАТЬ
   CButton button = GetDlgItem(IDOK);
   ATLASSERT(::IsWindow(button.m_hWnd));
   button.EnableWindow(FALSE);
   
   return 0;
}

LRESULT CCreateNewDlg::OnChangeNumPlayers(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
{
   //! Обновляем переменную m_nPlayersNumber после нажатия пользователем на
   //! счетчик
   DoDataExchange(DDX_SAVE);

   //! Делаем неактивными соответстующие поля ввода для имен игроков
   CEdit ledit;
   ledit = GetDlgItem(IDC_EDIT_PLAYER_3);
   ATLASSERT(::IsWindow(ledit.m_hWnd));
   ledit.EnableWindow(TRUE);
   ledit = GetDlgItem(IDC_EDIT_PLAYER_4);
   ATLASSERT(::IsWindow(ledit.m_hWnd));
   ledit.EnableWindow(TRUE);
   ledit = GetDlgItem(IDC_EDIT_PLAYER_5);
   ATLASSERT(::IsWindow(ledit.m_hWnd));
   ledit.EnableWindow(TRUE);
   ledit = GetDlgItem(IDC_EDIT_PLAYER_6);
   ATLASSERT(::IsWindow(ledit.m_hWnd));
   ledit.EnableWindow(TRUE);
   switch (m_nPlayersNumber)
   {
   case 2:
      ledit = GetDlgItem(IDC_EDIT_PLAYER_3);
      ATLASSERT(::IsWindow(ledit.m_hWnd));
      ledit.EnableWindow(FALSE);
   case 3:
      ledit = GetDlgItem(IDC_EDIT_PLAYER_4);
      ATLASSERT(::IsWindow(ledit.m_hWnd));
      ledit.EnableWindow(FALSE);
   case 4:
      ledit = GetDlgItem(IDC_EDIT_PLAYER_5);
      ATLASSERT(::IsWindow(ledit.m_hWnd));
      ledit.EnableWindow(FALSE);
   case 5:
      ledit = GetDlgItem(IDC_EDIT_PLAYER_6);
      ATLASSERT(::IsWindow(ledit.m_hWnd));
      ledit.EnableWindow(FALSE);
   case 6:
      break;
   }
   //! Служит для активации кнопки НАЧАТЬ, если одно из полей имен игров
   //! было пустым и мы его деактивировали
   BOOL tmp = FALSE;
   OnPlayerChanged(NULL, NULL, NULL, tmp);
   return 0;
}
LRESULT CCreateNewDlg::OnPlayerChanged(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
{
   DoDataExchange(DDX_SAVE);

   GetDlgItem(IDOK).EnableWindow(TRUE);

   //! Если ничего не введено в поле имени игрока, то деактивуруется
   //! кнопка ОК
   for (int i = 0; i < m_nPlayersNumber; i++)
   {
      if(GetControlText(GetDlgItem(IDC_EDIT_PLAYER_1 + i)) == std::string(""))
         GetDlgItem(IDOK).EnableWindow(FALSE);
   }
   return 0;
}
}
