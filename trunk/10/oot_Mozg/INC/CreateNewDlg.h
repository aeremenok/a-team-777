// **************************************************************************
//! \file   CreateNewDlg.h
//! \brief  Диалоговое для создания новой игры
//! \author Bessonov A.V.
//! \date   14.May.2008 - 14.May.2008
// **************************************************************************

#ifndef __CREATENEWDLG_H
#define __CREATENEWDLG_H

#include <vector>
#include "atlstr.h"

// ==========================================================================
namespace Game
{
//! Диалог создания новой игры
class CCreateNewDlg :   public CDialogImpl<CCreateNewDlg>,
                        public CWinDataExchange<CCreateNewDlg>
{
public:
   enum { IDD = IDD_DIALOG_CREATE_NEW };

   BEGIN_MSG_MAP(CCreateNewDlg)
      MESSAGE_HANDLER(WM_INITDIALOG, OnInitDialog)
      MESSAGE_HANDLER(WM_DESTROY, OnClose)
      COMMAND_HANDLER(IDC_EDIT_PLAYER_NUMBER, EN_CHANGE, OnPlayerNumChanged)

      COMMAND_HANDLER(IDOK, BN_CLICKED, OnOK)
      COMMAND_HANDLER(IDCANCEL, BN_CLICKED, OnCancel)
      COMMAND_HANDLER(IDC_RADIO_LOCAL_GAME, BN_CLICKED, OnBnClickedRadioLocalGame)
      COMMAND_HANDLER(IDC_RADIO_NETWORK_GAME, BN_CLICKED, OnBnClickedRadioNetworkGame)
      NOTIFY_HANDLER(IDC_SPIN_PLAYER_NUMBER, UDN_DELTAPOS, OnDeltaposSpinPlayerNumber)
   END_MSG_MAP();
   
   BEGIN_DDX_MAP(CCreateNewDlg)
      //DDX_INT(IDC_EDIT_PLAYER_NUMBER, m_nPlayersNumber)
      DDX_TEXT(IDC_EDIT_PLAYER_1, m_strPlayer1)
      /*DDX_TEXT(IDC_EDIT_PLAYER_2, m_strPlayer2)
      DDX_TEXT(IDC_EDIT_PLAYER_3, m_strPlayer3)
      DDX_TEXT(IDC_EDIT_PLAYER_4, m_strPlayer4)
      DDX_TEXT(IDC_EDIT_PLAYER_5, m_strPlayer5)
      DDX_TEXT(IDC_EDIT_PLAYER_6, m_strPlayer6)*/
   END_DDX_MAP()

   // Handler prototypes (uncomment arguments if needed):
   //   LRESULT MessageHandler(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
   //   LRESULT CommandHandler(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
   //   LRESULT NotifyHandler(int /*idCtrl*/, LPNMHDR /*pnmh*/, BOOL& /*bHandled*/)

   LRESULT OnInitDialog(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/);
   LRESULT OnClose(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/);

   LRESULT OnOK(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnCancel(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);

   LRESULT OnBnClickedRadioLocalGame(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedRadioNetworkGame(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnDeltaposSpinPlayerNumber(int /*idCtrl*/, LPNMHDR pNMHDR, BOOL& /*bHandled*/);
   LRESULT OnPlayerNumChanged(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
public:
   //! Количество игроков, задаваемое пользователем в окне
   int m_nPlayersNumber;
   
   std::vector<CString> m_strPlayers;
   ATL::CComBSTR m_strPlayer1; //!< Имя первого игрока
   //LPTSTR m_strPlayer2; //!< Имя второго игрока
   //LPTSTR m_strPlayer3; //!< Имя третьего игрока
   //LPTSTR m_strPlayer4; //!< Имя четвертого игрока
   //LPTSTR m_strPlayer5; //!< Имя пятого игрока
   //LPTSTR m_strPlayer6; //!< Имя шестого игрока
};
}
// ==========================================================================
#endif // __CREATENEWDLG_H