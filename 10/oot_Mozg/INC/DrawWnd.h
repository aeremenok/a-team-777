// **************************************************************************
//! \file   DrawWnd.h
//! \brief  ���� ���������
//! \author Lysenko A.A.
//! \date   20.May.2008 - 20.May.2008
// **************************************************************************

#ifndef __DRAWWND_H
#define __DRAWWND_H

// ==========================================================================
namespace Game
{
   // =======================================================================
   // ���� ��� ����������� ������� ���������
   class CDrawWnd : public CWindowImpl<CDrawWnd>
   {
   public:
      DECLARE_WND_CLASS(NULL)

      BOOL PreTranslateMessage(MSG* pMsg)
      {
         pMsg;
         return FALSE;
      }

      BEGIN_MSG_MAP(CDrawWnd)
         MESSAGE_HANDLER(WM_ERASEBKGND, OnEraseBackground)
         MESSAGE_HANDLER(WM_PAINT, OnPaint)
      END_MSG_MAP()

      // Handler prototypes (uncomment arguments if needed):
      //	LRESULT MessageHandler(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
      //	LRESULT CommandHandler(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
      //	LRESULT NotifyHandler(int /*idCtrl*/, LPNMHDR /*pnmh*/, BOOL& /*bHandled*/)
      
      LRESULT OnEraseBackground(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/);
      LRESULT OnPaint(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/);
   };

} // endof namespace Game
// ==========================================================================
#endif // __DRAWWND_H