// **************************************************************************
//! \file   MainDlg.h
//! \brief  interface of the CMainDlg class
//! \author Bessonov A.V.
//! \date   14.May.2008 - 14.May.2008
// **************************************************************************

#ifndef __MAINDLG_H
#define __MAINDLG_H

#include "Controller.h"

// ==========================================================================
namespace Game
{
class Controller;
class CMainDlg : public CDialogImpl<CMainDlg>, public CUpdateUI<CMainDlg>,
		public CMessageFilter, public CIdleHandler
{
public:
	enum { IDD = IDD_MAINDLG };

	virtual BOOL PreTranslateMessage(MSG* pMsg)
	{
		return CWindow::IsDialogMessage(pMsg);
	}

	virtual BOOL OnIdle()
	{
		return FALSE;
	}

   // -----------------------------------------------------------------------
   //!                                 КОМАНДЫ
   enum    {
               ID_CMD_NEW,       //!< Создать игру
               ID_CMD_OPEN,      //!< Открыть игру
               ID_CMD_SAVE,      //!< Сохранить игру
               ID_CMD_RESET,     //!< Начать заново
               ID_CMD_ABOUT,     //!< Информация о программе
               ID_CMD_CLOSE      //!< Закрыть программу
          };

	BEGIN_UPDATE_UI_MAP(CMainDlg)
	END_UPDATE_UI_MAP()

	BEGIN_MSG_MAP(CMainDlg)
		MESSAGE_HANDLER(WM_INITDIALOG, OnInitDialog)
		MESSAGE_HANDLER(WM_DESTROY, OnDestroy)

      NOTIFY_CODE_HANDLER(TTN_GETDISPINFO, OnToolTipInfo)

      COMMAND_ID_HANDLER(ID_CMD_NEW, OnNew)
      COMMAND_ID_HANDLER(ID_CMD_OPEN, OnOpen)
      COMMAND_ID_HANDLER(ID_CMD_SAVE, OnSave)
      COMMAND_ID_HANDLER(ID_CMD_RESET, OnReset)
      COMMAND_ID_HANDLER(ID_CMD_ABOUT, OnAppAbout)
		COMMAND_ID_HANDLER(ID_CMD_CLOSE, OnCancel)
      COMMAND_HANDLER(IDC_BUTTON_HOLE1, BN_CLICKED, OnBnClickedButtonHole1)
      COMMAND_HANDLER(IDC_BUTTON_HOLE2, BN_CLICKED, OnBnClickedButtonHole2)
      COMMAND_HANDLER(IDC_BUTTON_HOLE3, BN_CLICKED, OnBnClickedButtonHole3)
      COMMAND_HANDLER(IDC_BUTTON_HOLE4, BN_CLICKED, OnBnClickedButtonHole4)
      COMMAND_HANDLER(IDC_BUTTON_HOLE5, BN_CLICKED, OnBnClickedButtonHole5)
      COMMAND_HANDLER(IDC_BUTTON_HOLE6, BN_CLICKED, OnBnClickedButtonHole6)
   END_MSG_MAP()

// Handler prototypes (uncomment arguments if needed):
//	LRESULT MessageHandler(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
//	LRESULT CommandHandler(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
//	LRESULT NotifyHandler(int /*idCtrl*/, LPNMHDR /*pnmh*/, BOOL& /*bHandled*/)

	LRESULT OnInitDialog(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/);
	LRESULT OnDestroy(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/);

   LRESULT OnToolTipInfo(int idCtrl, LPNMHDR pnmh, BOOL& bHandled);
   
   LRESULT OnNew(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnOpen(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnSave(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnReset(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
	LRESULT OnAppAbout(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
	LRESULT OnCancel(WORD /*wNotifyCode*/, WORD wID, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
	
   void CloseDialog(int nVal);

private:
   enum { TBAR_BITMAPS_NUM = 6, //!< Количество картинок для кнопок
          TBAR_BTN_NUM = 6      //!< Количество кнопок тулбара
        };
   static TBBUTTON tbButtons[TBAR_BTN_NUM]; //!< Кнопки тулбара
   static LPTSTR  tbToolTips[TBAR_BTN_NUM]; //!< Тултипы для кнопок
   //-------------------------------------------------------------------------
   CImageList    m_images;         //!< Список картинок для тулбара
   CToolBarCtrl    m_tbar;         //!< Тулбар

public:
   LRESULT OnBnClickedButtonHole1(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole2(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole3(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole4(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole5(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole6(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);

   //! Функция для деактивации кнопок
   //! act == TRUE активировать 
   //! act == FALSE деактивировать
   void ActivateButtons(bool act = true);
   //! Функция для деактивации элемента меню СОХРАНИТЬ
   void ActivateSave(bool act = true);
   //! Функция для деактивации элемента меню ОТКРЫТЬ
   void ActivateOpen(bool act = true);
   //! Функция для деактивации элемента меню ПЕРЕЗАПУСТИТЬ
   void ActivateReset(bool act = true);
   //! Функция для деактивации элемента меню ВЫХОД
   void ActivateClose(bool act = true);
   //! Функция для деактивации элемента меню СОЗДАТЬ ИГРУ
   void ActivateNew(bool act = true);
   
   void setTip(std::string tip) ;//!< Функция устанавливающая подсказку
};
}
// ==========================================================================
#endif // __MAINDLG_H