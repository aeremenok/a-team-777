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
   //!                                 �������
   enum    {
               ID_CMD_NEW,       //!< ������� ����
               ID_CMD_OPEN,      //!< ������� ����
               ID_CMD_SAVE,      //!< ��������� ����
               ID_CMD_RESET,     //!< ������ ������
               ID_CMD_ABOUT,     //!< ���������� � ���������
               ID_CMD_CLOSE      //!< ������� ���������
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
   enum { TBAR_BITMAPS_NUM = 6, //!< ���������� �������� ��� ������
          TBAR_BTN_NUM = 6      //!< ���������� ������ �������
        };
   static TBBUTTON tbButtons[TBAR_BTN_NUM]; //!< ������ �������
   static LPTSTR  tbToolTips[TBAR_BTN_NUM]; //!< ������� ��� ������
   //-------------------------------------------------------------------------
   CImageList    m_images;         //!< ������ �������� ��� �������
   CToolBarCtrl    m_tbar;         //!< ������

public:
   LRESULT OnBnClickedButtonHole1(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole2(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole3(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole4(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole5(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);
   LRESULT OnBnClickedButtonHole6(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/);

   //! ������� ��� ����������� ������
   //! act == TRUE ������������ 
   //! act == FALSE ��������������
   void ActivateButtons(bool act = true);
   //! ������� ��� ����������� �������� ���� ���������
   void ActivateSave(bool act = true);
   //! ������� ��� ����������� �������� ���� �������
   void ActivateOpen(bool act = true);
   //! ������� ��� ����������� �������� ���� �������������
   void ActivateReset(bool act = true);
   //! ������� ��� ����������� �������� ���� �����
   void ActivateClose(bool act = true);
   //! ������� ��� ����������� �������� ���� ������� ����
   void ActivateNew(bool act = true);
   
   void setTip(std::string tip) ;//!< ������� ��������������� ���������
};
}
// ==========================================================================
#endif // __MAINDLG_H