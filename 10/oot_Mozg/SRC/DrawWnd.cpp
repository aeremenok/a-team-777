// **************************************************************************
//! \file   DrawWnd.cpp
//! \brief  Îêíî ðèñîâàíèÿ
//! \author Lysenko A.A.
//! \date   20.May.2008 - 20.May.2008
// **************************************************************************

#include "stdafx.h"
#include "DrawWnd.h"

using namespace Game;

// ==========================================================================
LRESULT CDrawWnd::OnEraseBackground(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
{
   // ÈÇÁÀÂÈÒÜÑß ÎÒ ÌÈÃÀÍÈß ÏÐÈ ÏÅÐÅÐÈÑÎÂÊÅ
   return 1;
}

// ==========================================================================
LRESULT CDrawWnd::OnPaint(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
{
   CPaintDC dc(m_hWnd); // device context for painting

   Controller::Instance()->Draw(dc);

   return 0;
}

// ==========================================================================
